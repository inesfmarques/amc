package Graph;

import java.util.Arrays;
import java.util.LinkedList;

public class Amostra implements Iamostra {
	
	public int size;
	private int len;
	private LinkedList<int []> a = new LinkedList<int[]>();
	
	
	public Amostra(int s) {
		size = s;
		len=0;
	}
	
	public int length() {
		return len;
	}

	public void add(int[] v) {
		if(v.length==size) {
			a.add(v);
			len++;
		}
	}
	
	public int[] element(int p) {
		return a.get(p);
	}

	
	public double count(LinkedList<Integer> u, LinkedList<Integer> v) {
		if(u.size()==v.size()) {
			int i=0;
			double r=0;
			while (i<len) {
				int j = 0;
				boolean b = true;
				while(j<u.size() && b) {
					if(a.get(i)[u.get(j)]!=v.get(j)) {b=false;}
					j++;
				}
				if(b) {r++;}
				i++;
			}
			return r;
		} else {
			System.out.println("Erro: os vetores não têm o mesmo comprimento");
			return -1;
		}
	}
	
	public int range(int i) {
		if(i<=size) {
			int max = 0;
			for(int j=0; j<len; j++) {
				if(a.get(j)[i]>max) {
					max=a.get(j)[i];
				}
			}
			return max;
		} else {
			System.out.println("Erro: não existe variável com índice i");
			return -1;
		}
	}

	public String toString() {
		for(int i=0; i<len; i++) {
			System.out.println(Arrays.toString(a.get(i)));
		}
		return "Amostra [size= " + size + ", len= " + len + " ]";
	}

	
}
