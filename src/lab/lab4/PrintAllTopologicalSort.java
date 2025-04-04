package lab.lab4;

import java.util.*;

public class PrintAllTopologicalSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 节点数
        int m = scanner.nextInt(); // 边数

        // 初始化节点
        NodePrintAllTopologicalSort[] nodes = new NodePrintAllTopologicalSort[n + 1]; // 1-based index
        for (int i = 1; i <= n; i++) {
            nodes[i] = new NodePrintAllTopologicalSort(i);
        }

        // 读取边并构建图
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            nodes[u].children.add(nodes[v]);
            nodes[v].inDegree++; // 目标节点 v 的入度增加
        }

        scanner.close();

        // 结果存储所有拓扑排序方案
        List<List<Integer>> allOrders = new ArrayList<>();
        // 递归回溯求解
        findTopologicalSorts(nodes, new ArrayList<>(), allOrders, n);

        // 输出所有拓扑排序
        for (List<Integer> order : allOrders) {
            System.out.println(order);
        }
    }

    static void findTopologicalSorts(NodePrintAllTopologicalSort[] nodes, List<Integer> currentOrder,
                                     List<List<Integer>> allOrders, int n) {
        boolean found = false; // 记录本轮是否有可选节点

        for (int i = 1; i <= n; i++) {
            if (nodes[i].inDegree == 0 && !nodes[i].visited) { // 选择入度为0且未访问的节点
                nodes[i].visited = true; // 标记已访问
                currentOrder.add(nodes[i].index);

                // 临时减少相邻节点的入度
                for (NodePrintAllTopologicalSort neighbor : nodes[i].children) {
                    neighbor.inDegree--;
                }

                // 递归搜索
                findTopologicalSorts(nodes, currentOrder, allOrders, n);

                // 回溯（撤销选择）
                nodes[i].visited = false;
                currentOrder.remove(currentOrder.size() - 1);
                for (NodePrintAllTopologicalSort neighbor : nodes[i].children) {
                    neighbor.inDegree++;
                }

                found = true; // 表示仍然有可选节点
            }
        }

        // 若当前排序长度等于 n，表示找到一个完整拓扑排序
        if (currentOrder.size() == n) {
            allOrders.add(new ArrayList<>(currentOrder));
        }
    }
}

// 节点类，存储索引、入度、邻接表
class NodePrintAllTopologicalSort {
    int index;
    int inDegree;
    boolean visited;
    List<NodePrintAllTopologicalSort> children;

    public NodePrintAllTopologicalSort(int index) {
        this.index = index;
        this.inDegree = 0;
        this.visited = false;
        this.children = new ArrayList<>();
    }
}

