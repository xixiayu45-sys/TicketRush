package com.ticketrush.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ticketrush.entity.Event;
import com.ticketrush.mapper.EventMapper;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EventMapper eventMapper;
    private final List<Event> fixtures = new ArrayList<>();

    @BeforeEach
    void setUp() {
        eventMapper.delete(new QueryWrapper<>());
        fixtures.clear();
        for (int i = 1; i <= 5; i++) {
            Event event = new Event();
            event.setName("测试 " + i);
            event.setDescription("测试数据");
            event.setTotalStock(100);
            event.setAvailableStock(100);
            event.setStartTime(LocalDateTime.of(2026, 10, 5, 0, 0, 0));
            event.setEndTime(LocalDateTime.of(2026, 12, 9, 0, 0, 0));
            event.setCreatedAt(LocalDateTime.now());
            event.setUpdatedAt(LocalDateTime.now());
            eventMapper.insert(event);
            fixtures.add(event);
        }

    }

    @Test
    void listShouldReturnFirstPage() throws Exception {
        mockMvc.perform(get("/api/events").param("page", "1").param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(2))
                .andExpect(jsonPath("$.data.total").value(5))
                .andExpect(jsonPath("$.data.pages").value(3));
    }

    @Test
    void getByIdShouldReturn404WhenEventNotFound() throws Exception {
        mockMvc.perform(get("/api/events/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("活动不存在"));
    }

    @Test
    void getByIdShouldReturn400WhenIdIsNotANumber() throws Exception {
        mockMvc.perform(get("/api/events/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void createShouldReturnNewEvent() throws Exception {
        String body = """
                {
                  "name": "新建活动",
                  "description": "来自自动化测试",
                  "totalStock": 10,
                  "availableStock": 10,
                  "startTime": "2026-12-05T19:00:00",
                  "endTime": "2026-12-05T21:00:00"
                }
                """;
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("新建活动"))
                .andExpect(jsonPath("$.data.id").isNumber());
    }

    @Test
    void createShouldReturn400WhenStockIsZero() throws Exception {
        String body = """
                {
                  "name": "非法活动",
                  "totalStock": 0,
                  "availableStock": 0,
                  "startTime": "2026-12-05T19:00:00",
                  "endTime": "2026-12-05T21:00:00"
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void updateShouldChangeEvent() throws Exception {
        Long id = fixtures.get(0).getId();

        String body = """
                {
                  "name": "改过的活动",
                  "description": "更新测试",
                  "totalStock": 50,
                  "availableStock": 50,
                  "startTime": "2026-12-01T19:00:00",
                  "endTime": "2026-12-01T21:00:00"
                }
                """;

        mockMvc.perform(put("/api/events/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("改过的活动"))
                .andExpect(jsonPath("$.data.totalStock").value(50))
                .andExpect(jsonPath("$.data.createdAt").exists());
    }

    @Test
    void deleteShouldRemoveEvent() throws Exception {
        Long id = fixtures.get(1).getId();

        mockMvc.perform(delete("/api/events/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/events/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404));
    }
}
