import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 
 * @author Jonathan
 *
 */

public abstract class Estatistica {
	
	private String nome;
	private List<Medicao> observacoes  = new ArrayList<Medicao>();
	private float valor;
	private List<Medicao> copiaObservacoes;
	private LocalDate dataFim;
	private LocalDate dataInicio;
	//para manter a integridade dos arquivos
	private ExportaRanking er;
	
	/**
	 * @param dataFim data de inicio da estatistica
	 * @param dataInicio data final da estatistica
	 */
	public Estatistica(LocalDate dataFim, LocalDate dataInicio) {
		this.dataFim = dataFim;
		this.dataInicio = dataInicio;
	}
	/**
	 * Caso queira iniciar estatisticas sem as datas, também é possível
	 */
	public Estatistica() {}
	/**
	 * Insere objeto na lista de medicoes
	 * @param observacao dados da medicao
	 */
	public void inclui(Medicao observacao) {
		this.observacoes.add(observacao);
	}
	/**
	 * 
	 * @return data do inico da medicao
	 */
	public LocalDate dataInicio() {
//		LocalDateTime data = this.copiaObservacoes.get(0).getMomento();
//		return data;
		return dataInicio;
	}
	/**
	 * 
	 * @return data do fim da medicao
	 */
	public LocalDate dataFim() {
//		LocalDateTime data = this.copiaObservacoes.get(this.copiaObservacoes.size() - 1).getMomento();
//		return data;
		return dataFim;
	}
	
	/**
	 * @return valor da taxa calculada
	 */
	public float valor() {
		return this.valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(float valor) {
		this.valor = valor;
	}
	/**
	 * Copia lista de medicoes
	 */
	public void copy() {
		this.copiaObservacoes = new ArrayList<>(observacoes);
	}
	/**
	 * Copia de volta a lista de medicoes originais
	 */
	public void restart() {
		Collections.copy(this.observacoes, this.copiaObservacoes); 
	}
	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	
	// deletar apos testes
	public void test() {
		for (Medicao medicao : observacoes) {
			
			
				System.out.println(medicao.getStatus() + "  " + medicao.getCasos());
				System.out.println(medicao.getPais().getSlug());
			
		}
	}
	
	
	
	/**
	 * Organiza lista com medicoes do maior numero de casos ao menor
	 * @param status
	 */
	public List<String> rankingNumerico(StatusCaso status, boolean tsv, boolean csv) {
		
		// organiza do menor ao maior
		Collections.sort(this.observacoes, new medicaoComparator());
		// inverte a ordem
		Collections.reverse(this.observacoes);
		List<String> output = new ArrayList<>();
		
		for (Medicao medicao : observacoes) {
			if (medicao.getStatus().equals(status)) {
				String str = medicao.getPais().getSlug() + " " + medicao.valor();
				output.add(str);
			}
		}
		
		ExportaRanking er = new ExportaRanking();
		er.exportaNumeros(status, csv, tsv, this.observacoes);
		
		return output;
	}
	
	
	
	
	/**
	 * Organiza lista por taxa de crescimento
	 * pega o valor da data de inicio e fim 
	 * para calcular a taxa de crescimento
	 * @param status tipo de medicao confirmados, recuperados ou mortos
	 */

	public List<String> rankingCrescimento(StatusCaso status, boolean tsv, boolean csv) {

		
//		float temp = 0;
//		
//		for (Medicao medicao : observacoes) {
//			
//			if (medicao.getStatus().equals(status)) {
//				
//				if (medicao.getMomento().equals(dataInicio())) {
//					temp = medicao.getCasos();
//					//System.out.println(temp);
//				}
//				if (medicao.getMomento().equals(dataFim())) {
//					//Passa o valor da taxa para a ultima data
//					if (temp != 0) {
//						//Divide numero diferente de zero
//						medicao.setValor((medicao.getCasos() - temp) / temp);
//						temp = 0;
//					} else {
//						medicao.setValor((medicao.getCasos() - 1) * 1.0f);
//					}
//				
//				}
//			}
//		}
		
		Collections.sort(this.observacoes, new comparatorCrescimento());
		Collections.reverse(this.observacoes);
		

		List<String> output = new ArrayList<>();
		
		
		for (Medicao medicao : observacoes) {
			//Printa taxa maior que 0
			if (medicao.valor() > 0) {
				String str = medicao.getPais().getSlug() + " " + medicao.valor();
				output.add(str);
			}
		}
		
		ExportaRanking er = new ExportaRanking();
		er.exportaCrescimentos(status, csv, tsv, this.observacoes);
		
		return output;
	}
	
	
	
	
	
	/**
	 * Organiza paises pela maior taxa de mortalidade
	 * @return false se não achar algum dado
	 */
	public void rankingMortalidade(boolean tsv, boolean csv) {
		
//		float temp = 0;
//		int casosInicio = 0;
//		int casosFim = 0;
//		DadosApi consulta = new DadosApi();
//		
//		for (Medicao medicao : observacoes) {
//			
//			
//			if (medicao.getStatus().equals(StatusCaso.MORTOS) || 
//					(medicao.getStatus().equals(StatusCaso.CONFIRMADOS))) {
//				
//				//String pais = medicao.getPais().getSlug();
//			
//				if (medicao.getMomento().equals(dataInicio())) {
//					
//					if (medicao.getStatus().equals(StatusCaso.CONFIRMADOS)) {
//						casosInicio = medicao.getCasos();
//					} else {
//						temp = medicao.getCasos();						
//					}
//				}
//				if (medicao.getMomento().equals(dataFim())) {
//					
//					if (medicao.getStatus().equals(StatusCaso.CONFIRMADOS)) {
//						casosFim = medicao.getCasos();
//					} else {
//						if (temp != 0) {
//							medicao.setValor((medicao.getCasos() - temp) * 1.0f / (casosFim - casosInicio));	
//							System.out.println(medicao.valor());
//						} else {
//							medicao.setValor((medicao.getCasos() - 1) * 1.0f / (casosFim - casosInicio));
//						}
//						
//					}
//				
//				}
//			
//			}
		//procura os dados de maneira bem ineficiente btw
		for(Medicao medicao : observacoes) {
			if (medicao.getStatus().equals(StatusCaso.MORTOS)) {
				for(Medicao medicaoCasos : observacoes) {
					if(medicao.getPais().equals(medicaoCasos.getPais()) 
							&& medicaoCasos.getStatus().equals(StatusCaso.CONFIRMADOS)){
						if(medicao.getCasosInicial() != 0) {
							medicao.setValor(medicao.getCasos() - medicao.getCasosInicial()* 1.0f /
									(medicaoCasos.getCasos()  - medicaoCasos.getCasosInicial())); 
						}else {
							medicao.setValor(medicao.getCasos() - 1 * 1.0f /
									(medicaoCasos.getCasos()  - medicaoCasos.getCasosInicial())); 
						}
					}
				}
			}
		}
		
		//Organiza as medições para exportar mortalidade
		Collections.sort(this.observacoes, new comparatorCrescimento());
		Collections.reverse(this.observacoes);
		//teste taxa
		for (Medicao medicao : observacoes) {
			//Printa taxa maior que 0
			if (medicao.valor() > 0) {
				System.out.println(medicao.valor());
				System.out.println(medicao.getPais().getSlug());
			}
		}
		er.exportaMortalidade(csv, tsv, this.observacoes);
	}
	
	
	
	/**
	 * Compara distancia em km
	 * @param raio de pesquisa
	 */
	public void distanciaKm(float raio, boolean tsv, boolean csv) {
		
		//organiza lista por casos confirmados
		rankingCrescimento(StatusCaso.CONFIRMADOS,false,false);
		
		//Coordenadas do local com maior crescimento
		float lat = this.observacoes.get(0).getPais().getLatitude();
		float lon = this.observacoes.get(0).getPais().getLongitude();
		
		String pais = this.observacoes.get(0).getPais().getNome();
		String temp = null;
		System.out.println("\n\n" + pais + "\n\n");
		
		List<Pais> ranking = new ArrayList<Pais>();
		for (Medicao medicao : copiaObservacoes) {
			
			float lat2 = medicao.getPais().getLatitude();
			float lon2 = medicao.getPais().getLongitude();
			
			float distancia = haversine(lat,lon,lat2,lon2);
			
			String pais2 = medicao.getPais().getNome();
			
			if (distancia <= raio && !pais.equals(pais2) && !pais2.equals(temp)) {
				System.out.println(pais2);
				System.out.println(distancia);
				temp = pais2;
				ranking.add(medicao.getPais());
			}
		}
		
		ExportaRanking er = new ExportaRanking();
		er.exportaLocal(csv, tsv, ranking, this.observacoes.get(0).getPais());
				
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
	
	
	
	
	
	/**
	 * Inner class para comparar elementos do tipo Medicao
	 * @author Miguel
	 *
	 */
	public class medicaoComparator implements Comparator<Medicao> {

		@Override
		public int compare(Medicao o1, Medicao o2) {
		    Integer  v1 = o1.getCasos();
		    Integer v2 = o2.getCasos();
		    return v1.compareTo(v2);
		    
		}
	}
	
	
	/**
	 * Inner class para comparar elementos do tipo Medicao especifico para a taxa de crescimento e mortalidade
	 * @author Miguel
	 *
	 */
	public class comparatorCrescimento implements Comparator<Medicao> {
		@Override
		public int compare(Medicao o1, Medicao o2) {
		    float  v1 = o1.valor();
		    float v2 = o2.valor();
		    if (v1 < v2) return -1;
		    if (v1 > v2) return 1;
		    return 0;
		    
		}
	}
	
}
