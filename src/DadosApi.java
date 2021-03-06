import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	public void start(Controller requisicao, View v) {//requição de dados
		String parts[] = requisicao.getDataInicial().split("-");
		String parts2[] = requisicao.getDataFinal().split("-");
		String dataInicial = parts[2] + "-" + parts[1] + "-" + parts[0];
		String dataFinal = parts2[2] + "-" + parts2[1] + "-" + parts2[0];
		
		readApi();
		
		
		
		getDadosByDate(dataInicial, dataFinal);
		super.copy(); //Copia medidas realizadas
		
		boolean tsv = requisicao.isTsv();
		boolean csv = requisicao.isCsv();
		
		char maiorNumero = requisicao.getOpcoesListaNumeros();
		
		switch (maiorNumero) {
		case 1:

			super.rankingNumerico(StatusCaso.CONFIRMADOS,tsv,csv);
			super.restart();
			break;
			
case 2:
			
			super.rankingNumerico(StatusCaso.MORTOS,tsv,csv);
			super.restart();
			break;
			
		case 3:
			
			super.rankingNumerico(StatusCaso.CONFIRMADOS,tsv,csv);
			super.restart();
			super.rankingNumerico(StatusCaso.MORTOS,tsv,csv);
			super.restart();
			break;
			
		case 4:
			
			super.rankingNumerico(StatusCaso.RECUPERADOS,tsv,csv);
			super.restart();
			break;
		
		case 5:
			
			super.rankingNumerico(StatusCaso.CONFIRMADOS,tsv,csv);
			super.restart();
			super.rankingNumerico(StatusCaso.RECUPERADOS,tsv,csv);
			super.restart();
			break;
			
		case 6:
			
			super.rankingNumerico(StatusCaso.MORTOS,tsv,csv);
			super.restart();
			super.rankingNumerico(StatusCaso.RECUPERADOS,tsv,csv);
			super.restart();
			break;
			
		case 7:
			
			super.rankingNumerico(StatusCaso.CONFIRMADOS,tsv,csv);
			super.restart();
			super.rankingNumerico(StatusCaso.MORTOS,tsv,csv);
			super.restart();
			super.rankingNumerico(StatusCaso.RECUPERADOS,tsv,csv);
			super.restart();
			break;
		}
		
		
		
		char maiorCrescimento = requisicao.getOpcoesListaCrescimento();
		
		
		switch (maiorCrescimento) {
		case 1:

			super.rankingCrescimento(StatusCaso.CONFIRMADOS,tsv,csv);
			super.restart();
			break;
			
		case 2:
			
			super.rankingCrescimento(StatusCaso.MORTOS,tsv,csv);
			super.restart();
			break;
			
		case 3:
			
			super.rankingCrescimento(StatusCaso.CONFIRMADOS,tsv,csv);
			super.restart();
			super.rankingCrescimento(StatusCaso.MORTOS,tsv,csv);
			super.restart();
			break;
			
		case 4:
			
			super.rankingCrescimento(StatusCaso.RECUPERADOS,tsv,csv);
			super.restart();
			break;
		
		case 5:
			
			super.rankingCrescimento(StatusCaso.CONFIRMADOS,tsv,csv);
			super.restart();
			super.rankingCrescimento(StatusCaso.RECUPERADOS,tsv,csv);
			super.restart();
			break;
			
		case 6:
			
			super.rankingCrescimento(StatusCaso.MORTOS,tsv,csv);
			super.restart();
			super.rankingCrescimento(StatusCaso.RECUPERADOS,tsv,csv);
			super.restart();
			break;
			
		case 7:
			
			super.rankingCrescimento(StatusCaso.CONFIRMADOS,tsv,csv);
			super.restart();
			super.rankingCrescimento(StatusCaso.MORTOS,tsv,csv);
			super.restart();
			super.rankingCrescimento(StatusCaso.RECUPERADOS,tsv,csv);
			super.restart();
			break;
		}

		
		
		
		boolean mortalidade = requisicao.isMaiorMortalidade();
		
		
		if (mortalidade) {
			super.rankingMortalidade(tsv, csv);
			super.restart();
		}
		
		
		float raio = (float) requisicao.getDistancia();
		
		
		if (raio > 0) {
			super.distanciaKm(raio,tsv,csv);
			super.restart();
		}

			

		super.er.close();

	}
	
	
	/**
     * Leitura inicial do banco de dados da covid 19, pela covid19api.com 
     * Será lida para guardar na memória, de acordo com a UML do enunciado.
     * @param controller
     */
	public void readApi() {
		HttpClient cliente = HttpClient.newBuilder().version(Version.HTTP_2).followRedirects(Redirect.ALWAYS).build();
		HttpRequest hRequest = HttpRequest.newBuilder().uri(URI.create("https://api.covid19api.com/summary")).build();
		try {         
			HttpResponse<String> resposta = cliente.send(hRequest, HttpResponse.BodyHandlers.ofString()); 
			JsonObject respostaJson = JsonParser.parseString(resposta.body()).getAsJsonObject() ;     
			JsonArray paises = respostaJson.get("Countries").getAsJsonArray();               
			for (Object dados : paises) {                                
				String strDados = dados.toString();                    
				JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();      
				String nome = info.get("Country").toString();                
				String codigo = info.get("CountryCode").toString();   
				String slug = info.get("Slug").toString();            
				Pais pais = new Pais(nome,codigo,slug);                 
				this.paises.put(nome, pais);                     
			} 
		}
		catch (IOException e) {    
			System.err.println("Problema com a conexão");     
			e.printStackTrace();        
		} catch (InterruptedException e) {   
			System.err.println("Requisição interrompida");     
			e.printStackTrace();         
		}
	}
	

	/**
	 * Pega todos os casos, mortes e recuperados para todos os paises no intervalo dado
	 * @param dateStart primeira data
	 * @param dateEnd segunda data
	 */
	public void getDadosByDate(String dateStart, String dateEnd) {
		int count =0;
		for (String key : this.paises.keySet()) {
			
			String strPais = this.paises.get(key).getSlug();
			Pais pais = this.paises.get(key);
			String link = "https://api.covid19api.com/country/" + strPais.replace("\"", "") + "?from=" + dateStart + "T00:00:00Z&to=" + dateEnd + "T00:00:00Z";
			getDadosPais(link,pais,dateStart,dateEnd);
			count++;
			if(count == 10) {
				//break;
			}
		}
	}
	
	
	
	/**
	 * Pega dados de todos os casos, recuperados e mortos da API
	 * @param link 
	 * @param linkFinal 
	 * @param pais
	 */
private void getDadosPais(String link, Pais pais, String dateStart, String dateEnd) {
		
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
					
						
					    String strDados = dados.toString();
					    
					    

					    JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();

					    
					  
					    
					    String province = info.get("Province").toString().replace("\"", "");
					    String strData = info.get("Date").toString().replace("\"", "");
					    
					    
					    //Pula dados se nao for dado geral do pais
					    if (province.isBlank()) {//&& (strData.equals(dateStart + "T00:00:00Z") || strData.equals(dateEnd + "T00:00:00Z"))) {
							
						
					    
						    //Gera uma nova medicao para guardar os dados
					    	//
							Medicao medicao = new Medicao();
							super.inclui(medicao);
							medicao.setPais(pais);
							
							
						    float latitude = Float.parseFloat(info.get("Lat").toString().replace("\"", ""));
						    pais.setLatitude(latitude);
						    float longitude = Float.parseFloat(info.get("Lon").toString().replace("\"", ""));
						    pais.setLongitude(longitude);
						    
						    
						    LocalDateTime data = converterData(info.get("Date").toString().replace("\"", ""));
						    
						    
						    
						    //Medicao para casos confirmadados
						    int confirmed = Integer.parseInt(info.get("Confirmed").getAsString().replace("\"", ""));
						    medicao.setStatus(StatusCaso.CONFIRMADOS);
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
	
	
	
	
	
	
	
	
	/**
	 * Converte a data para LocalDateTime formato yyyy-MM-ddTHH:mm:ssZ
	 * @param data 
	 * @return data formatada
	 */
	private static LocalDateTime converterData(String data) {
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		LocalDateTime date = LocalDateTime.parse(data, formato);
		
		return date;
		
	}
}
