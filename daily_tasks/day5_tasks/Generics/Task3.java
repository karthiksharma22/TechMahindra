package day5_tasks.Generics;

import java.util.List;

public class Task3 {

    
    public static <T> int findFirstOccurrence(List<T> list, T element) {  
        return list.indexOf(element);     
    }

    public static void main(String[] args) {
        
        List<String> stringList = List.of("Apple", "Banana", "Cherry", "Banana", "Date");
        String target = "Date";
        int index1 = findFirstOccurrence(stringList, target);
        System.out.println("First occurrence of '" + target + "' in stringList: " + index1);
      
        List<Integer> integerList = List.of(10, 20, 30, 20, 40);
        Integer target2 = 25;
        int index2 = findFirstOccurrence(integerList, target2);
        System.out.println("First occurrence of " + target2 + " in integerList: " + index2);
        
    }
}
