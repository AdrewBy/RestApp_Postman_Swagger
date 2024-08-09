package org.ustsinau.chapter2_4.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;


@MultipartConfig //для сохранения файла
@WebServlet("/api/v1/files")
@Tag(name = "Files", description = "Operations related to files")
public class FileController extends HttpServlet {

    private final FileService fileService;

    public FileController() {
        // Инициализация всех зависимостей
        UserService userService = new UserServiceImpl();
        EventService eventService = new EventServiceImpl();
        FileRepository fileRepository = new FileRepositoryImpl();
        this.fileService = new FileServiceImpl(userService, eventService, fileRepository);
    }

    private final Gson GSON = new Gson();




    @Operation(summary = "Get a file by ID or all files", description = "Fetch a file by its ID or retrieve a list of all files.")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = File.class)))
    @ApiResponse(responseCode = "404", description = "File not found")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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


    @Operation(summary = "Upload a new file", description = "Upload a new file associated with a user.")
    @ApiResponse(responseCode = "200", description = "File uploaded successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = File.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @Parameters({
            @Parameter(name = "user_id", in = ParameterIn.QUERY, description = "ID of the user", required = true, schema = @Schema(type = "integer"))
    })
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Получение userId из параметров запроса
        String userIdParam = request.getHeader("user_id");
        if (userIdParam == null || userIdParam.isEmpty()) {
            throw new NumberFormatException("user_id header is missing or empty");
        }
        Integer userId = Integer.valueOf(userIdParam);

        // Получение inputStream для загрузки файла
        InputStream inputStream = request.getInputStream();

        // Загрузка файла
        File file = fileService.uploadFile(inputStream, userId);

        out.print(GSON.toJson(file));

    }


    @Operation(summary = "Update an existing file", description = "Update details of an existing file.")
    @ApiResponse(responseCode = "200", description = "File updated successfully")
    @ApiResponse(responseCode = "404", description = "File not found")
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        File file = GSON.fromJson(request.getReader(), File.class);
        fileService.update(file);

        PrintWriter out = response.getWriter();
        out.print("Update file ...");
        out.flush();
    }


    @Operation(summary = "Delete a file", description = "Delete a file by its ID.")
    @ApiResponse(responseCode = "200", description = "File deleted successfully")
    @ApiResponse(responseCode = "404", description = "File not found")
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        File file = GSON.fromJson(request.getReader(), File.class);
        fileService.deleteById(file.getId());

        PrintWriter out = response.getWriter();
        out.print("Delete file ...");
        out.flush();
    }
}
