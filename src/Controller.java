import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
	/**
	 * Cada Controller representa uma requisição do usuário.
	 * Excplore seus getters para ver diretamente as opções escolhidas no menu.
	 * @author JoÃ£o Henrique Schmidt
	 *
	 */
public class Controller {
	private String dataInicial;
	private String dataFinal;
	private double distancia;
	private boolean tsv;
	private boolean csv;
	private boolean maiorMortalidade;
	private char opcoesListaNumeros;
	private char opcoesListaCrescimento;
	
	/**
	 * Pega os dados das opções do menu
	 * @param dataInicial
	 * @param dataFinal
	 * @param distancia
	 * @param tsv
	 * @param csv
	 * @param maiorMortalidade
	 * @param opcoesListaNumeros
	 * @param opcoesListaCrescimento
	 */
	public Controller(View v) {
		boolean isValid = true;
		if(UDF.isDateValid(v.getDataInicial())) {
			this.dataInicial = v.getDataInicial();
		}else {
			msgBox("Data inicial não é válida.");
			isValid = false;
		}
		if(UDF.isDateValid(v.getDataFinal())) {
			this.dataFinal = v.getDataFinal();
		}else{
			msgBox("Data final não é válida.");
			isValid = false;
		}
		if(!v.getKm().equals("")) {
			if(UDF.isFloat(v.getKm())) {
				this.distancia = Float.parseFloat(v.getKm());
			}else {
				msgBox("Distância não é válida");
				isValid = false;
			}
		}else {
			this.distancia = -1;
		}
		
		this.tsv = v.getCheckTSV();
		this.csv = v.getCheckCSV();
		this.maiorMortalidade = v.getCheckCSV();
		this.opcoesListaNumeros = v.getOpcoesListNumbers();
		this.opcoesListaCrescimento = v.getOpcoesListGrowth();
		if(isValid) {
			DadosApi dados = new DadosApi();
			dados.start(this, v);
		}
	}
	
	private static void msgBox(String s) {
		JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void output(List<String> output) {
		
			
			
			   JScrollPane scrollpane = new JScrollPane(); 
			  
			   JList list = new JList(output.toArray());
			
			   scrollpane = new JScrollPane(list);
			
			   JPanel panel = new JPanel(); 
			   panel.add(scrollpane);
			
			   scrollpane.getViewport().add(list);
			   JOptionPane.showMessageDialog(null, scrollpane, "Output",  
			   JOptionPane.PLAIN_MESSAGE);
			
	}

	/**
	 * Acesso ao atributo dataInicial
	 * @return the dataInicial
	 */
	public String getDataInicial() {
		return dataInicial;
	}


	/**
	 * Acesso ao atributo dataFinal
	 * @return the dataFinal
	 */
	public String getDataFinal() {
		return dataFinal;
	}
	
	/**
	 * Acesso ao atributo distancia
	 * @return the distancia
	 */
	public double getDistancia() {
		return distancia;
	}


	/**
	 * Acesso ao atributo tsv
	 * @return the tsv
	 */
	public boolean isTsv() {
		return tsv;
	}



	/**
	 * Acesso ao atributo csv
	 * @return the csv
	 */
	public boolean isCsv() {
		return csv;
	}


	/**
	 * Acesso ao atributo maiorMortalidade
	 * @return the maiorMortalidade
	 */
	public boolean isMaiorMortalidade() {
		return maiorMortalidade;
	}


	/**
	 * Acesso ao atributo opcoesListaNumeros
	 * @return the opcoesListaNumeros
	 */
	public char getOpcoesListaNumeros() {
		return opcoesListaNumeros;
	}

	/**
	 * Acesso ao atributo opcoesListaCrescimento
	 * @return the opcoesListaCrescimento
	 */
	public char getOpcoesListaCrescimento() {
		return opcoesListaCrescimento;
	}

}
