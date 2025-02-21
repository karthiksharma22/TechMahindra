package com.anurag.techM.Junit_Testing_Task;

import com.anurag.techM.Junit_Testing_Task.Task8;
import org.junit.Test;
import static org.junit.Assert.*;

public class Task8_Test {
	private final Task8 object = new Task8();
	
	@Test
	public void testPrivateSubtract() {
		assertEquals(5,object.callSubtract(10, 5));
	}
}
