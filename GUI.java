import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;

import java.awt.event.*;
import java.lang.NumberFormatException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GUI  extends JFrame implements KeyListener, ActionListener {
    drawGraph panel;
    private DirectedWeightedGraphAlgorithms graph;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu algoMenu;
    JMenu dwgMenu;
    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem isConnectedItem;
    JMenuItem shortestPathDistItem;
    JMenuItem shortestPathItem;
    JMenuItem centerItem;
    JMenuItem tspItem;
    JMenuItem addNodeItem;
    JMenuItem connectItem;
    JMenuItem removeNodeItem;
    JMenuItem removeEdgeItem;
    ImageIcon loadIcon;
    ImageIcon saveIcon;
    ImageIcon exitIcon;
    ImageIcon initIcon;
    ImageIcon isConnectedIcon;
    ImageIcon shortestPathDistIcon;
    ImageIcon shortestPathIcon;
    ImageIcon centerIcon;
    ImageIcon tspIcon;

    JLabel isConnected;


    public GUI(DirectedWeightedGraphAlgorithms algo) {
        super();
        graph=algo;

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        loadIcon = new ImageIcon("./resources/load.jpg");
        saveIcon = new ImageIcon("./resources/save.png");
        exitIcon = new ImageIcon("./resources/exit.jpg");
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        algoMenu = new JMenu("Algorithms");
        dwgMenu = new JMenu("Functions");
        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        isConnectedItem= new JMenuItem("isConnected");
        shortestPathDistItem = new JMenuItem("shortestPathDist");
        shortestPathItem = new JMenuItem("shortestPath");
        centerItem = new JMenuItem("center");
        tspItem = new JMenuItem("tsp");
        addNodeItem = new JMenuItem("addNode");
        connectItem = new JMenuItem("connect");
        removeNodeItem = new JMenuItem("removeNode");
        removeEdgeItem = new JMenuItem("removeEdge");
        loadItem.setIcon(loadIcon);
        saveItem.setIcon(saveIcon);
        exitItem.setIcon(exitIcon);
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        algoMenu.add(isConnectedItem);
        algoMenu.add(shortestPathDistItem);
        algoMenu.add(shortestPathItem);
        algoMenu.add(centerItem);
        algoMenu.add(tspItem);
        dwgMenu.add(addNodeItem);
        dwgMenu.add(connectItem);
        dwgMenu.add(removeNodeItem);
        dwgMenu.add(removeEdgeItem);


        menuBar.add(fileMenu);
        menuBar.add(algoMenu);
        menuBar.add(dwgMenu);
        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        isConnectedItem.addActionListener(this);
        shortestPathDistItem.addActionListener(this);
        shortestPathItem.addActionListener(this);
        centerItem.addActionListener(this);
        tspItem.addActionListener(this);

        addNodeItem.addActionListener(this);
        connectItem.addActionListener(this);
        removeNodeItem.addActionListener(this);
        removeEdgeItem.addActionListener(this);
        this.setJMenuBar(menuBar);
        panel = new drawGraph(this.graph);
        this.add(panel);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadItem) {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new java.io.File("./src"));
            int i = fc.showOpenDialog(this);
            if (i == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                String filepath = f.getPath();
                this.graph.load(filepath);
//                panel = new drawGraph((dwg) this.graph.getGraph());
//                this.add(panel);
                System.out.println("you loaded a file");
                this.repaint();
//                this.revalidate();
            }

        }
        if (e.getSource() == saveItem) {
            JFileChooser fc = new JFileChooser();
            int userSelection = fc.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fc.getSelectedFile();
                graph.save(fileToSave.getAbsolutePath() + ".json");
                System.out.println("you saved a file");
            }
        }
        if (e.getSource() == exitItem) {
            System.exit(0);
        }
        if (e.getSource() == isConnectedItem) {
            boolean isConnected = this.graph.isConnected();
            JOptionPane.showMessageDialog(this, isConnected ? "The graph is connected " : "The graph isn't connected");
            System.out.println("the graph is connected? " + graph.isConnected());
        }
        if(e.getSource() == shortestPathDistItem) {
            JFrame text = new JFrame();
            String getMessage = JOptionPane.showInputDialog(text, "Enter a source, a comma and a destination: ");
            String cities = getMessage;
            System.out.println(cities);
            String[] split = cities.split(",");
            System.out.println(Arrays.toString(split));
            System.out.println(split.length);
            String src = split[0];
            String dest = split[1];
            System.out.println(dest);
            double distance = graph.shortestPathDist(Integer.valueOf(src), Integer.valueOf(dest));
            System.out.println(distance);
            JOptionPane.showMessageDialog(this, "" + distance);
        }
        if (e.getSource() == shortestPathItem) {
            JFrame text = new JFrame();
            String getMessage = JOptionPane.showInputDialog(text, "Enter a source, a comma and a destination: ");
            String cities = getMessage;
            String[] split = cities.split(",");
            String src = split[0];
            String dest = split[1];
            List<NodeData> nodes = graph.shortestPath(Integer.valueOf(src), Integer.valueOf(dest));
            ArrayList list = new ArrayList();
            for (int i = 0; i < nodes.size(); i++) {
                list.add(nodes.get(i).getKey());
            }
            JOptionPane.showMessageDialog(this, "" + list);
        }

        if (e.getSource() == centerItem) {
            Node center = (Node) graph.center();
            JOptionPane.showMessageDialog(this, "The node center is: " + center.getKey());
            System.out.println("The node center is: " + center);
        }

        if (e.getSource() == tspItem) {
            JFrame text = new JFrame();
            String getMessage = JOptionPane.showInputDialog(text, "Enter a list of vertex: ");
            String cities = getMessage;
            String[] split = cities.split(",");
            List<NodeData> nodes = new ArrayList<>();
            for (String i : split) {;
                nodes.add(graph.getGraph().getNode(Integer.parseInt(i)));
            }
            List<NodeData> nodes_tsp = graph.tsp(nodes);
            ArrayList list = new ArrayList();
            for (int i = 0; i < nodes_tsp.size(); i++) {
                list.add(nodes_tsp.get(i).getKey());
            }
            JOptionPane.showMessageDialog(this, "The path is: " + ""+list);
            System.out.println("the tsp is worked");
        }


        if (e.getSource() == removeNodeItem) {

        }


        if (e.getSource() == addNodeItem) {
            //JFrame text = new JFrame();
            String getMessage = JOptionPane.showInputDialog(null, "Enter a new location and weight(x,y,weight):");
            String location = getMessage;
            System.out.println(location);
            String[] split = location.split(",");
            System.out.println(Arrays.toString(split));
//        try {
            double x = Double.parseDouble(split[0]);
            double y = Double.parseDouble(split[1]);
            double weight = Double.parseDouble(split[2]);
            location loc = new location(x, y, 0.0);
            Node n = new Node(loc, 0, graph.getGraph().nodeSize());
            graph.getGraph().addNode(n);
//        graph.getGraph().connect(graph.getGraph().nodeSize() - 1, graph.getGraph().nodeSize(), weight);
//        panel = new drawGraph(graph);
//        this.add(panel);
            this.repaint();


//        } catch (NumberFormatException s){
//            System.out.println("Try again! Make sure to put in doubles.");
//        }
        }

        if (e.getSource() == connectItem) {
            JFrame text = new JFrame();
            String getMessage = JOptionPane.showInputDialog(text, "Enter two node key in the graph:");
            String location = getMessage;
            String[] split = location.split(",");
            List<NodeData> nodes = new ArrayList<>();
            for (String i : split) {;
                nodes.add(graph.getGraph().getNode(Integer.parseInt(i)));
            }
            graph.getGraph().connect(nodes.get(0).getKey(),nodes.get(1).getKey(),2);
            this.repaint();
        }


//    private void addNode(MouseEvent e) {
//        double x = e.getPoint().x;
//        double y = e.getPoint().y;
//        location loc = new location(x, y, 0);
//        Node node = new Node(loc, 0, graph.getGraph().nodeSize());
//        graph.getGraph().addNode(node);
//        this.repaint();
//    }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}