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
			rankingLocal = new File("rankingMortalidade.tsv");
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
			rankingLocal = new File("rankingMortalidade.csv");
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
	
	
	public void exportaLocal(boolean csv, boolean tsv, List<Pais> ranking, Pais pais) {
		
		String nomePais = pais.getNome();
		float lat = pais.getLatitude();
		float lon = pais.getLongitude();
		
		if (tsv){
			rankingLocal = new File("rankingLocal.tsv");
			try {
				FileWriter fw = new FileWriter(rankingLocal);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("País"+"	"+"Próximos"+"	"+"Distância");
				pw.println("nomePais");
				for (int i=0; i<ranking.size(); i++) {
					String paises = ranking.get(i).getNome();
					float lat2 = ranking.get(i).getLatitude();
					float lon2 = ranking.get(i).getLongitude();
					float distancia = haversine(lat,lon,lat2,lon2);
					pw.println("	"+paises+"	"+distancia);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		if (csv) {
			rankingLocal = new File("rankingMortalidade.csv");
			try {
				FileWriter fw = new FileWriter(rankingLocal);
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("País"+","+"Próximos"+","+"Distância");
				pw.println("nomePais");
				for (int i=0; i<ranking.size(); i++) {
					String paises = ranking.get(i).getNome();
					float lat2 = ranking.get(i).getLatitude();
					float lon2 = ranking.get(i).getLongitude();
					float distancia = haversine(lat,lon,lat2,lon2);
					pw.println(","+paises+","+distancia);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
	}
	
	public float haversine(float latitude1, float longitude1, float latitude2, float longitude2) {
		//Raio da terra em km
		double raio = 6372.8;
		
		//Distancia entrea as coordenadas em radianos
        double distanciaLat = Math.toRadians(latitude2 - latitude1);
        double distanciaLon = Math.toRadians(longitude2 - longitude1);
        
        //Conversao das latitudes em radianos
        latitude1 = (float) Math.toRadians(latitude1);
        latitude2 = (float) Math.toRadians(latitude2);
        
        // hav(x) = sin^2(x/2) 
        //hav(distanciaLat) + cos(latitude1) * cos(latitude2) * hav(distanciaLon)
        double a = Math.pow(Math.sin(distanciaLat / 2),2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(distanciaLon / 2),2);
        double c = 2 * Math.asin(Math.sqrt(a));
        
        return (float) (raio * c);
    }
	

}
