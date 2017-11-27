package ru.tanya_dima.chat;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * This class does much of the work of the Chat application,
 * including registering/deregistering users in the List
 */
public class ChatListener implements ChatConstants, HttpSessionListener, ServletContextListener {

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        System.out.println("ChatApp Application Destroyed");
    }
}
