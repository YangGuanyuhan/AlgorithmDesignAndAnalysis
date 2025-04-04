package Assignment.Assignment2;

import java.util.*;

public class B1 {
    //dag , but might be disconnected
    static int mod = 1000000007;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        Node2B[] nodes = new Node2B[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node2B(i);
        }
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            nodes[x].children.add(nodes[y]);
            nodes[y].parents.add(nodes[x]);
            nodes[y].inDegree++;
            nodes[x].outDegree++;
        }
        in.close();


        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (nodes[i].inDegree == 0) {
                queue.add(i);
                nodes[i].inChainNumber = 1;
            }
        }
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (Node2B v : nodes[u].children) {
                v.inDegree--;
                v.inChainNumber += nodes[u].inChainNumber;
                v.inChainNumber %= mod;
                if (v.inDegree == 0) {
                    queue.add(v.index);
                }
            }
        }

        //finish reverse
        for (int i = 1; i <= n; i++) {
            if (nodes[i].outDegree == 0) {
                queue.add(i);
                nodes[i].outChainNumber = 1;
            }
        }
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (Node2B v : nodes[u].parents) {
                v.outDegree--;
                v.outChainNumber += nodes[u].outChainNumber;
                v.outChainNumber %= mod;
                if (v.outDegree == 0) {
                    queue.add(v.index);
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            System.out.print((nodes[i].inChainNumber * nodes[i].outChainNumber) % mod + " ");
        }
    }
}

class Node2B {
    int index;
    int inDegree;
    int inChainNumber;
    int outDegree;
    int outChainNumber;
    ArrayList<Node2B> children;
    ArrayList<Node2B> parents;

    public Node2B(int index) {
        this.index = index;
        this.inDegree = 0;
        this.inChainNumber = 0;
        this.outDegree = 0;
        this.outChainNumber = 0;
        this.children = new ArrayList<>();
        this.parents = new ArrayList<>();
    }
}