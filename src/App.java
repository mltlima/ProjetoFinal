import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.text.ParseException;
import java.util.HashMap;
//GUI
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Main Class
 *
 */
public class App {
	
	// Testnando UTF8 ãñóõòáÀàü
    public static void main( String[] args ) throws ParseException{
        
        
        new MainMenu();
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu menu = new MainMenu();
					menu.setFrameVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        
//        ControllerPaises controller = new ControllerPaises();
//        readApi(controller);
//        
//        HashMap<String,Pais> paises = controller.getHashMap();
//        for (String key : paises.keySet()) {
//			String strPais = paises.get(key).getSlug();
//			String link = "https://api.covid19api.com/country/" + strPais.replace("\"", "") + "/status/confirmed?from=2020-08-01T00:00:00Z&to=2020-08-01T10:00:00Z";
//			//System.out.println(link);
//			getDadosPais(controller,link);
//		}
//        
//        controller.printHashMap();
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
	public static void getDadosPais(ControllerPaises controller, String link) {
		
		HttpClient cliente = HttpClient.newBuilder()
		        .version(Version.HTTP_2)
		        .followRedirects(Redirect.ALWAYS)
		        .build();
		        
		        HttpRequest requisicao = HttpRequest.newBuilder()
		        .uri(URI.create(link))
		        .build();
		        
		        try {
		            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

		            

		            JsonArray pais =  JsonParser.parseString(resposta.body()).getAsJsonArray();
					
		      
					for (Object dados : pais) {
						
						
					    String strDados = dados.toString();
					    //System.out.println(strDados);
					    JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();
					    
					    String name = info.get("Country").toString();
					    String province = info.get("Province").toString().replace("\"", "");
					    controller.getPais(name);
					    
					    if (province.isBlank()) {
					    	float latitude = Float.parseFloat(info.get("Lat").toString().replace("\"", ""));
						    float longitude = Float.parseFloat(info.get("Lon").toString().replace("\"", ""));
						    controller.setLatitude(latitude);
						    controller.setLongitude(longitude);
						}
					    
					    
					  
					     
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