import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        point8B[] points = new point8B[n];
        for (int i = 0; i < n; i++) {
            double x = in.nextDouble();
            double y = in.nextDouble();
            point8B p = new point8B(x, y);
            points[i] = p;
        }
        Arrays.sort(points, Comparator.comparingDouble(a -> a.x));
        double min = minDistance(points, 0, n);
        System.out.printf("%.4f\n", min);
    }

    public static double distance(point8B a, point8B b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public static double minDistance(point8B[] points, int left, int right) {
        if (right - left <= 3) {
            double min = Double.MAX_VALUE;
            for (int i = left; i < right; i++) {
                for (int j = i + 1; j < right; j++) {
                    min = Math.min(min, distance(points[i], points[j]));
                }
            }
            return min;
        }
        int mid = (left + right) / 2;
        double d1 = minDistance(points, left, mid);
        double d2 = minDistance(points, mid, right);
        double d = Math.min(d1, d2);
        ArrayList<point8B> strip = new ArrayList<>();

        int i = mid - 1;
        while (i >= left && Math.abs(points[mid].x - points[i].x) < d) {
            strip.add(points[i]);
            i--;
        }

        i = mid;
        while (i < right && Math.abs(points[i].x - points[mid].x) < d) {
            strip.add(points[i]);
            i++;
        }

        strip.sort(Comparator.comparingDouble(a -> a.y));

        for (int j = 0; j < strip.size(); j++) {
            for (int k = j + 1; k < strip.size() && k <= j + 7; k++) {
                d = Math.min(d, distance(strip.get(j), strip.get(k)));
            }
        }
        return d;
    }
}

class point8B {
    double x;
    double y;

    public point8B(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
