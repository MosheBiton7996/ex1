package ex1.src;
import java.io.*;
import java.util.*;

/**
 * This class implements given interface weighted_graph_algorithms and
 * represents an algorithm class works on "weighted_graph" variables.
 */
public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph myGraph;
    public WGraph_Algo()
    {

    }

    /**
     * Initiate the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.myGraph = g;
    }

    /**
     * @return the underlying graph of which this class works on.
     */
    @Override
    public weighted_graph getGraph() {
        return myGraph;
    }

    /**
     * @return a deep copy of the graph of which this class works on.
     */
    @Override
    public weighted_graph copy() {
        return new WGraph_DS(myGraph);
    }

    /**
     * resets all tags in the graph to a given value.
     * @param prm - the tag to reset to.
     */
    private void resetTagsTo(int prm)
    {
        Iterator<node_info> neighs = myGraph.getV().iterator();
        node_info curr;
        while (neighs.hasNext()) {
            curr = neighs.next();
            curr.setTag(prm);
        }
    }

    /**
     * checks if all the nodes connected to the undirectional weighted graph.
     * using an iterator in a loop to get a node and marks it (using the tag).
     * @return true if all nodes connected
     */
    @Override
    public boolean isConnected() {
        if(myGraph.nodeSize() > 1) {
            resetTagsTo(0);
            Queue<node_info> q = new LinkedList<>();
            Iterator<node_info> itr= myGraph.getV().iterator();
            node_info firstNode = itr.next();
            firstNode.setTag(1);
            q.add(firstNode);
            while(!q.isEmpty()){
                firstNode = q.remove();
                Iterator<node_info> itr2 = myGraph.getV(firstNode.getKey()).iterator();
                node_info neighs;
                while (itr2.hasNext()) {
                    neighs = itr2.next();
                    if (neighs.getTag() == 0) {
                        neighs.setTag(1);
                        q.add(neighs);
                    }
                }
            }
            itr = myGraph.getV().iterator();
            node_info temp;
            while (itr.hasNext()) {
                temp = itr.next();
                if (temp.getTag() == 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * using the Dijkstra algorithm. The function resets all tags to -1 using resetTagsTo(-1).
     * An iterator sets every node's tag to the distance from the source starting from 0 on source node.
     * @param src - start node
     * @param dest - end (target) node
     * @return  the shortest path's distance to a destination node from the source node
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        node_info srcNode = myGraph.getNode(src), destNode = myGraph.getNode(dest);
        if (srcNode == null || destNode == null)
            return -1;
        if(dest == src)
            return 0;
        resetTagsTo(-1);
        PriorityQueue<node_info> q = new PriorityQueue<>(myGraph.edgeSize() , Comparator.comparingDouble(node_info::getTag));
        srcNode.setTag(0);
        q.add(srcNode);
        while(!q.isEmpty()){
            node_info nodeSearch = q.poll();
            if (nodeSearch.getKey() == dest)
                return destNode.getTag();
            Iterator<node_info> itr = myGraph.getV(nodeSearch.getKey()).iterator();
            node_info neighs;
            while (itr.hasNext()) {
                neighs = itr.next();
                if (neighs.getTag() == -1 || nodeSearch.getTag() + myGraph.getEdge(nodeSearch.getKey(), neighs.getKey()) < neighs.getTag()) {
                    neighs.setTag(nodeSearch.getTag() + myGraph.getEdge(nodeSearch.getKey(), neighs.getKey()));
                    q.add(neighs);
                }
            }
        }
        return destNode.getTag();
    }

    /**
     * using Dijkstra's algorithm.
     * using shortestPathDist(int src, int dest) all the nodes has they're tags set to the distance from the source to them.
     * if there's no such path the function returns null. else, the function
     * adds each time a node to a list from the destination node to the source using the tags to decide the path
     * (the path must go through each minimal neighbor tag from n to 0 once, n being the distance to destination node).
     * @param src - start node
     * @param dest - end (target) node
     * @return  the shortest path as a list to a destination node from the source node.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        node_info srcNode = myGraph.getNode(src), destNode = myGraph.getNode(dest);
        if (srcNode == null || destNode == null)
            return null;
        double pathLength = shortestPathDist(src, dest);
        if (pathLength == -1)
            return null;
        List<node_info> path = new ArrayList<>();
        path.add(srcNode);
        if (pathLength == 0)
            return path;
        path.remove(srcNode);
        List<node_info> pathMirror = new ArrayList<>();
        double smallestEdge =  destNode.getTag();
        node_info smallestNei = destNode;
        node_info curr = destNode;
        while (curr.getKey() != src) {
            pathMirror.add(curr);
            Iterator<node_info> itr = myGraph.getV(curr.getKey()).iterator();
            node_info neighs;
            while (itr.hasNext()) {
                neighs = itr.next();
                if (neighs.getTag() < smallestEdge && neighs.getTag() != -1) {
                    smallestEdge = neighs.getTag();
                    smallestNei = neighs;
                }
            }
            curr = smallestNei;
        }
        pathMirror.add(curr);
        while (!pathMirror.isEmpty())
            path.add(pathMirror.remove(pathMirror.size() - 1));
        return path;
    }

    /**
     * saves the underlying graph.
     * @param file - the file name (may include a relative path).
     * @return true if save is succesful
     */
    @Override
    public boolean save(String file) {
        try{
            FileOutputStream fops = new FileOutputStream(file , true);
            ObjectOutputStream oos = new ObjectOutputStream(fops);
            oos.writeObject(this.myGraph);
            return true;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * loads a weighted_graph and initiates this class to it.
     * @param file - file name
     * @return true if save is succesful.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            weighted_graph wgLoad = (weighted_graph) ois.readObject();
            this.init(wgLoad);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
