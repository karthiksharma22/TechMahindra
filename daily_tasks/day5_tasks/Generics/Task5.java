package day5_tasks.Generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task5 {

    public static void main(String[] args) {
        
        List<Integer> list1 = Arrays.asList(10, 20, 30, 40, 50);
        List<Integer> list2 = Arrays.asList(15, 25, 35, 45, 55);
        List<Integer> mergedIntegerList = mergeLists(list1, list2);
        System.out.println("Merged Integer List: " + mergedIntegerList);

        
        List<String> list3 = Arrays.asList("hi", "hello", "everyone");
        List<String> list4 = Arrays.asList("welcome", "to", "Java");
        List<String> mergedStringList = mergeLists(list3, list4);
        System.out.println("Merged String List: " + mergedStringList);

 
        List<Double> list5 = Arrays.asList(10.1, 40.2, 30.3, 40.4, 50.5);
        List<Double> list6 = Arrays.asList(10.1, 50.2, 30.3, 40.4, 50.0);
        List<Double> mergedDoubleList = mergeLists(list5, list6);
        System.out.println("Merged Double List: " + mergedDoubleList);
    }

    
    public static <T> List<T> mergeLists(List<T> list1, List<T> list2) {
        List<T> mergedList = new ArrayList<>();
        int maxSize = Math.max(list1.size(), list2.size());
        for (int i = 0; i < maxSize; i++) {
            if (i < list1.size()) {
                mergedList.add(list1.get(i));
            }
            if (i < list2.size()) {
                mergedList.add(list2.get(i));
            }
        }

        return mergedList;
    }
}