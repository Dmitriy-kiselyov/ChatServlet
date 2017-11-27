package ru.tanya_dima.chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChatServlet extends HttpServlet implements ChatConstants {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setContentType(resp);

        String action = req.getParameter(API_ACTION);
        if (action.equals(API_REGISTER)) {
            register(req, resp);
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

        if (action.equals(API_MESSAGES))
            getMessages(req, resp);

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

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            RegisterDatabase.register(user);
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
        resp.getWriter().write("{\"error\": {\"error_code\": 100}}");
    }

    private void sendResponse(HttpServletResponse resp, JSONObject json) throws JSONException, IOException {
        JSONObject wrapper = new JSONObject();
        wrapper.put("response", json);
        resp.getWriter().write(wrapper.toString());
    }

    private void sendResponse(HttpServletResponse resp, JSONArray
            json) throws JSONException, IOException {
        JSONObject wrapper = new JSONObject();
        wrapper.put("response", json);
        resp.getWriter().write(wrapper.toString());
    }

}
