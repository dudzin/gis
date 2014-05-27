package pl.edu.pw.gis.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pl.edu.pw.gis.model.Graph;
import pl.edu.pw.gis.model.Node;

public class JSONReader {
	JSONParser parser;
	ArrayList<Graph> graphs;
	ArrayList<String> graphNames;
	int count;
	public JSONReader(){
		
		parser = new JSONParser();
		
	}
	
	public ArrayList<Graph> readGraphs() {
		
		graphs = new ArrayList<Graph>();
		graphNames = new ArrayList<String>();
		Graph graph = null;
		 
		try {
			Object obj = parser.parse(new FileReader("graphs.json"));
			JSONObject jsonObject = (JSONObject) obj;
			getGraphNames(jsonObject);
			JSONArray msg;
			Iterator<String> iterator;
			
			for (String name : graphNames) {
				graph = new Graph();
				count = 0;
				msg = (JSONArray) jsonObject.get(name);
				iterator = msg.iterator();
				while (iterator.hasNext()) {
					Node node = new Node();
					node.setNumber(count);
					graph.addNode(node);
					iterator.next();
					count++;
				}
				
				msg = (JSONArray) jsonObject.get(name);
				iterator = msg.iterator();
				graph =addNodes(graph, iterator);
				graphs.add(graph);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return graphs;
		
    }
	
	private void getGraphNames(JSONObject jsonObject){
		
		
		
		JSONArray msg = (JSONArray) jsonObject.get("graphs");
		Iterator<String> iterator = msg.iterator();
		while (iterator.hasNext()) {
			graphNames.add(iterator.next());
			//System.out.println(iterator.next());
		}
		
	}
	
	private Graph addNodes(Graph graph, Iterator iterator){
		int i=0;
		
		while (iterator.hasNext()) {
			StringTokenizer tokenizer = new StringTokenizer((String) iterator.next(), ",");
			while (tokenizer.hasMoreElements()) {
				String elem = (String)tokenizer.nextElement();
				graph.getNodes().get(i).addNeighbour(
						graph.getNodes().get(Integer.parseInt(elem)-1)
						);
			}
			
			i++;
		}
		
		return graph;
	}
}
