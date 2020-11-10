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
		
		rankingNumeros = new File("rankingNumeros.html");
		try {
			FileWriter fw = new FileWriter(rankingNumeros);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("<!DOCTYPE html>");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<style>");
			pw.println("table, th, td {");
			pw.println("  border: 1px solid black;");
			pw.println("}");
			pw.println("</style>");
			pw.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">");
			pw.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
			pw.println("</head>");
			pw.println("<table class=\"table\">");
			pw.println("  <thead>");
			pw.println("    <tr>");
			pw.println("      <th scope=\"col\">POSIÇÃO</th>");
			pw.println("      <th scope=\"col\">PAÍS</th>");
			pw.println("      <th scope=\"col\">"+status+"</th>");
			pw.println("    </tr>");
			pw.println("  </thead>");
			pw.println("  <tbody>");
			
			for (int i=0; i<ranking.size(); i++) {
				String posicao = Integer.toString(i+1);
				String nomePais = ranking.get(i).getPais().getNome();
				String dado = Integer.toString(ranking.get(i).getCasos());
				pw.println("    <tr>");
				pw.println("      <th scope=\"row\">"+posicao+"</th>");
				pw.println("      <td>"+ nomePais +"</td>");
				pw.println("      <td>"+ dado +"</td>");
				pw.println("    </tr>");
			}
			pw.println("  </tbody>");
			pw.println("</table>");
		
			pw.close();
		}
		catch (IOException f) {
			System.out.println("Erro ao exportar para arquivo");
		}
	}

	
	/**
	 * Exporta ranking de crescimento de casos/recuperados/mortos para arquivo csv e tsv
	 * @param csv
	 * @param tsv
	 * @param ranking
	 */
	public void exportaCrescimentos(StatusCaso status, boolean csv, boolean tsv, List<Medicao> ranking) {
		
		rankingCrescimentos = new File("rankingCrescimentos.html");
		try {
			FileWriter fw = new FileWriter(rankingCrescimentos);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("<!DOCTYPE html>");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<style>");
			pw.println("table, th, td {");
			pw.println("  border: 1px solid black;");
			pw.println("}");
			pw.println("</style>");
			pw.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">");
			pw.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
			pw.println("</head>");
			pw.println("<table class=\"table\">");
			pw.println("  <thead>");
			pw.println("    <tr>");
			pw.println("      <th scope=\"col\">POSIÇÃO</th>");
			pw.println("      <th scope=\"col\">PAÍS</th>");
			pw.println("      <th scope=\"col\">"+status+"</th>");
			pw.println("    </tr>");
			pw.println("  </thead>");
			pw.println("  <tbody>");
			
			for (int i=0; i<ranking.size(); i++) {
				String posicao = Integer.toString(i+1);
				String nomePais = ranking.get(i).getPais().getNome();
				String dado = Float.toString(ranking.get(i).valor());
				pw.println("    <tr>");
				pw.println("      <th scope=\"row\">"+posicao+"</th>");
				pw.println("      <td>"+ nomePais +"</td>");
				pw.println("      <td>"+ dado +"</td>");
				pw.println("    </tr>");
			}
			pw.println("  </tbody>");
			pw.println("</table>");
		
			pw.close();
		}
		catch (IOException f) {
			System.out.println("Erro ao exportar para arquivo");
		}
		
	}
	
	/**
	 * Exporta ranking de mortalidade para arquivo csv e tsv
	 * @param csv
	 * @param tsv
	 * @param ranking
	 */
	public void exportaMortalidade(boolean csv, boolean tsv, List<Medicao> ranking) {
		
		rankingMortalidade = new File("rankingMortalidade.html");
		try {
			FileWriter fw = new FileWriter(rankingMortalidade);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("<!DOCTYPE html>");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<style>");
			pw.println("table, th, td {");
			pw.println("  border: 1px solid black;");
			pw.println("}");
			pw.println("</style>");
			pw.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">");
			pw.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
			pw.println("</head>");
			pw.println("<table class=\"table\">");
			pw.println("  <thead>");
			pw.println("    <tr>");
			pw.println("      <th scope=\"col\">POSIÇÃO</th>");
			pw.println("      <th scope=\"col\">PAÍS</th>");
			pw.println("      <th scope=\"col\">MORTALIDADE</th>");
			pw.println("    </tr>");
			pw.println("  </thead>");
			pw.println("  <tbody>");
			
			for (int i=0; i<ranking.size(); i++) {
				String posicao = Integer.toString(i+1);
				String nomePais = ranking.get(i).getPais().getNome();
				String dado = Float.toString(ranking.get(i).valor());
				pw.println("    <tr>");
				pw.println("      <th scope=\"row\">"+posicao+"</th>");
				pw.println("      <td>"+ nomePais +"</td>");
				pw.println("      <td>"+ dado +"</td>");
				pw.println("    </tr>");
			}
			pw.println("  </tbody>");
			pw.println("</table>");
		
			pw.close();
		}
		catch (IOException f) {
			System.out.println("Erro ao exportar para arquivo");
		}
		
	}
	
	
	public void exportaLocal(boolean csv, boolean tsv, List<Pais> ranking, Pais pais) {
		
		String nomePais = pais.getNome();
		float lat = pais.getLatitude();
		float lon = pais.getLongitude();
		
		rankingLocal = new File("rankingLocal.html");
		try {
			FileWriter fw = new FileWriter(rankingLocal);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("<!DOCTYPE html>");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<style>");
			pw.println("table, th, td {");
			pw.println("  border: 1px solid black;");
			pw.println("}");
			pw.println("</style>");
			pw.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">");
			pw.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\" integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\" crossorigin=\"anonymous\"></script>");
			pw.println("</head>");
			pw.println("<caption>"+nomePais+"</caption>");
			pw.println("<table class=\"table\">");
			pw.println("  <thead>");
			pw.println("    <tr>");
			pw.println("      <th scope=\"col\">POSIÇÃO</th>");
			pw.println("      <th scope=\"col\">PAÍS</th>");
			pw.println("      <th scope=\"col\">MORTALIDADE</th>");
			pw.println("    </tr>");
			pw.println("  </thead>");
			pw.println("  <tbody>");
			
			for (int i=0; i<ranking.size(); i++) {
				String paises = ranking.get(i).getNome();
				float lat2 = ranking.get(i).getLatitude();
				float lon2 = ranking.get(i).getLongitude();
				String distancia = Float.toString(haversine(lat,lon,lat2,lon2));
				pw.println("    <tr>");
				pw.println("      <th scope=\"row\">"+paises+"</th>");
				pw.println("      <td>"+ distancia +"</td>");
				pw.println("    </tr>");
			}
			pw.println("  </tbody>");
			pw.println("</table>");
		
			pw.close();
		}
		catch (IOException f) {
			System.out.println("Erro ao exportar para arquivo");
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
