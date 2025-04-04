package lab.lab4;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortKahnTest {

    @Test
    void topologicalOrderForSimpleDAG() {
        Node[] nodes = new Node[4];
        for (int i = 1; i <= 3; i++) {
            nodes[i] = new Node(i);
        }
        nodes[1].children.add(nodes[2]);
        nodes[2].inDegree++;
        nodes[1].children.add(nodes[3]);
        nodes[3].inDegree++;
        nodes[2].children.add(nodes[3]);
        nodes[3].inDegree++;

        List<Integer> result = TopologicalSortKahn.getTopologicalOrder(nodes, 3);
        assertEquals(List.of(1, 2, 3), result);
    }

    @Test
    void topologicalOrderForComplexDAG() {
        Node[] nodes = new Node[6];
        for (int i = 1; i <= 5; i++) {
            nodes[i] = new Node(i);
        }
        nodes[1].children.add(nodes[2]);
        nodes[2].inDegree++;
        nodes[1].children.add(nodes[3]);
        nodes[3].inDegree++;
        nodes[2].children.add(nodes[4]);
        nodes[4].inDegree++;
        nodes[3].children.add(nodes[4]);
        nodes[4].inDegree++;
        nodes[4].children.add(nodes[5]);
        nodes[5].inDegree++;

        List<Integer> result = TopologicalSortKahn.getTopologicalOrder(nodes, 5);
        assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    @Test
    void topologicalOrderForNonDAG() {
        Node[] nodes = new Node[4];
        for (int i = 1; i <= 3; i++) {
            nodes[i] = new Node(i);
        }
        nodes[1].children.add(nodes[2]);
        nodes[2].inDegree++;
        nodes[2].children.add(nodes[3]);
        nodes[3].inDegree++;
        nodes[3].children.add(nodes[1]);
        nodes[1].inDegree++;

        List<Integer> result = TopologicalSortKahn.getTopologicalOrder(nodes, 3);
        assertEquals(List.of(), result);
    }

    @Test
    void topologicalOrderForSingleNode() {
        Node[] nodes = new Node[2];
        nodes[1] = new Node(1);

        List<Integer> result = TopologicalSortKahn.getTopologicalOrder(nodes, 1);
        assertEquals(List.of(1), result);
    }

    @Test
    void topologicalOrderForDisconnectedGraph() {
        Node[] nodes = new Node[4];
        for (int i = 1; i <= 3; i++) {
            nodes[i] = new Node(i);
        }
        nodes[1].children.add(nodes[2]);
        nodes[2].inDegree++;
        nodes[3].children.add(nodes[1]);
        nodes[1].inDegree++;

        List<Integer> result = TopologicalSortKahn.getTopologicalOrder(nodes, 3);
        assertEquals(List.of(3, 1, 2), result);
    }
}