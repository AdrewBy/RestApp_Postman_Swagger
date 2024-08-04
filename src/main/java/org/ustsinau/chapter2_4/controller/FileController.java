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


@MultipartConfig //для сохранения файла
@WebServlet("/api/v1/files")

public class FileController extends HttpServlet {

    private  final FileRepository fileRepository = new FileRepositoryImpl();
    private final EventService eventService = new EventServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final FileService fileService = new FileServiceImpl(userService,eventService,fileRepository);
    private final Gson GSON = new Gson();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        File file = GSON.fromJson(request.getReader(), File.class);

        PrintWriter out = response.getWriter(); // в сервлетах закр авто

        Integer id = file.getId();
        if (id == 0) {
            List<File> fileList = fileService.getAll();

            out.print(GSON.toJson(fileList));
            out.flush();
        } else {
            File f = fileService.getById(id);

            out.print(GSON.toJson(f));
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        InputStream inputStream = request.getInputStream();
        Integer userId  = Integer.valueOf(request.getHeader("user_id"));

            File file = fileService.uploadFile(inputStream,userId);

        out.print(GSON.toJson(file));

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        File file = GSON.fromJson(request.getReader(), File.class);
        fileService.update(file);

        PrintWriter out = response.getWriter();
        out.print("Update file ...");
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.setContentType("application/json");
        File file = GSON.fromJson(request.getReader(), File.class);
        fileService.deleteById(file.getId());

        PrintWriter out = response.getWriter();
        out.print("Delete file ...");
        out.flush();
    }
}
