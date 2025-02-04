package day2;
import java.lang.System;

public class task1 {
	public static void main(String args[]) {
		int arr1[] = {1,2,3,4,5};
		int arr2[] = {10,11,12,13,14};
		
		for(int i=0;i<arr2.length;i+=2) {
			System.arraycopy(arr1,i,arr2,i,1);
		}
		for(int i=0;i<arr2.length;i++) {
			System.out.printf("%d ", arr2[i]);
		}
	}
}