package pl.edu.pw.gis.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pl.edu.pw.gis.model.*;

public class LFAlg {

	private Graph graph;
	private Graph bestGraph;
	private int l;
	private int q;
	private int qmax;
	
	
	public void sort(){
		graph.sort();
	}
	
	public int exec(){
		
		l=1;
		q=0;
		sort();
		int c=1;
		ArrayList<Integer> iv ;
		ArrayList<int[]> seq = getSeq(graph.getFirstNodes());
		int j=0;
		
		graph.sort();
		graph.saveBefore();
		
		//for(int j=0;j<graph.getFirstNodes();j++){
		
		for (int[] is : seq) {
			graph.restoreInitOrder();
			graph.clearColouring();
			graph.resort(is);
			j++;		
			System.out.println("LF round: " +j + " of " + seq.size());
			for (Node node : graph.getNodes()) {
				iv =  new ArrayList<Integer>();
				for(Node neighbour: node.getNeighbours()){
					iv.add(neighbour.getColour());
				}
				Collections.sort(iv);
				c=1;
				for (int i=0; i <iv.size();i++) {
					if(c==iv.get(i)){
						c++;
						if(i==iv.size()-1){
							
							if(c>q && q!=0){
								break;
							}
							
							node.setColour(c);
							System.out.println("col set: " + c );
							if(l<c) l++;
							break;
						}
					}else if(i==iv.size()-1){
						//c++;
						node.setColour(c);
						System.out.println("col set: " + c);
						break;
					}
				}
				if(c>q && q!=0){
					break;
				}
				
			}
			
			
			q=l;
			System.out.println("Q:" + q);
			
		}
		return q;
	}
	
	
	public ArrayList<int[]> getSeq(int number){
		int[] numbers = new int[number];
		for(int i =0;i<number;++i){
			numbers[i]=i+1;
		}
		
		return permutations(numbers);
	}
	
	static ArrayList<int[]> permutations(int[] a) {
	    ArrayList<int[]> ret = new ArrayList<int[]>();
	    permutation(a, 0, ret);
	    return ret;
	}

	public static void permutation(int[] arr, int pos, ArrayList<int[]> list){
	    if(arr.length - pos == 1)
	        list.add(arr.clone());
	    else
	        for(int i = pos; i < arr.length; i++){
	            swap(arr, pos, i);
	            permutation(arr, pos+1, list);
	            swap(arr, pos, i);
	        }
	}

	public static void swap(int[] arr, int pos1, int pos2){
	    int h = arr[pos1];
	    arr[pos1] = arr[pos2];
	    arr[pos2] = h;
	}
	
	
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public Graph getBestGraph() {
		return bestGraph;
	}

	public void setBestGraph(Graph bestGraph) {
		this.bestGraph = bestGraph;
	}
	
}
