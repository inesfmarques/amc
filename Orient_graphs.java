package Graph;

import java.util.LinkedList;

public interface Orient_graphs {
	public void add_edge(int f,int t);
	public void remove_edge(int f,int t);
	public LinkedList<Integer> parents (int s);
}
