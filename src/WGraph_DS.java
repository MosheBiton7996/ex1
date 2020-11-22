package ex1.src;
import java.io.Serializable;
import java.util.*;

/**
 * This class implements given interface weighted_graph and represents an undirectional weighted graph.
 * It supports a large number of nodes (over 10^6, with average degree of 10).
 *
 */

public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> nodes;
    private int nodeSize, edgeSize;
    private int mcCounter = 0;
    private HashMap<node_info , HashMap<node_info , Double>> edges;
    public WGraph_DS() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
        nodeSize = 0;
        edgeSize = 0;
    }

    /**
     * deep copying builder, works in O(n^2) to deep copy the nodes and the edges in between.
     * @param origin - the copied graph
     */
    public WGraph_DS(weighted_graph origin){
        nodes = new HashMap<>();
        edges = new HashMap<>();
        Iterator<node_info> neigh = origin.getV().iterator();
        node_info curr;
        while (neigh.hasNext()) {
            curr = neigh.next();
            addNode(curr.getKey());
        }
        for (int i: nodes.keySet())
            for (int j: nodes.keySet())
                if(origin.hasEdge(i, j))
                    connect(i, j, origin.getEdge(i, j));

        nodeSize = origin.nodeSize();
        edgeSize = origin.edgeSize();
        mcCounter = origin.getMC();
    }

    /**
     * returns the node asked through the given key.
     * @param key - the node_id
     * @return
     */
    @Override
    public node_info getNode(int key) {
        return nodes.get(key);
    }

    /**
     * checks if theres an edge between the 2 given nodes
     * @param node1
     * @param node2
     * @return true iff there is an edge
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        node_info n1 = getNode(node1), n2 = getNode(node2);
        if (n1 != null && n2 != null && node1 != node2) {
            return edges.get(n1).containsKey(n2) && edges.get(n2).containsKey(n1);
        }
        return false;
    }

    /**
     * gets the weight of the edge between the 2 given nodes
     * @param node1
     * @param node2
     * @return the edge weight
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (this.hasEdge(node1,node2))
            return edges.get(getNode(node1)).get(getNode(node2));
        return -1;
    }

    /**
     * adds a node with the specified key
     * Note: if there is a node with specified key the new node will not be added.
     * @param key
     */
    @Override
    public void addNode(int key) {

        if(!nodes.containsKey(key)) {
            nodes.put(key, new NodeInfo(key));
            nodeSize++;
            mcCounter++;
        }
    }

    /**
     * connects the given node with an edge weighted as requested.
     * @param node1
     * @param node2
     * @param w - weight og the edge connecting
     */
    @Override
    public void connect(int node1, int node2, double w) {
        node_info n1 = getNode(node1), n2 = getNode(node2);
        if (n1 != null && n2 != null && node1 != node2) {
            if (!edges.containsKey(n1) || !edges.containsKey(n2)) {
                if (!edges.containsKey(n1)) {
                    HashMap<node_info, Double> inner = new HashMap<>();
                    inner.put(n2, w);
                    edges.put(n1, inner);
                }
                if (!edges.containsKey(n2)) {
                    HashMap<node_info, Double> inner = new HashMap<>();
                    inner.put(n1, w);
                    edges.put(n2, inner);
                }
                edgeSize++;
                mcCounter++;
                if (!edges.get(n1).containsKey(n2))
                    edges.get(n1).put(n2, w);
                if (!edges.get(n2).containsKey(n1))
                    edges.get(n2).put(n1, w);
            }
            else
                if (!hasEdge(node1, node2)) {
                    edges.get(n2).put(n1, w);
                    edges.get(n2).put(n1, w);
                edgeSize++;
                mcCounter++;
            }
        }
    }

    /**
     * @return a collection of nodes in the graph
     */
    @Override
    public Collection<node_info> getV() {
        return nodes.values();
    }

    /**
     * @param node_id
     * @return a collection of nodes that the given node is connected to (share edge).
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        return edges.get(getNode(node_id)).keySet();
    }

    /**
     * delete the given node
     * Note: deleting the node deletes all edges the node has.
     * @param key
     * @return the deleted node
     */
    @Override
    public node_info removeNode(int key) {
        if (getNode(key) != null && this.getV(key) != null)
        {
            Iterator<node_info> neigh = this.getV(key).iterator();
            node_info n;
            while (neigh.hasNext()) {
                n = neigh.next();
                removeEdge(key, n.getKey());
                neigh = this.getV(key).iterator();
            }
            nodeSize--;
            mcCounter++;
            return nodes.remove(key);
        }
        return null;
    }

    /**
     * Delete the edge from the graph.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            node_info n1 = getNode(node1), n2 = getNode(node2);
            HashMap<node_info, Double> inner = edges.get(n1);
            inner.remove(n2);
            inner = edges.get(n2);
            inner.remove(n1);
            edgeSize--;
            mcCounter++;
        }
    }

    /**
     * @return the number of vertices (nodes) in the graph.
     */
    @Override
    public int nodeSize() {
        return nodeSize;
    }

    /**
     * @return the number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return edgeSize;
    }

    /**
     * @return the Mode Count - for testing changes in the graph.
     */
    @Override
    public int getMC() {
        return mcCounter;
    }

    private class NodeInfo implements node_info, Serializable{

        private int key;
        private double tag;
        private String info;

        public NodeInfo(int key){
            this.key = key;
            this.info = "";
            this.tag = 0;
        }
        public NodeInfo(node_info origin){
            this.key = origin.getKey();
            this.info = origin.getInfo();
            this.tag = origin.getTag();
        }
        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }
    }
}
