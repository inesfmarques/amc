package Graph;

import java.util.LinkedList;

public interface Iamostra {
	
	//recebe um vector e acrescenta o vector � amostra
	public void add(int[] v);
	//recebe uma posi��o e retorna o vector da amostra
	public int[] element(int p);
	//recebe um vector de vari�veis (inteiros -> descri��o dos dados) e um vector de valores (inteiros -> valor correspondente ao dado) e retorna o n�mero de
	//ocorr�ncias desses valores para essas vari�veis na amostra (quantas pessoas t�m esses dados iguais a esses valores)
	public double count(LinkedList<Integer> u,LinkedList<Integer> v);
	
}
