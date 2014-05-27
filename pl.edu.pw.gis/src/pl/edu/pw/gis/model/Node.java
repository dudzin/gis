package pl.edu.pw.gis.model;

import java.util.ArrayList;

public class Node {

	private int colour;
	private ArrayList<Node> neighbours;
	private int number;
	
	public Node(){
		neighbours = new ArrayList<Node>();
		
	}
	
	public int compareTo(Node node) {
	    final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
	
	    if(this.neighbours.size() == node.neighbours.size()) return EQUAL;
	    if(this.neighbours.size() > node.neighbours.size()) return BEFORE;
	    if(this.neighbours.size() < node.neighbours.size()) return AFTER;
	    
	    return 0;
	}
	
	public void addNeighbour(Node node){
		neighbours.add(node);
	}
	
	public int getColour() {
		return colour;
	}
	public void setColour(int colour) {
		this.colour = colour;
	}
	public ArrayList<Node> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(ArrayList<Node> neighbours) {
		this.neighbours = neighbours;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
	
}
