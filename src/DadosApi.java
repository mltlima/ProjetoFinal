import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
	
	private ControllerPaises controller = new ControllerPaises();
	private HashMap<String,Pais> paises = controller.getHashMap();
	
	public void start() {
		
		readApi(this.controller);
		getDadosByDateStatus("deaths","2020-09-01T00:00:00Z","2020-09-03T00:00:00Z");
//      controller.printHashMap();
	}
	
	public void getDadosByDateStatus(String status, String dateStart, String dateEnd) {
		
		
		
		for (String key : this.paises.keySet()) {
			
			String strPais = this.paises.get(key).getSlug();
			Pais pais = this.paises.get(key);
			String link = "https://api.covid19api.com/total/country/" + strPais.replace("\"", "") + "/status/" + status + "?from=" + dateStart + "&to=" + dateEnd;
			getDadosPais(this.controller,link,pais);

		}
	}
	
	/**
     * Leitura inicial do banco de dados da covid 19, pela covid19api.com 
     * Será lida para guardar na memória, de acordo com a UML do enunciado.
     * @param controller
     */
	public static void readApi(ControllerPaises controller) {
		
		HttpClient cliente = HttpClient.newBuilder()
		        .version(Version.HTTP_2)
		        .followRedirects(Redirect.ALWAYS)
		        .build();
		        
		        HttpRequest requisicao = HttpRequest.newBuilder()
		        .uri(URI.create("https://api.covid19api.com/summary"))
		        .build();
		        
		        
		        try {
		            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

		            
					JsonObject respostaJson = JsonParser.parseString(resposta.body()).getAsJsonObject() ;
					JsonArray paises =  respostaJson.get("Countries").getAsJsonArray();
		      
					for (Object dados : paises) {
						
					    String strDados = dados.toString();
					    JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();
					    
					    String nome = info.get("Country").toString();
					    String codigo = info.get("CountryCode").toString();
					    String slug = info.get("Slug").toString();
					    
					    controller.criarNovoPais(nome);
					    controller.setNome(nome);
					    controller.setCodigo(codigo);
					    controller.setSlug(slug);
					     
					}   
		        } catch (IOException e) {
		            System.err.println("Problema com a conexão");
		            e.printStackTrace();
		        } catch (InterruptedException e) {
		            System.err.println("Requisição interrompida");
		            e.printStackTrace();
		        }
	}
	
	
	
	/**
	 *  
	 * @param controller
	 * @param link
	 */
	public void getDadosPais(ControllerPaises controller, String link, Pais pais) {
		
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
					    
					    int casos = Integer.parseInt(info.get("Cases").toString().replace("\"", ""));
					    LocalDateTime data = converterData(info.get("Date").toString().replace("\"", ""));
					    String status = info.get("Status").getAsString().replace("\"", "");
					    
					    //Verifica o tipo de dado e passa para medicao
					    if (status.equals("deaths")) {
					    	medicao.setStatus(StatusCaso.MORTOS);
						}
					    if (status.equals("confirmed")) {
					    	medicao.setStatus(StatusCaso.COMFIRMADOS);
						}
					    if (status.equals("recovered")) {
					    	medicao.setStatus(StatusCaso.RECUPERADOS);
						}
					    
					    
					    medicao.setCasos(casos);
					    medicao.setMomento(data);
					    
					    
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
	/**
	 * Convert a data de String para LocalDateTime
	 * @param data String em formato de data yyyy-MM-dd'T'HH:mm:ss'Z'
	 * @return string formatada
	 */
	public static LocalDateTime converterData(String data) {
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		LocalDateTime date = LocalDateTime.parse(data, formato);
		
		return date;
		
	}
}
