package com.anurag.techM.Junit_Testing_Task;

import com.anurag.techM.Junit_Testing_Task.Task6;
import org.junit.*;
import static org.junit.Assert.*;

public class Task6_Test {
	private final Task6 object = new Task6();
	
	@Test
    public void testDivide() {
        assertEquals(2, object.divide(10, 5));
    }

    @Ignore("Since the zero division handler is not yet created this testcase will be skipped")
    @Test
    public void testDivideByZero() {
        object.divide(10, 0);
    }
}
