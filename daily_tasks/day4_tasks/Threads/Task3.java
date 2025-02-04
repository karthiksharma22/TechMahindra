package day4_tasks.Threads;
import java.util.Arrays;

class SortThread extends Thread {
    private int[] arr;
    public SortThread(int[] arr) { this.arr = arr; }
    public void run() { Arrays.sort(arr); }
}

public class Task3{

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		int[] arr = {5, 3, 8, 6, 2, 7, 4, 1};
		System.out.println("Before Sorted: " + Arrays.toString(arr));
        SortThread sorter = new SortThread(arr);
        sorter.start();
        sorter.join();
        System.out.println("After Sorted: " + Arrays.toString(arr));
	}

}
