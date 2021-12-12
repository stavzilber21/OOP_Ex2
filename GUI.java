import api.DirectedWeightedGraphAlgorithms;
import gui.draw.graph.GFrame;
import gui.draw.graph.GraphPanel;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.File;

import static gui.buttons.MenuBarExample.scaleImageIcon;

public class GUI  extends JFrame implements KeyListener, ActionListener {
    BorderLayout border = new BorderLayout();
    Container cont = getContentPane();
    drawGraph panel;
    dwgAlgorithm graph;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu algoMenu;
    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem initItem;
    JMenuItem isConnectedItem;
    JMenuItem shortestPathDistItem;
    JMenuItem shortestPathItem;
    JMenuItem centerItem;
    JMenuItem tspItem;
    ImageIcon loadIcon;
    ImageIcon saveIcon;
    ImageIcon exitIcon;
    ImageIcon initIcon;
    ImageIcon isConnectedIcon;
    ImageIcon shortestPathDistIcon;
    ImageIcon shortestPathIcon;
    ImageIcon centerIcon;
    ImageIcon tspIcon;

    public static void main(String[] args) {
        new GUI();
    }
    public GUI() {
        super();
//        panel =new drawGraph(graph);
//        this.add(panel);
        graph=new dwgAlgorithm();
//        cont.add(panel, BorderLayout.CENTER);
//        cont.setLayout(border);

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        loadIcon = new ImageIcon("./resources/load.jpg");
        saveIcon = new ImageIcon("./resources/save.png");
        exitIcon = new ImageIcon("./resources/exit.jpg");
        loadIcon = scaleImageIcon(loadIcon,20,20);
        saveIcon = scaleImageIcon(saveIcon,20,20);
        exitIcon = scaleImageIcon(exitIcon,20,20);
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        algoMenu = new JMenu("Algorithms");
        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        initItem = new JMenuItem("init");
        isConnectedItem= new JMenuItem("isConnected");
        shortestPathDistItem = new JMenuItem("shortestPathDist");
        shortestPathItem = new JMenuItem("shortestPath");
        centerItem = new JMenuItem("center");
        tspItem = new JMenuItem("tsp");
        loadItem.setIcon(loadIcon);
        saveItem.setIcon(saveIcon);
        exitItem.setIcon(exitIcon);
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        algoMenu.add(initItem);
        algoMenu.add(isConnectedItem);
        algoMenu.add(shortestPathDistItem);
        algoMenu.add(shortestPathItem);
        algoMenu.add(centerItem);
        algoMenu.add(tspItem);


        menuBar.add(fileMenu);
        menuBar.add(algoMenu);
        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        initItem.addActionListener(this);
        isConnectedItem.addActionListener(this);
        shortestPathDistItem.addActionListener(this);
        shortestPathItem.addActionListener(this);
        centerItem.addActionListener(this);
        tspItem.addActionListener(this);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadItem) {
            JFileChooser fc = new JFileChooser();
            int i = fc.showOpenDialog(this);
            if (i == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                String filepath = f.getPath();
                graph.load(filepath);
                panel =new drawGraph(graph);
                this.add(panel);
                System.out.println("you loaded a file");
            }
        }
        if (e.getSource() == saveItem) {
            //graph.save();
            System.out.println("you saved a file");
        }
        if (e.getSource() == exitItem) {
            System.exit(0);
        }


    }
    //    public void paint(Graphics g){
//            for (int i = 0; i < g.getGraph().nodeSize(); i++) {
//                //getContentPane().setBackground(Color.RED);
//                g.setColor(new Color(234, 26, 171));
//                Node n = (Node) graph.getGraph().getNode(i);
//                g.fillOval((int) n.getLocation().x(), (int) n.getLocation().y(), 20, 20);
//
//            }
//        }
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
