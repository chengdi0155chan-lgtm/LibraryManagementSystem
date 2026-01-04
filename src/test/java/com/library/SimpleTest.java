package com.library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleTest {
    
    @Test
    void testSimple() {
        System.out.println("简单测试运行成功！");
        assertEquals(2, 1 + 1);
    }
}