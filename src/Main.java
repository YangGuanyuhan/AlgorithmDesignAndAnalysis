import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();//bunny num
        int m = in.nextInt();//nest num
        int c = in.nextInt();//capacity
        int t = in.nextInt();//time
        int result = 0;

        int[] bunny = new int[n + 1];
        int[] nestPosition = new int[m + 1];
        int[] nest = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            bunny[i] = in.nextInt();
        }
        for (int i = 1; i <= m; i++) {
            int x = in.nextInt();
            nestPosition[i] = x;
        }

        Arrays.sort(bunny, 1, n + 1);
        Arrays.sort(nestPosition, 1, m + 1);

        int PtrNest = 1;
        for (int i = 1; i <= n; i++) {
            if (PtrNest > m) {
                break;
            }
            if (bunny[i] - nestPosition[PtrNest] < -t) {
                continue;
            } else if (bunny[i] - nestPosition[PtrNest] > t) {
                PtrNest++;
                i--;
                continue;
            } else {
                if (nest[PtrNest] < c) {
                    nest[PtrNest]++;
                    result++;
                } else {
                    PtrNest++;
                    i--;
                    if (PtrNest > m) {
                        break;
                    }
                }

            }

        }


        System.out.println(result);


    }
}
