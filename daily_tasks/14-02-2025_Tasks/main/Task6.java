package com.anurag.techM.Junit_Testing_Task;

public class Task6 {
	public int divide(int num1,int num2) {
		if(num2 == 0) throw new ArithmeticException("Cannot divide by Zero");
		return num1 / num2;
	}
}
