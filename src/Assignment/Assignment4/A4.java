package Assignment.Assignment4;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class A4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // number of nodes
        int m = in.nextInt(); // number of edges

        graphNode4A[] nodes = new graphNode4A[n + 1];
        doubleEdge4A[] edges = new doubleEdge4A[m];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new graphNode4A(i);
        }

        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            nodes[u].children.add(nodes[v]);
            nodes[u].childWeight.add(w);
            nodes[v].children.add(nodes[u]);
            nodes[v].childWeight.add(w);
            edges[i] = new doubleEdge4A(u, v, w);

        }

        int[] distancesFromStart = dijkstra(nodes, 1);
        updateDistance(nodes);
        int[] distancesFromEnd = dijkstra(nodes, n);
//        // Print the distances from start to each node
//        printArray(distancesFromStart);
//
//        // Print the distances from end to each node
//        printArray(distancesFromEnd);


        for (int i = 1; i <= n; i++) {
            if (distancesFromStart[i] == Integer.MAX_VALUE || distancesFromEnd[i] == Integer.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(distancesFromStart[i] + distancesFromEnd[i]);
            }
        }
        for (int i = 0; i < m; i++) {
            System.out.println(Math.min((edges[i].weight + distancesFromStart[edges[i].start] + distancesFromEnd[edges[i].end])
                    ,
                    (edges[i].weight + distancesFromStart[edges[i].end] + distancesFromEnd[edges[i].start])));
        }

    }

    public static void printArray(int[] array) {
        for (int j : array) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    public static int[] dijkstra(graphNode4A[] nodes, int start) {
        int[] result = new int[nodes.length];
        PriorityQueue<heapNode4A> minHeap = new PriorityQueue<>();
        nodes[start].minimumDistance = 0;
        minHeap.add(new heapNode4A(nodes[start], 0));
        while (!minHeap.isEmpty()) {
            heapNode4A currentHeapnode = minHeap.poll();
            graphNode4A currentGraphNode = currentHeapnode.node;
            if (currentGraphNode.isGhost) {
                continue; // Skip if the node is a ghost
            }
            currentGraphNode.isGhost = true; // Mark the node as visited
            for (int i = 0; i < currentGraphNode.children.size(); i++) {
                graphNode4A child = currentGraphNode.children.get(i);
                int weight = currentGraphNode.childWeight.get(i);
                int newDistance = currentHeapnode.distance + weight;
                if (newDistance < child.minimumDistance) {
                    child.minimumDistance = newDistance;
                    minHeap.add(new heapNode4A(child, newDistance));
                }
            }
        }
        for (int i = 1; i < nodes.length; i++) {
            result[i] = nodes[i].minimumDistance;
        }
        return result;
    }

    public static int[] inverseDijkstra(graphNode4A[] nodes, int end) {
        int[] result = new int[nodes.length];
        PriorityQueue<heapNode4A> minHeap = new PriorityQueue<>();
        nodes[end].minimumDistance = 0;
        minHeap.add(new heapNode4A(nodes[end], 0));
        while (!minHeap.isEmpty()) {
            heapNode4A currentHeapnode = minHeap.poll();
            graphNode4A currentGraphNode = currentHeapnode.node;
            if (currentGraphNode.isGhost) {
                continue; // Skip if the node is a ghost
            }
            currentGraphNode.isGhost = true; // Mark the node as visited
            for (int i = 0; i < currentGraphNode.parents.size(); i++) {
                graphNode4A parent = currentGraphNode.parents.get(i);
                int weight = currentGraphNode.parentWeight.get(i);
                int newDistance = currentHeapnode.distance + weight;
                if (newDistance < parent.minimumDistance) {
                    parent.minimumDistance = newDistance;
                    minHeap.add(new heapNode4A(parent, newDistance));
                }
            }
        }
        for (int i = 1; i < nodes.length; i++) {
            result[i] = nodes[i].minimumDistance;
        }
        return result;
    }

    public static void updateDistance(graphNode4A[] node) {
        for (int i = 1; i < node.length; i++) {
            node[i].minimumDistance = Integer.MAX_VALUE;
            node[i].isGhost = false;
        }
    }
}

class doubleEdge4A {
    int start;
    int end;
    int weight;

    doubleEdge4A(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}

class graphNode4A {
    int id;
    boolean isGhost;
    int minimumDistance;
    ArrayList<graphNode4A> children = new ArrayList<>();
    ArrayList<Integer> childWeight = new ArrayList<>();
    ArrayList<graphNode4A> parents = new ArrayList<>();
    ArrayList<Integer> parentWeight = new ArrayList<>();

    graphNode4A(int id) {
        this.id = id;
        this.isGhost = false;
        this.minimumDistance = Integer.MAX_VALUE;
    }
}

class heapNode4A implements Comparable<heapNode4A> {
    graphNode4A node;
    int distance;

    heapNode4A(graphNode4A node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(heapNode4A other) {
        return Integer.compare(this.distance, other.distance);
    }
}

