package Lists;

//Lista de arestas

public class List implements Fila{
	int len;
	Node first;
	
	public List() {
		len = 0;
		first = null;
	}
	
	public boolean emptyQ(){
		return len==0;
	}
	
	//insere no inicio
	public void insert(int o, int d, double p) {
		Node n = new Node(o,d,p);
		n.next = first;
		first = n;
		len++;
	}
	
	public boolean memberQ(int x) {
		Node n = first;
		while(n!=null && n.end!=x) {
			n = n.next;
		}
		return n!=null;
	}
	
	//remove a primeira aresta
	public void remove() {
		first = first.next;
		len--;
	}
	
	//remove uma aresta
	public void remove(Node n) {
		Node aux = first;
		if(aux!=null) {
			if(aux.end==n.end && aux.parent==n.parent && aux.w==n.w) {
				remove();
			} else {
				while(aux.next!=null && (aux.end!=n.end || aux.parent!=n.parent || aux.w!=n.w)) {
					aux = aux.next;
				}
				if(aux.next!=null) {
					aux.next=aux.next.next;
					len--;
				}
			}
		}
	}
	
	public String toString() {
		Node aux = first;
		while(aux!=null) {
			System.out.print("(" + aux.parent + "," + aux.end + "," + aux.w + ") ");
			aux = aux.next;
		}
		return "";
	}
	
	//metodos uteis
	
	//remove todas as aretas com destino em x
	public void removend(int x) {
		Node prev = null;
		Node aux = first;
		while(aux!=null && aux.end==x) {first=aux.next;aux=first;len--;}
		if(aux!=null) {
			prev=aux;
			aux=aux.next;
			while(aux!=null) {
				if(aux.end==x) {
					prev.next=aux.next;
					aux=aux.next;
					len--;
				} else {
					prev=aux;
					aux=aux.next;
				}
			}
		}
	}
	
	public Node max() {
		Node aux = first;
		Node max = first;
		while(aux!=null){
			if(aux.w>max.w) {max = aux;}
			aux = aux.next;
		}
		//devolve o node com o peso maximo
		return max;
	}
	
	//devolve true se todas as arestas para x tiverem peso menor p
	public boolean searchw(int x, double p) {
		Node aux = first;
		while(aux!=null){
			if(aux.end==x && aux.w>=p) {return false;}
			aux = aux.next;
		}
		return true;
	}
	
	//uniao de duas listas e, se houverem nodes com destinos iguais escolhe aquele com maior peso
	public void union(List l) {
		Node aux = l.first;
		while(aux!=null){
			if(!memberQ(aux.end)) {
				insert(aux.parent,aux.end,aux.w);
			}
			else if(searchw(aux.end,aux.w)) {
				removend(aux.end);
				insert(aux.parent,aux.end,aux.w);
			}
			aux = aux.next;
		}
	}
}