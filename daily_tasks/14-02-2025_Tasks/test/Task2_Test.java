package com.anurag.techM.Junit_Testing_Task;

import org.junit.Test;
import static org.junit.Assert.*;
import com.anurag.techM.Junit_Testing_Task.Task2;

public class Task2_Test {
	
	@Test
	public void assertNegativeCheck() {
		
		Task2 object = new Task2();
		try {
            object.checkNegative(-1);
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Number must not be negative", e.getMessage());
        }
	}
}
