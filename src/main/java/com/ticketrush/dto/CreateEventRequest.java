package com.ticketrush.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventRequest {
    @NotBlank(message = "活动名称不能为空")
    private String name;
    private String description;

    @NotNull(message = "总库存不能为空")
    @Min(value = 1,message = "总库存必须大于0")
    private Integer totalStock;

    @NotNull(message = "剩余库存不能为空")
    @Min(value = 0,message = "剩余库存不能小于0")
    private Integer availableStock;

    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;
}
