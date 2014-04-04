package pl.edu.pw.gis.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import pl.edu.pw.gis.app.JSONReader;
import pl.edu.pw.gis.model.Graph;
import pl.edu.pw.gis.model.Node;

public class JSONReaderTest {

	JSONReader reader;
	ArrayList<Graph> graphsTemplate;
	ArrayList<Graph> graphs;
	
	@Before 
	public void init(){
		reader = new JSONReader();
		initTemplates();
	}
	
	@Test
	public void graphInitTest() {
		
		graphs = reader.readGraphs();
		assertEquals(4, graphs.size());
		
		assertEquals(graphsTemplate.get(0).getNodes().size(), graphs.get(0).getNodes().size());
		assertEquals(5, graphs.get(1).getNodes().size());
		assertEquals(7, graphs.get(2).getNodes().size());
		
		assertEquals(graphsTemplate.get(0).getNodes().get(0).getNeighbours().size(), graphs.get(0).getNodes().get(0).getNeighbours().size());
		assertEquals(graphsTemplate.get(0).getNodes().get(1).getNeighbours().size(), graphs.get(0).getNodes().get(1).getNeighbours().size());
		assertEquals(graphsTemplate.get(0).getNodes().get(2).getNeighbours().size(), graphs.get(0).getNodes().get(2).getNeighbours().size());
				
	}

	private void initTemplates(){
		graphsTemplate = new ArrayList<Graph>();
		Graph graph1 = new Graph();
		Graph graph2 = new Graph();
		Graph graph3 = new Graph();
		
		Node node1 = new Node();
		Node node2 = new Node();
		Node node3 = new Node();
		
		node1.addNeighbour(node3);
		node2.addNeighbour(node3);
		node3.addNeighbour(node1);
		node3.addNeighbour(node2);
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		
		graph1.setNodes(nodes);
		
		
		graphsTemplate.add(graph1);
		graphsTemplate.add(graph2);
		graphsTemplate.add(graph3);
	}
	
}
