import api.EdgeData;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

class dwgAlgorithmTest {
    HashMap<Integer, NodeData> Nodes= new HashMap<Integer, NodeData>();
    HashMap<Point2D, EdgeData> Edges=new HashMap<Point2D, EdgeData>();
    dwg graph = new dwg(Nodes,Edges);
    location l1 = new location(1,2,0);
    location l2 = new location(2,1,0);
    location l3 = new location(3,4,0);
    location l4 = new location(5,6,0);
    Node n0 = new Node(l1,7.0,0,0,"");
    Node n1 = new Node(l1,3.0,1,0,"");
    Node n2 = new Node(l2,4.0,2,0,"");
    Node n3 = new Node(l3,5.0,3,0,"");
    Node n4 = new Node(l4,6.0,4,0,"");
    edge e1 = new edge(n1,n2,7,0,"");
    edge e2 = new edge(n3,n4,12,0,"");
    dwgAlgorithm graph_a = new dwgAlgorithm(null);
    dwgAlgorithm graph_b = new dwgAlgorithm(null);
    dwgAlgorithm graph_c = new dwgAlgorithm(null);



    @Test
    void init() {
        long startTime = System.currentTimeMillis();
        graph_a.init(graph);
        assertEquals(graph_a.getGraph(),graph);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
    }

    @Test
    void getGraph() {
        graph_a.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\1000Nodes.json");
        long startTime = System.currentTimeMillis();
        assertEquals(graph_a.getGraph(),graph);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        graph_b.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\10000Nodes.json");
        startTime = System.currentTimeMillis();
        assertEquals(graph_b.getGraph(),graph);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        graph_c.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\100000.json");
        startTime = System.currentTimeMillis();
        assertEquals(graph_c.getGraph(),graph);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

    }

    @Test
    void copy(){

        graph_a.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\1000Nodes.json");
        long startTime = System.currentTimeMillis();
        dwg graph2 = (dwg)graph_a.copy();
        graph_a.getGraph().equals(graph2);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        graph_b.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\10000Nodes.json");
        startTime = System.currentTimeMillis();
        dwg graph1 = (dwg)graph_b.copy();
        graph_b.getGraph().equals(graph1);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        graph_c.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\100000.json");
        startTime = System.currentTimeMillis();
        dwg graph3 = (dwg)graph_c.copy();
        graph_c.getGraph().equals(graph3);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

    }

    @Test
    void isConnected() {
        graph_a.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\1000Nodes.json");
        long startTime = System.currentTimeMillis();
        boolean isConnected0 = graph_a.isConnected();
        assertEquals(true,isConnected0);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");


        graph_a.getGraph().removeEdge(2,3);
        startTime = System.currentTimeMillis();
        boolean isConnected1 = graph_a.isConnected();
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        assertEquals(true,isConnected1);

        startTime = System.currentTimeMillis();
        boolean isConnected2 = graph_a.isConnected();
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        assertEquals(true,isConnected2);

        graph_b.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\10000Nodes.json");
        startTime = System.currentTimeMillis();
        boolean isConnected3 = graph_b.isConnected();
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        assertEquals(true,isConnected3);

        graph_c.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\100000Nodes.json");
        startTime = System.currentTimeMillis();
        boolean isConnected4 = graph_c.isConnected();
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        assertEquals(true,isConnected4);
    }

    @Test
    void shortestPathDist() {

        graph_a.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\1000Nodes.json");
        long startTime = System.currentTimeMillis();
        double a = graph_a.shortestPathDist(12,900);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        assertEquals(1983.0043901916415,a);

        startTime = System.currentTimeMillis();
        double b = graph_a.shortestPathDist(2,501);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        assertEquals(3946.2547384865434,b);

        startTime = System.currentTimeMillis();
        double c = graph_a.shortestPathDist(216,814);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        assertEquals(4197.852828481402,c);

        graph_b.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\10000Nodes.json");
        startTime = System.currentTimeMillis();
        double d = graph_b.shortestPathDist(216,814);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        assertEquals(999999.0,d);

        graph_c.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\100000Nodes.json");
        startTime = System.currentTimeMillis();
        double e = graph_c.shortestPathDist(216,814);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
//        assertEquals(,e);

    }

    @Test
    void shortestPath() {

        graph_a.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\1000Nodes.json");

        long startTime = System.currentTimeMillis();
        List<NodeData> a = graph_a.shortestPath(10,999);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");


        startTime = System.currentTimeMillis();
        List<NodeData> b =graph_a.shortestPath(600,51);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();
        List<NodeData> c =graph_a.shortestPath(320,893);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        graph_b.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\10000Nodes.json");
        startTime = System.currentTimeMillis();
        List<NodeData> d =graph_b.shortestPath(320,7399);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();
        List<NodeData> e =graph_b.shortestPath(15,6598);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
    }

    @Test
    void center() {
        graph_a.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\1000Nodes.json");
        long startTime = System.currentTimeMillis();
        Node a = (Node)graph_a.center();
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        System.out.println(a.getKey());

        graph_b.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\10000Nodes.json");
        startTime = System.currentTimeMillis();
        Node b = (Node)graph_b.center();
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        System.out.println(b.getKey());
    }


    @Test
    void tsp() {
        graph_a.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\1000Nodes.json");
        List<NodeData> a = Arrays.asList(graph_a.getGraph().getNode(878),graph_a.getGraph().getNode(987),graph_a.getGraph().getNode(3),graph_a.getGraph().getNode(67),graph_a.getGraph().getNode(15));
        long startTime = System.currentTimeMillis();
        graph_a.tsp(a);
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        List<NodeData> b = Arrays.asList(graph_a.getGraph().getNode(788),graph_a.getGraph().getNode(13),graph_a.getGraph().getNode(999),graph_a.getGraph().getNode(6),graph_a.getGraph().getNode(80));
        startTime = System.currentTimeMillis();
        graph_a.tsp(b);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        graph_b.load("C:\\Users\\User\\IdeaProjects\\graph\\src\\data\\10000Nodes.json");
        List<NodeData> c = Arrays.asList(graph_b.getGraph().getNode(7848),graph_b.getGraph().getNode(163),graph_b.getGraph().getNode(9699),graph_b.getGraph().getNode(654),graph_b.getGraph().getNode(80));
        startTime = System.currentTimeMillis();
        graph_b.tsp(c);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

        List<NodeData> d = Arrays.asList(graph_b.getGraph().getNode(1),graph_b.getGraph().getNode(13),graph_b.getGraph().getNode(9499),graph_b.getGraph().getNode(63),graph_b.getGraph().getNode(8460));
        startTime = System.currentTimeMillis();
        graph_b.tsp(d);
        endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");

    }

}