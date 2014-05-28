package pl.edu.pw.gis.app;

import pl.edu.pw.gis.model.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Mariusz on 2014-05-24.
 */
public class GisGui {
    private JTextPane programGiSLiczbaChromatywnaTextPane;
    private JTextField graphField;
    private JTextPane wpiszGrafTextPane;
    private JButton sprawdzButton;
    private JButton sprawdzJsonButton;
    private JPanel GisGui;
    private JTextArea textConsole;
    private JButton jchoosefileButton;

    public GisGui() {
        sprawdzJsonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGraph();
                System.out.println("sprawdz json button");
            }
        });

    }

    protected void checkGraph() {
        JSONReader reader = new JSONReader();
        LFAlg chromatic = new LFAlg();
        ArrayList<Graph> list = new ArrayList<>();
        if (graphField.getText() != "") {
            reader.readGraphs();
        }
        chromatic.setGraph(reader.graphs.get(0));
        chromatic.exec();
        System.out.println(chromatic.getQ());

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GisGui");
        frame.setContentPane(new pl.edu.pw.gis.app.GisGui().GisGui);
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
