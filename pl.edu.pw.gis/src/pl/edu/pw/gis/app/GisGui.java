package pl.edu.pw.gis.app;

import pl.edu.pw.gis.model.Graph;
import pl.edu.pw.gis.model.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by Mariusz on 2014-05-24.
 */
public class GisGui {
    private JTextPane programGiSLiczbaChromatycznaTextPane;
    private JTextField graphField;
    private JTextPane wpiszGrafTextPane;
    private JButton sprawdzJsonButton;
    private JPanel GisGui;

    public JTextArea getTextConsole() {
        return textConsole;
    }

    public JTextArea textConsole;
    private JButton jchoosefileButton;
    private JButton testWydajnosciButton;
    private JButton wczytaneGrafyButton;
    protected String fileName;
    protected JSONReader reader;
    protected LFAlg chromatic;

    public GisGui() {

        sprawdzJsonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateChromatic();
            }
        });

        jchoosefileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final JFileChooser fc = new JFileChooser();

                int returnVal = fc.showOpenDialog(GisGui);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    fileName = file.getPath();
                    System.out.println("Opening: " + file.getPath() + ".");
                    graphField.setText(fileName);
                    sprawdzJsonButton.setEnabled(true);
                    testWydajnosciButton.setEnabled(true);
                    wczytaneGrafyButton.setEnabled(true);
                    checkGraph();
                } else {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });
        testWydajnosciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performanceTest();
            }
        });
        wczytaneGrafyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = 0;
                for(Graph graph : reader.graphs){
                    System.out.println("graph " + String.valueOf(index) + ":");
                    for(Node nod : graph.getNodes()){
                        String summary = String.valueOf(nod.getNumber()) + ":";
                        for(Node nd : nod.getNeighbours()){
                            summary += " " + nd.getNumber();
                        }
                        System.out.println(summary);
                    }
                    System.out.println("\n");
                    index++;
                }

            }
        });
    }

    protected void checkGraph() {

        chromatic = new LFAlg();
        ArrayList<Graph> list = new ArrayList<>();
        if (graphField.getText() != "") {
            reader = new JSONReader(fileName);
        }else{
            reader = new JSONReader();
        }
        reader.readGraphs();
    }

    protected void calculateChromatic(){
        int index = 0;
        for(Graph graph : reader.graphs) {
            chromatic.setGraph(graph);
            int q = chromatic.exec();
            ArrayList<Node> bestColouring = chromatic.getBestGraph().getNodes();

            System.out.println("graph " + String.valueOf(index) + ":");
            for (int j = 0; j < bestColouring.size(); j++) {
                System.out.println("Node : " + bestColouring.get(j).getNumber() + "  Colour: " + bestColouring.get(j).getColour());
            }
            System.out.println("q: " + String.valueOf(q));
            System.out.println("\n");
            index++;
        }
    }

    protected void performanceTest(){
        ArrayList<Node> bestColouring;
        for(int i =5 ;i<reader.graphs.size(); i++){
            chromatic.setGraph(reader.graphs.get(i));
            int q = chromatic.exec();
            bestColouring = chromatic.getBestGraph().getNodes();
            System.out.println("\nlancuch" + (i-4) + " Graph Statistics:");
            System.out.println("Q 	          : " + q);
            System.out.println("Sort 	  Time: " + chromatic.getSortTime());
            System.out.println("Execution Time: " + chromatic.getExecutionTime());
            System.out.println("Breaks   Count: " + chromatic.getRejectedCount() + "\n");

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Program GiS - Liczba Chromatyczna");

        GisGui panel = new pl.edu.pw.gis.app.GisGui();
        frame.setContentPane(panel.GisGui);

        JTextAreaOutputStream out = new JTextAreaOutputStream (panel.getTextConsole());
        System.setOut (new PrintStream(out));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setData(JSONReader data) {
        System.out.println("setData");
    }

    public void getData(JSONReader data) {
        System.out.println("getData");
    }

    public boolean isModified(JSONReader data) {
        return false;
    }
}
