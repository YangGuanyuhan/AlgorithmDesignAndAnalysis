import java.util.Comparator;
import java.util.Scanner;

public class hello {
    public static void main(String[] args) {
        Comparator<Integer> cmp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
       //comparator test
        System.out.println(cmp.compare(1, 3));
        System.out.println(cmp.compare(2, 1));
        System.out.println(cmp.compare(1, 1));

    }
}