package com.ticketrush.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    private Long id;
    private String name;
    private String description;
    private Integer totalStock;
    private Integer availableStock;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
