import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
/**
 * 
 * @author Miguel
 *
 */
public class DadosApi extends Estatistica{
	

	private LinkedHashMap<String,Pais> paises = new LinkedHashMap<>();
	
	
	
	/**
	 * O controller já validou os dados. 
	 * Essa função calcula o caminho mais eficiente 
	 * para pegar os dados da API
	 * @param requisicao A requisição de dados que veio do usuário
	 */
//	public void start(Controller requisicao) {//requição de dados
	public void start() {
		//Calculcar qual pesquisa deve ser feita aqui
		//De preferência alguma que não exploda a API
		
		readApi();
		getDadosByDate("2020-08-01T00:00:00Z","2020-08-30T00:00:00Z");

	}
	
	public void getDadosByDate(String dateStart, String dateEnd) {
		
		for (String key : this.paises.keySet()) {
			
			String strPais = this.paises.get(key).getSlug();
			Pais pais = this.paises.get(key);
			String link = "https://api.covid19api.com/total/country/" + strPais.replace("\"", "") + "?from=" + dateStart + "&to=" + dateEnd;
			getDadosPais(link,pais);

		}
	}
	
	
	/**
     * Leitura inicial do banco de dados da covid 19, pela covid19api.com 
     * Será lida para guardar na memória, de acordo com a UML do enunciado.
     * @param controller
     */
	public void readApi() {

		
		HttpClient cliente = HttpClient.newBuilder()
		        .version(Version.HTTP_2)
		        .followRedirects(Redirect.ALWAYS)
		        .build();
		        
		        HttpRequest hRequest = HttpRequest.newBuilder()
		        .uri(URI.create("https://api.covid19api.com/summary"))
		        .build();
		        
		        
		        try {
		            HttpResponse<String> resposta = cliente.send(hRequest, HttpResponse.BodyHandlers.ofString());

		            
					JsonObject respostaJson = JsonParser.parseString(resposta.body()).getAsJsonObject() ;
					JsonArray paises =  respostaJson.get("Countries").getAsJsonArray();
		      
					for (Object dados : paises) {
						
					    String strDados = dados.toString();
					    JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();
					    
					    String nome = info.get("Country").toString();
					    String codigo = info.get("CountryCode").toString();
					    String slug = info.get("Slug").toString();
					    
					    Pais pais = new Pais(nome,codigo,slug);
					    
					    this.paises.put(nome, pais);
					}   
					
		        } catch (IOException e) {
		            System.err.println("Problema com a conexão");
		            e.printStackTrace();
		        } catch (InterruptedException e) {
		            System.err.println("Requisição interrompida");
		            e.printStackTrace();
		        }
	}
	
	
	
	private void getDadosPais(String link, Pais pais) {
		
		HttpClient cliente = HttpClient.newBuilder()
		        .version(Version.HTTP_2)
		        .followRedirects(Redirect.ALWAYS)
		        .build();
		        
		        HttpRequest requisicao = HttpRequest.newBuilder()
		        .uri(URI.create(link))
		        .build();
		        
		        try {
		            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

		            
		           
		            JsonArray paisArray =  JsonParser.parseString(resposta.body()).getAsJsonArray();
					
		     
		      
					for (Object dados : paisArray) {
						
						//Gera uma nova medicao para guardar os dados
						Medicao medicao = new Medicao();
						super.inclui(medicao);
						medicao.setPais(pais);
						
					    String strDados = dados.toString();
					    //System.out.println(strDados);
					    JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();
					    
					    //int casos = Integer.parseInt(info.get("Cases").toString().replace("\"", ""));
					    LocalDateTime data = converterData(info.get("Date").toString().replace("\"", ""));
					    //String status = info.get("Status").getAsString().replace("\"", "");
					    
					    
					    //Medicao para casos confirmadados
					    int confirmed = Integer.parseInt(info.get("Confirmed").getAsString().replace("\"", ""));
					    medicao.setStatus(StatusCaso.COMFIRMADOS);
					    medicao.setCasos(confirmed);
					    medicao.setMomento(data);
					    
					    
					    
					    //Nova medicao para mortes
					    Medicao medicao2 = new Medicao();
						super.inclui(medicao2);
						medicao2.setPais(pais);
					    int deaths = Integer.parseInt(info.get("Deaths").getAsString().replace("\"", ""));
					    medicao2.setStatus(StatusCaso.MORTOS);
					    medicao2.setCasos(deaths);
					    medicao2.setMomento(data);
					    
					    
					    //Nova medicao para Recuperados
					    Medicao medicao3 = new Medicao();
						super.inclui(medicao3);
						medicao3.setPais(pais);
						int recovered = Integer.parseInt(info.get("Recovered").getAsString().replace("\"", ""));
					    medicao3.setStatus(StatusCaso.RECUPERADOS);
					    medicao3.setCasos(recovered);
					    medicao3.setMomento(data);
					    
					    
					    /*
					    String name = info.get("Country").toString();
					    String province = info.get("Province").toString().replace("\"", "");
					    controller.getPais(name);
					    
					    if (province.isBlank()) {
					    	float latitude = Float.parseFloat(info.get("Lat").toString().replace("\"", ""));
						    float longitude = Float.parseFloat(info.get("Lon").toString().replace("\"", ""));
						    controller.setLatitude(latitude);
						    controller.setLongitude(longitude);
						}
					    
					    */
					  
					     
					}   
		        } catch (IOException e) {
		            System.err.println("Problema com a conexão");
		            e.printStackTrace();
		        } catch (InterruptedException e) {
		            System.err.println("Requisição interrompida");
		            e.printStackTrace();
		        }
	}
	private static LocalDateTime converterData(String data) {
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		LocalDateTime date = LocalDateTime.parse(data, formato);
		
		return date;
		
	}
}
