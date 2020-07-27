package Bayes;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class app2 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					app2 window = new app2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public app2() {
		try {
			initialize();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws IOException, ClassNotFoundException{
		
		FileInputStream fis= new FileInputStream("BN.ser");
		ObjectInputStream ois= new ObjectInputStream(fis);
		BN Rede = (BN) ois.readObject();
		ois.close();		
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(153, 204, 255));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(47, 63, 280, 30);
		frame.getContentPane().add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(47, 186, 167, 30);
		frame.getContentPane().add(textArea_1);
		
		JLabel lblIntroduzirParmetrosDo = new JLabel("Introduzir parâmetros do paciente");
		lblIntroduzirParmetrosDo.setBounds(49, 44, 238, 14);
		frame.getContentPane().add(lblIntroduzirParmetrosDo);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(50, 100, 300, 10);
		
		JButton btnClassificar = new JButton("Classificar");
		btnClassificar.setForeground(new Color(255, 255, 255));
		btnClassificar.setBackground(new Color(0, 102, 153));
		btnClassificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				lblNewLabel.setText("");
				frame.getContentPane().add(lblNewLabel);
				String texto = textArea.getText();
				String[] vstring = texto.split(",");
				LinkedList<Integer> vint = new LinkedList<Integer>();
				//verificar se foram introduzidos o numero certo de dados
				if(vstring.length!=Rede.G.getDim()-1) {
					lblNewLabel.setText("Erro: número incorreto de parâmetros");
					frame.getContentPane().add(lblNewLabel);
					textArea_1.setText("");
					return;
				}
				//converter os valores em string para inteiros
				for(int j=0; j<vstring.length; j++) {
					vint.add(Integer.parseInt(vstring[j]));
					//verificar se o valor nao ultrapassa o dominio da variavel
					if(vint.getLast()>Rede.dom[j]) {
						lblNewLabel.setText("Erro: fora do domínio da variável " + j);
						frame.getContentPane().add(lblNewLabel);
						textArea_1.setText("");
						return;
					}
				}
				double[] p = {0,0};
				//calcular a probabilidade do vetor para cada c e ir guardando a maior
				for(int i=0; i<Rede.thetaC.length; i++) {
					vint.add(i);
					double prob = Rede.prob(vint);
					//System.out.println(prob);
					if(prob>p[0]) {
						p[0]=prob;
						p[1]=i;
					}
					vint.removeLast();
				}
				//output
				textArea_1.setText("" + p[1]);
			}
		});
		btnClassificar.setBounds(47, 140, 151, 35);
		frame.getContentPane().add(btnClassificar);
	}
}
