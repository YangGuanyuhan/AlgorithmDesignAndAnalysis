package Assignment.Assignment9;

import java.util.Scanner;

public class B9 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();//backpack capacity
        int m = in.nextInt();//types of items
        item9B[] items = new item9B[m];
        for (int i = 0; i < m; i++) {
            int weight = in.nextInt();
            int value = in.nextInt();
            items[i] = new item9B(weight, value);
        }
        int[] dp = new int[n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = items[i].weight; j <= n; j++) {
                dp[j] = Math.max(dp[j], dp[j - items[i].weight] + items[i].value);
            }

        }
        int[] choose = new int[m];
        int currentCapacity = n;
        while (currentCapacity > 0 && dp[currentCapacity] > 0) {
            for (int i = 0; i < m; i++) {
                if (currentCapacity - items[i].weight >= 0
                        && dp[currentCapacity] == dp[currentCapacity - items[i].weight] + items[i].value) {
                    choose[i]++;
                    currentCapacity -= items[i].weight;
                    break;
                }
            }
        }
        System.out.println(dp[n]);
        for (int i = 0; i < m; i++) {
            System.out.println(choose[i]);
        }
    }
}

class item9B {
    int weight;
    int value;

    public item9B(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}
