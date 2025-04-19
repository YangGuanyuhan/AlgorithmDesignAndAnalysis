package Assignment.Assignment4;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class B4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        GraphNodeB4[] nodes = new GraphNodeB4[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new GraphNodeB4(i);
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            nodes[u].children.add(nodes[v]);
            nodes[u].childrenWeights.add(w);
            nodes[v].parents.add(nodes[u]);
            nodes[v].parentsWeights.add(w);
            nodes[u].outDegree++;
            nodes[v].inDegree++;
        }
        int start = 1;
        int result[] = dijkstra(nodes, start);

//        for (int i = 1; i <= n; i++) {
//            for (int j = 0; j < nodes[i].dijkstraPath.size(); j++) {
//                //打印
//                System.out.print(nodes[i].dijkstraPath.get(j).id + " ");
//            }
//            System.out.println();
//        }

        int finalResult = 0;
        for (int i = 2; i <= n; i++) {
            if (nodes[i].dijkstraPath.isEmpty()) {
                System.out.println(-1);
            } else if (nodes[i].dijkstraPath.size() == 1) {
                GraphNodeB4 parent = nodes[i].dijkstraPath.get(0);
                GraphNodeB4 child = nodes[i];
                //寻找parent到child的weight
                int minWeight = Integer.MAX_VALUE;

                for (int j = 0; j < parent.children.size(); j++) {
                    if (parent.children.get(j).id == child.id) {
                        minWeight = Math.min(minWeight, parent.childrenWeights.get(j));
                    }
                }

                finalResult += minWeight;

            } else {
                int minWeight = Integer.MAX_VALUE;
                for (int j = 0; j < nodes[i].dijkstraPath.size(); j++) {
                    GraphNodeB4 parent = nodes[i].dijkstraPath.get(j);
                    for (int k = 0; k < parent.children.size(); k++) {
                        if (parent.children.get(k).id == i) {
                            minWeight = Math.min(minWeight, parent.childrenWeights.get(k));
                        }
                    }
                }
                finalResult += minWeight;

            }
        }

        System.out.println(finalResult);
    }


    public static int[] dijkstra(GraphNodeB4[] nodes, int start) {
        int n = nodes.length;
        int result[] = new int[n];
        for (int i = 1; i < n; i++) {
            nodes[i].minDistance = Integer.MAX_VALUE;
            nodes[i].dijkstraPath.clear();
        }
        PriorityQueue<HeapNodeB4> minHeap = new PriorityQueue<>();
        nodes[start].minDistance = 0;
        minHeap.add(new HeapNodeB4(nodes[start], 0));
        while (!minHeap.isEmpty()) {
            HeapNodeB4 current = minHeap.poll();
            GraphNodeB4 currentNode = current.node;
            int currentDistance = current.distance;
            if (currentDistance > currentNode.minDistance) {
                continue;
            }
            for (int i = 0; i < currentNode.children.size(); i++) {
                GraphNodeB4 child = currentNode.children.get(i);
                int weight = currentNode.childrenWeights.get(i);
                int newDistance = currentDistance + weight;

                if (newDistance < child.minDistance) {
                    child.minDistance = newDistance;
                    child.dijkstraPath.clear();
                    child.dijkstraPath.add(currentNode);
                    minHeap.add(new HeapNodeB4(child, newDistance));
                } else if (newDistance == child.minDistance) {
                    child.dijkstraPath.add(currentNode);
                }
            }
        }
        for (int i = 1; i < n; i++) {
            result[i] = nodes[i].minDistance;
        }
        return result;
    }

}

class GraphNodeB4 {
    int id;
    boolean isGhost;
    int minDistance;
    int inDegree;
    int outDegree;
    ArrayList<GraphNodeB4> dijkstraPath;
    ArrayList<GraphNodeB4> children;
    ArrayList<Integer> childrenWeights;
    ArrayList<GraphNodeB4> parents;
    ArrayList<Integer> parentsWeights;

    public GraphNodeB4(int id) {
        this.id = id;
        this.isGhost = false;
        this.minDistance = Integer.MAX_VALUE;
        this.inDegree = 0;
        this.outDegree = 0;
        this.dijkstraPath = new ArrayList<>();
        this.children = new ArrayList<>();
        this.childrenWeights = new ArrayList<>();
        this.parents = new ArrayList<>();
        this.parentsWeights = new ArrayList<>();
    }

}

class HeapNodeB4 implements Comparable<HeapNodeB4> {
    GraphNodeB4 node;
    int distance;

    public HeapNodeB4(GraphNodeB4 node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(HeapNodeB4 other) {
        return Integer.compare(this.distance, other.distance);
    }

}
