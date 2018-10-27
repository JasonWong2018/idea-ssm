package example;

import com.ssm.user.domain.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * java8 Lambda练习
 */
public class LambdaDemo {

    @Test
    public void oldRunable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("The old runable now is using!");
                System.out.println("The old runable now is using!");
            }
        }).start();
    }

    @Test
    public void runable() {
        new Thread(
                () -> {
                    System.out.println("It's a lambda function!");
                    System.out.println("It's a lambda function!");
                }
        ).start();
    }

    @Test
    public void oldForeach() {
        List<Integer> oldList = Arrays.asList(1, 2, 3, 4);
        for (Integer integer : oldList) {
            System.out.println(integer);
        }
    }

    @Test
    public void foreach() {
        List<Integer> oldList = Arrays.asList(1, 2, 3, 4);
        oldList.forEach(x -> System.out.println(x));
        oldList.forEach(x -> {
            System.out.println(x);
            System.out.println(x);
        });
    }

    @Test
    public void map() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        list.stream().map(x -> x * 2).forEach(x -> System.out.println(x));
        list.stream().map(x -> x + x * 2).forEach(x -> System.out.println(x));
    }

    @Test
    public void sumTest() {
        List<Double> cost = Arrays.asList(10.0, 20.0, 30.0);
        double sum = 0;
        for (double each : cost) {
            sum += each;
        }
        System.out.println(sum);
    }

    @Test
    public void lamSumTest(){
        List<Double> cost = Arrays.asList(10.0, 20.0, 30.0);
        Double aDouble = cost.stream().map(x -> x *2).reduce((sum, x) -> sum + x).get();
        System.out.println(aDouble);
    }

    @Test
    public void filterTest(){
        List<Double> cost = Arrays.asList(10.0, 20.0,30.0,40.0);
        List<Double> collect = cost.stream().filter(x -> x > 25).collect(Collectors.toList());

        System.out.println(collect);
        Double aDouble = cost.stream().filter(x -> x > 25).reduce((sum, x) -> sum + x).get();
        System.out.println(aDouble);
    }

    @Test
    public void comparetoTest(){
        String[] players = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};

        //Comparator<String> sortByName = (String s1, String s2) -> (s1.compareTo(s2));
        // Arrays.sort(players, sortByName);
        Arrays.sort(players,(String s1, String s2) -> (s1.compareTo(s2)));
        Arrays.asList(players).forEach( x -> System.out.println(x));


        List<Integer> list = Arrays.asList(232,12,324,21,245,2321,21,167);
        //Collections.sort(list, (h1, h2) -> h1- h2);
        //list.sort((h1 , h2) -> h1 - h2);
        list.forEach(x -> System.out.println(x));



    }
    
    @Test
    public void compareTest(){
        List<String> list = Arrays.asList("Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "JuanMartin Del Potro");
        //Collections.sort(list, String::compareToIgnoreCase);
        list.sort(String::compareTo);
        list.forEach(x -> System.out.println(x));
    }

    @Test
    public void baseTest(){
        Callable<String> jerry = helloCallable("jerry");

    }
    Callable<String> helloCallable(String name) {
        String hello = "Hello";
        return () -> (hello + ", " + name);
    }


}
