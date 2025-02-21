package com.anurag.techM.Junit_Testing_Task;

import com.anurag.techM.Junit_Testing_Task.Task5;
import org.junit.Test;


public class Task5_Test {
	private final Task5 object = new Task5();
	
	@Test(timeout=1100)
	public void testTimeOut() {
		object.checkTimeOut();
	}
}
