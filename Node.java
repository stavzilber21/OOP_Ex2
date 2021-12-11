import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import java.util.*;

public class Node implements NodeData, Comparator<Node> {
    private GeoLocation location;
    private double weight;
    private int key;
    private int tag;
    private List<EdgeData> edges;
    private String info;

    public Node(double x, double y, double z, double weight, int key) {
        GeoLocation location = new location(x, y, z);
        this.location = location;
        this.weight = weight;
        this.key = key;
        this.edges = new LinkedList<>();
        this.tag = tag;
        this.info = info;
        this.edges = new ArrayList<>();
    }

    public Node(GeoLocation location, double weight, int key) {
        this.location = location;
        this.weight = weight;
        this.key = key;
        this.tag = tag;
        this.info = info;
        this.edges = new ArrayList<>();
    }

    public Node(GeoLocation location, double weight, int key, int tag, String info) {
        this.location = location;
        this.weight = weight;
        this.key = key;
        this.tag = tag;
        this.info = info;
        this.edges = new ArrayList<>();
    }

    public Node(Node other) {
        this.location = new location((location) other.location);
        this.weight = other.weight;
        this.key = other.key;
        this.tag = other.tag;
        this.info = other.info;
        this.edges=other.edges;
    }


    public Node() {
        this.weight = Double.MAX_VALUE;
    }

    public void addToList(EdgeData e) {
        this.edges.add(e);
    }

    public List<EdgeData> getEdges() {
        return edges;
    }

    public Iterator<EdgeData> getIter() {
        return this.edges.iterator();
    }

    public void setEdges(List<EdgeData> edges) {
        this.edges = edges;
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
        this.location = p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public int compare(Node a, Node b) {
        if (a.weight < b.weight) {
            return -1;
        }
        if (a.weight > b.weight) {
            return 1;
        }
        return 0;
    }
    public void removeEdge(int src, int dest){
        for(int i=0; i<this.edges.size(); i++) {
            if(edges.get(i).getDest()==dest && edges.get(i).getSrc()==src) {
                this.edges.remove(edges.get(i));
            }
        }
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
