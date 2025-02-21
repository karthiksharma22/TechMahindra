package com.anurag.techM.Junit_Testing_Task;

import com.anurag.techM.Junit_Testing_Task.Task7;
import org.junit.Test;
import static org.junit.Assert.*;

public class Task7_Test {
	private final Task7 object = new Task7();
	
	@Test
    public void testSubtract() {
        int result = object.subtract(15, 3);
        assertEquals("Expected difference of 15 and 3 to be 12", 12, result);
    }
}
