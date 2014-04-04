package pl.edu.pw.gis.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.edu.pw.gis.app.JSONReader;
import pl.edu.pw.gis.app.LFAlg;
import pl.edu.pw.gis.model.Graph;
import pl.edu.pw.gis.model.Node;

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
			System.out.println("Seq: " + is[0]+ is[1] + is[2]);
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
		assertEquals(2, lfalg.exec());
		
		System.out.println("Second Graph");
		
		lfalg.setGraph(graphs.get(1));
		assertEquals(2, lfalg.exec());
		
		System.out.println("Fourth Graph");
		lfalg.setGraph(graphs.get(3));
		assertEquals(2, lfalg.exec());
		
		
		
		//lfalg.setGraph(graphs.get(2));
		//assertEquals(4, lfalg.exec());
	}
	
	
	
}
