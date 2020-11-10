import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



/**
 * 
 * @author Miguel, João
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
		//Miguel, calcule qual pesquise deve ser feita. Mas preste atenção no uso de dados
		//Não precisamos de todas as datas, apenas o final e o inicio dos rankings
		//De preferência alguma que não exploda a API
		String parts[] = requisicao.getDataInicial().split("-");
		String parts2[] = requisicao.getDataFinal().split("-");
		String dataInicial = parts[2] + "-" + parts[1] + "-" + parts[0];
		String dataFinal = parts2[2] + "-" + parts2[1] + "-" + parts2[0];
		
		readApi();
		
		
		//exemplo de link para peguar todos os rankings necessários:
		//https://api.covid19api.com/country/south-africa?from=2020-06-01T00:00:00Z&to=2020-06-01T00:00:01Z
		
		getDadosByDate(dataInicial, dataFinal);
		//getDadosByDate(dataInicial + "T00:00:00Z", dataFinal + "T00:00:00Z");
		super.copy(); //Copia medidas realizadas
		
		boolean tsv = requisicao.isTsv();
		boolean csv = requisicao.isCsv();
		
		char maiorNumero = requisicao.getOpcoesListaNumeros();
		
		switch (maiorNumero) {
		case 1:

			v.printOutput(super.rankingNumerico(StatusCaso.CONFIRMADOS,tsv,csv));
			super.restart();
			break;
			
		case 2:
			
			v.printOutput(super.rankingNumerico(StatusCaso.MORTOS,tsv,csv));
			super.restart();
			break;
			
		case 3:
			
			v.printOutput(super.rankingNumerico(StatusCaso.CONFIRMADOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingNumerico(StatusCaso.MORTOS,tsv,csv));
			super.restart();
			break;
			
		case 4:
			
			v.printOutput(super.rankingNumerico(StatusCaso.RECUPERADOS,tsv,csv));
			super.restart();
			break;
		
		case 5:
			
			v.printOutput(super.rankingNumerico(StatusCaso.CONFIRMADOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingNumerico(StatusCaso.RECUPERADOS,tsv,csv));
			super.restart();
			break;
			
		case 6:
			
			v.printOutput(super.rankingNumerico(StatusCaso.MORTOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingNumerico(StatusCaso.RECUPERADOS,tsv,csv));
			super.restart();
			break;
			
		case 7:
			
			v.printOutput(super.rankingNumerico(StatusCaso.CONFIRMADOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingNumerico(StatusCaso.MORTOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingNumerico(StatusCaso.RECUPERADOS,tsv,csv));
			super.restart();
			break;
		}
		
		
		
		char maiorCrescimento = requisicao.getOpcoesListaCrescimento();
		
		
		switch (maiorCrescimento) {
		case 1:

			v.printOutput(super.rankingCrescimento(StatusCaso.CONFIRMADOS,tsv,csv));
			super.restart();
			break;
			
		case 2:
			
			v.printOutput(super.rankingCrescimento(StatusCaso.MORTOS,tsv,csv));
			super.restart();
			break;
			
		case 3:
			
			v.printOutput(super.rankingCrescimento(StatusCaso.CONFIRMADOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingCrescimento(StatusCaso.MORTOS,tsv,csv));
			super.restart();
			break;
			
		case 4:
			
			v.printOutput(super.rankingCrescimento(StatusCaso.RECUPERADOS,tsv,csv));
			super.restart();
			break;
		
		case 5:
			
			v.printOutput(super.rankingCrescimento(StatusCaso.CONFIRMADOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingCrescimento(StatusCaso.RECUPERADOS,tsv,csv));
			super.restart();
			break;
			
		case 6:
			
			v.printOutput(super.rankingCrescimento(StatusCaso.MORTOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingCrescimento(StatusCaso.RECUPERADOS,tsv,csv));
			super.restart();
			break;
			
		case 7:
			
			v.printOutput(super.rankingCrescimento(StatusCaso.CONFIRMADOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingCrescimento(StatusCaso.MORTOS,tsv,csv));
			super.restart();
			v.printOutput(super.rankingCrescimento(StatusCaso.RECUPERADOS,tsv,csv));
			super.restart();
			break;
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
		        .uri(URI.create("https://api.covid19api.com/countries"))
		        .build();
		        
		        try {
		            HttpResponse<String> resposta = cliente.send(hRequest, HttpResponse.BodyHandlers.ofString());

		            
					JsonObject respostaJson = JsonParser.parseString(resposta.body()).getAsJsonObject() ;
					JsonArray paises =  respostaJson.get("Countries").getAsJsonArray();
		      
					for (Object dados : paises) {
						
					    String strDados = dados.toString();
					    JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();
					    
					    String nome = info.get("Country").toString();
					    //ISO2 = código com 2 letras apenas
					    String codigo = info.get("ISO2").toString();
					    String slug = info.get("Slug").toString();
					    //System.out.println(slug);
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
	

	/**
	 * Pega todos os casos, mortes e recuperados para todos os paises no intervalo dado
	 * @param dateStart primeira data
	 * @param dateEnd segunda data
	 */
	public void getDadosByDate(String dateStart, String dateEnd) {
		int count = 0;
		for (String key : this.paises.keySet()) {
			String strPais = this.paises.get(key).getSlug();
			Pais pais = this.paises.get(key);
			
			//link de exemplo:
			//https://api.covid19api.com/country/south-africa?from=2020-03-01T00:00:00Z&to=2020-04-01T00:00:00Z
			String linkInicial = "https://api.covid19api.com/country/" + strPais.replace("\"", "") + "?from=" +
			dateStart + "T00:00:00Z&to=" + dateStart + "T00:00:01Z";
			String linkFinal = "https://api.covid19api.com/country/" + strPais.replace("\"", "") + "?from=" +
			dateStart + "T00:00:00Z&to=" + dateEnd + "T00:00:01Z";
			getDadosPais(linkInicial, linkFinal,pais);
			count++;
			if(count == 50) { //desative isso, para pegar os outros países
				break;
			}
		}
	}
	
	
	
	/**
	 * Pega dados de todos os casos, recuperados e mortos da API
	 * @param link 
	 * @param linkFinal 
	 * @param pais
	 */
	private void getDadosPais(String linkInicial, String linkFinal, Pais pais) {
		
		HttpClient cliente = HttpClient.newBuilder()
		        .version(Version.HTTP_2)
		        .followRedirects(Redirect.ALWAYS)
		        .build();
		        
				//requisção para o valor na data inicial
		        HttpRequest requisicaoDataInicial = HttpRequest.newBuilder()
		        .uri(URI.create(linkInicial))
		        .build();
		        //requisição para o valor na data final
		        HttpRequest requisicaoDataFinal = HttpRequest.newBuilder()
				        .uri(URI.create(linkFinal))
				        .build();
		        
		        try {
		            HttpResponse<String> respostaInicial = cliente.send(requisicaoDataInicial, HttpResponse.BodyHandlers.ofString());
		            HttpResponse<String> respostaFinal = cliente.send(requisicaoDataInicial, HttpResponse.BodyHandlers.ofString());
		           
		            JsonArray arrayInicial =  JsonParser.parseString(respostaInicial.body()).getAsJsonArray();
		            JsonArray arrayFinal =  JsonParser.parseString(respostaFinal.body()).getAsJsonArray();
		     
		      
					for (Object dados : arrayInicial) {
					
						//coloca os dados numa string
					    String strDados = dados.toString();										
					    //coloca a string como json, para ler mais facilmente com o get("atributo")
					    JsonObject infoInicial = JsonParser.parseString(strDados).getAsJsonObject();	

					    //Miguel, a variável infoFinal precisa ser feita ainda
					    
					    //Pula dados se nao for dado geral do pais
					    if (infoInicial.get("Province").toString().replace("\"", "").isBlank()) {
					    
						    //Gera uma nova medicao para guardar os dados
							Medicao medicao = new Medicao();
							super.inclui(medicao);
							medicao.setPais(pais);
							
							
						    float latitude = Float.parseFloat(infoInicial.get("Lat").toString().replace("\"", ""));
						    pais.setLatitude(latitude);
						    float longitude = Float.parseFloat(infoInicial.get("Lon").toString().replace("\"", ""));
						    pais.setLongitude(longitude);
						    
						    
						    LocalDateTime data = converterData(infoInicial.get("Date").toString().replace("\"", ""));
						    
						    
						    
						    //Medicao para casos confirmadados
						    int confirmed = Integer.parseInt(infoInicial.get("Confirmed").getAsString().replace("\"", ""));
						    medicao.setStatus(StatusCaso.CONFIRMADOS);
						    medicao.setCasos(confirmed);
						    medicao.setMomento(data);
						    
						    
						    
						    //Nova medicao para mortes
						    Medicao medicao2 = new Medicao();
							super.inclui(medicao2);
							medicao2.setPais(pais);
						    int deaths = Integer.parseInt(infoInicial.get("Deaths").getAsString().replace("\"", ""));
						    medicao2.setStatus(StatusCaso.MORTOS);
						    medicao2.setCasos(deaths);
						    medicao2.setMomento(data);
						    
						    
						    //Nova medicao para Recuperados
						    Medicao medicao3 = new Medicao();
							super.inclui(medicao3);
							medicao3.setPais(pais);
							int recovered = Integer.parseInt(infoInicial.get("Recovered").getAsString().replace("\"", ""));
						    medicao3.setStatus(StatusCaso.RECUPERADOS);
						    medicao3.setCasos(recovered);
						    medicao3.setMomento(data);
					    
					    }
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
