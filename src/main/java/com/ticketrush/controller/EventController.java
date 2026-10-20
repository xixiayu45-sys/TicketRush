package com.ticketrush.controller;

import com.ticketrush.common.PageResult;
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
    @PutMapping("/api/events/{id}")
    public Result<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody CreateEventRequest request){
        Event event = eventService.updateEvent(id,request);
        return Result.success(event);
    }

    @DeleteMapping("/api/events/{id}")
    public Result<Void> deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
        return Result.success(null);
    }
    @GetMapping("/api/events")
    public Result<PageResult<Event>> listEvents(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "5") int size){
        PageResult<Event> events =eventService.listEventsByPage(page,size);
        return Result.success(events);
    }
}
