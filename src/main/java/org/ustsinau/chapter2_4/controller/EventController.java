package org.ustsinau.chapter2_4.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ustsinau.chapter2_4.models.Event;
import org.ustsinau.chapter2_4.service.EventService;
import org.ustsinau.chapter2_4.service.impl.EventServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/v1/events")
@Tag(name = "Events", description = "Operations related to events")
public class EventController extends HttpServlet {
    private final EventService eventService = new EventServiceImpl();
    private final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


    @Operation(summary = "Get an event by ID or all events", description = "Fetch an event by its ID or retrieve a list of all events.")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class)))
    @ApiResponse(responseCode = "404", description = "Event not found")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        Event event = GSON.fromJson(request.getReader(), Event.class);
        Integer id = event.getId();
        if (id == 0) {
            List<Event> eventList = eventService.getAll();

            out.print(GSON.toJson(eventList));
            out.flush();
        } else {
            Event event1 = eventService.getById(id);

            out.print(GSON.toJson(event1));
            out.flush();
        }
    }


    @Operation(summary = "Create a new event", description = "Create a new event.")
    @ApiResponse(responseCode = "200", description = "Event created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Event event = GSON.fromJson(request.getReader(), Event.class);
        eventService.create(event);

        PrintWriter out = response.getWriter();
        out.print("Save event ...");
        out.flush();
    }


    @Operation(summary = "Update an existing event", description = "Update details of an existing event.")
    @ApiResponse(responseCode = "200", description = "Event updated successfully")
    @ApiResponse(responseCode = "404", description = "Event not found")
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Event event = GSON.fromJson(request.getReader(), Event.class);
        eventService.update(event);

        PrintWriter out = response.getWriter();
        out.print("Update event ...");
        out.flush();
    }


    @Operation(summary = "Delete an event", description = "Delete an event by its ID.")
    @ApiResponse(responseCode = "200", description = "Event deleted successfully")
    @ApiResponse(responseCode = "404", description = "Event not found")
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Event event = GSON.fromJson(request.getReader(), Event.class);
        eventService.deleteById(event.getId());

        PrintWriter out = response.getWriter();
        out.print("Delete event ...");
        out.flush();
    }
}
