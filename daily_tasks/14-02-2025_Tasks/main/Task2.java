package com.anurag.techM.Junit_Testing_Task;

public class Task2 {
	public void checkNegative(int number) {
		if(number < 0) throw new IllegalArgumentException("Number must not be negative");
	}
}
