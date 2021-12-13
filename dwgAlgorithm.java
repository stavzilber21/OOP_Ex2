
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
    private int INFINITI = 999999;
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
        HashMap<Integer, NodeData> NodesMap = new HashMap<>();
        HashMap<Point2D, EdgeData> EdgesMap = new HashMap<>();
        for(int i = 0; i < GRAPH.nodeSize(); i++){
            NodesMap.put(i,GRAPH.getNode(i));
        }
        for(int j = 0; j < GRAPH.edgeSize(); j++){
            if(GRAPH.getNode(j) != null){
                for(int r = 0; r < GRAPH.edgeSize();r++){
                    if(GRAPH.getEdge(j,r) == null){
                        continue;
                    }
                    Point2D point = new Point(j,r);
                    EdgesMap.put(point,GRAPH.getEdge(j,r));
                }
            }
        }
        DirectedWeightedGraph newG = new dwg(NodesMap,EdgesMap);
        return newG;
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
    public double shortestPathDist(int src, int dest) {
        double[] ans = temp_Dist(src, dest);
        return ans[dest];
    }

    public double[] temp_Dist(int src, int dest) {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        double[] dist = new double[GRAPH.nodeSize()];
        int[] prev = new int[GRAPH.nodeSize()];
        for (int i = 0; i < GRAPH.nodeSize(); i++) {
            dist[i] = INFINITI;
            prev[i] = Integer.MAX_VALUE;
            queue.add(i);
        }
        dist[src] = 0;
        int u = 0;
        double updateWeight = 0;
        while (!queue.isEmpty()) {
            u = queue.poll();
            for (int v = 0; v < GRAPH.nodeSize(); v++) {
                if (GRAPH.getEdge(u, v) == null || queue.contains(v) == false) {
                    continue;
                }
                updateWeight = dist[u] + GRAPH.getEdge(u, v).getWeight();
                if (updateWeight < dist[v]) {
                    dist[v] = updateWeight;
                    prev[v] = u;

                }
            }
        }
        return dist;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        int[] list_nodes = temp_Path(src, dest);
        List<NodeData> ans = new LinkedList<>();
        for (int i = src + 1; i < GRAPH.nodeSize(); i++) {
            ans.add(GRAPH.getNode(list_nodes[i]));
        }
        return ans;
    }

    public int[] temp_Path(int src, int dest) {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        double[] dist = new double[GRAPH.nodeSize()];
        int[] prev = new int[GRAPH.nodeSize()];
        for (int i = 0; i < GRAPH.nodeSize(); i++) {
            dist[i] = INFINITI;
            prev[i] = Integer.MAX_VALUE;
            queue.add(i);
        }
        dist[src] = 0;
        int u = 0;
        double updateWeight = 0;
        while (!queue.isEmpty()) {
            u = queue.poll();
            for (int v = 0; v < GRAPH.nodeSize(); v++) {
                if (GRAPH.getEdge(u, v) == null || queue.contains(v) == false) {
                    continue;
                }
                updateWeight = dist[u] + GRAPH.getEdge(u, v).getWeight();
                if (updateWeight < dist[v]) {
                    dist[v] = updateWeight;
                    prev[v] = u;

                }
            }
        }
        return prev;
    }

    @Override
    public NodeData center() {
        if (!isConnected()){
            return null;
        }
        double min = Double.MAX_VALUE;
        NodeData answer = null;

        for(int i =0; i<GRAPH.nodeSize();i++){
            double center = temp_center(GRAPH.getNode(i));
            if(center<min){
                min = center;
                answer = GRAPH.getNode(i);
            }
        }
        return answer;
    }

    private double temp_center(NodeData node){
        double max = Double.MIN_VALUE;
        for(int i=0; i<GRAPH.nodeSize();i++){
            if (i!=node.getKey()){
                double temp = shortestPathDist(i,node.getKey());
                if (temp>max){
                    max = temp;
                }
            }
        }
        return max;
    }


    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (!isConnected()){
            return null;
        }
        List<Integer> copy_cities = new ArrayList<>();
        List<NodeData> result = new ArrayList<>();
        int same,place;
        for (int i=0;i<cities.size();i++){
            copy_cities.add(cities.get(i).getKey());
        }
        List<NodeData> list= new ArrayList<>();
        Node temp = (Node) cities.get(0);
        result.add(GRAPH.getNode(copy_cities.get(0)));
        copy_cities.remove(0);
        while (copy_cities.size()>=1) {
            double min =Double.MAX_VALUE;
            same = -1; place = -1;
            for (int i = 0; i < copy_cities.size(); i++) {
                int open = copy_cities.get(i);
                if (shortestPathDist(temp.getKey(),open)<min){
                    min = shortestPathDist(temp.getKey(),open);
                    same = open;
                    place = i;
                }
            }

            list = shortestPath(temp.getKey(),same);
            while(list.size()>=1){
                if (!(result.contains(list.get(0)))){
                     result.add(list.get(0));;
                }
                list.remove(0);
            }

            int q = copy_cities.get(place);
            temp = (Node) GRAPH.getNode(q);
            copy_cities.remove(copy_cities.get(place));
            if (copy_cities.size()==1 && !result.contains(GRAPH.getNode(same+1)))
                result.add(GRAPH.getNode(same+1));
        }
        return result;

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
            Object ob = new JSONParser().parse(new FileReader(file));
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
