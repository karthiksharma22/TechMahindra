package day2;
import java.util.Arrays;

public class task4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr1[] = {1,2,3,4,5,2,3,6,8,9};
		int arr2[] = Arrays.copyOfRange(arr1, 0, 7);
		for(int i:arr2) {
			System.out.printf("%d ", i);
		}
		
		System.out.println();
		arr2 = Arrays.copyOfRange(arr1, 3, 8);
		for(int i:arr2) {
			System.out.printf("%d ", i);
		}
		
	}

}
