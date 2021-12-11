
import api.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;

public class dwgAlgorithm implements DirectedWeightedGraphAlgorithms{
    private DirectedWeightedGraph GRAPH;
    public dwgAlgorithm(){

    }

    public dwgAlgorithm(DirectedWeightedGraph g){
        this.GRAPH=g;
    }
    @Override
    public void init(DirectedWeightedGraph g) {
        this.GRAPH=g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.GRAPH;
    }

    @Override
    public DirectedWeightedGraph copy() {
        HashMap<Integer, NodeData> nodeMap = new HashMap<>();
        HashMap<Point2D, EdgeData> edgeMap = new HashMap<>();
        for(int i = 0; i < GRAPH.nodeSize(); i++){
            nodeMap.put(i,GRAPH.getNode(i));
        }
        for(int j = 0; j < GRAPH.edgeSize(); j++){
            if(GRAPH.getNode(j) != null){
                for(int r = 0; r < GRAPH.edgeSize();r++){
                    if(GRAPH.getEdge(j,r) == null){
                        continue;
                    }
                    Point2D p = new Point(j,r);
                    edgeMap.put(p,GRAPH.getEdge(j,r));
                }
            }
        }
        DirectedWeightedGraph g_copy = new dwg(nodeMap,edgeMap);
        return g_copy;
    }

    public boolean isConnected1() {
        for(int i=0; i<GRAPH.nodeSize();i++){
            for(int j=0; j<GRAPH.nodeSize();j++){
                if(i!=j){
                    if(shortestPathDist(i,j)==-1){
                        return false;
                    }
                }
            }
        }
        return true;
    }



    @Override
    public boolean isConnected() {
        boolean pre [] =new boolean[GRAPH.nodeSize()];

        for(int i=0; i<GRAPH.nodeSize(); i++) {
            dfs(GRAPH, i, pre, 0);
            if(!check(pre)){
                return false;
            }
            else{
                for(int j=0; j<GRAPH.nodeSize();j++){
                    pre[j]=false;
                }
            }
        }

        boolean post [] =new boolean[GRAPH.nodeSize()];
        dwg graph = transpose((dwg) GRAPH);
        for(int i=0; i<GRAPH.nodeSize(); i++) {
            dfs(graph, i, post, 0);
            if (!check(post)) {
                return false;
            }
            else{
                for(int j=0; j<GRAPH.nodeSize();j++){
                    post[j]=false;
                }
            }
        }
        return true;
    }

    public dwg transpose(dwg graph) {
        HashMap<Point2D, EdgeData> Edges = new HashMap<Point2D, EdgeData>();
        Iterator<EdgeData> iter = GRAPH.edgeIter();
        while (iter.hasNext()) {
            edge temp = (edge) iter.next();
            temp.p();
            Node source = (Node) GRAPH.getNode(temp.getSrc());
            Node destination =  (Node) GRAPH.getNode(temp.getDest());
            edge edge = new edge(destination,source, temp.getWeight());
            Point2D point = new Point(temp.getDest(),temp.getSrc());
            Edges.put(point, edge);
        }
        dwg g = new dwg((HashMap) graph.getNodes(), Edges);
        return g;
    }


    public boolean check(boolean[] array){
        for(int i=0; i<array.length;i++){
            if(array[i]!=true){
                return false;
            }
        }
        return true;
    }

    public void dfs(DirectedWeightedGraph g, int u, boolean visited[], int count) {
        {
            visited[u] = true;
            if(check(visited)){
                return;
            }
            Iterator itEdge =((Node)GRAPH.getNode(u)).getIter();
            if(itEdge==null){
                return;
            }
            while(itEdge.hasNext()) {
                edge ed = (edge) itEdge.next();
                int dest = ed.getDest();
                if (visited[dest] == false) {
                    count++;
                    dfs(g, dest, visited, count);
                }
            }
        }
    }

//    public HashMap<Point2D, EdgeData> neighbors(int src){
//        HashMap<Point2D, EdgeData> answer =new HashMap<>();
//        Iterator<EdgeData> iter = GRAPH.edgeIter();
//        while(iter.hasNext()){
//            edge temp = (edge)iter.next();
//            if(temp.getSrc()==src){
//                Point2D point = new Point(temp.getSrc(),temp.getDest());
//                edge t = new edge(temp);
//                answer.put(point,t);
//            }
//        }
//
//        return answer;
//    }


    @Override
    public double shortestPathDist(int src, int dest) {
        double answer = 0.0;
        int almost = -1;
        double weight = Double.MAX_VALUE;
        while(almost!=dest) {
            Iterator iterSrc = ((Node) GRAPH.getNode(src)).getIter();
            while (iterSrc.hasNext()) {
                edge temp = (edge) iterSrc.next();
                if (((Node) GRAPH.getNode(src)).getEdges().size() == 1) {
                    weight = temp.getWeight();
                    src = temp.getDest();
                } else if (weight > temp.getWeight()) {
                    weight = temp.getWeight();
                    src = temp.getDest();
                }
            }
            answer += weight;
            almost = src;
        }
        return answer;
    }


    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> answer = new ArrayList<>();
        int almost = -1;
        double weight = Double.MAX_VALUE;
        while(almost!=dest) {
            Iterator iterSrc = ((Node) GRAPH.getNode(src)).getIter();
            while (iterSrc.hasNext()) {
                edge temp = (edge) iterSrc.next();
                if (((Node) GRAPH.getNode(src)).getEdges().size() == 1) {
                    weight = temp.getWeight();
                    src = temp.getDest();
                } else if (weight > temp.getWeight()) {
                    weight = temp.getWeight();
                    src = temp.getDest();
                }
            }
            System.out.println(((Node) GRAPH.getNode(src)).getKey());
            answer.add(((Node) GRAPH.getNode(src)));
            almost = src;
        }
        return answer;
    }

    @Override
    public NodeData center() {
//        if (!this.isConnected()) {
//            return null;
//        }
        double max;
        double min = Double.MAX_VALUE;
        NodeData centerOfGraph = null;
        for (Integer me :((dwg) this.getGraph()).getNodes().keySet()) {
            Node node = (Node) GRAPH.getNode(me);
            max = Double.MIN_VALUE;
            for (Integer me1 :((dwg) this.getGraph()).getNodes().keySet()) {
                Node node1 = (Node) GRAPH.getNode(me1);
                if (node1.getKey() != node.getKey()) {
                    double temp = shortestPathDist(node.getKey(), node1.getKey());
                    if (temp > max) {
                        max = temp;
                    }
                }
            }
            if (max < min) {
                min = max;
                centerOfGraph = node;
            }
        }
        return centerOfGraph;
    }
//    @Override
//    public NodeData center() {
////        if (!isConnected()){
////            return null;
////        }
//        double min = Double.MAX_VALUE;
//        NodeData answer = null;
//
//        for(int i =0; i<GRAPH.nodeSize();i++){
//            if(centerOfNode(GRAPH.getNode(i))<min){
//                min = centerOfNode(GRAPH.getNode(i));
//                answer = GRAPH.getNode(i);
//            }
//        }
//        return answer;
//    }
//
//    private double longestPath(NodeData node){
//        double answer = 0.0;
//        for(int i=0; i<GRAPH.nodeSize();i++){
//            if(shortestPathDist(node.getKey(), i)>answer){
//                answer = shortestPathDist(node.getKey(), i);
//            }
//        }
//        System.out.println(answer);
//        return answer;
//    }
//
//    private double centerOfNode(NodeData node){
//        double max = Double.MIN_VALUE;
//        for(int i=0; i<GRAPH.nodeSize();i++){
////        for (Integer key :((dwg) this.getGraph()).getNodes().keySet()) {
//            if (i!=node.getKey()){
//                double temp = shortestPathDist(i , node.getKey());
//                if (temp>=max){
//                    max = temp;
//                }
//            }
//        }
//        return max;
//    }


    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (!isConnected()){
            return null;
        }
        List<NodeData> answer=new ArrayList<NodeData>();
        answer.add(cities.get(0));
        int curr = 1;
        for(int i = 1 ; i < cities.size() ; i++){
            for(int j = 2 ; j < cities.size() ;j++){
                if(!(j== curr && answer.contains(cities.get(j))) &&
                        (GRAPH.getEdge(i,answer.get(i).getKey())!=null) &&
                        (GRAPH.getEdge(j,answer.get(i).getKey())!=null))
                {
                    if(GRAPH.getEdge(i,answer.get(i).getKey()).getWeight() >
                            GRAPH.getEdge(j,answer.get(i).getKey()).getWeight()) {
                        curr = i;}
                    else if (j == cities.size()-1) {
                        answer.add(GRAPH.getNode(curr));
                        i=1;
                    }

                    else{
                        curr=j;}
                }
                else {
                    continue;
                }
            }
        }
        return answer;

    }

    @Override
    public boolean save(String file) {
        try {
            FileWriter json_file = new FileWriter(file);
            BufferedWriter b = new BufferedWriter(json_file);
            b.write(this.GRAPH.toString());
            b.close();
            json_file.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean load(String file) {
        try {
            Object ob = new JSONParser().parse(new FileReader("src/data/G1.json"));
            if (ob == null)
                throw new Exception("Error");
            JSONObject js = (JSONObject) ob;
            JSONArray edgesArr = (JSONArray) js.get("Edges");
            JSONArray nodesArr = (JSONArray) js.get("Nodes");
            Iterator itEdge = edgesArr.iterator();
            Iterator itNode = nodesArr.iterator();

            Map mapNode;
            HashMap<Integer, NodeData> nodesMap = new HashMap<>();

            while (itNode.hasNext()) {
                mapNode = (Map) itNode.next();
                int id = Integer.parseInt(Objects.toString(mapNode.get("id")));
                String[] pos = ((String) mapNode.get("pos")).split(",");
                double x = Double.parseDouble(pos[0]);
                double y = Double.parseDouble(pos[1]);
                double z = Double.parseDouble(pos[2]);
                Node node = new Node(x,y,z,0,id);
                nodesMap.put(id,node);
            }

            Map mapEdge;
            HashMap<Point2D, EdgeData> edgesMap = new HashMap<>();

            while (itEdge.hasNext()) {
                mapEdge = (Map) itEdge.next();
                int src = Integer.parseInt(Objects.toString(mapEdge.get("src")));
                int dest = Integer.parseInt(Objects.toString(mapEdge.get("dest")));
                double w = Double.parseDouble(Objects.toString(mapEdge.get("w")));
                Node one = (Node) nodesMap.get(src);
                Node two = (Node) nodesMap.get(dest);
                edge e = new edge(one,two,w);
                Point2D p =new Point(src,dest);
                ((Node)nodesMap.get(src)).addToList(e);
                edgesMap.put(p,e);

            }
            this.GRAPH= new dwg(nodesMap,edgesMap);

        } catch (Exception e) {

            System.out.println(e);
            return false;

        }
        return true;
    }
}
