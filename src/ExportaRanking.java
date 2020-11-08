import java.io.*;
import java.util.*;

/**
 * 
 * @author Jonathan Suhett Barbêdo
 *	
 */
public class ExportaRanking {
	
	private File rankingNumeros;
	private File rankingCrescimentos;
	private File rankingMortalidade;
	private File rankingLocal;
	
	/**
	 * Exporta ranking de maiores casos/recuperados/mortos para arquivo csv e tsv
	 * @param opcoes 
	 * @param csv
	 * @param tsv
	 * @param ranking
	 */
	public void exportaNumeros(StatusCaso status, boolean csv, boolean tsv, List<Medicao> ranking) {
		
		if (tsv){
			rankingNumeros = new File("rankingNumeros.tsv");
			try {
				FileWriter fw = new FileWriter(rankingNumeros);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+"	"+"País"+"	"+status);
				for (int i=0; i<ranking.size(); i++) {
					String nomePais = ranking.get(i).getPais().getNome();
					String dado = Integer.toString(ranking.get(i).getCasos());
					pw.println(Integer.toString(i+1)+"	"+nomePais+"	"+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		if (csv) {
			rankingNumeros = new File("rankingNumeros.csv");
			try {
				FileWriter fw = new FileWriter(rankingNumeros);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+","+"País"+","+status);
				for (int i=0; i<ranking.size(); i++) {
					String nomePais = ranking.get(i).getPais().getNome();
					String dado = Integer.toString(ranking.get(i).getCasos());
					pw.println(Integer.toString(i+1)+","+nomePais+","+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
	}
	
	/**
	 * Exporta ranking de crescimento de casos/recuperados/mortos para arquivo csv e tsv
	 * @param csv
	 * @param tsv
	 * @param ranking
	 */
	public void exportaCrescimentos(StatusCaso status, boolean csv, boolean tsv, List<Medicao> ranking) {
		
		if (tsv){
			rankingCrescimentos = new File("rankingCrescimentos.tsv");
			try {
				FileWriter fw = new FileWriter(rankingCrescimentos);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+"	"+"País"+"	"+status);
				for (int i=0; i<ranking.size(); i++) {
					String nomePais = ranking.get(i).getPais().getNome();
					String dado = Float.toString(ranking.get(i).valor());
					pw.println(Integer.toString(i+1)+"	"+nomePais+"	"+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		if (csv) {
			rankingNumeros = new File("rankingCrescimentos.csv");
			try {
				FileWriter fw = new FileWriter(rankingCrescimentos);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+","+"País"+","+status);
				for (int i=0; i<ranking.size(); i++) {
					String nomePais = ranking.get(i).getPais().getNome();
					String dado = Float.toString(ranking.get(i).valor());
					pw.println(Integer.toString(i+1)+","+nomePais+","+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
	}
	
	/**
	 * Exporta ranking de mortalidade para arquivo csv e tsv
	 * @param csv
	 * @param tsv
	 * @param ranking
	 */
	public void exportaMortalidade(boolean csv, boolean tsv, List<Medicao> ranking) {
		
		if (tsv){
			rankingMortalidade = new File("rankingMortalidade.tsv");
			try {
				FileWriter fw = new FileWriter(rankingMortalidade);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+"	"+"País"+"	"+"Mortalidade");
				for (int i=0; i<ranking.size(); i++) {
					String nomePais = ranking.get(i).getPais().getNome();
					String dado = Float.toString(ranking.get(i).valor());
					pw.println(Integer.toString(i+1)+"	"+nomePais+"	"+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		if (csv) {
			rankingMortalidade = new File("rankingMortalidade.csv");
			try {
				FileWriter fw = new FileWriter(rankingMortalidade);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+","+"País"+","+"Mortalidade");
				for (int i=0; i<ranking.size(); i++) {
					String nomePais = ranking.get(i).getPais().getNome();
					String dado = Float.toString(ranking.get(i).valor());
					pw.println(Integer.toString(i+1)+","+nomePais+","+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
	}
	

}
