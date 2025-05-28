package Assignment.Assignment10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class A10 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // capacity
        int m = in.nextInt(); // types
        ArrayList<Item10A> items = new ArrayList<>(2000);
        for (int i = 0; i < m; i++) {
            int op = in.nextInt(); // operation type
            if (op == 0) {
                int type = 0;
                int weight = in.nextInt(); // item weight
                int value = in.nextInt(); // item value
                items.add(new Item10A(type, weight, value));
            } else if (op == 1) {
                int type = 1;
                int weight = in.nextInt(); // item weight
                int value = in.nextInt(); // item value
                items.add(new Item10A(type, weight, value));
            } else {
                int x = in.nextInt();//num
                int weight = in.nextInt(); // item weight
                int value = in.nextInt(); // item value
                int a = 1;
                while (a <= x + 5) {
                    items.add(new Item10A(0, weight * a, value * a));
                    a *= 2;
                }

            }
        }
        long[] opt = new long[n + 1];
        Arrays.fill(opt, Long.MIN_VALUE);
        opt[0] = 0;
        for (int i = 0; i < items.size(); i++) {
            Item10A item = items.get(i);
            if (item.type == 0) {
                for (int j = item.weight; j <= n; j++) {
                    opt[j] = Math.max(opt[j], opt[j - item.weight] + item.value);
                }
            } else {
                for (int j = n; j >= item.weight; j--) {
                    opt[j] = Math.max(opt[j], opt[j - item.weight] + item.value);
                }
            }
        }
        System.out.println(opt[n]);

    }
}


class Item10A {
    int type;
    int weight;
    int value;

    public Item10A(int type, int weight, int value) {
        this.type = type;
        this.weight = weight;
        this.value = value;
    }
}
