package org.ustsinau.chapter2_4.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ustsinau.chapter2_4.models.User;
import org.ustsinau.chapter2_4.service.UserService;
import org.ustsinau.chapter2_4.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = GSON.fromJson(request.getReader(), User.class);
        Integer id = user.getId();
        if (id == 0) {
            List<User> userList = userService.getAll();
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(userList);
            out.flush();
        } else {
            User u = userService.getById(id);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(u);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = GSON.fromJson(request.getReader(), User.class);
        userService.create(user);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("Save user ...");
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = GSON.fromJson(request.getReader(), User.class);
        userService.update(user);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("Update user ...");
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = GSON.fromJson(request.getReader(), User.class);
        userService.deleteById(user.getId());
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("Delete user ...");
        out.flush();
    }
}
