package Lists;

//cada node é uma aresta com origem, destino e peso

public class Node{
	int parent;
	int end;
	double w;
	Node next;
	
	
	public Node(int o, int v, double p){
		parent = o;
		end = v;
		w = p;
		next = null;
	}
	
	public int getParent() {
		return parent;
	}

	public int getEnd() {
		return end;
	}

	public double getW() {
		return w;
	}

	public String toString() {
		return "Node [parent=" + parent + ", destino=" + end + ", w=" + w + "]";
	}
	
	
	
}