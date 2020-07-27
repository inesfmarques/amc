package Bayes;

import Graph.Dgraph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import Graph.Amostra;

public class BN implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	Dgraph G;
	double[] thetaC;
	double[][] theta1;
	ArrayList<double[][][]> theta2;
	int[] dom;

	public BN(Dgraph O, Amostra A, double S){
		
		G = O;
		dom = new int[A.size];
		
		LinkedList<Integer> LC = new LinkedList<Integer>();
		LinkedList<Integer> L1 = new LinkedList<Integer>();
		LinkedList<Integer> L2 = new LinkedList<Integer>();
		
		//definir o tipo de cada nó e o seu domínio
		for(int i=0; i<A.size; i++) {
			if (O.parents(i).size()==0) {
				LC.add(i);
				dom[i]=A.range(i);
			} else if(O.parents(i).size()==1) {
				L1.add(i);
				L2.add(i);
				dom[i]=A.range(i);
			} else {
				L2.add(i);
				dom[i]=A.range(i);
			}
		}
		
		//thetas do C
		int d =dom[LC.getFirst()];
		thetaC = new double[d+1];
		for(int j=0; j<=d; j++) {
			LinkedList<Integer> v = new LinkedList<Integer>();
			v.add(j);
			double TC = A.count(LC, v);
			thetaC[j]=(TC+S)/(S*(d+1)+A.length());
		}
		
		//thetas da raíz
		int x1 = L1.getFirst();
		LinkedList<Integer> p = O.parents(x1);
		int d1 = dom[x1];
		int w1 = A.range(p.getFirst());
		
		theta1 = new double[d1+1][w1+1];
		for(int j=0; j<=d1; j++) {
			for(int k=0; k<=w1; k++) {
				LinkedList<Integer> v = new LinkedList<Integer>();
				v.add(k);
				double TW = A.count(p,v);
				v.addFirst(j);
				p.addFirst(x1);
				double T1 = A.count(p,v);
				p.removeFirst();
				theta1[j][k]=(T1+S)/(TW+S*(d1+1));
			}
		}
		
		//thetas dos restantes
		theta2 = new ArrayList<double[][][]>();
		
		for(int i=0; i<L2.size(); i++) {
			int xi = L2.get(i);
			if(xi!=x1) {
				LinkedList<Integer> par = O.parents(xi);//pais xi
				int di = dom[xi];//dominio xi
				int w1i = dom[par.getFirst()];//dominio pai 1 xi
				int w2i = dom[par.getLast()];//dominio pai 2 xi
				
				double[][][] cube = new double[di+1][w1i+1][w2i+1]; //definir o cubo
				for(int j=0; j<=di; j++) { //iterar dominio xi
					for(int k=0; k<=w1i; k++) { //iterar dominio pai 1
						for(int t=0; t<=w2i; t++) { //iterar dominio pai 2
							LinkedList<Integer> v = new LinkedList<Integer>();
							v.add(k);
							v.add(t);
							double Tpar = A.count(par,v);
							v.addFirst(j);
							par.addFirst(xi); //adicionar xi aos pais
							double T2 = A.count(par,v);
							par.removeFirst(); //remover xi aos pais
							cube[j][k][t] = (T2+S)/(Tpar+S*(di+1));
						}
					}
				}
				theta2.add(cube);
			} else {
				//adicionar um cubo sem nada para manter as posições certas
				theta2.add(new double[][][] {{{1}}});
			}
		}
			
		
	}
	
	public double prob(LinkedList<Integer> v) {
		double p = thetaC[v.getLast()];
		for(int i=0; i<v.size()-1; i++) {
			LinkedList<Integer> pais = G.parents(i);
			if(pais.size()==1) {
				p = p*theta1[v.get(i)][v.get(pais.getFirst())];
			} else {
				double[][][] t = theta2.get(i);
				p = p*t[v.get(i)][v.get(pais.getFirst())][v.get(pais.getLast())];
			}	
		}
		return p;
	}
	
}