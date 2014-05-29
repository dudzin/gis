package pl.edu.pw.gis.app;

import pl.edu.pw.gis.model.Graph;
import pl.edu.pw.gis.model.Node;

import java.util.ArrayList;
import java.util.Collections;

public class LFAlg {

    private Graph graph;
    private Graph bestGraph;
    private int l;
    private int q;

    //private int qmax;
    private int qmax;
    private int round;
    ArrayList<Integer> iv ;
    private int c;
    private long executionTime;
    private long sortTime;
    private int rejectedCount;
    private int[] bestseq;
    private long singleExecTime;

    public void sort(){
        graph.sort();
    }

    public int exec(){

        long startTime = System.nanoTime();

        l=1; // minimalna l chromatyczna w danej iteracji
        q=0; // minimalna dotychczasowa l chromatyczna
        sort();
        //int
        c=1;
        rejectedCount =0;
        //z�o�ono�� permutations
        ArrayList<int[]> seq = getSeq(graph.getFirstNodes());
        //int j=0;
        //z�o�ono�� Collections.sort
        graph.sort();
        graph.saveBefore();
        round =0;
        sortTime = System.nanoTime()- startTime;
        startTime = System.nanoTime();
        for (int[] is : seq) {
            exec(is, seq.size());
        }
        executionTime = System.nanoTime() - startTime;
        return q;
    }


    public void exec(int[] seq, int seqsize){
        long startTime = System.nanoTime();
        graph.restoreInitOrder();
        graph.clearColouring();
        graph.resort(seq);
        round++;
        //System.out.println("LF round: " +round + " of " + seqsize);
        for (Node node : graph.getNodes()) {
            iv =  new ArrayList<Integer>();
            //zbieramy kolory s�siad�w i sortujemy list�
            for(Node neighbour: node.getNeighbours()){
                if(neighbour.getColour()!= 0) {
                    iv.add(neighbour.getColour());
                }
            }
            Collections.sort(iv);
            c=1;
            //
            if(iv.size() ==0){
                node.setColour(1);
            }else {
                for (int i=0; i <iv.size();i++) {
                    if(c==iv.get(i)){
                        c++;
                        if(i==iv.size()-1){
                            if(c>q && q!=0){
                                rejectedCount++;
                                return;
                                //break;
                            }
                            node.setColour(c);
                            if(l<c) l++;
                            break;
                        }
                    }else if(i==iv.size()-1){
                        node.setColour(c);
                        if(l<c) l++;
                        break;
                    }else {
                        node.setColour(c);
                        break;
                    }
                }
            }
            if(c>q && q!=0){
                rejectedCount++;
                return;
                //break;
            }

            singleExecTime = System.nanoTime() - startTime;
        }
        if(l<q || q==0){
            q=l;
            bestGraph = new Graph(graph.getNodes(), graph.getBefore());
            bestseq = seq;
            //
        }
        //System.out.println("Q:" + q + " l: " + l);
        l=1;
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

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public int getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(int rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public long getSortTime() {
        return sortTime;
    }

    public void setSortTime(long sortTime) {
        this.sortTime = sortTime;
    }

    public int[] getBestseq() {
        return bestseq;
    }

    public void setBestseq(int[] bestseq) {
        this.bestseq = bestseq;
    }

    public long getSingleExecTime() {
        return singleExecTime;
    }

    public void setSingleExecTime(long singleExecTime) {
        this.singleExecTime = singleExecTime;
    }

}
