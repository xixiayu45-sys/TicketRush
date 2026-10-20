package com.ticketrush.service.impl;

import com.ticketrush.common.PageResult;
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
    public Event createEvent(CreateEventRequest request){
        validate(request);
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

    @Override
    public Event updateEvent(Long id, CreateEventRequest request) {
        Event  event = getEventById(id);
        if (event == null) {
            throw new BusinessException(404,"活动不存在");
        }
        validate(request);
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setTotalStock(request.getTotalStock());
        event.setAvailableStock(request.getAvailableStock());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setUpdatedAt(LocalDateTime.now());

        eventMapper.updateById(event);
        return event;
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventMapper.selectById(id);
        if (event == null) {
            throw new BusinessException(404,"活动不存在");
        }
        eventMapper.deleteById(id);
    }

    @Override
    public PageResult<Event> listEventsByPage(int page, int size) {
        if(page < 1){
            page = 1;
        }
        if(size < 1){
            size = 5;
        }
        if (size>100){
            size = 100;
        }
        long offset = (long) (page-1) *size;
        Long total = eventMapper.selectCount(null);
        List<Event> records = eventMapper.selectPageList(offset,size);
        return PageResult.of(records,total,page,size);
    }

    public void validate(CreateEventRequest request){
        if (request.getAvailableStock() > request.getTotalStock()) {
            throw new BusinessException(400,"剩余库存不能大于总库存");
        }
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new BusinessException(400,"结束时间必须晚于开始时间");
        }
    }
}
