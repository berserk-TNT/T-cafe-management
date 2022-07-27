package hmt.controller;

import hmt.model.Users;
import hmt.service.ILoginService;
import hmt.service.LoginServiceImpl;
import hmt.utils.MySQLConnUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    ILoginService loginService;

    @Override
    public void init() throws ServletException {
        loginService = new LoginServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "login":
                showLoginPage(req, resp);
                break;
            case "logout":
                logout(req, resp);
                break;
            default:
                showLoginPage(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "login":
                doLogin(req, resp);
                break;
            default:
                doLogin(req, resp);
                break;
        }
    }

    public void showLoginPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/login/login.jsp");
        dispatcher.forward(req, resp);
    }

    public void doLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Users user = loginService.login(username, password);
            String destPage = "/cp/login/login.jsp";
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);

                Cookie u = new Cookie("userC", username);
                Cookie p = new Cookie("passC", password);
                u.setMaxAge(9999);
                p.setMaxAge(9999);
                System.out.println(u);
                System.out.println(p);
                resp.addCookie(u);
                resp.addCookie(p);
                resp.sendRedirect("/products");
            } else {
                String message = "Wrong username or password!";
                req.setAttribute("message", message);
                RequestDispatcher dispatcher = req.getRequestDispatcher(destPage);
                dispatcher.forward(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnUtils.printSQLException(e);
        }
    }

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/login/login.jsp");
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("user");

            Cookie[] cookies = req.getCookies();
            for (Cookie c : cookies) {
                if (c.getName().equals("userC")){
                    System.out.println(c.getName());
                    c.setMaxAge(0);
                    resp.addCookie(c);
                    resp.sendRedirect("/login");
                }
            }
            resp.setContentType("application/json; charset=utf-8");
        }
        dispatcher.forward(req, resp);
    }
}
