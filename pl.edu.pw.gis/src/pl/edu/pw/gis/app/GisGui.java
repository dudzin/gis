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
    private JTextPane programGiSLiczbaChromatywnaTextPane;
    private JTextField graphField;
    private JTextPane wpiszGrafTextPane;
    private JButton sprawdzJsonButton;
    private JPanel GisGui;

    public JTextArea getTextConsole() {
        return textConsole;
    }

    public JTextArea textConsole;
    private JButton jchoosefileButton;
    protected String fileName;

    public GisGui() {

        sprawdzJsonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGraph();
            }
        });

        jchoosefileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a file chooser
                final JFileChooser fc = new JFileChooser();

                //In response to a button click:
                int returnVal = fc.showOpenDialog(GisGui);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    fileName = file.getPath();
                    System.out.println("Opening: " + file.getPath() + ".");
                    wpiszGrafTextPane.setText(fileName);
                } else {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });
    }


    protected void checkGraph() {
        JSONReader reader;
        LFAlg chromatic = new LFAlg();
        ArrayList<Graph> list = new ArrayList<>();
        if (graphField.getText() != "") {
            reader = new JSONReader(fileName);
        }else{
            reader = new JSONReader();
        }
        reader.readGraphs();
        for(Graph graph : reader.graphs){
            System.out.println("graf:");
            for(Node nod : graph.getNodes()){
                System.out.println(nod.getNumber() + " " + nod.getNeighbours().toString());
            }

        }
        System.out.println("licze");
        chromatic.setGraph(reader.graphs.get(0));
        chromatic.exec();
        ArrayList<Node> bestColouring = chromatic.getBestGraph().getNodes();

        System.out.println("best graph");
        for(int j =0 ;j< bestColouring.size() ; j++){
            System.out.println("Node : " + bestColouring.get(j).getNumber()+"  Colour: " + bestColouring.get(j).getColour());
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GisGui");

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
