package hmt.controller;

import hmt.model.Products;
import hmt.model.Users;
import hmt.service.IUserService;
import hmt.service.UserServiceImpl;
import hmt.utils.ValidateUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {

    IUserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = "";
        Cookie[] cookies = null;

        cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("userC")) {
                name = c.getName();
            }
        }
        if (name.equals("")) {
            resp.sendRedirect("/login");
        } else {
            String action = req.getParameter("action");
            if (action == null) {
                action = "";
            }
            switch (action) {
                case "list":
                    showListPage(req, resp);
                    break;
                case "add":
                    showAddPage(req, resp);
                    break;
                case "edit":
                    showEditPage(req, resp);
                    break;
                case "delete":
                    doDelete(req, resp);
                    break;
                case "detail":
                    showDetailPage(req, resp);
                    break;
               case "search":
                    doSearchUsers(req, resp);
                    break;
                default:
                    showListPage(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "list":
                showListPage(req, resp);
            case "add":
                doAdd(req, resp);
                break;
            case "edit":
                doEdit(req, resp);
        }
    }

    public void showListPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/users/list-users.jsp");
        List<Users> userList = userService.findAllUsers();
        req.setAttribute("userList", userList);
        dispatcher.forward(req, resp);
    }

    public void showAddPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("cp/users/add-users.jsp");
        dispatcher.forward(req, resp);
    }

    public void showEditPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/users/edit-users.jsp");
        int id = Integer.parseInt(req.getParameter("id"));
        Users user = userService.findUserById(id);
        if (user != null) {
            req.setAttribute("user", user);
        }
        dispatcher.forward(req, resp);
    }

    public void showDetailPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/users/detail-users.jsp");
        int id = Integer.parseInt(req.getParameter("id"));
        Users user = userService.findUserById(id);
        if (user != null) {
            req.setAttribute("user", user);
        }
        dispatcher.forward(req, resp);
    }

    private void doAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/users/add-users.jsp");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String retypePassword = req.getParameter("retypePassword");

        Users user = new Users(username, password, name, phone, email, address);

        List<String> errors = new ArrayList<>();
        boolean isUsernameValid = ValidateUtils.isUsernameRegex(username);
        boolean isPasswordValid = ValidateUtils.isPasswordRegex(password);
        boolean isNameValid = ValidateUtils.isNameRegex(name);
        boolean isPhoneValid = ValidateUtils.isPhoneRegex(phone);
        boolean isEmailValid = ValidateUtils.isEmailRegex(email);

        if (username.length() < 6 || username.length() > 30) {
            errors.add("Username is from 6 to 30 characters!");
        } else {
            if (!isUsernameValid) {
                errors.add("Username is not valid!");
            }
        }
        if (password.length() < 6 || password.length() > 30) {
            errors.add("Password is from 6 to 30 characters!");
        } else {
            if (!isPasswordValid) {
                errors.add("Password is not valid!");
            }
        }
        if (name.length() == 0 || name.length() > 100) {
            errors.add("Name can't be empty or over than 100 letters!");
        } else {
            if (!isNameValid) {
                errors.add("Name is not valid!");
            }
        }
        if (!isPhoneValid || phone.length() != 10) {
            errors.add("Phone is not valid!");
        }
        if (!isEmailValid || email.length() == 0 || email.length() > 100) {
            errors.add("Email is not valid!");
        }
        if (address.length() == 0 || address.length() > 255) {
            errors.add("Address can't be empty or too long!");
        }
        if (!retypePassword.equals(password)) {
            errors.add("Retype password is not correct!");
        }

        if (errors.size() == 0) {
            Map<String, String> result = userService.add(user);
            String isSuccess = result.get("isSuccess");
            String message = result.get("message");

            if (isSuccess.equals("true")) {
                req.setAttribute("isSuccess", true);
                req.setAttribute("message", message);
            } else {
                errors.add(message);
                req.setAttribute("isError", true);
            }
        } else {
            req.setAttribute("errors", errors);
        }
        req.setAttribute("user", user);
        dispatcher.forward(req, resp);
    }

    public void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/users/edit-users.jsp");
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");

        Users user = new Users(id, name, phone, email, address);

        List<String> errors = new ArrayList<>();
        boolean isNameValid = ValidateUtils.isNameRegex(name);
        boolean isPhoneValid = ValidateUtils.isPhoneRegex(phone);
        boolean isEmailValid = ValidateUtils.isEmailRegex(email);

        if (name.length() == 0 || name.length() > 100) {
            errors.add("Name can't be empty or over than 100 letters!");
        } else {
            if (!isNameValid) {
                errors.add("Name is not valid!");
            }
        }
        if (!isPhoneValid || phone.length() != 10) {
            errors.add("Phone is not valid!");
        }
        if (!isEmailValid) {
            errors.add("Email is not valid!");
        }
        if (address.length() == 0 ) {
            errors.add("Address can't be empty!");
        }
        if (address.length() > 255) {
            errors.add("Address is too long!");
        }


        if (errors.size() == 0) {
            Map<String, String> result = userService.edit(user);
            String isSuccess = result.get("isSuccess");
            String message = result.get("message");

            if (isSuccess.equals("true")) {
                req.setAttribute("isSuccess", true);
            } else {
                errors.add(message);
                req.setAttribute("isError", true);
            }
        } else {
            req.setAttribute("errors", errors);
        }
        dispatcher.forward(req, resp);
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/users/list-users.jsp");
        int id = Integer.parseInt(req.getParameter("id"));

        boolean isSuccess = userService.delete(id);
        if (isSuccess) {
            req.setAttribute("isSuccess", true);
        } else {
            req.setAttribute("isError", true);
        }

        List<Users> userList = userService.findAllUsers();
        req.setAttribute("userList", userList);

        dispatcher.forward(req, resp);
    }

    public void doSearchUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = "";
        if (req.getParameter("key") != null) {
            key = req.getParameter("key");
        }
        List<Users> userList = userService.searchUsers(key);
        req.setAttribute("userList", userList);
        req.setAttribute("key", key);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/cp/users/list-users.jsp");
        dispatcher.forward(req, resp);
    }
}
