package org.ustsinau.chapter2_4.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ustsinau.chapter2_4.models.File;
import org.ustsinau.chapter2_4.repository.FileRepository;
import org.ustsinau.chapter2_4.repository.impl.FileRepositoryImpl;
import org.ustsinau.chapter2_4.service.EventService;
import org.ustsinau.chapter2_4.service.FileService;
import org.ustsinau.chapter2_4.service.UserService;
import org.ustsinau.chapter2_4.service.impl.EventServiceImpl;
import org.ustsinau.chapter2_4.service.impl.FileServiceImpl;
import org.ustsinau.chapter2_4.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;


@MultipartConfig
//@WebServlet("/api/v1/files")
public class FileControllerGPT extends HttpServlet {

    private final FileRepository fileRepository = new FileRepositoryImpl();
    private final EventService eventService = new EventServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final FileService fileService = new FileServiceImpl(userService, eventService, fileRepository);
    private final Gson GSON = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            File file = null;
            try {
                file = GSON.fromJson(request.getReader(), File.class);
            } catch (JsonSyntaxException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Malformed JSON\"}");
                return;
            }

            if (file == null || file.getId() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid JSON data\"}");
                return;
            }

            Integer id = file.getId();
            if (id == 0) {
                List<File> fileList = fileService.getAll();
                out.print(GSON.toJson(fileList));
            } else {
                File f = fileService.getById(id);
                if (f == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"File not found\"}");
                } else {
                    out.print(GSON.toJson(f));
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Server error\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            InputStream inputStream = request.getInputStream();
            Integer userId;
            try {
                userId = Integer.valueOf(request.getHeader("user_id"));
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid user_id\"}");
                return;
            }

            try {
                File file = fileService.uploadFile(inputStream, userId);
                out.print(GSON.toJson(file));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"File upload failed\"}");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            File file = null;
            try {
                file = GSON.fromJson(request.getReader(), File.class);
            } catch (JsonSyntaxException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Malformed JSON\"}");
                return;
            }

            if (file == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid JSON data\"}");
                return;
            }

            fileService.update(file);
            out.print("{\"message\":\"Update successful\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Update failed\"}");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            File file = null;
            try {
                file = GSON.fromJson(request.getReader(), File.class);
            } catch (JsonSyntaxException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Malformed JSON\"}");
                return;
            }

            if (file == null || file.getId() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Invalid JSON data\"}");
                return;
            }

            try {
                fileService.deleteById(file.getId());
                out.print("{\"message\":\"Delete successful\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"error\":\"Delete failed\"}");
            }
        }
    }
}