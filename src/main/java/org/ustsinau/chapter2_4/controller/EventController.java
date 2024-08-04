package org.ustsinau.chapter2_4.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class EventController extends HttpServlet {
    private final EventService eventService = new EventServiceImpl();
    private final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Event event = GSON.fromJson(request.getReader(), Event.class);
        eventService.create(event);

        PrintWriter out = response.getWriter();
        out.print("Save event ...");
        out.flush();
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Event event = GSON.fromJson(request.getReader(), Event.class);
        eventService.update(event);

        PrintWriter out = response.getWriter();
        out.print("Update user ...");
        out.flush();
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Event event = GSON.fromJson(request.getReader(), Event.class);
        eventService.deleteById(event.getId());

        PrintWriter out = response.getWriter();
        out.print("Delete event ...");
        out.flush();
    }
}
