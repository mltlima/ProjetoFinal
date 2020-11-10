import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;




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
		
		try {
		getCountriesCache(); //Pega dados dos países: nome, código, slug;
		} catch (IOException e) {
	        UDF.msgBox("Problema com a conexão");
	        e.printStackTrace();
	    } catch (InterruptedException e) {
	        UDF.msgBox("Requisição interrompida");
	        e.printStackTrace();
	    } catch (JsonSyntaxException e){
	    	UDF.msgBox("Houve algum problema com o json fornecido");
	    	e.printStackTrace();
	    }
		
		//exemplo de link para peguar todos os rankings necessários:
		//https://api.covid19api.com/country/south-africa?from=2020-06-01T00:00:00Z&to=2020-06-01T00:00:01Z
		
		getDadosByDate(dataInicial, dataFinal);
		//getDadosByDate(dataInicial + "T00:00:00Z", dataFinal + "T00:00:00Z");
		//super.copy(); //Copia medidas realizadas
		
		boolean tsv = requisicao.isTsv();
		boolean csv = requisicao.isCsv();
		
//		char maiorNumero = requisicao.getOpcoesListaNumeros();
		
		char opcoesNum = requisicao.getOpcoesListaNumeros();
		if(opcoesNum%2==1) //casos
			v.printOutput(super.rankingNumerico(StatusCaso.CONFIRMADOS,tsv,csv));
		if((opcoesNum&0b10) == 0b10) //mortos
			v.printOutput(super.rankingNumerico(StatusCaso.MORTOS,tsv,csv));
		if((opcoesNum&0b100) == 0b100) //recuperados
			v.printOutput(super.rankingNumerico(StatusCaso.RECUPERADOS,tsv,csv));
		
		char opcoesCresc = requisicao.getOpcoesListaNumeros();
		if(opcoesCresc%2==1) //casos
			v.printOutput(super.rankingCrescimento(StatusCaso.CONFIRMADOS,tsv,csv));
		if((opcoesCresc&0b10) == 0b10) //mortos
			v.printOutput(super.rankingCrescimento(StatusCaso.MORTOS,tsv,csv));
		if((opcoesCresc&0b100) == 0b100) //recuperados
			v.printOutput(super.rankingCrescimento(StatusCaso.RECUPERADOS,tsv,csv));
	}
	
	
	
	
	/**
     * Leitura inicial do banco de dados da covid 19, pela covid19api.com 
     * Pega as informações dos países apenas!
     * Será lida para guardar na memória, de acordo com a UML do enunciado.
	 * @throws IOException 
	 * @throws JsonSyntaxException 
	 * @throws InterruptedException Caso a  
     */
	private void getCountriesCache() throws JsonSyntaxException, IOException, InterruptedException {
		
		File file = new File("countries.json").getCanonicalFile();
		JsonArray paises;
		
		if(!file.exists()) { //Se arq não existir, irá pegas as informações da internet
			HttpClient cliente = HttpClient.newBuilder()
	        .version(Version.HTTP_2)
	        .followRedirects(Redirect.ALWAYS)
	        .build();
	        
	        HttpRequest hRequest = HttpRequest.newBuilder()
	        .uri(URI.create("https://api.covid19api.com/countries"))
	        .build();
	        
	        
	            HttpResponse<String> resposta = cliente.send(hRequest, HttpResponse.BodyHandlers.ofString());
				paises =  JsonParser.parseString(resposta.body()).getAsJsonArray();
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(paises.getAsString());
				fileWriter.flush();
				fileWriter.close();//não tenho certeza se está fazendo flush
				
		}else {
			//pegando o caminho do arquivo
			Path path = Path.of(file.getAbsolutePath());
			paises = JsonParser.parseString(Files.readString(path)).getAsJsonArray();
			
		}
		for (Object dados : paises) {
			
		    String strDados = dados.toString();
		    JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();
		    
		    String nome = info.get("Country").toString();
		    //ISO2 = código com 2 letras apenas
		    String codigo = info.get("ISO2").toString();
		    String slug = info.get("Slug").toString();
		    //System.out.println(slug);
		    
		    if(!this.paises.containsKey(nome)) {
			    Pais pais = new Pais(nome,codigo,slug);
			    this.paises.put(nome, pais);
		    }
		}   
		
	}
	/*
	 * Tenta buscar os dados gerais no computador, se não achar pega na internet
	 */
	private void getCache() throws IOException {
		File file = new File("cache.json").getCanonicalFile();
		if(!file.exists()) {
			file.createNewFile();
			
			HttpClient cliente = HttpClient.newBuilder()
			        .version(Version.HTTP_2)
			        .followRedirects(Redirect.ALWAYS)
			        .build();
			HttpRequest hRequest = HttpRequest.newBuilder()
			        .uri(URI.create("https://api.covid19api.com/all"))
			        .build();
			
			HttpResponse<String> resposta;
			try {
				resposta = cliente.send(hRequest, HttpResponse.BodyHandlers.ofString());
				JsonArray all =  JsonParser.parseString(resposta.body()).getAsJsonArray();
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(all.getAsString());
				fileWriter.flush();
				fileWriter.close();//não tenho certeza se está fazendo flush
			} catch (InterruptedException e) {
				UDF.msgBox("Api interrompeu a conexão.");
				e.printStackTrace();
			}
		}
	}
	
	private void getDadosPais(String dateStart, String dateEnd) {
		getCache();
		int count = 0;
		for (String key : this.paises.keySet()) {
			String strPais = this.paises.get(key).getSlug();
			Pais pais = this.paises.get(key);
			
			//link de exemplo:
			//https://api.covid19api.com/country/south-africa?from=2020-03-01T00:00:00Z&to=2020-04-01T00:00:00Z
			
			//Caso o cache não possua os dados, iremos baixar bastante dados
			String link = "https://api.covid19api.com/country/" + strPais.replace("\"", "") + "?from=" + dateStart + "&to=" + dateEnd;
			//getDadosPais(linkInicial, linkFinal,link, pais);
			count++;
			if(count == 3) { //ative isso para pegar os outros países
				//break;
			}
		}
		
		
		        
	        
        try {
        	File file = new File("cache.json").getCanonicalFile();
        	Path path = Path.of(file.getAbsolutePath());
			JsonArray cacheArr = JsonParser.parseString(Files.readString(path)).getAsJsonArray();
            
            //JsonArray tera apenas um elemento
            //Se tiver mais de dois lançará um aviso
            if(cacheArr.size() != 1) System.out.println("Resposta tem mais de um elemento no array");
            
        	String strDados = arrayInicial.get(0).toString();
        	JsonObject infoInicial = JsonParser.parseString(strDados).getAsJsonObject();
        	strDados = arrayFinal.get(0).toString();
        	JsonObject infoFinal = JsonParser.parseString(strDados).getAsJsonObject();
        	
        //////////colocando longitude e latitude do PARAMETRO pais
        	
        	float latitude = Float.parseFloat(infoInicial.get("Lat").toString().replace("\"", ""));
		    pais.setLatitude(latitude);
		    float longitude = Float.parseFloat(infoInicial.get("Lon").toString().replace("\"", ""));
		    pais.setLongitude(longitude);
        	
		//////////medições
		    LocalDate dataI = converterData(infoInicial.get("Date").toString().replace("\"", "")).toLocalDate();
		    LocalDate dataF = converterData(infoFinal.get("Date").toString().replace("\"", "")).toLocalDate();
        	Medicao medicaoCasos = new Medicao(dataI, dataF);
        	Medicao medicaoMortes = new Medicao(dataI,dataF);
        	Medicao medicaoRecup = new Medicao(dataI,dataF);
        	
        	super.inclui(medicaoCasos);
        	super.inclui(medicaoMortes);
        	super.inclui(medicaoRecup);
        	
        	medicaoCasos.setPais(pais);
        	medicaoMortes.setPais(pais);
        	medicaoRecup.setPais(pais);
        	
        	medicaoCasos.setCasos(Integer.parseInt(infoInicial.get("Confirmed").getAsString().replace("\"", "")));
        	medicaoMortes.setCasos(Integer.parseInt(infoInicial.get("Deaths").getAsString().replace("\"", "")));
        	medicaoRecup.setCasos(Integer.parseInt(infoInicial.get("Recovered").getAsString().replace("\"", "")));
        	
        	medicaoCasos.setStatus(StatusCaso.CONFIRMADOS);
        	medicaoMortes.setStatus(StatusCaso.MORTOS);
        	medicaoRecup.setStatus(StatusCaso.RECUPERADOS);
            
        	medicaoCasos.setMomento(converterData(infoFinal.get("Date").toString().replace("\"", "")));
        	medicaoMortes.setMomento(converterData(infoFinal.get("Date").toString().replace("\"", "")));
        	medicaoRecup.setMomento(converterData(infoFinal.get("Date").toString().replace("\"", "")));
		    
			   
        } catch (IOException e) {
            System.err.println("Problema com a conexão");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Requisição interrompida");
            e.printStackTrace();
        }
        
      //requisição para o valor na data final
        HttpRequest requisicao = HttpRequest.newBuilder()
	        .uri(URI.create(link))
	        .build();
	        
        
//        try {
//            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
//            JsonArray paisArray =  JsonParser.parseString(resposta.body()).getAsJsonArray();
        
//			for (Object dados : paisArray) {
//				//pegando info
//			    String strDados = dados.toString();
//			    JsonObject info = JsonParser.parseString(strDados).getAsJsonObject();
//			    String province = info.get("Province").toString().replace("\"", "");
//			    
//			    //Pula dados se nao for dado geral do pais
//			    if (province.isBlank()) {
//				    //Gera uma nova medicao para guardar os dados
//					Medicao medicao = new Medicao();
//					super.inclui(medicao);
//					medicao.setPais(pais);
//					//colocando latitude e longitude
//				    float latitude = Float.parseFloat(info.get("Lat").toString().replace("\"", ""));
//				    pais.setLatitude(latitude);
//				    float longitude = Float.parseFloat(info.get("Lon").toString().replace("\"", ""));
//				    pais.setLongitude(longitude);
//				    
//				    LocalDateTime data = converterData(info.get("Date").toString().replace("\"", ""));
//				    
//				    //Medicao para casos confirmadados
//				    int confirmed = Integer.parseInt(info.get("Confirmed").getAsString().replace("\"", ""));
//				    medicao.setStatus(StatusCaso.CONFIRMADOS);
//				    medicao.setCasos(confirmed);
//				    medicao.setMomento(data);
//				    
//				    //Nova medicao para mortes
//				    Medicao medicao2 = new Medicao();
//					super.inclui(medicao2);
//					medicao2.setPais(pais);
//				    int deaths = Integer.parseInt(info.get("Deaths").getAsString().replace("\"", ""));
//				    medicao2.setStatus(StatusCaso.MORTOS);
//				    medicao2.setCasos(deaths);
//				    medicao2.setMomento(data);
//				    
//				    //Nova medicao para Recuperados
//				    Medicao medicao3 = new Medicao();
//					super.inclui(medicao3);
//					medicao3.setPais(pais);
//					int recovered = Integer.parseInt(info.get("Recovered").getAsString().replace("\"", ""));
//				    medicao3.setStatus(StatusCaso.RECUPERADOS);
//				    medicao3.setCasos(recovered);
//				    medicao3.setMomento(data);
//			    
//			    }
//			}   
//        } catch (IOException e) {
//            System.err.println("Problema com a conexão");
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            System.err.println("Requisição interrompida");
//            e.printStackTrace();
//        }
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
