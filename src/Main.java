import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws IOException, ParseException, JSONException {

        try {
            Object ob = new JSONParser().parse(new FileReader("src/data/G1.json"));
            if (ob == null)
                throw new Exception("Error");
            JSONObject js = (JSONObject) ob;
            JSONArray edgesArr = (JSONArray) js.get("Edges");
            JSONArray nodesArr = (JSONArray) js.get("Nodes");
            Iterator itEdge = edgesArr.iterator();
//            System.out.println(nodesArr);
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
//                System.out.println(MessageFormat.format("{0}, {1}, {2}, {3}", x, y, z, id));
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
//                edgesMap.put(new Point(src, dest), new edge(new Node(new location(0, 0, 0), w, src),
//                        new Node(new location(0, 0, 0), 0, dest), w));
                edgesMap.put(p,e);
//                System.out.println(edgesMap.get(p).getSrc()+","+edgesMap.get(p).getDest()+","+edgesMap.get(p).getWeight());

            }
            DirectedWeightedGraph graph = new dwg(nodesMap,edgesMap);
            transpose((dwg) graph);
            print((dwg) graph);


        } catch (Exception e) {

            System.out.println(e);

        }

    }

    public static void print(dwg GRAPH) {
        System.out.println("Edges");
        while(GRAPH.edgeIter().hasNext()){
            edge edge = new edge((edge) GRAPH.edgeIter().next());
            System.out.println("src "+edge.getSrc());
            System.out.println("w "+edge.getWeight());
            System.out.println("dest "+edge.getDest());
            GRAPH.edgeIter().next();
        }
//        for(int i=0; i<GRAPH.edgeSize();i++){
////            edge edge = new edge((edge) GRAPH.getEdges().get(i));
//            System.out.println("src "+GRAPH..getSrc());
//            System.out.println("w "+GRAPH.getEdges().get(i).getWeight());
//            System.out.println("dest "+GRAPH.getEdges().get(i).getDest());
//        }

        System.out.println("Nodes");
        for(int i =0; i< GRAPH.nodeSize(); i++) {
            Node node = new Node((Node) GRAPH.getNode(i));
            System.out.println("pos"+node.getLocation().x()+","+node.getLocation().y()+","+node.getLocation().z());
            System.out.println("id"+node.getKey());
        }
    }
    public static void transpose(dwg GRAPH) {
        HashMap<Point2D, EdgeData> Edges = new HashMap<Point2D, EdgeData>();
        int i=0;
        while (GRAPH.edgeIter().hasNext()) {
            edge temp = (edge) GRAPH.edgeIter().next();
            Node source = (Node) GRAPH.getNode(temp.getSrc());
            Node destination =  (Node) GRAPH.getNode(temp.getDest());
            edge edge = new edge(destination,source, temp.getWeight());
            Point2D point = new Point(temp.getDest(),temp.getSrc());
            Edges.put(point, edge);
            i++;
        }
        ((dwg) GRAPH).setEdges(Edges);
    }


}
