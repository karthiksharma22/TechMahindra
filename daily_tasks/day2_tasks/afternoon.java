package day2;
import java.util.Scanner;

class Employee{
	int empid;
	String empName;
	double sal;
	Scanner scan = new Scanner(System.in);
	
	Employee(String name, int id,double sal){
		empName = name;
		empid = id;
		this.sal = sal;
	}
	
	void getEmployeeDetails() {
		System.out.printf("Name of the Employee : %s \n", empName);
		System.out.printf("ID of the Employee : %d \n", empid);
		System.out.printf("Salary of the Employee : %f \n", sal);
	}
	
	void setEmployeeDetails(String name, int id,double sal) {
		empName = name;
		empid = id;
		this.sal = sal;
	}
	
	int getLoanEligibility(int exp) {
		if(exp>5) {
			if(sal==600000) return 200000;
			else if(sal>=1000000) return 500000;
			else if(sal>=150000) return 700000;
		}
		return 0;	
	}	
}

public class afternoon {
	public static void main(String args[]) {
		Employee emp = new Employee("Nandu",36,705000);
		emp.getEmployeeDetails();
		System.out.println("The loan this employee gets is "+emp.getLoanEligibility(6));
	}
}
