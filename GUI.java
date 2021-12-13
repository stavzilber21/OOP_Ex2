import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;

import java.awt.event.*;
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
        panel = new drawGraph( this.graph);
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
            JOptionPane.showMessageDialog(null, isConnected ? "The graph is connected " : "The graph isn't connected");
        }

        if(e.getSource() == shortestPathDistItem) {
            String cities = JOptionPane.showInputDialog(null, "Enter a source, a comma and a destination: ");
            String[] split = cities.split(",");
            double distance = graph.shortestPathDist(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            JOptionPane.showMessageDialog(null, "" + distance);
        }

        if (e.getSource() == shortestPathItem) {
            String cities = JOptionPane.showInputDialog(null, "Enter a source, a comma and a destination: ");
            String[] split = cities.split(",");
            List<NodeData> nodes = graph.shortestPath(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            ArrayList list = new ArrayList();
            for (int i = 0; i < nodes.size(); i++) {
                list.add(nodes.get(i).getKey());
            }
            JOptionPane.showMessageDialog(null, "" + list);
        }

        if (e.getSource() == centerItem) {
            Node center = (Node) graph.center();
            JOptionPane.showMessageDialog(null, "The node center is: " + center.getKey());
        }

        if (e.getSource() == tspItem) {
            String cities = JOptionPane.showInputDialog(null, "Enter a list of vertex: ");
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
            JOptionPane.showMessageDialog(null, "The path is: " +list);
        }

        if (e.getSource() == removeNodeItem) {
            String location = JOptionPane.showInputDialog(null, "Enter a node key:");
            System.out.println(graph.getGraph().nodeSize());
            this.graph.getGraph().removeNode(Integer.parseInt(location));
            System.out.println(graph.getGraph().nodeSize());
            this.repaint();
        }

        if (e.getSource() == addNodeItem) {
            String location = JOptionPane.showInputDialog(null, "Enter a new location(x,y):");
            System.out.println(location);
            String[] split = location.split(",");
            System.out.println(Arrays.toString(split));
            double x = Double.parseDouble(split[0]);
            double y = Double.parseDouble(split[1]);
            location loc = new location(x, y, 0.0);
            Node n = new Node(loc, 0, graph.getGraph().nodeSize());
            this.graph.getGraph().addNode(n);
            this.repaint();
        }

        if (e.getSource() == connectItem) {
            String location = JOptionPane.showInputDialog(null, "Enter two node keys and weight(src, dest, weight):");
            String[] split = location.split(",");
            this.graph.getGraph().connect(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Double.parseDouble(split[2]));
            this.repaint();
        }

        if (e.getSource() == removeEdgeItem){
            String location = JOptionPane.showInputDialog(null, "Enter two node keys whose edge to remove(source, destination):");
            String[] split = location.split(",");
            System.out.println(graph.getGraph().edgeSize());
            this.graph.getGraph().removeEdge(Integer.parseInt(split[0]),Integer.parseInt(split[1]));
            System.out.println(graph.getGraph().edgeSize());
            this.repaint();
        }


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