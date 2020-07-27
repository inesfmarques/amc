package Graph;

import java.util.LinkedList;

public interface Iamostra {
	
	//recebe um vector e acrescenta o vector à amostra
	public void add(int[] v);
	//recebe uma posição e retorna o vector da amostra
	public int[] element(int p);
	//recebe um vector de variáveis (inteiros -> descrição dos dados) e um vector de valores (inteiros -> valor correspondente ao dado) e retorna o número de
	//ocorrências desses valores para essas variáveis na amostra (quantas pessoas têm esses dados iguais a esses valores)
	public double count(LinkedList<Integer> u,LinkedList<Integer> v);
	
}
