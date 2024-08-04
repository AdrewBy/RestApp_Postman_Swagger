package org.ustsinau.chapter2_4.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ustsinau.chapter2_4.models.User;
import org.ustsinau.chapter2_4.service.UserService;
import org.ustsinau.chapter2_4.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/v1/users")
public class UserController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    private final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        User user = GSON.fromJson(request.getReader(), User.class);
        Integer id = user.getId();
        if (id == 0) {
            List<User> userList = userService.getAll();

            out.print(GSON.toJson(userList));
            out.flush();
        } else {
            User u = userService.getById(id);

            out.print(GSON.toJson(u));
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        User user = GSON.fromJson(request.getReader(), User.class);
        userService.create(user);

        out.print("Save user ...");
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        User user = GSON.fromJson(request.getReader(), User.class);
        userService.update(user);

        PrintWriter out = response.getWriter();
        out.print("Update user ...");
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        User user = GSON.fromJson(request.getReader(), User.class);
        userService.deleteById(user.getId());

        PrintWriter out = response.getWriter();
        out.print("Delete user ...");
        out.flush();
    }
}
