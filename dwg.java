import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class dwg implements DirectedWeightedGraph {
    private Map<Integer, NodeData> Nodes= new HashMap<Integer, NodeData>();
    private int MC;
    private Map<Point2D, EdgeData> Edges=new HashMap<Point2D, EdgeData>();



    public dwg(HashMap Nodes, HashMap Edges){
        this.Nodes=Nodes;
        this.Edges=Edges;
        this.MC=0;
    }

    public dwg(dwg other){
        HashMap<Integer, NodeData> Nodes= new HashMap<Integer, NodeData>();
        HashMap<Point2D, EdgeData> Edges=new HashMap<Point2D, EdgeData>();
        for (int i=0; i<other.nodeSize(); i++){
            Node node = new Node((Node) other.getNode(i));
            Nodes.put(i, node);
        }
        while(other.edgeIter().hasNext()){
            edge edge = new edge((edge) other.edgeIter().next());
            Point2D point = new Point(edge.getSrc(),edge.getDest());
            Edges.put(point,edge);
        }
        dwg answer = new dwg(Nodes, Edges);
        this.Edges= answer.Edges;
        this.MC=0;
        this.Nodes= answer.Nodes;


    }

    @Override
    public NodeData getNode(int key) {
        return this.Nodes.get(key);
    }
    @Override
    public EdgeData getEdge(int src, int dest) {
        Point2D p =new Point(src,dest);
        if(Edges.get(p)!=null)
            return Edges.get(p);
        return null;
    }

    @Override
    public void addNode(NodeData n) {
        this.Nodes.put(n.getKey(),n);
        MC++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        Node s = (Node) getNode(src);
        Node d = (Node) getNode(dest);
        Point2D p =new Point(src,dest);
        edge e = new edge(s,d,w,0,"");
        this.Edges.put(p,e);
        MC++;
        ((Node)this.Nodes.get(src)).addToList(e);
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        try {
            Iterator <NodeData> iterator =Nodes.values().iterator();
            return iterator;
        }
        catch (Exception e ){
            return null;
        }

    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        try {
            Iterator <EdgeData> iterator =Edges.values().iterator();
            return iterator;
        }
        catch (Exception e ){
            return null;
        }
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        Iterator<EdgeData> iterator = Edges.values().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            for (int i = 0; i < this.Edges.size(); i++) {
                if ( this.Edges.get(i) != null &&this.Edges.get(i).getSrc() != node_id) {
                    iterator.remove();
                }
            }
        }
        return iterator;
    }
//        HashMap<Point2D, EdgeData> iter =new HashMap<Point2D, EdgeData>();
//        for (int i = 0 ; i<this.Edges.size();i++){
//            if (this.Edges.get(i).getSrc() == node_id){
//                Point2D p =new Point(this.Edges.get(i).getSrc(),this.Edges.get(i).getDest());
//                iter.put(p,this.Edges.get(i));
//            }}
//        return iter.values().iterator();


    @Override
    public NodeData removeNode(int key) {
        if (!Edges.isEmpty()) {
            //remove all edges in the iterator whose source or destination are key
            for (int i = 0; i < Nodes.size(); i++) {
                for (int j = 0; j < Nodes.size(); j++) {
                    if(i != j) {
                        if (i == key) {
                            Point2D temp = new Point(i, j);
                            this.Edges.remove(temp);
                        }
                        if (j == key) {
                            Point2D temp = new Point(i, j);
                            this.Edges.remove(temp);
                        }
                    }
                }
            }
        }
        //remove all edges in the edge list of key
        ArrayList hold = new ArrayList();
        for(int i = 0; i < Nodes.size(); i++) {
            Iterator iterSrc = ((Node) Nodes.get(i)).getIter();
            while (iterSrc.hasNext()) {
                edge temp = (edge) iterSrc.next();
                if (temp.getDest() == key) {
                    hold.add(temp);
                }
            }
        }
        for(int i = 0; i < Nodes.size(); i++) {
            for(int j=0; j<hold.size(); j++){
                if(((Node) Nodes.get(i)).getEdges().contains(hold.get(j))){
                    ((Node) Nodes.get(i)).getEdges().remove(hold.get(j));
                }
            }
        }

            return this.Nodes.remove(key);
        }


        @Override
        public EdgeData removeEdge(int src, int dest) {
            Point2D p =new Point(src,dest);
            edge copy = (edge) getEdge(src, dest);
            this.Edges.remove(p);

            //Remove from src node's list of edges
            ((Node)Nodes.get(src)).removeEdge(src,dest);
            MC++;
            return copy;
        }

        @Override
        public int nodeSize() {
            return Nodes.size();
        }

        @Override
        public int edgeSize() {
            return Edges.size();
        }

        @Override
        public int getMC() {
            return MC;
        }

        public Map getNodesMap() {
            return this.Nodes;
        }
        public Map getEdgesMap() {
            return this.Edges;
        }

        public String toString(DirectedWeightedGraph graph){
            Iterator itNodes = this.nodeIter();
            Iterator itEdges = this.edgeIter();

            String answer = "{\n" + "\"Edges\": [\n";

            while(edgeIter().hasNext()){
                answer = answer+edgeIter().next().toString();
                if(edgeIter().hasNext()){
                    answer = answer + ",\n";
                }
                else{
                    answer = answer + "\n";
                }
            }

            answer = answer +"],\n" + "\"Nodes\":[";
            while(nodeIter().hasNext()){
                answer = answer+nodeIter().next().toString();
                if(nodeIter().hasNext()){
                    answer = answer + ",\n";
                }
                else{
                    answer = answer + "\n";
                }
            }
            answer = answer + "]\n" + '}';
            return answer;
        }
        public Map<Point2D, EdgeData> getEdges(){
            return this.Edges;
        }

        public void setEdges(Map<Point2D, EdgeData> Edges){
            this.Edges=Edges;
        }

        public Map<Integer, NodeData> getNodes(){
            return this.Nodes;
        }

        public void setNodes(Map<Integer, NodeData> Nodes){
            this.Nodes=Nodes;
        }

        @Override
        public String toString() {
            return "dwg{" +
                    "Nodes=" + Nodes +
                    ", MC=" + MC +
                    ", Edges=" + Edges +
                    '}';
        }
    }
