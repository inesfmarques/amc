package Graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;


public class Dgraph implements Orient_graphs, Serializable {
	
	private static final long serialVersionUID=1L;
	
	private int dim;
	private ArrayList<LinkedList<Integer>> graph;
	
	public Dgraph(int d){
		dim = d;
		this.graph = new ArrayList<LinkedList<Integer>>();
		for(int i=0; i<d; i++) 
			graph.add(new LinkedList<Integer>());
	}
	

	public int getDim() {
		return dim;
	}


	public void add_edge(int f, int t) {
		if(f<dim && t<dim && !graph.get(f).contains(t))
			{graph.get(f).add(t);}
	}
	
	public void remove_edge(int f, int t) {
		if(f<dim) {
		graph.get(f).removeFirstOccurrence(t);
		}
	}
	
	public LinkedList<Integer> parents(int s) {
		LinkedList<Integer> p = new LinkedList<Integer>();
		for (int i=0; i<dim; i++) {
			if(graph.get(i).contains(s)) {
				p.add(i);
			}	
		}
		return p;
	}
	
}
