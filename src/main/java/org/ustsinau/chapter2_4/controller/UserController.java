package org.ustsinau.chapter2_4.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Users", description = "Operations related to users")
public class UserController extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    private final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();



    @Operation(summary = "Get users", description = "Retrieve all users or a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user(s)"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Parameters({
            @Parameter(name = "id", in = ParameterIn.QUERY, description = "ID of the user", required = true, schema = @Schema(type = "integer"))
    })
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String userIdParam = request.getParameter("id");
        if (userIdParam == null || userIdParam.isEmpty()) {
            throw new NumberFormatException("user_id parameter is missing or empty");
        }
        Integer userId = Integer.valueOf(userIdParam);

        if (userId == 0) {
            List<User> userList = userService.getAll();

            out.print(GSON.toJson(userList));
            out.flush();
        } else {
            User u = userService.getById(userId);

            out.print(GSON.toJson(u));
            out.flush();
        }
    }


    @Operation(summary = "Create user", description = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        User user = GSON.fromJson(request.getReader(), User.class);
        userService.create(user);

        out.print("Save user ...");
        out.flush();
    }

    @Override
    @Operation(summary = "Update user", description = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        User user = GSON.fromJson(request.getReader(), User.class);

        userService.update(user);

        PrintWriter out = response.getWriter();
        out.print("Update user ...");
        out.flush();
    }


    @Operation(summary = "Delete user", description = "Delete an existing user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        User user = GSON.fromJson(request.getReader(), User.class);
        userService.deleteById(user.getId());

        PrintWriter out = response.getWriter();
        out.print("Delete user ...");
        out.flush();
    }
}
