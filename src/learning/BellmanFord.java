package learning;

import java.util.ArrayList;
import java.util.Scanner;

public class BellmanFord {
    static final long INF = Long.MAX_VALUE / 2; // 防止溢出

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // 顶点数量
        int m = in.nextInt(); // 边数量

        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int u = in.nextInt(); // 起点
            int v = in.nextInt(); // 终点
            long w = in.nextLong(); // 权重
            edges.add(new Edge(u, v, w));
        }

        long[][] dp = new long[n][n + 1]; // dp[i][v]：用 i 条边到达 v 的最短路径长度

        // 初始化 dp[0][v]
        for (int v = 1; v <= n; v++) {
            dp[0][v] = INF;
        }
        dp[0][1] = 0; // 从源点 1 出发

        // 动态规划迭代
        for (int i = 1; i < n; i++) {
            for (int v = 1; v <= n; v++) {
                dp[i][v] = dp[i - 1][v]; // 不使用第 i 条边
            }
            for (Edge e : edges) {
                if (dp[i - 1][e.from] != INF) {
                    dp[i][e.to] = Math.min(dp[i][e.to], dp[i - 1][e.from] + e.weight);
                }
            }
        }

        // 检查负权环（第 n 次还能变小说明有负环）
        boolean hasNegativeCycle = false;
        long[] prev = dp[n - 1].clone(); // 保存上一轮
        long[] curr = new long[n + 1];
        System.arraycopy(prev, 0, curr, 0, n + 1);
        for (Edge e : edges) {
            if (prev[e.from] != INF && prev[e.from] + e.weight < curr[e.to]) {
                hasNegativeCycle = true;
                break;
            }
        }

        if (hasNegativeCycle) {
            System.out.println("Graph contains a negative-weight cycle");
        } else if (dp[n - 1][n] == INF) {
            System.out.println(-1); // 终点不可达
        } else {
            System.out.println(dp[n - 1][n]); // 输出 1 到 n 的最短路径长度
        }
    }
}

class Edge {
    int from, to;
    long weight;

    Edge(int from, int to, long weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
