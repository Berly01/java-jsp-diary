package com.yusufsezer.controller;

import com.yusufsezer.model.User;
import com.yusufsezer.util.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
	
	private static final String VIEW_FILE = "viewFile";
	private static final String REGISTER = "register.jsp";
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(VIEW_FILE, REGISTER);
        request.setAttribute("pageTitle", "Register");
        Helper.view(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] parameters = {"first_name", "last_name", "email", "password"};
        boolean checkResult = Helper
                .checkParameters(parameters, request.getParameterMap());

        if (!checkResult) {
            request.setAttribute(VIEW_FILE, REGISTER);
            request.setAttribute("message", "Please fill all field");
            Helper.view(request, response);
        } else {

            String fistName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User newUser = new User();
            newUser.setFirstName(fistName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPassword(Helper.md5(password));

            boolean registerResult = Helper.userRepository().add(newUser);
            if (registerResult) {
                response.sendRedirect("login");
            } else {
                request.setAttribute("message", "Something went wrong.");
                request.setAttribute(VIEW_FILE, REGISTER);
                request.setAttribute("pageTitle", "Register");
                Helper.view(request, response);
            }
        }
    }
}
