package com.task.delivery;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        double lat = 51.742503;
        double lon = 19.432956;
        if (args.length == 2) {
            lat = Double.parseDouble(args[0]);
            lon = Double.parseDouble(args[1]);
        }
        else if (args.length != 0){
            System.out.println("Wrong amount of arguments specified");
            return;
        }
        long start = System.currentTimeMillis();
        System.out.println("Hello World!");
        System.out.println(lat);
        System.out.println(lon);
        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Program took: " + elapsed + " ms");
    }

}
