package Graph;
public interface Weight{
	void add_edge(int n1, int n2, double p);
	void remove_edge(int n1, int n2);
	Dgraph MST(int n);
}