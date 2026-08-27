package com.ticketrush.service.impl;

import com.ticketrush.dto.CreateEventRequest;
import com.ticketrush.entity.Event;
import com.ticketrush.exception.BusinessException;
import com.ticketrush.mapper.EventMapper;
import com.ticketrush.service.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    public EventServiceImpl(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    @Override
    public List<Event> listEvents(){
        return eventMapper.selectList(null);
    }

    @Override
    public Event createEvent(CreateEventRequest request){
        if (request.getAvailableStock() > request.getTotalStock()) {
            throw new BusinessException(400,"剩余库存不能大于总库存");
        }
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new BusinessException(400,"结束时间必须晚于开始时间");
        }
        LocalDateTime now = LocalDateTime.now();

        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setTotalStock(request.getTotalStock());
        event.setAvailableStock(request.getAvailableStock());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setCreatedAt(now);
        event.setUpdatedAt(now);

        eventMapper.insert(event);
        return event;
    }
    @Override
    public Event getEventById(Long id){
        Event event = eventMapper.selectById(id);

        if (event == null) {
            throw new BusinessException(404,"活动不存在");
        }
        return event;
    }
}
