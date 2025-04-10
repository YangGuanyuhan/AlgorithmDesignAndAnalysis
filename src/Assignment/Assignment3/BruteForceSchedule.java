package Assignment.Assignment3;

import java.util.*;

public class BruteForceSchedule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // number of tasks
        int m = sc.nextInt(); // number of time slots

        int[][] tasks = new int[n][2];
        int[][] slots = new int[m][2];

        // Read tasks
        for (int i = 0; i < n; i++) {
            tasks[i][0] = sc.nextInt(); // li
            tasks[i][1] = sc.nextInt(); // ri
        }

        // Read time slots
        for (int j = 0; j < m; j++) {
            slots[j][0] = sc.nextInt(); // sj
            slots[j][1] = sc.nextInt(); // ej
        }

        int count = 0;

        // For each task, check if it can fit in any time slot
        for (int i = 0; i < n; i++) {
            int l = tasks[i][0];
            int r = tasks[i][1];
            for (int j = 0; j < m; j++) {
                int s = slots[j][0];
                int e = slots[j][1];
                if (l >= s && r <= e) {
                    count++;
                    break; // schedule task only once
                }
            }
        }

        System.out.println(count);
    }
}

