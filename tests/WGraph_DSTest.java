package ex1.tests;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WGraph_DSTest {
    @Test
    void getNodeTest(){
        weighted_graph wg = new WGraph_DS();
        if (wg.getNode(1) != null)
            fail("get node fail");
    }
    @Test
    void addNodeTest(){
        weighted_graph wg = new WGraph_DS();
        wg.addNode(1);
        if (wg.getNode(1) == null)
            fail("add node fail");

    }
    @Test
    void connectTest(){
        weighted_graph wg = new WGraph_DS();
        wg.addNode(0);
        wg.addNode(1);
        wg.connect(1, 0, 1.2);
        wg.connect(1, 0, 2.8);
        assertEquals(1.2, wg.getEdge(0, 1));
        assertEquals(1, wg.edgeSize());

    }
    @Test
    void connectTest2(){
        weighted_graph wg = new WGraph_DS();
        wg.addNode(0);
        wg.addNode(1);
        wg.connect(1, 0, 1.2);
        if (!wg.hasEdge(0 ,1))
            fail("didnt create edge");
        assertEquals(1, wg.edgeSize());
    }
    @Test
    void hasEdgeTest() {
        weighted_graph wg = new WGraph_DS();
        wg.addNode(0);
        wg.connect(0, 0, 3.5);
        if (wg.hasEdge(0, 0))
            fail("connected node to itself");
    }
    @Test
    void removeNodeTest(){
        weighted_graph wg = new WGraph_DS();
        wg.addNode(2);
        wg.addNode(4);
        wg.addNode(5);
        wg.connect(2, 4, 8.7);
        wg.connect(5, 2, 3.2);
        wg.removeNode(2);
        assertEquals(2, wg.nodeSize());
        assertEquals(0, wg.edgeSize());

    }
    @Test
    void removeNodeTest2(){
        weighted_graph wg = new WGraph_DS();
        wg.addNode(2);
        wg.addNode(4);
        wg.addNode(5);

        wg.connect(2, 4 , 0.5);
        wg.connect(2, 5 , 3.5);
        assertEquals(2, wg.edgeSize());
        if (wg.removeNode(3) != null)
            fail("removed node that doesnt exist");

    }
    @Test
    void removeEdgeTest(){
        weighted_graph wg = new WGraph_DS();
        wg.addNode(0);
        wg.addNode(1);
        wg.connect(1, 0, 1.2);
        assertEquals(1, wg.edgeSize());
        wg.removeEdge(0, 1);
        assertEquals(0, wg.edgeSize());
    }

}
