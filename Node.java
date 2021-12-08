import api.GeoLocation;
import api.NodeData;

import java.util.Comparator;

public class Node implements NodeData , Comparator<Node> {
    private GeoLocation location;
    private double weight;
    private int key;
    private int tag;
    private String info;

    public Node(double x, double y, double z, double weight, int key){
        GeoLocation location = new location(x,y,z);
        this.location=location;
        this.weight=weight;
        this.key=key;
        this.tag=tag;
        this.info=info;
    }

    public Node(GeoLocation location, double weight, int key){
        this.location=location;
        this.weight=weight;
        this.key=key;
        this.tag=tag;
        this.info=info;
    }
    public Node(GeoLocation location, double weight, int key, int tag, String info){
        this.location=location;
        this.weight=weight;
        this.key=key;
        this.tag=tag;
        this.info=info;
    }

    public Node (Node other){
        this.location = new location((location) other.location);
        this.weight=other.weight;
        this.key=other.key;
        this.tag=other.tag;
        this.info=other.info;
    }


    public Node() {
        this.weight=Double.MAX_VALUE;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location=p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
      this.tag=t;
    }

    @Override
    public int compare(Node a, Node b) {
        if (a.weight<b.weight) {
            return -1;
        }
        if (a.weight>b.weight){
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "location=" + location +
                ", weight=" + weight +
                ", key=" + key +
                ", tag=" + tag +
                ", info='" + info + '\'' +
                '}';
    }
}
