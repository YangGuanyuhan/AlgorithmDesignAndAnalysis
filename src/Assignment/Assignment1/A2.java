package Assignment.Assignment1;

import java.util.*;

public class A2 {
    public static void main(String[] args) {
        //无向连通图
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int a = in.nextInt();
        int b = in.nextInt();
        NodeA2[] nodes = new NodeA2[n + 1];//node from 1 to n
        for (int i = 1; i <= n; i++) {
            nodes[i] = new NodeA2(i);
        }
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            nodes[x].children.add(nodes[y]);
            nodes[y].children.add(nodes[x]);
        }
        for (int i = 1; i <= n; i++) {
            nodes[i].path = -1;//-1 means not visited,represent cant reach
        }
        //first remove b nodes from the whole graph
        for (NodeA2 child : nodes[b].children) {
            if (child != null) {
                child.children.remove(nodes[b]);
            }
        }
        //create a hash set to store the nodes that can be reached from a
        Set<NodeA2> aCanReach = new HashSet<>();
        //then bfs from a node
        Queue<NodeA2> queue = new LinkedList<>();
        nodes[a].path = 0;
        nodes[a].visited = true;
        queue.add(nodes[a]);
        while (!queue.isEmpty()) {
            NodeA2 current = queue.poll();
            for (NodeA2 child : current.children) {
                if (child != null && !child.visited) {
                    child.visited = true;
                    child.path = current.path + 1;
                    queue.add(child);
                    aCanReach.add(child);
                }
            }
        }

        //recover all the nodes state
        for (int i = 1; i <= n; i++) {
            nodes[i].path = -1;
            nodes[i].visited = false;
        }
        //recover b to the graph
        for (NodeA2 child : nodes[b].children) {
            if (child != null) {
                child.children.add(nodes[b]);
            }
        }

        //remove a nodes from the whole graph
        for (NodeA2 child : nodes[a].children) {
            if (child != null) {
                child.children.remove(nodes[a]);
            }
        }
        //create a hash set to store the nodes that can be reached from b
        Set<NodeA2> bCanReach = new HashSet<>();
        //then bfs from b node
        nodes[b].path = 0;
        nodes[b].visited = true;
        queue.add(nodes[b]);
        while (!queue.isEmpty()) {
            NodeA2 current = queue.poll();
            for (NodeA2 child : current.children) {
                if (child != null && !child.visited) {
                    child.visited = true;
                    child.path = current.path + 1;
                    queue.add(child);
                    bCanReach.add(child);
                }
            }
        }

//        //print acanreach
//        System.out.println("aCanReach size"+aCanReach.size());
//        for (node node : aCanReach) {
//            System.out.print(node.index);
//        }
//        System.out.println();
//        System.out.println("---------------------------------------------");
//        //print bcanreach
//        System.out.println("bCanReach size"+bCanReach.size());
//        for (node node : bCanReach) {
//            System.out.print(node.index);
//        }
//        System.out.println();
//        System.out.println("---------------------------------------------");
//

        Set<NodeA2> bothCanReach = new HashSet<>();
        bothCanReach.addAll(aCanReach);
        bothCanReach.retainAll(bCanReach);

//        //print bothcanreach
//        System.out.println("bothCanReach size"+bothCanReach.size());
//        for (node node : bothCanReach) {
//            System.out.print(node.index);
//        }
//        System.out.println();
//        System.out.println("---------------------------------------------");


        aCanReach.removeAll(bothCanReach);
        bCanReach.removeAll(bothCanReach);

//        //print acanreach
//        System.out.println("only aCanReach size"+aCanReach.size());
//        for (node node : aCanReach) {
//            System.out.print(node.index);
//        }
//        System.out.println();
//        System.out.println("---------------------------------------------");
//        //print bcanreach
//        System.out.println("only bCanReach size"+bCanReach.size());
//        for (node node : bCanReach) {
//            System.out.print(node.index);
//        }
//        System.out.println();
//        System.out.println("---------------------------------------------");


        int result = 0;
        result = aCanReach.size() * bCanReach.size();
        System.out.println(result);



    }
}

class NodeA2 {
    int index;
    int path;
    boolean visited;
    ArrayList<NodeA2> children;

    public NodeA2(int index) {
        this.index = index;
        this.path = 0;
        this.visited = false;
        this.children = new ArrayList<>();
    }


}