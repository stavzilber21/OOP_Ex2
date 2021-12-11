import api.EdgeData;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
    void copy(){
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
        graph_a.init(graph);
        graph_a.getGraph().addNode(n0);
        graph_a.getGraph().addNode(n1);
        graph_a.getGraph().addNode(n2);
        graph_a.getGraph().addNode(n3);
        graph_a.getGraph().addNode(n4);
        graph_a.getGraph().connect(n0.getKey(),n1.getKey(),4);
        graph_a.getGraph().connect(n0.getKey(),n2.getKey(),4);
        graph_a.getGraph().connect(n1.getKey(),n2.getKey(),4);
        graph_a.getGraph().connect(n2.getKey(),n3.getKey(),3);
        graph_a.getGraph().connect(n3.getKey(),n4.getKey(),6);
        graph_a.getGraph().connect(n4.getKey(),n1.getKey(),6);
        boolean isConnected0 = graph_a.isConnected();
        assertEquals(false,isConnected0);

        graph_a.getGraph().removeEdge(2,3);
        boolean isConnected1 = graph_a.isConnected();
        assertEquals(false,isConnected1);

        graph_a.getGraph().connect(n2.getKey(),n3.getKey(),6);
        graph_a.getGraph().connect(n4.getKey(),n0.getKey(),6);
        boolean isConnected2 = graph_a.isConnected();
        assertEquals(true,isConnected2);
    }

    @Test
    void shortestPathDist() {
        graph_a.init(graph);
        graph_a.getGraph().addNode(n0);
        graph_a.getGraph().addNode(n1);
        graph_a.getGraph().addNode(n2);
        graph_a.getGraph().addNode(n3);
        graph_a.getGraph().addNode(n4);
        graph_a.getGraph().connect(n0.getKey(), n1.getKey(), 4);
        graph_a.getGraph().connect(n1.getKey(), n2.getKey(), 4);
        graph_a.getGraph().connect(n2.getKey(), n3.getKey(), 3);
        graph_a.getGraph().connect(n3.getKey(), n4.getKey(), 5);
        graph_a.getGraph().connect(n4.getKey(), n1.getKey(), 6);
        for (int i = 0; i < graph_a.getGraph().nodeSize(); i++) {
            System.out.println(graph_a.getGraph().getNode(i));
        }
        Node n0 = new Node(l1, 7.0, 0, 0, "");
        Node n1 = new Node(l1, 3.0, 1, 0, "");
        Node n2 = new Node(l2, 4.0, 2, 0, "");
        Node n3 = new Node(l3, 5.0, 3, 0, "");
        Node n4 = new Node(l4, 6.0, 4, 0, "");
        for(int i=0; i<graph_a.getGraph().nodeSize();i++) {
            Iterator iterSrc = ((Node) graph_a.getGraph().getNode(i)).getIter();
            while (iterSrc.hasNext()) {
                edge edge = (edge)iterSrc.next();
                System.out.println(edge.getSrc()+","+edge.getDest());
            }
        }
        double a = graph_a.shortestPathDist(n1.getKey(),n3.getKey());
        assertEquals(7,a);

        double b = graph_a.shortestPathDist(n0.getKey(),n1.getKey());
        assertEquals(4,b);

        double c = graph_a.shortestPathDist(n1.getKey(),n4.getKey());
        assertEquals(12,c);

    }

    @Test
    void shortestPath() {
        graph_a.init(graph);
        graph_a.getGraph().addNode(n0);
        graph_a.getGraph().addNode(n1);
        graph_a.getGraph().addNode(n2);
        graph_a.getGraph().addNode(n3);
        graph_a.getGraph().addNode(n4);
        graph_a.getGraph().connect(n0.getKey(), n1.getKey(), 4);
        graph_a.getGraph().connect(n1.getKey(), n2.getKey(), 4);
        graph_a.getGraph().connect(n2.getKey(), n3.getKey(), 3);
        graph_a.getGraph().connect(n3.getKey(), n4.getKey(), 5);
        graph_a.getGraph().connect(n4.getKey(), n1.getKey(), 6);

        List<NodeData> a = new ArrayList<>();
        a.add(n2);
        a.add(n3);
        a.add(n4);
        assertEquals(a,graph_a.shortestPath(1,4));

        graph_a.getGraph().connect(n1.getKey(), n4.getKey(), 4);
        List<NodeData> b = new ArrayList<>();
        b.add(n4);
        assertEquals(b,graph_a.shortestPath(1,4));

        graph_a.getGraph().connect(n1.getKey(), n0.getKey(), 8);
        graph_a.getGraph().connect(n2.getKey(), n0.getKey(), 2);
        List<NodeData> c = new ArrayList<>();
        c.add(n1);
        c.add(n2);
        c.add(n0);
        assertEquals(c,graph_a.shortestPath(4,0));
    }

    @Test
    void center() {
        graph_a.init(graph);
        graph_a.getGraph().addNode(n0);
        graph_a.getGraph().addNode(n1);
        graph_a.getGraph().addNode(n2);
        graph_a.getGraph().addNode(n3);
        graph_a.getGraph().addNode(n4);
        graph_a.getGraph().connect(n0.getKey(),n1.getKey(),4);
        graph_a.getGraph().connect(n0.getKey(),n2.getKey(),4);
        graph_a.getGraph().connect(n1.getKey(),n2.getKey(),4);
        graph_a.getGraph().connect(n2.getKey(),n3.getKey(),3);
        graph_a.getGraph().connect(n3.getKey(),n4.getKey(),6);
        graph_a.getGraph().connect(n4.getKey(),n1.getKey(),6);
        graph_a.getGraph().connect(n4.getKey(),n0.getKey(),6);
        Node a = (Node)graph_a.center();
        assertEquals(n1.getKey(),a.getKey());
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