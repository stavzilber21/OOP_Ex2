import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import javax.swing.*;
public class drawGraph extends JPanel{
    private List<NodeData> Nodes;
    private List<EdgeData> Edges;
    private dwgAlgorithm graph;
    String message;
    public drawGraph(dwgAlgorithm graph) {
        super();
        Nodes = new ArrayList<>();
        Edges =new ArrayList<>();
        this.setBackground(new Color(89, 157, 215));
        Iterator iterNodes= graph.getGraph().nodeIter();
        while (iterNodes.hasNext()){
            Nodes.add((NodeData) iterNodes.next());
        }
        Iterator iterEdges = graph.getGraph().edgeIter();
        while (iterEdges.hasNext()){
            Edges.add((EdgeData) iterEdges.next());
        }
        message = "To add a graph, please load a json file";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("MV Boli",Font.PLAIN,25)); //set font of text
        g.setColor(Color.red);
        g.drawString(message, 100,100);
        int j=1;
        for (int i = 0; i < Nodes.size(); i++) {
            System.out.println(Nodes.get(i).getLocation());
            g.setColor(new Color(234, 26, 171));
            Node n = (Node) Nodes.get(i);
            g.fillOval((int) (n.getLocation().x()*1000)%10,(int)(n.getLocation().y()*1000)%10,10,10);
            j+=5;
        }
        for (int i = 0; i < Edges.size(); i++) {
            g.setColor(new Color(40, 234, 26));
            edge e = (edge) Edges.get(i);
            Node src = (Node) Nodes.get(e.getSrc());
            Node dest = (Node) Nodes.get(e.getDest());
            g.drawLine(src.getLocation().x(),src.getLocation().y(),dest.getLocation().x(),src.getLocation().y());
        }
    }
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (scores.size() - 1);
//        double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE - 1);
//
//        List<Point> graphPoints = new ArrayList<Point>();
//        for (int i = 0; i < Nodes.size(); i++) {
//            int x1 = (int) (i * xScale + BORDER_GAP);
//            int y1 = (int) ((MAX_SCORE - scores.get(i)) * yScale + BORDER_GAP);
//            graphPoints.add(new Point(x1, y1));
//        }

        // create x and y axes
//        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
//        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);
//
//        // create hatch marks for y axis.
//        for (int i = 0; i < Y_HATCH_CNT; i++) {
//            int x0 = BORDER_GAP;
//            int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
//            int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
//            int y1 = y0;
//            g2.drawLine(x0, y0, x1, y1);


//        // and for x axis
//        for (int i = 0; i < scores.size() - 1; i++) {
//            int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size() - 1) + BORDER_GAP;
//            int x1 = x0;
//            int y0 = getHeight() - BORDER_GAP;
//            int y1 = y0 - GRAPH_POINT_WIDTH;
//            g2.drawLine(x0, y0, x1, y1);
//        }
//
//        Stroke oldStroke = g2.getStroke();
//        g2.setColor(GRAPH_COLOR);
//        g2.setStroke(GRAPH_STROKE);
//        for (int i = 0; i < graphPoints.size() - 1; i++) {
//            int x1 = graphPoints.get(i).x;
//            int y1 = graphPoints.get(i).y;
//            int x2 = graphPoints.get(i + 1).x;
//            int y2 = graphPoints.get(i + 1).y;
//            g2.drawLine(x1, y1, x2, y2);
//        }
//
//        g2.setStroke(oldStroke);
//        g2.setColor(GRAPH_POINT_COLOR);
//        for (int i = 0; i < graphPoints.size(); i++) {
//            int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
//            int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
//            int ovalW = GRAPH_POINT_WIDTH;
//            int ovalH = GRAPH_POINT_WIDTH;
//            g2.fillOval(x, y, ovalW, ovalH);
//        }
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}
