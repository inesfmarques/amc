package Bayes;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import Graph.Amostra;
import Graph.Dgraph;
import Graph.WGraph;

import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class app1 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					app1 window = new app1();
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
	public app1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(153, 255, 204));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(117, 62, 162, 128);
		frame.getContentPane().add(scrollPane);
		
		JFileChooser fileChooser= new JFileChooser();
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setForeground(new Color(255, 255, 255));
		btnBrowse.setBackground(new Color(51, 102, 102));
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog((Component)e.getSource());
				if(result==JFileChooser.APPROVE_OPTION) {
					textArea.setText(fileChooser.getSelectedFile().toString());
					List<String> lines;
					try {
						//lines são as linhas do ficheiro
						lines = Files.readAllLines(fileChooser.getSelectedFile().toPath(),Charset.defaultCharset());
						Amostra A = new Amostra(lines.get(1).split(",").length);
						//converter os valores em strings para inteiros
						for(int i=0; i<lines.size();i++) {
							String vstring[] = lines.get(i).split(",");
							int[] vint = new int[vstring.length];
							for(int j=0; j<vstring.length; j++) {
								vint[j]=Integer.parseInt(vstring[j]);
							}
							A.add(vint);
						}
						textArea.setText(textArea.getText() + "\n" + "A amostra tem " + A.size + " variaveis");
						
						int c = A.size-1;
						double N = A.length();
						WGraph G = new WGraph(A.size);
						//i percorre a primeira variavel
						for(int i=0; i<c-1; i++) {
							// j percorre a segunda variavel
							
							for(int j=i+1; j<c; j++) {
								//calcular as informacoes mutuas de Xi e Xj
								double sum = 0;
								//k percorre o dominio da classe c
								for(int k=0; k<=A.range(c); k++) {
									LinkedList<Integer> vars = new LinkedList<Integer>();
									LinkedList<Integer> vals = new LinkedList<Integer>();
									vars.add(c);
									vals.add(k);
									double PC = ((double)A.count(vars,vals));
									//dx percorre o dominio da primeira variavel
									for(int dx=0; dx<=A.range(i);dx++) {
										//dy percorre a segunda variavel
										for(int dy=0; dy<=A.range(j); dy++) {
											vars.addFirst(i);
											vals.addFirst(dx);
											double PXC = ((double)A.count(vars,vals));
											vars.addLast(j);
											vals.addLast(dy);
											double PXYC = A.count(vars,vals);
											vars.removeFirst();
											vals.removeFirst();
											
											if(PXYC!=0) {
												double PYC = ((double)A.count(vars,vals))/N;
												sum = sum + PXYC/N*(java.lang.Math.log(PXYC)+java.lang.Math.log(PC)
												-java.lang.Math.log(PYC)-java.lang.Math.log(PXC));
											}
										}
									}
								}
								G.add_edge(i, j, sum);
							}
						}
						//construir arvore geradora maximal
						Dgraph O = G.MST(0);
						//ligar a classe a todos os vertices
						for(int t=0; t<c; t++) {
							O.add_edge(c, t);
						}
						//construir a rede de bayes
						BN Rede = new BN(O,A,0.5);
						
						//guardar a rede de bayes no disco
						FileOutputStream fos= new FileOutputStream("BN.ser");
						ObjectOutputStream oos= new ObjectOutputStream(fos);
						oos.writeObject(Rede);
						oos.close();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnBrowse.setBounds(145, 22, 110, 29);
		frame.getContentPane().add(btnBrowse);
		
	}
}
