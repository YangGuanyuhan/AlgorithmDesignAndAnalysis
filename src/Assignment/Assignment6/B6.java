package Assignment.Assignment6;

import java.util.*;

public class B6 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // number of nodes
        int k = in.nextInt(); // target path sum
        Node6B[] nodes = new Node6B[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node6B(i);
        }
        for (int i = 0; i < n - 1; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            nodes[u].children.add(nodes[v]);
            nodes[u].childWeight.add(w);
            nodes[v].children.add(nodes[u]);
            nodes[v].childWeight.add(w);
        }
        int centroid = findCentroid(nodes);
        updateNodes(nodes);
        if (pathSumQuery(nodes, centroid, k)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

    }

    public static void bfs(Node6B[] nodes, int root) {
        updateNodes(nodes);
        Queue<Node6B> queue = new LinkedList<>();
        queue.add(nodes[root]);
        while (!queue.isEmpty()) {
            Node6B currentNode = queue.poll();
            currentNode.isVisited = true;
            for (int i = 0; i < currentNode.children.size(); i++) {
                Node6B child = currentNode.children.get(i);
                if (!child.isVisited) {
                    queue.add(child);
                }
            }
        }

    }

    public static boolean pathSumQuery(Node6B[] nodes, int root, int k) {
        updateNodes(nodes);
        boolean[] flag = new boolean[10000001];
        ArrayList<Integer> dArray = new ArrayList<>();
        Queue<Node6B> queue = new LinkedList<>();
        queue.add(nodes[root]);
        while (!queue.isEmpty()) {
            Node6B currentNode = queue.poll();
            currentNode.isVisited = true;
            if (calc(nodes, currentNode, k, flag, dArray)) {
                return true;
            }
            for (int i = 0; i < currentNode.children.size(); i++) {
                Node6B child = currentNode.children.get(i);
                if (!child.isVisited) {
                    queue.add(child);
                    dArray.add(currentNode.childWeight.get(i));
                }
            }
        }
        return false;
    }

    public static boolean calc(Node6B[] nodes, Node6B currentNode, int k, boolean[] flag, ArrayList<Integer> dArray) {
        for (int i = 1; i < nodes.length; i++) {
            nodes[i].isVisited2 = false;
            nodes[i].distance = 0;
        }
        dArray.clear();
        Arrays.fill(flag, false);
        currentNode.isVisited2 = true;

        for (int i = 0; i < currentNode.children.size(); i++) {
            Node6B currentNodeChild = currentNode.children.get(i);
            currentNodeChild.distance = currentNode.childWeight.get(i);
            Queue<Node6B> queue = new LinkedList<>();
            queue.add(currentNodeChild);
            dArray.add(currentNodeChild.distance);
            while (!queue.isEmpty()) {
                Node6B child = queue.poll();
                child.isVisited2 = true;
                if (child.id == currentNode.id) {
                    continue;
                }
                for (int j = 0; j < child.children.size(); j++) {
                    Node6B grandChild = child.children.get(j);
                    if (!grandChild.isVisited2) {
                        queue.add(grandChild);
                        int weight = child.childWeight.get(j);
                        grandChild.distance = child.distance + weight;
                        dArray.add(grandChild.distance);

                    }
                }
            }
            for (int j = 0; j < dArray.size(); j++) {
                int distance = dArray.get(j);
                if (distance == k) {
                    return true;
                }
                if (distance < k) {
                    if (flag[k - distance]) {
                        return true;
                    }
                }
            }
            for (int j = 0; j < dArray.size(); j++) {
                int distance = dArray.get(j);
                flag[distance] = true;

            }
            dArray.clear();

        }

        return false;
    }

    public static void dfs(Node6B[] nodes, int root) {
        nodes[root].isVisited = true;
        for (int i = 0; i < nodes[root].children.size(); i++) {
            Node6B child = nodes[root].children.get(i);
            if (!child.isVisited) {
                dfs(nodes, child.id);
                nodes[root].subTreeSize += child.subTreeSize;
            }
        }
    }

    public static int findCentroid(Node6B[] nodes) {
        dfs(nodes, 1);
        int centroid = 1;
        int nodesLength = nodes.length - 1;
        int parent = -1;
        while (true) {
            int max = nodesLength - nodes[centroid].subTreeSize;
            int nextCentroid = -1;
            for (Node6B child : nodes[centroid].children) {
                if (child.id == parent)
                    continue;
                if (child.subTreeSize > max) {
                    max = child.subTreeSize;
                    nextCentroid = child.id;
                }
            }
            if (max > nodesLength / 2) {
                parent = centroid;
                centroid = nextCentroid;
            } else {
                return centroid;
            }
        }

    }

    public static void updateNodes(Node6B[] nodes) {
        for (int i = 1; i < nodes.length; i++) {
            nodes[i].isVisited = false;
        }
    }
}

class Node6B {
    int id;
    int subTreeSize;
    boolean isVisited;
    boolean isVisited2;
    int distance;
    ArrayList<Node6B> children;
    ArrayList<Integer> childWeight;

    Node6B(int id) {
        this.id = id;
        this.subTreeSize = 1;
        this.distance = 0;
        this.isVisited = false;
        this.isVisited2 = false;
        this.children = new ArrayList<>();
        this.childWeight = new ArrayList<>();
    }

}
