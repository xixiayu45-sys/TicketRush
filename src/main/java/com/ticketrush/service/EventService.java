package com.ticketrush.service;

import com.ticketrush.dto.CreateEventRequest;
import com.ticketrush.entity.Event;

import java.util.List;

public interface EventService {
        List<Event> listEvents();
        Event createEvent(CreateEventRequest request);
        Event getEventById(Long id);
}
