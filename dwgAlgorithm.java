import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
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

public class dwgAlgorithm implements api.DirectedWeightedGraphAlgorithms{
    private DirectedWeightedGraph GRAPH;
    final static int INF = 999999;

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
    public DirectedWeightedGraph copy() throws CloneNotSupportedException {
//        dwg answer = new dwg((dwg) this.GRAPH);
//        return answer;
        int k;
        HashMap<Integer, NodeData> nodeMap = new HashMap<>();
        HashMap<Point2D, EdgeData> edgeMap = new HashMap<>();
        for(int i = 0; i < GRAPH.nodeSize(); i++){
            nodeMap.put(i,GRAPH.getNode(i));
        }
        for(int j = 0; j < GRAPH.edgeSize(); j++){
            if(GRAPH.getNode(j) != null){
                for(k = 0; k < GRAPH.edgeSize();k++){
                    if(GRAPH.getEdge(j,k) == null){
                        continue;
                    }
                    Point2D p = new Point(j,k);
                    edgeMap.put(p,GRAPH.getEdge(j,k));
                }
            }
        }
        DirectedWeightedGraph g_copy = new dwg(nodeMap,edgeMap);
        return g_copy;
    }


    @Override
    public boolean isConnected() {
        boolean pre [] =new boolean[GRAPH.nodeSize()];
        boolean post [] =new boolean[GRAPH.nodeSize()];
        for(int i =0; i< GRAPH.nodeSize(); i++)  {
            pre[i]=false;
            post[i]=false;
        }
        int count1 = dfs(GRAPH,0,pre,0);
        if(count1 != GRAPH.nodeSize())
            return false;
        dwg graph = transpose((dwg) GRAPH);
        int count2 = dfs(graph,0,post,0);
        if(count2!= graph.nodeSize())
            return false;
        return true;
    }

    public void print() {
        System.out.println("Edges");
        while(GRAPH.edgeIter().hasNext()){
            edge edge = new edge((edge) GRAPH.edgeIter().next());
            System.out.println("src"+edge.getSrc());
            System.out.println("w"+edge.getWeight());
            System.out.println("dest"+edge.getDest());
        }

        System.out.println("Nodes");
        for(int i =0; i< GRAPH.nodeSize(); i++) {
            Node node = new Node((Node) GRAPH.getNode(i));
            System.out.println("pos"+node.getLocation().x()+","+node.getLocation().y()+","+node.getLocation().z());
            System.out.println("id"+node.getKey());
        }
    }

    public dwg transpose(dwg graph) {
        HashMap<Point2D, EdgeData> Edges = new HashMap<Point2D, EdgeData>();
        int i=0;
        Iterator<EdgeData> iter = GRAPH.edgeIter();
        while (iter.hasNext()) {
            edge temp = (edge) this.GRAPH.edgeIter().next();
            Node source = (Node) GRAPH.getNode(temp.getSrc());
            Node destination =  (Node) GRAPH.getNode(temp.getDest());
            edge edge = new edge(destination,source, temp.getWeight());
            Point2D point = new Point(temp.getDest(),temp.getSrc());
            Edges.put(point, edge);
            i++;
        }
        dwg g = new dwg((HashMap) graph.getNodes(), Edges);
        return g;
    }

    public static int dfs (DirectedWeightedGraph g, int u, boolean visited [], int count) {
        {
            visited[u] = true;
            Iterator<EdgeData> iter = g.edgeIter(u);
            while (iter.hasNext()) {
                int dest = iter.next().getDest();
                if (visited[dest] == false) {
                    count++;
                    dfs(g, dest, visited, count);
                }
            }
        }
        return count;
    }
    @Override
    public double shortestPathDist(int src, int dest) {
        int n_nodes = GRAPH.nodeSize();
        Iterator<EdgeData> iterSrc = GRAPH.edgeIter(src);
        Set<Node> settled =new HashSet<Node>();
        double []dist= new double [n_nodes];
        List<List<Node>> adj = null;
        for(int i=0;i<GRAPH.nodeSize();i++){
            List list = new ArrayList<Node>();
            while (iterSrc.hasNext()) {
                edge temp = (edge) iterSrc.next();
                Node destination =  (Node) GRAPH.getNode(temp.getDest());
                list.add(destination);
            }
            adj.add(i,list);
        }
        PriorityQueue <Node> q_node =new PriorityQueue<Node>(n_nodes,new Node());
        int [] prev =new int [n_nodes];
        for(int i =0; i< n_nodes; i++) {
            dist[i] = Double.MAX_VALUE;
            q_node.add((Node) GRAPH.getNode(i));
        }
        dist[src] = 0;
        while(settled.size() != n_nodes) {
            int u = q_node.remove().getKey();
            if (settled.contains(u))
                continue;
            settled.add((Node) GRAPH.getNode(u));

            neighbours1(u, adj,settled,dist,q_node,prev);
        }
        return dist[dest];
    }

    private void neighbours1(int u, List<List<Node> > adj,Set<Node> settled,double []dist,PriorityQueue<Node> q_node ,int []prev)
    {
        List<NodeData> list_node=new ArrayList<NodeData>();
        double edgeDistance = -1;
        double newDistance = -1;
        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);
            if (!settled.contains(v.getKey())) {
                edgeDistance = v.getWeight();
                newDistance = dist[u] + edgeDistance;
                if (newDistance < dist[v.getKey()])
                    dist[v.getKey()] = newDistance;
                prev[v.getKey()] = u;
                list_node.add(GRAPH.getNode(u));
                q_node.add(new Node((Node) GRAPH.getNode(v.getKey())));
            }
        }
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        int n_nodes = GRAPH.nodeSize();
        Iterator<EdgeData> iterSrc = GRAPH.edgeIter(src);
        Set<Node> settled =new HashSet<Node>();
        double []dist= new double [n_nodes];
        List<List<Node>> adj = null;
        for(int i=0;i<GRAPH.nodeSize();i++){
            List list = new ArrayList<Node>();
            while (iterSrc.hasNext()) {
                edge temp = (edge) iterSrc.next();
                Node destination =  (Node) GRAPH.getNode(temp.getDest());
                list.add(destination);
            }
            adj.add(i,list);
        }
        PriorityQueue <Node> q_node =new PriorityQueue<Node>(n_nodes,new Node());
        int [] prev =new int [n_nodes];
        for(int i =0; i< n_nodes; i++) {
            dist[i] = Double.MAX_VALUE;
            q_node.add((Node) GRAPH.getNode(i));
        }
        dist[src] = 0;
        List<NodeData> answer = new ArrayList<NodeData>();
        while(settled.size() != n_nodes) {
            int u = q_node.remove().getKey();
            if (settled.contains(u))
                continue;
            settled.add((Node) GRAPH.getNode(u));

            answer= neighbours2(u, adj,settled,dist,q_node,prev);
        }
        return answer;
    }
    private List<NodeData> neighbours2(int u, List<List<Node> > adj,Set<Node> settled,double []dist,PriorityQueue<Node> q_node ,int []prev)
    {
        List<NodeData> list_node=new ArrayList<NodeData>();
        double edgeDistance = -1;
        double newDistance = -1;
        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);
            if (!settled.contains(v.getKey())) {
                edgeDistance = v.getWeight();
                newDistance = dist[u] + edgeDistance;
                if (newDistance < dist[v.getKey()])
                    dist[v.getKey()] = newDistance;
                prev[v.getKey()] = u;
                list_node.add(GRAPH.getNode(u));
                q_node.add(new Node((Node) GRAPH.getNode(v.getKey())));
            }
        }
        return list_node;
    }

    @Override
    public NodeData center() {
        if (!isConnected()){
            return null;
        }
        double min = Double.MAX_VALUE;
        NodeData ans = null;

        for(int i =0; i<GRAPH.nodeSize();i++){
            if(centerOfNode(GRAPH.getNode(i))<min){
                min = centerOfNode(GRAPH.getNode(i));
                ans = GRAPH.getNode(i);
            }
        }
        return ans;
    }

    private double centerOfNode(NodeData node){
        double max = Double.MIN_VALUE;
        for (Integer key :((dwg) this.getGraph()).getNodes().keySet()) {
            if (key!=node.getKey()){
                double temp = shortestPathDist(key , node.getKey());
                if (temp>=max){
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
                edgesMap.put(p,e);

            }
            DirectedWeightedGraph graph = new dwg(nodesMap,edgesMap);

        } catch (Exception e) {

            System.out.println(e);
            return false;

        }
        return true;
    }
}
