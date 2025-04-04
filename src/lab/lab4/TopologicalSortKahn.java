package lab.lab4;

import java.util.*;

public class TopologicalSortKahn {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 节点数
        int m = scanner.nextInt(); // 边数

        // 初始化图
        Node[] nodes = new Node[n + 1]; // 1-based index
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }

        // 读取边并构建图
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            nodes[u].children.add(nodes[v]); // u -> v
            nodes[v].inDegree++; // v 入度增加
        }

        scanner.close();

        // 计算拓扑排序（只求一种解）
        List<Integer> result = getTopologicalOrder(nodes, n);

        // 输出结果
        if (result.size() == n) {
            System.out.println(result);
        } else {
            System.out.println("impossible");
        }
    }

    // Kahn 算法（BFS）计算拓扑排序
    static List<Integer> getTopologicalOrder(Node[] nodes, int n) {
        List<Integer> topologicalOrder = new ArrayList<>();
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            if (nodes[i].inDegree == 0) {
                minHeap.offer(i);
            }
        }
        while (!minHeap.isEmpty()) {
            int u = minHeap.poll();
            topologicalOrder.add(u);
            for (Node v : nodes[u].children) {
                v.inDegree--;
                if (v.inDegree == 0) {
                    minHeap.offer(v.index);
                }
            }
        }

        return topologicalOrder;
    }
}

// 节点类，存储索引、入度、邻接表
class Node {
    int index;
    int inDegree;
    List<Node> children;

    public Node(int index) {
        this.index = index;
        this.inDegree = 0;
        this.children = new ArrayList<>();
    }
}

