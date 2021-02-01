package com.company;

import java.io.*;
import java.util.*;

public class Main {
    static Random rand = new Random();
    static int setSize = 0;
    static String target = "";
    static List<String> expressions = new ArrayList<>();
    static Map<String,Integer> map = new HashMap<>(); /* maps variable names to random integers */

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File ("/Users/bilaalahmad/IdeaProjects/EquivalentExpression/src/com/company/input.txt");
        Scanner sc = new Scanner(file);
        setSize = Integer.parseInt(sc.nextLine());
        target = sc.nextLine();

        while (sc.hasNextLine()) {
            expressions.add(sc.nextLine());
        }

        sc.close();

        int targetVal = evaluator(target);

        for (String exp : expressions) {
            int val = evaluator(exp);
            if (val == targetVal) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }

        }
    }

    public static int evaluator(String input) {

        String[] parts = input.split(" ");

        for (int i = 0; i < parts.length - 1; i++) {
            if (parts[i].equals("+") || parts[i].equals("-")) {
                // get variable before and after, assign value and put in map
                String var1 = parts[i - 1];
                String var2 = parts[i + 1];

                if (!map.containsKey(var1)) {
                    map.put(var1, rand.nextInt(10) + 1);
                }
                if (!map.containsKey(var2)) {
                    map.put(var2, rand.nextInt(10) + 1);
                }

                int result = 0;
                if (parts[i].equals("+")) {
                    result = addition(map.get(var1), map.get(var2));
                } else if (parts[i].equals("-")) {
                    result = subtraction(map.get(var1), map.get(var2));
                }
                String resultStr = Integer.toString(result);;
                map.put(resultStr, result);

                parts[i - 1] = resultStr;
                parts[i] = resultStr;
                parts[i + 1] = resultStr;

                i++;
            }
        }

        return Integer.parseInt(parts[parts.length - 1]);
    }

    public static int addition(int first, int second) {
        return first + second;
    }

    public static int subtraction(int first, int second) {
        return first - second;
    }
}
