package com.anurag.techM.Junit_Testing_Task;

import com.anurag.techM.Junit_Testing_Task.Task1;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class Task1_Test {
	@Test
	public void testSubtract() {
		
		Task1 object = new Task1();
		assertEquals(5,object.subtract(10,5));
	}
}
