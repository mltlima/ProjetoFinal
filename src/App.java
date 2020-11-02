import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.text.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Main Class
 *
 */
public class App {
	
	
	
	
    public static void main( String[] args ) throws ParseException{
        
    	Pais modelo = new Pais();
        ControllerPaises controller = new ControllerPaises();
        readApi(controller);
        controller.printHashSet();
    }
    
    
    
    
    
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
		            //JsonArray paises =  JsonParser.parseString(resposta.body()).getAsJsonArray();
					
					//String something = respostaJson.get("Global").toString();
					//System.out.println(something);
		        	
					
					/*
					for (Object element : dayOne) {
						String elements = element.toString();
						System.out.println(elements);
					}
					*/
					
					
		      
					for (Object dados : paises) {
						
						controller.criarNovoPais();
						
					    String strDados = dados.toString();
					    JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();
					    
					    String nome = info.get("Country").toString();
					    String codigo = info.get("CountryCode").toString();
					    String slug = info.get("Slug").toString();
					    
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

}