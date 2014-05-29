package pl.edu.pw.gis.test;

import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.gis.app.JSONReader;
import pl.edu.pw.gis.app.LFAlg;
import pl.edu.pw.gis.model.Graph;
import pl.edu.pw.gis.model.Node;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LFAlgTest {

    JSONReader reader;
    ArrayList<Graph> graphs;
    LFAlg lfalg;

    @Before
    public void init(){
        reader = new JSONReader();
        graphs = reader.readGraphs();
        lfalg = new LFAlg();

    }

    @Test
    public void sortTest() {

        lfalg.setGraph(graphs.get(0));
        lfalg.sort();

        assertTrue(lfalg.getGraph().getNodes().get(0).getNeighbours().size()==2);

        lfalg.setGraph(graphs.get(1));
        lfalg.sort();

        assertTrue(lfalg.getGraph().getNodes().get(0).getNeighbours().size()==3);
        assertTrue(lfalg.getGraph().getNodes().get(1).getNeighbours().size()==2);
        assertTrue(lfalg.getGraph().getNodes().get(2).getNeighbours().size()==2);
        assertTrue(lfalg.getGraph().getNodes().get(3).getNeighbours().size()==2);
        assertTrue(lfalg.getGraph().getNodes().get(4).getNeighbours().size()==1);


    }

    @Test
    public void seqTest(){
        ArrayList<int[]> seq = lfalg.getSeq(3);
        graphs.get(2).sort();
        graphs.get(2).saveBefore();
        for (int[] is : seq) {
            //System.out.println("Seq: " + is[0]+ is[1] + is[2]);
        }
        System.out.println("Seq3: " + seq.get(3)[0]+ seq.get(3)[1] + seq.get(3)[2]);

        System.out.println("before:");
        for(int i=0;i<6;i++){
            System.out.println(graphs.get(2).getBefore().get(i));
        }
        System.out.println("nodes");
        for(int i=0;i<7;i++){
            System.out.println(graphs.get(2).getNodes().get(i));
        }
        graphs.get(2).resort(seq.get(3));
        System.out.println("");
        System.out.println("after");
        for(int i=0;i<7;i++){
            System.out.println(graphs.get(2).getNodes().get(i));
        }

        assertTrue(true);
    }


    @Test
    public void execTest() {

        lfalg.setGraph(graphs.get(0));
        lfalg.exec();
        ArrayList<Node> bestColouring = lfalg.getBestGraph().getNodes();

        for(int j =0 ;j< bestColouring.size() ; j++){
            System.out.println("Node : " + bestColouring.get(j).getNumber()+"  Colour: " + bestColouring.get(j).getColour());
        }
        assertEquals(2, lfalg.exec());

        System.out.println("Second Graph");

        lfalg.setGraph(graphs.get(1));
        assertEquals(2, lfalg.exec());
        System.out.println("\nSecond Graph Statistics:");
        System.out.println("Sort 	  Time: " + lfalg.getSortTime());
        System.out.println("Execution Time: " + lfalg.getExecutionTime());
        System.out.println("Breaks   Count: " + lfalg.getRejectedCount() + "\n");
        bestColouring = lfalg.getBestGraph().getNodes();

        for(int j =0 ;j< bestColouring.size() ; j++){
            System.out.println("Node : " + bestColouring.get(j).getNumber()+"  Colour: " + bestColouring.get(j).getColour());
        }

        System.out.println("Fourth Graph");
        lfalg.setGraph(graphs.get(3));
        assertEquals(2, lfalg.exec());
        System.out.println("\nFourth Graph Statistics:");
        System.out.println("Sort 	  Time: " + lfalg.getSortTime());
        System.out.println("Execution Time: " + lfalg.getExecutionTime());
        System.out.println("Breaks   Count: " + lfalg.getRejectedCount() + "\n");
        bestColouring = lfalg.getBestGraph().getNodes();

        for(int j =0 ;j< bestColouring.size() ; j++){
            System.out.println("Node : " + bestColouring.get(j).getNumber()+"  Colour: " + bestColouring.get(j).getColour());
        }

        System.out.println("Third Graph");
        int[] i = {5,4,2,1,3,6};
        lfalg.setGraph(graphs.get(2));
        assertEquals(3, lfalg.exec());
        System.out.println("\nThird Graph Statistics:");
        System.out.println("Sort 	  Time: " + lfalg.getSortTime());
        System.out.println("Execution Time: " + lfalg.getExecutionTime());
        System.out.println("Breaks   Count: " + lfalg.getRejectedCount() + "\n");

        //Graph best = lfalg.getBestGraph();
        bestColouring = lfalg.getBestGraph().getNodes();

        for(int j =0 ;j< bestColouring.size() ; j++){
            System.out.println("Node : " + bestColouring.get(j).getNumber()+"  Colour: " + bestColouring.get(j).getColour());
        }


        lfalg.exec(lfalg.getBestseq(), lfalg.getBestseq().length);
        bestColouring = lfalg.getBestGraph().getNodes();

        for(int j =0 ;j< bestColouring.size() ; j++){
            System.out.println("Node : " + bestColouring.get(j).getNumber()+"  Colour: " + bestColouring.get(j).getColour());
        }


    }

    @Test
    public void performanceTest() {
        ArrayList<Node> bestColouring;
        for(int i =5 ;i<graphs.size(); i++){
            lfalg.setGraph(graphs.get(i));
            int q = lfalg.exec();
            bestColouring = lfalg.getBestGraph().getNodes();
            System.out.println("\nlancuch" + (i-4) + " Graph Statistics:");
            System.out.println("Q 	          : " + q);
            System.out.println("Sort 	  Time: " + lfalg.getSortTime());
            System.out.println("Execution Time: " + lfalg.getExecutionTime());
            System.out.println("Breaks   Count: " + lfalg.getRejectedCount() + "\n");

        }

    }

    @Test
    public void oneRoundTest(){
        System.out.println("\nSingle Round Test");
        long startTime;
        long singleExecTime;
        for(int i =5 ;i<graphs.size(); i++){
            lfalg.setGraph(graphs.get(i));
            startTime = System.nanoTime();
            for(int j =0;j<10; j++){
                lfalg.setQ(100);
                int[] seq = new int[0] ;
                lfalg.exec(seq, 1);
            }
            singleExecTime = System.nanoTime() - startTime;

            System.out.println("\nlancuch" + (i-4) + " Graph Statistics:");
            System.out.println("Q 	          : " + lfalg.getQ());
            System.out.println("Execution Time: " + singleExecTime);

        }

    }


}
