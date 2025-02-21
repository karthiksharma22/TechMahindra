package com.anurag.techM.Junit_Testing_Task;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.anurag.techM.Junit_Testing_Task.Task10_1;
import com.anurag.techM.Junit_Testing_Task.Task10_2;

public class Task10_Test {
	 private Task10_2 transactionService;
	 private Task10_1 account;
	 
	  @Before
	   public void setup() {
	        account = new Task10_1(1000); 
	        transactionService = new Task10_2(account);
	    }

	    @Test
	    public void testDeposit() {
	        transactionService.check("deposit", 500);
	        Assert.assertEquals(1500, transactionService.getAccBalance(), 0.01);
	    }

	    @Test
	    public void testWithdraw() {
	        transactionService.check("withdraw", 300);
	        Assert.assertEquals(700, transactionService.getAccBalance(), 0.01);
	    }

	    @Test(expected = IllegalArgumentException.class)
	    public void testWithdrawInsufficientBalance() {
	        transactionService.check("withdraw", 2000);
	    }

	    @Test(expected = IllegalArgumentException.class)
	    public void testInvalidTransaction() {
	        transactionService.check("transfer", 500);
	    }
}
