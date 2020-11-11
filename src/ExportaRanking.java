import java.awt.Desktop;
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
	private File rankingHtml = new File("rankingHtml.html");
	
	/**
	 * Construtor
	 */
	public ExportaRanking() {
		try {
			FileWriter fw = new FileWriter(rankingHtml);
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
			pw.close();
		}
		catch (IOException f) {
			System.out.println("Erro ao exportar para arquivo");
		}
	}
	
	/**
	 * Exporta ranking de maiores casos/recuperados/mortos para arquivo csv e tsv
	 * @param status diz se a medição é de caso, morte ou recuperação
	 * @param csv define se arquivo csv será exportado
	 * @param tsv define se arquivo tsv será exportado
	 * @param observacoes ranking de números
	 */
	public void exportaNumeros(StatusCaso status, boolean csv, boolean tsv, List<Medicao> observacoes) {
		
		if (tsv){
			rankingNumeros = new File("rankingNumeros.tsv");
			try {
				FileWriter fw = new FileWriter(rankingNumeros);
				PrintWriter pw = new PrintWriter(fw);

				pw.println("Posição"+"	"+"País"+"	"+status);
				for (int i=0; i<observacoes.size(); i++) {
					String nomePais = observacoes.get(i).getPais().getNome();
					String dado = Integer.toString(observacoes.get(i).getCasos());
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
				for (int i=0; i<observacoes.size(); i++) {
					String nomePais = observacoes.get(i).getPais().getNome();
					String dado = Integer.toString(observacoes.get(i).getCasos());
					pw.println(Integer.toString(i+1)+","+nomePais+","+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
		try {
			FileWriter fw = new FileWriter(rankingHtml,true);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("<table class=\"table\">");
			pw.println("<thead>");
			pw.println("<tr class=\"table-secondary\">");
			pw.println(" <td colspan=\"4\">RANKING DO NÚMERO DE "+status+"</td>");
			pw.println("</tr>");
			pw.println("</tread>");
			pw.println("<body>");
			pw.println("    <tr>");
			pw.println("      <th scope=\"col\">POSIÇÃO</th>");
			pw.println("      <th scope=\"col\">PAÍS</th>");
			pw.println("      <th scope=\"col\">"+status+"</th>");
			pw.println("    </tr>");
			pw.println("  </body>");
			pw.println("  <tbody>");
			
			for (int i=0; i<observacoes.size(); i++) {
				String posicao = Integer.toString(i+1);
				String nomePais = observacoes.get(i).getPais().getNome();
				String dado = Integer.toString(observacoes.get(i).getCasos());
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
	 * @param csv define se arquivo csv será exportado
	 * @param tsv define se arquivo tsv será exportado
	 * @param output ranking de crescimento
	 */
	public void exportaCrescimentos(StatusCaso status, boolean csv, boolean tsv, List<Medicao> output) {
		
		if (tsv){
			rankingCrescimentos = new File("rankingCrescimentos.tsv");
			try {
				FileWriter fw = new FileWriter(rankingCrescimentos);
				PrintWriter pw = new PrintWriter(fw);

				pw.println("Posição"+"	"+"País"+"	"+status);
				for (int i=0; i<output.size(); i++) {
					String nomePais = output.get(i).getPais().getNome();
					String dado = Float.toString(output.get(i).valor());
					pw.println(Integer.toString(i+1)+"	"+nomePais+"	"+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
			
		if (csv) {
			rankingCrescimentos = new File("rankingCrescimentos.csv");
			try {
				FileWriter fw = new FileWriter(rankingCrescimentos);
				PrintWriter pw = new PrintWriter(fw);

				pw.println("Posição"+","+"País"+","+status);
				for (int i=0; i<output.size(); i++) {
					String nomePais = output.get(i).getPais().getNome();
					String dado = Float.toString(output.get(i).valor());
					pw.println(Integer.toString(i+1)+","+nomePais+","+dado);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
		try {
			FileWriter fw = new FileWriter(rankingHtml,true);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("<table class=\"table\">");
			pw.println("<tread>");
			pw.println("<tr class=\"table-secondary\">");
			pw.println(" <td colspan=\"4\">RANKING DO CRESCIMENTO DE "+status+"</td>");
			pw.println("</tr>");
			pw.println("</tread>");
			pw.println("  <body>");
			pw.println("    <tr>");
			pw.println("      <th scope=\"col\">POSIÇÃO</th>");
			pw.println("      <th scope=\"col\">PAÍS</th>");
			pw.println("      <th scope=\"col\">"+status+"</th>");
			pw.println("    </tr>");
			pw.println("  </body>");
			pw.println("  <tbody>");
			
			for (int i=0; i<output.size(); i++) {
				String posicao = Integer.toString(i+1);
				String nomePais = output.get(i).getPais().getNome();
				String dado = Float.toString(output.get(i).valor());
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
	 * @param csv define se será exportado arquivo csv
	 * @param tsv define se será exportado arquivo tsv
	 * @param ranking de mortalidade
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
		
		try {
			FileWriter fw = new FileWriter(rankingHtml,true);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("<table class=\"table\">");
			pw.println("<tread>");
			pw.println("<tr class=\"table-secondary\">");
			pw.println(" <td colspan=\"4\">RANKING DE MORTALIDADE</td>");
			pw.println("    </tr>");
			pw.println("  </thead>");
			pw.println("  <body>");
			pw.println("    <tr>");
			pw.println("      <th scope=\"col\">POSIÇÃO</th>");
			pw.println("      <th scope=\"col\">PAÍS</th>");
			pw.println("      <th scope=\"col\">MORTALIDADE</th>");
			pw.println("    </tr>");
			pw.println("  </body>");
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
	 * Exporta a lista de locais próximos ao ponto de maior crescimento para arquivos externos
	 * @param csv define se será arquivo csv será criado
	 * @param tsv define se será arquivo tsv será criado
	 * @param ranking lista dos países próixmos ao de mais crescimento
	 * @param output de maior crescimento
	 */
	public void exportaLocal(boolean csv, boolean tsv, List<Pais> ranking, Pais output) {
		
		String nomePais = output.getNome();
		float lat = output.getLatitude();
		float lon = output.getLongitude();
		
		if (tsv){
			rankingLocal = new File("rankingLocal.tsv");
			try {
				FileWriter fw = new FileWriter(rankingLocal);
				PrintWriter pw = new PrintWriter(fw);

				pw.println("País"+"	"+"Próximos"+"	"+"Distância");
				for (int i=0; i<ranking.size(); i++) {
					String paises = ranking.get(i).getNome();
					float lat2 = ranking.get(i).getLatitude();
					float lon2 = ranking.get(i).getLongitude();
					float distancia = haversine(lat,lon,lat2,lon2);
					pw.println(nomePais+"	"+paises+"	"+distancia);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
		if (csv) {
			rankingLocal = new File("rankingLocal.csv");
			try {
				FileWriter fw = new FileWriter(rankingLocal);
				PrintWriter pw = new PrintWriter(fw);

				pw.println("País"+","+"Próximos"+","+"Distância");
				for (int i=0; i<ranking.size(); i++) {
					String paises = ranking.get(i).getNome();
					float lat2 = ranking.get(i).getLatitude();
					float lon2 = ranking.get(i).getLongitude();
					float distancia = haversine(lat,lon,lat2,lon2);
					pw.println(nomePais+","+paises+","+distancia);
				}
				pw.close();
			}
			catch (IOException f) {
				System.out.println("Erro ao exportar para arquivo");
			}
		}
		
		try {
			FileWriter fw = new FileWriter(rankingHtml,true);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("<table class=\"table\">");
			pw.println("  <thead>");
			pw.println("<tr class=\"table-secondary\">");
			pw.println(" <td colspan=\"4\">Países próximos a "+output.getNome()+"</td>");
			pw.println("</tr>");
			pw.println("</tread>");
			pw.println("<body>");
			pw.println("    <tr>");
			pw.println("      <th scope=\"col\">PAÍS</th>");
			pw.println("      <th scope=\"col\">DISTÂNCIA(km)</th>");
			pw.println("    </tr>");
			pw.println("  </body>");
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
	/**
	 * Encerra o programa e inicia o navegado, 
	 * para o usuário visualizar a interface
	 */
	public void close() {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				File htmlFile = new File("rankingHtml.html").getCanonicalFile();
				Desktop.getDesktop().browse(htmlFile.toURI());
				//Desktop.getDesktop().browse(new URI("rankingHtml.html"));
			}
			catch(IOException e) {
			}
		}
	}
	
	/**
	 * Calcula da distancia em KM das coordenadas usando a formula de Haversine
	 * @param latitude1
	 * @param longitude1
	 * @param latitude2
	 * @param longitude2
	 * @return distancia em km entre os dois pontos
	 */
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
