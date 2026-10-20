package com.ticketrush.service;

import com.ticketrush.common.PageResult;
import com.ticketrush.dto.CreateEventRequest;
import com.ticketrush.entity.Event;

import java.util.List;

public interface EventService {

        Event createEvent(CreateEventRequest request);
        Event getEventById(Long id);
        Event updateEvent(Long id, CreateEventRequest request);
        void deleteEvent(Long id);
        PageResult<Event> listEventsByPage(int page, int size);
}
