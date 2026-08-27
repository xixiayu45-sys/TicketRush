package com.ticketrush.controller;

import com.ticketrush.common.Result;
import com.ticketrush.dto.RegisterRequest;
import com.ticketrush.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Result<String> health() {
        return Result.success("TicketRush is running");
    }

    @GetMapping("/api/health/error")
    public Result<String> error() {
        throw new BusinessException(500, "测试异常");
    }

    @PostMapping("/api/health/register-test")
    public Result<String> registerTest(@Valid @RequestBody RegisterRequest request) {
        return Result.success("参数校验通过" + request.getUsername());
    }

}
