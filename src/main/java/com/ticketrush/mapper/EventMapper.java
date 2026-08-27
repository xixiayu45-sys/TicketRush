package com.ticketrush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticketrush.entity.Event;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventMapper extends BaseMapper<Event> {
    
}
