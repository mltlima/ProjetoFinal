import java.io.*;
import java.util.ArrayList;

/**
 * 
 * @author Jonathan Suhett Barbêdo
 *	
 */
public class ExportaRanking {
	
	private File topNumeros;
	private File topCrescimentos;
	private File topMortalidade;
	private File topLocal;
	
	
	public void exportaNumeros(int opcoes, boolean csv, boolean tsv, ArrayList<Medicao> medicoes) {
		
		String status = "Mortes";
		if (opcoes%2==1) status = "Casos";
		else if (opcoes>=4) status = "Recuperados";
		
		if (csv){
			topNumeros = new File("topNumeros.tsv");
			try {
				FileWriter fw = new FileWriter(topNumeros);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+"	"+"País"+"	"+status);
				for (int i=0; i<medicoes.size(); i++) {
					String nomePais = medicoes.get(i).getPais().getNome();
					String dado = Integer.toString(medicoes.get(i).getCasos());
					pw.println(Integer.toString(i+1)+"	"+nomePais+"	"+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		else {
			topNumeros = new File("topNumeros.csv");
			try {
				FileWriter fw = new FileWriter(topNumeros);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+","+"País"+","+status);
				for (int i=0; i<medicoes.size(); i++) {
					String nomePais = medicoes.get(i).getPais().getNome();
					String dado = Integer.toString(medicoes.get(i).getCasos());
					pw.println(Integer.toString(i+1)+"	"+nomePais+"	"+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
	}
	
	
	public void exportaCrescimentos(boolean csv, boolean tsv, ArrayList<Medicao> medicoes) {
		
		if (csv){
			topNumeros = new File("topNumeros.tsv");
			try {
				FileWriter fw = new FileWriter(topNumeros);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+"	"+"País");
				for (int i=0; i<medicoes.size(); i++) {
					String nomePais = medicoes.get(i).getPais().getNome();
					pw.println(Integer.toString(i+1)+"	"+nomePais);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		else {
			topNumeros = new File("topNumeros.csv");
			try {
				FileWriter fw = new FileWriter(topNumeros);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+","+"País");
				for (int i=0; i<medicoes.size(); i++) {
					String nomePais = medicoes.get(i).getPais().getNome();
					pw.println(Integer.toString(i+1)+"	"+nomePais);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
	}
	
	
	public void exportaMortalidade(boolean csv, boolean tsv, ArrayList<Medicao> medicoes) {
		
		if (csv){
			topNumeros = new File("topNumeros.tsv");
			try {
				FileWriter fw = new FileWriter(topNumeros);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+"	"+"País"+"	"+"Mortes");
				for (int i=0; i<medicoes.size(); i++) {
					String nomePais = medicoes.get(i).getPais().getNome();
					String dado = Integer.toString(medicoes.get(i).getCasos());
					pw.println(Integer.toString(i+1)+"	"+nomePais+"	"+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		else {
			topNumeros = new File("topNumeros.csv");
			try {
				FileWriter fw = new FileWriter(topNumeros);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("Posição"+","+"País"+","+"Mortes");
				for (int i=0; i<medicoes.size(); i++) {
					String nomePais = medicoes.get(i).getPais().getNome();
					String dado = Integer.toString(medicoes.get(i).getCasos());
					pw.println(Integer.toString(i+1)+"	"+nomePais+"	"+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
	}

}
