import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class locationTest {

    location l1 = new location(1,2,0);
    location l2 = new location(2,1,0);

    @Test
    void x() {
        assertEquals(l1.x(),1);
        assertEquals(l2.x(),2);
    }

    @Test
    void y() {
        assertEquals(l1.y(),2);
        assertEquals(l2.y(),1);
    }

    @Test
    void z() {
        assertEquals(l1.z(),0);
        assertEquals(l2.z(),0);
    }


    @Test
    void distance() {
        assertEquals(l1.distance(l2), 1.4142135623730951);
        assertEquals(l1.distance(new location(5,6,0)), 5.656854249492381);

    }
}