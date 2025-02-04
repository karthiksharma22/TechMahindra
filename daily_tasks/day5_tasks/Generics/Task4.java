package day5_tasks.Generics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task4 {

	public static <T> List<T> reverseList(List<T> list) {
		List<T> mutableList = new ArrayList<>(list);
        Collections.reverse(mutableList);
        return mutableList;
    }

    public static void main(String[] args) {
        
        List<String> stringList = List.of("Apple", "Banana", "Cherry", "Banana", "Date");
        List<String> reversed1 = reverseList(stringList); 
        for(int i =0;i<reversed1.size();i++) {
        	System.out.print(reversed1.get(i)+" ");
        }
      
        System.out.println();
        List<Integer> integerList = List.of(10, 20, 30, 20, 40);       
        List<Integer> reversed2 = reverseList(integerList);
        for(int i =0;i<reversed2.size();i++) {
        	System.out.print(reversed2.get(i)+" ");
        }     
    }

}
