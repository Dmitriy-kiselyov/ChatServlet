package ru.tanya_dima.chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Queue;

public class ChatServlet extends HttpServlet implements ChatConstants {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setContentType(resp);

        String action = req.getParameter(API_ACTION);
        if (action.equals(API_REGISTER)) {
            register(req, resp);
            return;
        }

        if (!checkAuthentication(req, resp))
            return;

        if (action.equals(API_POST_MESSAGE)) {
            postMessage(req, resp);
            return;
        }

        sendError(resp, ERR_UNKNOWN_PARAM, "Неизвестный запрос");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setContentType(resp);

        String action = req.getParameter(API_ACTION);

        if (!checkAuthentication(req, resp))
            return;

        if (action.equals(API_MESSAGES)) {
            getMessages(req, resp);
            return;
        }

        if (action.equals(API_MESSAGE_COMET)) {
            cometMessage(req, resp);
            return;
        }

        sendError(resp, ERR_UNKNOWN_PARAM, "Неизвестный запрос");
    }

    private void setContentType(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private boolean checkAuthentication(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute(LOGIN) == null) {
            sendError(resp, ERR_AUTH_FAIL, "Для данной операции требуется авторизация");
            return false;
        }

        return true;
    }

    private void getMessages(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChatApp app = (ChatApp) getServletContext().getAttribute(APP);
        try {
            sendResponse(resp, app.getJSONMessages());
        }
        catch (JSONException e) {
            sendUnknownError(resp);
        }
    }

    private void cometMessage(HttpServletRequest req, HttpServletResponse resp) {
        AsyncContext context = req.startAsync(req, resp);
        ServletContext servletContext = req.getServletContext();
        Queue<AsyncContext> users = (Queue<AsyncContext>) servletContext.getAttribute(COMET_USERS);
        users.add(context);
    }

    private void postMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ChatApp app = (ChatApp) getServletContext().getAttribute(APP);
        HttpSession sess = req.getSession();

        User sender = app.getUser((String) sess.getAttribute(LOGIN));
        String text = req.getParameter("message");
        Message message = new Message(sender, text);
        app.addMessage(message);
        broadcastMessage(req, resp, message);

        try {
            sendResponse(resp, message.toJSON());
        }
        catch (JSONException e) {
            sendUnknownError(resp);
        }
    }

    private void broadcastMessage(HttpServletRequest req, HttpServletResponse resp, Message message) throws IOException {
        AsyncContext context = req.startAsync(req, resp);
        ServletContext servletContext = req.getServletContext();
        Queue<Message> messages = (Queue<Message>) servletContext.getAttribute(COMET_MESSAGES);
        messages.add(message);
        context.complete();
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChatApp app = (ChatApp) getServletContext().getAttribute(APP);
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login == null || password == null) {
            sendError(resp, ERR_NO_PARAMS, "Нет необходимых параметров");
            return;
        }
        User user = new User(login, password);

        JSONObject json = new JSONObject();
        try {
            app.registerUser(user);
            session.setAttribute(LOGIN, user.getLogin());
            json.put("url", "chat.jsp");
            sendResponse(resp, json);
        }
        catch (DatabaseException e) {
            sendError(resp, ERR_LOGIN_FAIL, e.getMessage());
        }
        catch (JSONException e) {
            sendUnknownError(resp);
        }
    }

    private void sendError(HttpServletResponse resp, int code, String message) throws IOException {
        try {
            JSONObject json = new JSONObject();
            json.put("error_code", code);
            json.put("error_msg", message);
            JSONObject error = new JSONObject();
            error.put("error", json);

            resp.getWriter().write(error.toString());
        }
        catch (JSONException e) {
            sendUnknownError(resp);
        }
    }

    private void sendUnknownError(HttpServletResponse resp) throws IOException {
        //Never happens
        resp.getWriter().write("{\"error\": {\"error_code\": " + ERR_DEFAULT + "}}");
    }

    private void sendResponse(HttpServletResponse resp, JSONObject json) throws JSONException, IOException {
        JSONObject wrapper = new JSONObject();
        wrapper.put("response", json);
        resp.getWriter().write(wrapper.toString());
    }

    private void sendResponse(HttpServletResponse resp, JSONArray json) throws JSONException, IOException {
        JSONObject wrapper = new JSONObject();
        wrapper.put("response", json);
        resp.getWriter().write(wrapper.toString());
    }

}
