package ru.tanya_dima.chat;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Using to broadcast messages to all users
 */
public class ChatService implements ChatConstants, HttpSessionListener, ServletContextListener {

    @Override
    public void sessionCreated(HttpSessionEvent e) {
        HttpSession sess = e.getSession();
        System.out.println("ChatApp User Set Up, ID=" + sess.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent e) {
        HttpSession sess = e.getSession();
        System.out.println("ChatApp User Removed, ID=" + sess.getId());
    }

    /**
     * The Chat Application is starting up
     */
    @Override
    public void contextInitialized(ServletContextEvent e) {
        ServletContext ctx = e.getServletContext();
        ctx.setAttribute(APP, new ChatApp());
        System.out.println("ChatApp Application Initialized");

        listenToMessages(e);
    }

    private void listenToMessages(ServletContextEvent sce) {
        new Thread(() -> {
            Queue<AsyncContext> users = new ConcurrentLinkedQueue<>();
            sce.getServletContext().setAttribute(COMET_USERS, users);
            Queue<Message> messages = new ConcurrentLinkedQueue<>();
            sce.getServletContext().setAttribute(COMET_MESSAGES, messages);

            Executor messageExec = Executors.newCachedThreadPool();
            Executor usersExec = Executors.newCachedThreadPool();

            while (true) {
                if (!messages.isEmpty()) {
                    Message message = messages.poll();
                    messageExec.execute(() -> {

                        while (!users.isEmpty()) {
                            AsyncContext context = users.poll();
                            usersExec.execute(() -> {
                                try {
                                    ServletResponse response = context.getResponse();
                                    setContentType(response);
                                    sendResponse(response, message.toJSON());
                                    context.complete();
                                }
                                catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        }

                    });
                }
            }
        }).start();
    }

    private void sendResponse(ServletResponse resp, JSONObject json) throws JSONException, IOException {
        JSONObject wrapper = new JSONObject();
        wrapper.put("response", json);
        resp.getWriter().write(wrapper.toString());
    }

    private void setContentType(ServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        System.out.println("ChatApp Application Destroyed");
    }
}
