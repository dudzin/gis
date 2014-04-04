package pl.edu.pw.gis.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Graph {

	private ArrayList<Node> nodes;
	private ArrayList<Node> before;
	
	public Graph(){
		nodes = new ArrayList<>();
		before  = new ArrayList<>();
	}
	
	public void clearColouring(){
		for (Node n : nodes) {
			n.setColour(0);
		}
		
	}
	
	public void sort(){
		
		Collections.sort(nodes, new Comparator<Node>() {
	        @Override
	        public int compare(Node  node1, Node  node2)
	        {

	            return  node1.compareTo(node2);
	        }
	    });
	}
	
	public void resort(int[] seq){
		
		int l = seq.length;
		System.out.println("length: " + l + " seq: "); 
		for(int i =0;i <l;i++){
			System.out.print(seq[i]);
		}
		
		for(int i =0; i< l;i++){
			
			nodes.set(i, before.get(seq[i]-1));
			System.out.println("i:" + i + " seq[i]: " + seq[i]+ " node from seq: " + 	nodes.get(seq[i]-1));		
			
		}
		
		/*
		Node first = nodes.get(0);
		for(int i =0; i<getFirstNodes()-1;i++){
			nodes.set(i, nodes.get(i+1));
		}
		nodes.set(getFirstNodes()-1, first);*/
	}
	
	public int getFirstNodes(){
		sort();
		int count =1;
		for(int i =1; i<nodes.size();i++){
			if(nodes.get(i).getNeighbours().size() == nodes.get(0).getNeighbours().size()){
				count++;
			}else{
				break;
			}
		}
		//System.out.println("C:" + count);
		
		return count;
	}
	
	public void addNode(Node node){
		nodes.add(node);
	}
	
	public void saveBefore(){
		int l = getFirstNodes();
		before = new ArrayList<>();
		for(int i=0;i<l;i++){
			before.add(nodes.get(i));
		}
	}
	
	public void restoreInitOrder(){
		for(int i=0;i<before.size();i++){
			nodes.set(i, before.get(i));
		}
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<Node> getBefore() {
		return before;
	}

	public void setBefore(ArrayList<Node> before) {
		this.before = before;
	}
	
	
}
