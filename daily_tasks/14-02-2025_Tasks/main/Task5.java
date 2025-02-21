package com.anurag.techM.Junit_Testing_Task;

public class Task5 {
	public void checkTimeOut() {
		try {
			Thread.sleep(1000);
		}
		catch(Exception e) {
			System.err.println(e);
		}
	}
}
