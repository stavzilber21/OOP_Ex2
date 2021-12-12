import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import java.awt.geom.Line2D;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import javax.swing.*;
public class drawGraph extends JPanel{
    private List<NodeData> Nodes;
    private List<EdgeData> Edges;
    private dwgAlgorithm graph;
    public drawGraph(dwgAlgorithm graph) {
        super();
        Nodes = new ArrayList<>();
        Edges =new ArrayList<>();
        this.setBackground(new Color(21, 218, 203));
        Iterator iterNodes= graph.getGraph().nodeIter();
        while (iterNodes.hasNext()){
            Nodes.add((NodeData) iterNodes.next());
        }
        Iterator iterEdges = graph.getGraph().edgeIter();
        while (iterEdges.hasNext()){
            Edges.add((EdgeData) iterEdges.next());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("MV Boli", Font.PLAIN, 25)); //set font of text
        g.setColor(Color.red);
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = 0, maxY = 0;
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
        double scaleX = getSize().getWidth() / absX;
        double scaleY = getSize().getHeight() / absY;

        for (int i = 0; i < Edges.size(); i++) {
            //g.setColor(new Color(40, 234, 26));
            edge e = (edge) Edges.get(i);
            Node src = (Node) Nodes.get(e.getSrc());
            Node dest = (Node) Nodes.get(e.getDest());
            double src_x = (src.getLocation().x() * scaleX) % getSize().getWidth();
            double src_y = (src.getLocation().y() * scaleY) % getSize().getHeight();
            double dest_x = (dest.getLocation().x() * scaleX) % getSize().getWidth();
            double dest_y = (dest.getLocation().y() * scaleY) % getSize().getHeight();
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            //g.drawLine((int)src_x,(int)src_y,(int)dest_x,(int)dest_y);
            g2.setColor(new Color(234, 26, 137));
            g2.draw(new Line2D.Float((int) src_x, (int) src_y, (int) dest_x, (int) dest_y));

        }
        for (int i = 0; i < Nodes.size(); i++) {
            g.setColor(new Color(220, 234, 26));
            Node n = (Node) Nodes.get(i);
            g.fillOval((int) ((n.getLocation().x() * scaleX) % getSize().getWidth()), ((int) ((n.getLocation().y() * scaleY) % getSize().getHeight())), 10, 10);
        }
    }

    public void reset(){
        Nodes = new ArrayList<>();
        Edges = new ArrayList<>();
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(5,6);
    }

    private static void createAndShowGui() {
        List<Integer> scores = new ArrayList<Integer>();
        Random random = new Random();
        int maxDataPoints = 16;
        int maxScore = 20;
        for (int i = 0; i < maxDataPoints ; i++) {
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

    public static void main(String[] args) {

    }
}
