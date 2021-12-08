import api.EdgeData;

public class edge implements EdgeData {
    private Node src;
    private Node dest;
    private double weight;
    private int tag;
    private String info;


    public edge(Node src, Node dest, double weight){
        this.src=src;
        this.dest=dest;
        this.weight=weight;
    }

    public edge(Node src, Node dest, double weight, int tag, String info){
        this.src=src;
        this.dest=dest;
        this.weight=weight;
        this.tag=tag;
        this.info=info;
    }
    public edge(edge other){
        this.src = new Node(other.src);
        this.dest = new Node(other.dest);
        this.weight=other.weight;
        this.tag=other.tag;
        this.info=other.info;
    }

    @Override
    public int getSrc() {
        return src.getKey();
    }

    @Override
    public int getDest() {
        return dest.getKey();
    }

    @Override
    public double getWeight() {
        return this.weight;
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
}
