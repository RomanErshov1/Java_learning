package arraylist;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args){
        MyArrayList<Integer> myArrayList = new MyArrayList<>();

        Random rnd = new Random();
        List<Integer> arrayList = Stream.generate(() -> rnd.nextInt() % 100).limit(10).collect(toList());
        myArrayList.addAll(arrayList);

        myArrayList.forEach(System.out::println);

        myArrayList.sort(Integer::compareTo);
        System.out.println("Sorted list");
        myArrayList.forEach(System.out::println);

        System.out.println(" Copy ");
        Collections.copy(myArrayList, arrayList);
        myArrayList.forEach(System.out::println);

        myArrayList.clear();
        arrayList = Stream.generate(()->rnd.nextInt() % 100).limit(1024).collect(toList());
        myArrayList.addAll(arrayList);
        myArrayList.add(10);
        myArrayList.addAll(arrayList);
        int elem1 = myArrayList.get(100);
        int elem2 = myArrayList.get(1147);
        myArrayList.remove(new Integer(elem1));
        myArrayList.remove(new Integer(elem2));
    }
}
