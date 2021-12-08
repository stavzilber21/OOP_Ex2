import api.EdgeData;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class dwgAlgorithmTset {
    static HashMap<Integer, NodeData> Nodes= new HashMap<Integer, NodeData>();
    static HashMap<Point2D, EdgeData> Edges=new HashMap<Point2D, EdgeData>();
    static dwg graph = new dwg(Nodes,Edges);
    static location l1 = new location(1,2,0);
    static location l2 = new location(2,1,0);
    static location l3 = new location(3,4,0);
    static location l4 = new location(5,6,0);
    static Node n1 = new Node(l1,3.0,1,0,"");
    static Node n2 = new Node(l2,4.0,2,0,"");
    static Node n3 = new Node(l3,5.0,3,0,"");
    static Node n4 = new Node(l4,6.0,4,0,"");
    static edge e1 = new edge(n1,n2,7,0,"");
    static edge e2 = new edge(n3,n4,12,0,"");
    dwgAlgorithm graph_a = new dwgAlgorithm(null);
    @Test
    void init() {
        graph_a.init(graph);
        assertEquals(graph_a.getGraph(),graph);
    }

    @Test
    void getGraph() {
        graph_a.init(graph);
        assertEquals( graph_a.getGraph(),graph);
    }

    @Test
    void copy() throws CloneNotSupportedException {
         graph_a.init(graph);
         graph_a.getGraph().addNode(n1);
         graph_a.getGraph().addNode(n2);
         graph_a.getGraph().addNode(n3);
         graph_a.getGraph().addNode(n4);
         graph_a.getGraph().connect(n1.getKey(),n2.getKey(),4);
         graph_a.getGraph().connect(n2.getKey(),n3.getKey(),3);
         graph_a.getGraph().connect(n3.getKey(),n4.getKey(),6);
         graph_a.getGraph().connect(n4.getKey(),n1.getKey(),6);
         dwg graph2 = (dwg)graph_a.copy();
         graph_a.getGraph().equals(graph2);

    }

    @Test
    void isConnected() {
//        algo gr1 = new algo();
//        Node n0 = new Node(0, geo_locationTest.g1, 3,"",0);
//        Node n1 = new Node(1, geo_locationTest.g2, 3,"",0);
//        Node n2 = new Node(2,new geo_location(5,1,8), 0, "", 0);
//        Node n3 = new Node(3,new geo_location(5,6,6), 0, "", 0);
//        gr1.getGraph().addNode(n0);
//        gr1.getGraph().addNode(n1);
//        gr1.getGraph().addNode(n2);
//        gr1.getGraph().addNode(n3);
//        gr1.getGraph().connect(n0.getKey(),n1.getKey(),4);
//        gr1.getGraph().connect(n1.getKey(),n2.getKey(),3);
//        gr1.getGraph().connect(n2.getKey(),n3.getKey(),6);
//        gr1.getGraph().connect(n3.getKey(),n0.getKey(),6);
//        boolean isConnected0 = gr1.isConnected();
//        assertEquals(isConnected0,true);
//        gr1.getGraph().removeEdge(2,3);
//        boolean isConnected1 = gr1.isConnected();
//        assertEquals(isConnected1,false);
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}