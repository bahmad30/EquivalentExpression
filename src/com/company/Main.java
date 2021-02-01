package com.company;

import java.io.*;
import java.util.*;

public class Main {
    static Random rand = new Random();
    static int setSize = 0;
    static String target = "";
    static List<String> expressions = new ArrayList<>();
    static Map<String,Double> map = new HashMap<>(); /* maps variable names to random integers */

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File ("/Users/bilaalahmad/IdeaProjects/EquivalentExpression/src/com/company/input.txt");
        Scanner sc = new Scanner(file);
        setSize = Integer.parseInt(sc.nextLine());
        target = sc.nextLine();

        while (sc.hasNextLine()) {
            expressions.add(sc.nextLine());
        }

        sc.close();

        double targetVal = evaluator(target);
        System.out.println("final target: " + targetVal);
        System.out.println();

        for (String exp : expressions) {
            double val = evaluator(exp);
            System.out.println("final exp: " + val);
            if (val == targetVal) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
            System.out.println();

        }
    }

    public static double evaluator(String input) {

        String[] parts = input.split(" ");
        List<String> partsList = new ArrayList<>(Arrays.asList(parts));
        //System.out.println("before: " + partsList.toString());
        double answer = 0;

        for (int i = 0; i < partsList.size(); i++) {
                if (partsList.get(i).equals("/") || partsList.get(i).equals("*")) {
                    // get variable before and after, assign value and put in map
                    String var1 = partsList.get(i - 1);
                    String var2 = partsList.get(i + 1);

                    if (!map.containsKey(var1)) {
                        map.put(var1, (double) rand.nextInt(100) + 1);
                    }
                    if (!map.containsKey(var2)) {
                        map.put(var2, (double) rand.nextInt(100) + 1);
                    }

                    double result = 0;
                    if (partsList.get(i).equals("/")) {
                        result = division(map.get(var1), map.get(var2));
                    } else if (partsList.get(i).equals("*")) {
                        result = multiplication(map.get(var1), map.get(var2));
                    }

                    String resultStr = Double.toString(result);

                    map.put(resultStr, result);

                    partsList.remove(i - 1);
                    partsList.remove(i - 1);
                    partsList.set(i - 1, resultStr);
                    i--;
                }

        }

        //System.out.println("after div: " + partsList.toString());

        for (int i = 0; i < partsList.size(); i++) {
            if (partsList.get(i).equals("+") || partsList.get(i).equals("-")) {
                // get variable before and after, assign value and put in map
                String var1 = partsList.get(i - 1);
                String var2 = partsList.get(i + 1);

                if (!map.containsKey(var1)) {
                    map.put(var1, (double) rand.nextInt(100) + 1);
                }
                if (!map.containsKey(var2)) {
                    map.put(var2, (double) rand.nextInt(100) + 1);
                }

                double result = 0;

                if (partsList.get(i).equals("+")) {
                    result = addition(map.get(var1), map.get(var2));
                } else if (partsList.get(i).equals("-")) {
                    result = subtraction(map.get(var1), map.get(var2));
                }

                String resultStr = Double.toString(result);

                map.put(resultStr, result);

                partsList.remove(i - 1);
                partsList.remove(i - 1);
                partsList.set(i - 1, resultStr);
                i--;
            }
        }

        //System.out.println("after: " + partsList.toString());

        return Math.round(Double.parseDouble(partsList.get(0)));
    }

    public static double addition(double first, double second) {
        return first + second;
    }

    public static double subtraction(double first, double second) {
        return first - second;
    }

    public static double multiplication (double first, double second) {
        return first * second;
    }

    public static double division (double first, double second) {
        return first / second;
    }
}
