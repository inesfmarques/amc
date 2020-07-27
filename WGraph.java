package Graph;

import java.util.LinkedList;

import Lists.List;
import Lists.Node;

public class WGraph implements Weight{
	List[] G;
	int dim;
	
	public WGraph(int n) {
		G = new List[n];
		dim = n;
		for(int i=0; i<n; i++) {
			G[i] = new List();
		}
	}
	
	public void add_edge(int n1, int n2, double p) {
		//sem regra para introduzir as arestas, isto é,
		//introduzimos nos dois sentidos, para ser mais fácil ver os nós adjacentes de cada nó
		G[n1].insert(n1,n2,p);
		G[n2].insert(n2,n1,p);
	}
	
	public void remove_edge(int n1, int n2) {
		G[n1].removend(n2);
		G[n2].removend(n1);
	}
	
	public Dgraph MST(int n) {
		Dgraph A = new Dgraph(dim);
		if(n<dim) {
			//V é o conjunto dos vértices a que podemos chegar
			List V = G[n];
			//visited é o conjunto de vértices que já percorremos
			LinkedList<Integer> visited = new LinkedList<Integer>();
			visited.add(n);
			while(!V.emptyQ()) {
				//aresta com peso maximo
				//se max for nulo significa que V já é vazia
				Node max = V.max();
				//System.out.println(max.toString());
				//adicionar a aresta
				A.add_edge(max.getParent(), max.getEnd());
				//adicionar aos vértices visitados, o destino de max
				visited.add(max.getEnd());
				//acrescentar a V os descendentes do nó destino de max
				V.union(G[max.getEnd()]);
				//V = descendentes de visited - visited
				for(int i=0; i<visited.size(); i++) {V.removend(visited.get(i));}
			}
		} else {
			System.out.println("Erro: não existe o nó " + n);
		}
		return A;
		
	}

	public String toString() {
		for(int i=0; i<dim; i++) {
			System.out.println(G[i].toString());
		}
		return "";
	}
	
	
}