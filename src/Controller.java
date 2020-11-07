import java.util.List;

import javax.swing.JOptionPane;
	/**
	 * Cada Controller representa uma requisição do usuário.
	 * Excplore seus getters para ver as diretamente as opções escolhidas no menu.
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
		if(UDF.isDateValid(v.getDataInicial())) {
			this.dataInicial = v.getDataInicial();
		}else {
			msgBox("Data inicial não é válida.");
		}
		if(UDF.isDateValid(v.getDataFinal())) {
			this.dataFinal = dataFinal;
		}else{
			msgBox("Data final não é válida.");
		}
		if(UDF.isFloat(v.getKm())) {
			this.distancia = distancia;
		}else {
			msgBox("Distância não é válida");
		}
		
		this.tsv = v.getCheckTSV();
		this.csv = v.getCheckCSV();
		this.maiorMortalidade = v.getCheckCSV();
		this.opcoesListaNumeros = v.getOpcoesListNumbers();
		this.opcoesListaCrescimento = v.getOpcoesListGrowth();
	}
	
	private static void msgBox(String s) {
		JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @return the dataInicial
	 */
	public String getDataInicial() {
		return dataInicial;
	}


	/**
	 * @param dataInicial the dataInicial to set
	 */
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}


	/**
	 * @return the dataFinal
	 */
	public String getDataFinal() {
		return dataFinal;
	}


	/**
	 * @param dataFinal the dataFinal to set
	 */
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}


	/**
	 * @return the distancia
	 */
	public double getDistancia() {
		return distancia;
	}


	/**
	 * @param distancia the distancia to set
	 */
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}


	/**
	 * @return the tsv
	 */
	public boolean isTsv() {
		return tsv;
	}


	/**
	 * @param tsv the tsv to set
	 */
	public void setTsv(boolean tsv) {
		this.tsv = tsv;
	}


	/**
	 * @return the csv
	 */
	public boolean isCsv() {
		return csv;
	}


	/**
	 * @param csv the csv to set
	 */
	public void setCsv(boolean csv) {
		this.csv = csv;
	}


	/**
	 * @return the maiorMortalidade
	 */
	public boolean isMaiorMortalidade() {
		return maiorMortalidade;
	}


	/**
	 * @param maiorMortalidade the maiorMortalidade to set
	 */
	public void setMaiorMortalidade(boolean maiorMortalidade) {
		this.maiorMortalidade = maiorMortalidade;
	}


	/**
	 * @return the opcoesListaNumeros
	 */
	public char getOpcoesListaNumeros() {
		return opcoesListaNumeros;
	}


	/**
	 * @param opcoesListaNumeros the opcoesListaNumeros to set
	 */
	public void setOpcoesListaNumeros(char opcoesListaNumeros) {
		this.opcoesListaNumeros = opcoesListaNumeros;
	}


	/**
	 * @return the opcoesListaCrescimento
	 */
	public char getOpcoesListaCrescimento() {
		return opcoesListaCrescimento;
	}


	/**
	 * @param opcoesListaCrescimento the opcoesListaCrescimento to set
	 */
	public void setOpcoesListaCrescimento(char opcoesListaCrescimento) {
		this.opcoesListaCrescimento = opcoesListaCrescimento;
	}
}
