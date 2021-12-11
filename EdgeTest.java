import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    location l1 = new location(1,2,0);
    location l2 = new location(2,1,0);
    Node n1 = new Node(l1,3.0,1,0,"");
    Node n2 = new Node(l2,4.0,2,0,"");
    edge e = new edge(n1,n2,7,0,"");

    @Test
    void getSrc() {
        assertEquals(e.getSrc(),1);
    }

    @Test
    void getDest() {
        assertEquals(e.getDest(),2);
    }

    @Test
    void getWeight() {
        assertEquals(e.getWeight(),7);
    }

    @Test
    void getInfo() {
        assertEquals(e.getInfo(),"");
    }

    @Test
    void setInfo() {
        e.setInfo("stav");
        assertEquals(e.getInfo(),"stav");
    }

    @Test
    void getTag() {
        assertEquals(e.getTag(),0);
    }

    @Test
    void setTag() {
        e.setTag(1);
        assertEquals(e.getTag(),1);
    }
}