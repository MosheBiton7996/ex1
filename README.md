# ex1
 ex1 of Object Orinatated Programming class

This document explains the methods used in the Java Project "ex1".

ex1.tests:
test classes to test the classes implemented in the project
-WGraph_DSTest - tests WGraph_DS.
-WGraph_AlgoTest - tests WGraph_Algo.

ex1.src:
Classes implemented in the project.
-NodeInfo (implements given interface node_info), represents a node (vertex) in a (undirectional) weighted graph. implemented privatly on WGraph_DS.
-WGraph_DS (implements givan interface weighted_graph) represents an undirectional weighted graph.
-WGraph_Algo (implements given interface weighted_graph_algorithms), an algorithm class works on "weighted_graph" variables.

NodeInfo:
uses the following parameters:
key (an integer unique to every node), info (string value stored in node) and tag (an integer to mark a node for sorts to be determined).

The methods implemented in the  java class:
public NodeData(int key).
public NodeData(node_data n).
public int getKey().
get \ set Info.
get \ set Tag.

WGraph_DS:
uses the following parameters:
nodes (HashMap of the nodes in the graph), nodeSize(the amount of nodes in the graph), edgeSize(the amount of connections between nodes in the graph),
mcCounter (a counter of the actions that changes the graph), edges (HashMap of edges between nodes in the graph and their weight).

The methods implemented in the  java class:
public WGraph_DS().
public WGraph_DS(weighted_graph origin).
public node_info getNode(int key).
public boolean hasEdge(int node1, int node2) .
public double getEdge(int node1, int node2).
public void addNode(node_data n).
public void connect(int node1, int node2, double w).
public Collection<node_info> getV() .
public Collection<node_info> getV(int node_id).
public node_info removeNode(int key).
public void removeEdge(int node1, int node2).
public int nodeSize() \ edgeSize() \ getMC().

 WGraph_Algo:
uses the following parameters:
myGraph  - the graph which the algorithm class works on.

The methods implemented in the  java class:
public void init(weighted_graph g) - initiates the graph the class works on.
public weighted_graph copy() - creates a deep copy of myGraph using Graph_DS(graph origin).
public void  resetTagsTo(int prm) - resets all nodes' tags to the parameter required with an iterator going through myGraph's values using getV().
public boolean isConnected() - checks if all the nodes connected to an undirectional unweighted graph. using an iterator in a loop to get a node and marks it (using the tag).
the iterator runs through the node's neighbors, marks them and by adding them to a queue checks all the connected nodes using using getNi().
finally, an iterator checks in a loop if theres an unmarked node.
public double shortestPathDist(int src, int dest) - returns the shortest path's distance to a destination node from the source node using the Dijkstra algorithm. The function resets all tags to -1 using resetTagsTo(-1).
An iterator sets every node's tag to the distance from the source starting from 0 on source node.
if there's no path the function returns -1 since the destination node's tag was reset to -1.
public List<node_info> shortestPath(int src, int dest) - returns the shortest path as a list to a destination node from the source node using Dijkstra's algorithm.
     using shortestPathDist(int src, int dest) all the nodes has they're tags set to the distance from the source to them.
     if there's no such path the function returns null. else, the function
     adds each time a node to a list from the destination node to the source using the tags to decide the path
     (the path must go through each minimal neighbor tag from n to 0 once, n being the distance to destination node).
public boolean save(String file) - saves the underlying graph.
public boolean load(String file) - loads a weighted_graph and initiates this class object to it.


 