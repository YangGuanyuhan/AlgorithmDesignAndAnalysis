package Assignment.Assignment9;


import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class A9 {
    static int ans = Integer.MAX_VALUE;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int l = in.nextInt();//position l
        int ma = in.nextInt();//length for jump
        int xa = in.nextInt();//max times jump
        int ca = in.nextInt();//cost
        int mb = in.nextInt();
        int xb = in.nextInt();
        int cb = in.nextInt();
        int[] stonePosition = new int[n];
        for (int i = 0; i < n; i++) {
            stonePosition[i] = in.nextInt();
        }
        int[][] opt = new int[xa + 1][xb + 1];

        for (int i = 0; i <= xa; i++) {
            for (int j = 0; j < xb; j++) {
                opt[i][j] = Integer.MAX_VALUE;

            }
        }


        opt[0][0] = 0;

        Set<Integer> valid = new HashSet<>();//record valid position in jump
        valid.add(0);
        valid.add(l);
        for (int i = 0; i < n; i++) {
            valid.add(stonePosition[i]);
        }


        int pos = 0;
        for (int i = 0; i <= xa; i++) {
            for (int j = 0; j < xb; j++) {
                pos = i * ma + j * mb;
                if (pos==l) {
                    ans = Math.min(ans, opt[i][j]);
                    continue;
                }
                if (pos > l || !valid.contains(pos)) {
                    continue;
                }
                if (opt[i][j] == Integer.MAX_VALUE) continue;

                if (i < xa && pos + ma <= l && valid.contains(pos + ma)) {
                    opt[i + 1][j] = Math.min(opt[i + 1][j], opt[i][j] + ca);
                }

                if (j < xb && pos + mb <= l && valid.contains(pos + mb)) {
                    opt[i][j + 1] = Math.min(opt[i][j + 1], opt[i][j] + cb);

                }
            }
        }




        if (ans == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }


    }
}

