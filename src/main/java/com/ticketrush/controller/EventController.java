package com.ticketrush.controller;

import com.ticketrush.common.Result;
import com.ticketrush.dto.CreateEventRequest;
import com.ticketrush.entity.Event;
import com.ticketrush.service.EventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/api/events")
    public Result<List<Event>> listEvent(){
        List<Event> events = eventService.listEvents();
        return Result.success(events);
    }

    @GetMapping("/api/events/{id}")
    public Result<Event> getEventById(@PathVariable Long id){
        Event event = eventService.getEventById(id);
        return Result.success(event);
    }

    @PostMapping("/api/events")
    public Result<Event> createEvent(@Valid @RequestBody CreateEventRequest request){
        Event savedEvent = eventService.createEvent(request);
        return Result.success(savedEvent);
    }
}
