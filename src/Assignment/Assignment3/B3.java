package Assignment.Assignment3;

import java.util.Arrays;
import java.util.Scanner;


public class B3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // number of tasks
        int m = in.nextInt(); // number of time slots
        NodeB3[] array = new NodeB3[n + m];
        for (int i = 0; i < n; i++) {
            int start = in.nextInt();
            int end = in.nextInt();
            array[i] = new NodeB3(start, end, TypeB3.task);
        }
        for (int i = 0; i < m; i++) {
            int start = in.nextInt();
            int end = in.nextInt();
            array[n + i] = new NodeB3(start, end, TypeB3.slot);
        }


        // 使用 Arrays.sort 而不是 array.sort
        Arrays.sort(array, (a, b) -> {
            if (a.start != b.start) {
                return Integer.compare(a.start, b.start);
            } else if (a.end != b.end) {
                return Integer.compare(b.end, a.end); // end越大越靠前
            } else {
                return a.type.compareTo(b.type); // slot < task
            }
        });

        int end = 0;
        int ans = 0;
        for (NodeB3 node : array) {
            if (node.type == TypeB3.task) {
                if (node.end <= end) {
                    ans++;
                    continue;
                }
            } else {
                end = Math.max(end, node.end);
            }
        }
        System.out.println(ans);

    }
}

enum TypeB3 {
    slot, task
}

class NodeB3 {
    int start;
    int end;
    TypeB3 type;

    public NodeB3(int start, int end, TypeB3 type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }


    @Override
    public String toString() {
        return "Node{" + "start=" + start + ", end=" + end + ", type=" + type + '}';
    }


}