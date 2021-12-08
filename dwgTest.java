import api.EdgeData;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class dwgTest {
     HashMap<Integer, NodeData> Nodes= new HashMap<Integer, NodeData>();
     HashMap<Point2D, EdgeData> Edges=new HashMap<Point2D, EdgeData>();
     dwg graph = new dwg(Nodes,Edges);
     location l1 = new location(1,2,0);
     location l2 = new location(2,1,0);
     location l3 = new location(3,4,0);
     location l4 = new location(5,6,0);
     Node n1 = new Node(l1,3.0,1,0,"");
     Node n2 = new Node(l2,4.0,2,0,"");
     Node n3 = new Node(l3,5.0,3,0,"");
     Node n4 = new Node(l4,6.0,4,0,"");
     edge e1 = new edge(n1,n2,7,0,"");
     edge e2 = new edge(n3,n4,12,0,"");




    @Test
    void getNode() {
        graph.addNode(n1);
        graph.getNode(1).equals(n1.getKey());
    }

    @Test
    void getEdge() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.connect(n1.getKey(),n2.getKey(),1);
        graph.connect(n2.getKey(),n3.getKey(),2);
        graph.connect(n4.getKey(),n3.getKey(),3);
        edge e1a = (edge) graph.getEdge(n1.getKey(),n2.getKey());
        edge e2a = (edge) graph.getEdge(n3.getKey(),n4.getKey());
        e1.equals(e1a);
        e2.equals(e2a);
    }

    @Test
    void addNode() {
        graph.addNode(n1);
        graph.getNode(1).equals(n1.getKey());
    }

    @Test
    void connect() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.connect(n1.getKey(),n2.getKey(),1);
        graph.connect(n2.getKey(),n3.getKey(),2);
        graph.connect(n4.getKey(),n3.getKey(),3);
        edge e11 = (edge) graph.getEdge(n1.getKey(),n2.getKey());
        edge e22 = (edge) graph.getEdge(n3.getKey(),n4.getKey());
        e1.equals(e11);
        e2.equals(e22);
    }

    @Test
    void nodeIter() {
        Iterator<NodeData> iterator = graph.nodeIter();
        int i =0;
        if(graph.getNodes()!=null){
            while (iterator.hasNext()){
                NodeData curr = iterator.next();
                Node n = (Node) graph.getNodes().get(i);
                System.out.println(n);
                assertEquals(curr.getKey(),n.getKey());
                assertEquals(curr.getInfo(),n.getInfo());
                assertEquals(curr.getWeight(),n.getWeight());
                assertEquals(curr.getTag(),n.getTag());
                i++;
            }
        }
    }

    @Test
    void edgeIter() {
        Iterator<EdgeData> iterator = graph.edgeIter();
        int i =0;
        if(graph.getEdges()!=null){
            while (iterator.hasNext()){
                EdgeData curr = iterator.next();
                Point2D p =new Point(i,curr.getDest());
                assertEquals(curr.getSrc(),graph.getEdges().get(p).getSrc());
                assertEquals(curr.getDest(),graph.getEdges().get(p).getDest());
                assertEquals(curr.getInfo(),graph.getEdges().get(p).getInfo());
                assertEquals(curr.getWeight(),graph.getEdges().get(p).getWeight());
                assertEquals(curr.getTag(),graph.getEdges().get(p).getTag());
                i++;
            }
        }
    }

    @Test
    void testEdgeIter() {
        Iterator<EdgeData> iterator = graph.edgeIter(0);
        while (iterator.hasNext()) {
            EdgeData e = iterator.next();
            assertEquals(e.getSrc(), 0);
            assertEquals(e.getDest(), graph.getEdge(0,e.getDest()).getDest());
            assertEquals(e.getInfo(), graph.getEdge(e.getSrc(),e.getDest()).getInfo());
            assertEquals(e.getWeight(), graph.getEdge(e.getSrc(),e.getDest()).getWeight());
            assertEquals(e.getTag(), graph.getEdge(e.getSrc(),e.getDest()).getTag());
        }
    }



    @Test
    void removeNode() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.connect(n1.getKey(),n2.getKey(),1);
        graph.connect(n2.getKey(),n3.getKey(),2);
        graph.connect(n4.getKey(),n3.getKey(),3);
        graph.removeNode(2);
        assertNull(graph.removeNode(2));
    }

    @Test
    void removeEdge() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.connect(n1.getKey(),n2.getKey(),4);
        graph.connect(n2.getKey(),n3.getKey(),3);
        graph.connect(n4.getKey(),n3.getKey(),6);
        graph.removeEdge(1,2);
        assertNull(graph.getEdge(1,2));
    }

    @Test
    void nodeSize() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        assertEquals(graph.nodeSize(),4);
    }

    @Test
    void edgeSize() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.connect(n1.getKey(),n2.getKey(),4);
        graph.connect(n2.getKey(),n3.getKey(),3);
        graph.connect(n4.getKey(),n3.getKey(),6);
        assertEquals(graph.edgeSize(),3);
    }

    @Test
    void getMC() {
        dwg graph = new dwg(Nodes,Edges);
        assertEquals(graph.getMC(),0);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.connect(n1.getKey(),n2.getKey(),4);
        graph.connect(n2.getKey(),n3.getKey(),3);
        graph.connect(n4.getKey(),n3.getKey(),6);
        graph.removeEdge(1,2);
        assertEquals(graph.getMC(),8);
    }
}