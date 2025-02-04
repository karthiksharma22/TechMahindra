package day5_tasks.Generics;

import java.util.Arrays;

public class Task1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Integer[] arr1= {10,20,30,40,50};
		Integer[] arr2= {10,20,30,40,50};	
		System.out.println(checkarray(arr1,arr2));
			
		String[] arr3= {"hi","hello","everyone"};
		String[] arr4= {"hi","hello","everyone"};	
		System.out.println(checkarray(arr3,arr4));
		
		Double[] arr5= {10.1,40.2,30.3,40.4,50.5};
		Double[] arr6= {10.1,50.2,30.3,40.4,50.0};
		System.out.println(checkarray(arr5,arr6));
		
	}
	public static <T> String checkarray(T[] arr1,T[] arr2) {
		if(Arrays.equals(arr1, arr2)) {
			return  "Equals";
		}
		else {
			return  "Not Equals";
		}
		
	}

}
