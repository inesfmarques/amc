package Lists;
public interface Fila{
	boolean emptyQ();
	void insert(int o, int d, double p);
	boolean memberQ(int x);
	void removend(int x);
	void remove();
	Node max();
	boolean searchw(int x, double p);
	void union(List l);
}