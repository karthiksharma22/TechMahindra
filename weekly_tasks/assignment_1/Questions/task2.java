package day2;
import java.util.Scanner;
import java.util.Collections;
import java.util.ArrayList;

class Student{
	int id,numsubjects;
	String name;
	int marks[];
	double average;
	Student(int id,String name,int[] marks,double average){
		this.id = id;
		this.name=name;
		this.marks = marks;
		this.average = average;
	}
	double getaverage() {
		return average;
	}
}

public class task2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		ArrayList<Student> al = new ArrayList<Student>();
		System.out.println("Enter Number of Students and Number of subjects");
		
		int numstudents = scan.nextInt();
		int numsubjects = scan.nextInt();
		
		double sum = 0;
		for(int i=0;i<numstudents;i++) {
			String name; int id; int marks[] = new int[numsubjects];
			System.out.println("Enter student "+(i+1)+" name and id ");
		    name = scan.next();
		    id = scan.nextInt();
		    System.out.println("Enter the marks");
		    for(int j=0;j<numsubjects;j++) {
		    	marks[j] = scan.nextInt();
		    	sum += marks[j];
		    }
		    
		    Student s = new Student(id,name,marks,sum/numsubjects);
		    al.add(s);
		    sum = 0;
		}
		scan.close();
		
		Collections.sort(al,(p1,p2)->Double.compare(p1.getaverage(), p2.getaverage()));
		Collections.reverse(al);
		
		for(Student s: al) {
			System.out.printf("Name: %s, ID: %d, AVGmarks : %.3f \n", s.name,s.id,s.average);
		}
	}
	
}
