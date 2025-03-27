package lab.lab1;

import java.util.*;

public class permutation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder(scanner.next());

        System.out.print("Permutations:\n");
        StringBuilder ans = new StringBuilder();
        printPermutations(input, ans);

    }

    public static void printPermutations(StringBuilder str, StringBuilder ans) {
        if (str.isEmpty()) {
            System.out.print(ans + " ");
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            StringBuilder ros = new StringBuilder(str);
            ros.deleteCharAt(i);
            StringBuilder newAns = new StringBuilder(ans);
            newAns.append(ch);
            printPermutations(ros, newAns);

        }
    }

}

