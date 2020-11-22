package ex1.tests;
import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WGraph_AlgoTest {
    weighted_graph createGraph(){
        weighted_graph wg = new WGraph_DS();
        wg.addNode(2);
        wg.addNode(4);
        wg.addNode(5);
        wg.connect(2, 4, 8.7);
        wg.connect(5, 2, 3.2);
        return wg;
    }
    weighted_graph createGraph2(){
        weighted_graph wg = new WGraph_DS();
        wg.addNode(2);
        wg.addNode(9);
        wg.addNode(8);
        wg.addNode(7);
        wg.addNode(4);
        wg.addNode(5);
        wg.connect(2, 4, 8.7);
        wg.connect(5, 2, 3.2);
        wg.connect(5, 8, 1.0);
        wg.connect(4, 8, 2.0);
        wg.connect(9, 8, 5.0);

        return wg;
    }
    @Test
    void copyTest(){
        weighted_graph wg = createGraph();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(wg);
        weighted_graph wgCopy = wga.copy();
        assertEquals(wg.edgeSize(), wgCopy.edgeSize());
        assertEquals(wg.nodeSize(), wgCopy.nodeSize());
        assertEquals(wg.getMC(), wgCopy.getMC());
        assertEquals(wg.hasEdge(2,5), wgCopy.hasEdge(2,5));
        assertEquals(wg.hasEdge(4,5), wgCopy.hasEdge(5,4));
    }
    @Test
    void isConnectedTest(){
        weighted_graph wg = createGraph();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(wg);
        assertEquals(wga.isConnected(), true);
        wga.getGraph().addNode(8);
        assertEquals(wga.isConnected(), false);
    }
    @Test
    void shortestDistTest(){
        weighted_graph wg = createGraph2();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(wg);
        assertEquals(wga.shortestPathDist(2, 2), 0);
        assertEquals(wga.shortestPathDist(2, 9), 9.2);
        assertEquals(wga.shortestPathDist(2, 7), -1);
    }
    @Test
    void shortestPathTest(){
        weighted_graph wg = createGraph2();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(wg);
        List<node_info> path = new ArrayList<>();
        path.add(wga.getGraph().getNode(2));
        path.add(wga.getGraph().getNode(5));
        path.add(wga.getGraph().getNode(8));
        path.add(wga.getGraph().getNode(9));

        assertEquals(wga.shortestPath(2, 9), path);
    }
    @Test
    void saveLoadTest(){
        weighted_graph wg = createGraph2();
        weighted_graph_algorithms wga = new WGraph_Algo();
        wga.init(wg);
        assertEquals(wga.save("Graph1"), true);
        weighted_graph_algorithms wgaLoaded = new WGraph_Algo();
        assertEquals(wgaLoaded.load("Graph1"), true);
        if (wga.getGraph() != wgaLoaded.getGraph())
            fail("not same graph");
    }
}
