package com.ticketrush.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticketrush.entity.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventMapper extends BaseMapper<Event> {
    @Select("Select * from events order by id limit #{offset}, #{size}")
    List<Event> selectPageList(@Param("offset") long offset, @Param("size") long size);
}
