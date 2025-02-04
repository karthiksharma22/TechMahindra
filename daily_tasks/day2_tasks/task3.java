package day2;
import java.util.Arrays;

public class task3 {
	public static void main(String args[]) {
		int arr1[][] = {{1,2,3},{4,5,6}};
		int arr2[][] = {{1,2,3},{4,5,6}};
		
		//nested arrays not are not recursively checked by the .equals() method
		if(Arrays.equals(arr1, arr2)) {
			System.out.println("Equal using equals method");
		}
		else System.out.println("Not equal using the equals method");
		
		//.deepEquals() method checks each element recursively
		if(Arrays.deepEquals(arr1, arr2)) System.out.println("Equal using the deepEquals method");
		else System.out.println("Not equal using the deepEqual Method");
		
		Object[] arr3 = {1,2,3,4,5};
		Object[] arr4 = {1,2,3,4,5};
		
		if(Arrays.equals(arr3, arr4)) {
			System.out.println("Equal using equals method");
		}
		else System.out.println("Not equal using the equals method");
		
		//.deepEquals() method checks each element recursively
		if(Arrays.deepEquals(arr3, arr4)) System.out.println("Equal using the deepEquals method");
		else System.out.println("Not equal using the deepEqual Method");
	}
	
	
}
