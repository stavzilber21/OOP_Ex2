import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    static location l1 = new location(1,2,0);
    static location l2 = new location(2,1,0);
    static Node n1 = new Node(l1,3.0,1,0,"");
    static Node n2 = new Node(l2,4.0,2,0,"");

    @org.junit.jupiter.api.Test
    void getKey() {
        assertEquals(n1.getKey(),1);
        assertEquals(n2.getKey(),2);
    }

    @org.junit.jupiter.api.Test
    void getLocation() {
        assertEquals(n1.getLocation(),l1);
    }

    @org.junit.jupiter.api.Test
    void setLocation() {
        location g = new location(1,1,1);
        Node n = new Node();
        n.setLocation(g);
        assertEquals(n.getLocation(),g);
    }

    @org.junit.jupiter.api.Test
    void getWeight() {
        assertEquals(n1.getWeight(),3.0);
        assertEquals(n2.getWeight(),4.0);
    }

    @org.junit.jupiter.api.Test
    void setWeight() {
        Node n = new Node();
        n.setWeight(23);
        assertEquals(n.getWeight(),23.0);
    }

    @org.junit.jupiter.api.Test
    void getInfo() {
        assertEquals(n1.getInfo(),"");
    }

    @org.junit.jupiter.api.Test
    void setInfo() {
        n1.setInfo("stav");
        assertEquals(n1.getInfo(),"stav");
    }

    @org.junit.jupiter.api.Test
    void getTag() {
        assertEquals(n1.getTag(),0);
    }

    @org.junit.jupiter.api.Test
    void setTag() {
        n1.setTag(1);
        assertEquals(n1.getTag(),1);
    }


}