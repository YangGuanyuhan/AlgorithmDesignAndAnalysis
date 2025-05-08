package Assignment.Assignment7;


import java.util.Scanner;

public class A7 {
    static int Max = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.next();
        int k = in.nextInt();
        StringBuilder inputString = new StringBuilder(input);
        int result = findMax(inputString, k);
        System.out.println(result);


    }

    static int findMax(StringBuilder input, int k) {
        if (input.length() < k) {
            return 0;
        }

        int[] count = new int[26];
        for (int i = 0; i < input.length(); i++) {
            count[input.charAt(i) - 'a']++;
        }
        boolean flag = true;
        for (int i = 0; i < input.length(); i++) {
            if (count[input.charAt(i) - 'a'] < k) {
                flag = false;
                break;
            }
        }

        if (flag) {
            return input.length();
        }

        //  printArray(count);

        int beforeIndex = 0;
        for (int i = 0; i < input.length(); i++) {

            if (count[input.charAt(i) - 'a'] < k) {
                int afterIndex = i;
                StringBuilder newInput = new StringBuilder();
                for (int j = beforeIndex; j < afterIndex; j++) {
                    newInput.append(input.charAt(j));
                }
                if (newInput.length() < k) {
                    beforeIndex = afterIndex + 1;
                    continue;
                } else {
                    Max = Math.max(Max, findMax(newInput, k));
                }
                beforeIndex = afterIndex + 1;
            }
            if (i == input.length() - 1) {
                StringBuilder newInput = new StringBuilder();
                for (int j = beforeIndex; j < input.length(); j++) {
                    newInput.append(input.charAt(j));
                }
                if (newInput.length() < k) {
                    beforeIndex = input.length();
                    continue;
                } else {
                    Max = Math.max(Max, findMax(newInput, k));
                }
            }
        }
        return Max;


    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
