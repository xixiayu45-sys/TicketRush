package com.ticketrush.common;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageResultTest {
    @Test
    void pagesShouldRoundUp(){
        PageResult<String> pageResult = PageResult.of(List.of("a","b"),5,1,2);
        assertEquals(3, pageResult.getPages());
    }
    @Test
    void pagesShouldBeZeroWhenTotalIsZero(){
        PageResult<String> result = PageResult.of(List.of(),0,1,5);
        assertEquals(0, result.getPages());
    }
}
