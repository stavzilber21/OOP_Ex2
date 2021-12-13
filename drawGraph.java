import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.awt.geom.Line2D;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class drawGraph extends JPanel {
    private List<NodeData> Nodes;
    private List<EdgeData> Edges;
    private DirectedWeightedGraphAlgorithms graph;
    boolean toPrint;
    String message;

    public drawGraph(DirectedWeightedGraphAlgorithms graph) {
        super();
        toPrint = false;
        this.graph = graph;

        this.setBackground(new Color(21, 218, 203));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Nodes = new ArrayList<>();
        Edges = new ArrayList<>();
        Iterator iterNodes = graph.getGraph().nodeIter();
        while (iterNodes.hasNext()) {
            Nodes.add((NodeData) iterNodes.next());
        }
        Iterator iterEdges = graph.getGraph().edgeIter();
        while (iterEdges.hasNext()) {
            Edges.add((EdgeData) iterEdges.next());
        }
        System.out.println(Edges);
        System.out.println(Nodes);
        g.setFont(new Font("MV Boli", Font.PLAIN, 25)); //set font of text
        g.setColor(Color.red);
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

        for (int i = 0; i < Nodes.size(); i++) {
            Node n = (Node) Nodes.get(i);
            if (n.getLocation().x() > maxX) {
                maxX = n.getLocation().x();
            }
            if (n.getLocation().x() < minX) {
                minX = n.getLocation().x();
            }
            if (n.getLocation().y() > maxY) {
                maxY = n.getLocation().y();
            }
            if (n.getLocation().y() < minY) {
                minY = n.getLocation().y();
            }
        }
        double absX = Math.abs(maxX - minX);
        double absY = Math.abs(maxY - minY);
        double scaleX = (getSize().getWidth() / absX) * 0.9;
        double scaleY = (getSize().getHeight() / absY) * 0.9;
        for (int i = 0; i < Nodes.size(); i++) {
            g.setColor(new Color(220, 234, 26));
            Node n = (Node) Nodes.get(i);
            n.setLocation(new location(((n.getLocation().x() - minX) * scaleX * 0.9 + 32), ((n.getLocation().y() - minY) * scaleY * 0.9 + 30), 0));
//            g.fillOval((int) n.getLocation().x()-7, (int) n.getLocation().y()-7, 15, 15);
//            g.fillOval((int) ((n.getLocation().x() - minX) * scaleX * 0.9 + 30), (int) ((n.getLocation().y() - minY) * scaleY * 0.9 + 30), 10, 10);
//            g.fillOval((int) ((n.getLocation().x() * scaleX) % getSize().getWidth()), ((int) ((n.getLocation().y() * scaleY) % getSize().getHeight())), 10, 10);
        }
        for (int i = 0; i < Edges.size(); i++) {
            edge e = (edge) Edges.get(i);
            Node dest = (Node) Nodes.get(e.getDest());
            Node src = (Node) Nodes.get(e.getSrc());
//            double src_x = (src.getLocation().x() * scaleX) % getSize().getWidth();
//            double src_y = (src.getLocation().y() * scaleY) % getSize().getHeight();
//            double dest_x = (dest.getLocation().x() * scaleX) % getSize().getWidth();
//            double dest_y = (dest.getLocation().y() * scaleY) % getSize().getHeight();
            double src_x = (src.getLocation().x());
            double src_y = (src.getLocation().y());
            double dest_x = (dest.getLocation().x());
            double dest_y = (dest.getLocation().y());
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.setColor(new Color(234, 26, 137));
            g2.draw(new Line2D.Float((int) src_x, (int) src_y, (int) dest_x, (int) dest_y));

        }
        for (int i = 0; i < Nodes.size(); i++) {
            g.setColor(new Color(220, 234, 26));
            Node n = (Node) Nodes.get(i);
            g.fillOval((int) n.getLocation().x() - 7, (int) n.getLocation().y() - 7, 15, 15);
        }

    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(5, 6);
    }

    private static void createAndShowGui() {
        List<Integer> scores = new ArrayList<Integer>();
        Random random = new Random();
        int maxDataPoints = 16;
        int maxScore = 20;
        for (int i = 0; i < maxDataPoints; i++) {
            scores.add(random.nextInt(maxScore));
        }
        //drawGraph mainPanel = new drawGraph(Nodes);

        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintComponent(g);
    }


    @Override
    public void repaint() {
        super.repaint();
    }

    public static void main(String[] args) {

    }
}