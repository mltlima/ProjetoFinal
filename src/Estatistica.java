import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Estatistica {
	
	private String nome;
	private List<Medicao> observacoes  = new ArrayList<Medicao>();
	private float valor;
	private List<Medicao> copiaObservacoes;
	
	
	
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
	public LocalDateTime dataInicio() {
		LocalDateTime data = this.copiaObservacoes.get(0).getMomento();
		return data;
	}
	/**
	 * 
	 * @return data do fim da medicao
	 */
	public LocalDateTime dataFim() {
		LocalDateTime data = this.copiaObservacoes.get(this.copiaObservacoes.size() - 1).getMomento();
		return data;
	}
	/**
	 * Valor da taxa de mortalidade e crescimento adicionada ao ultimo medicao do pais
	 * @param valor taxa calculada
	 */
	public void setValor(float valor) {
		this.valor = valor;
	}
	/**
	 * 
	 * @return valor da taxa calculada
	 */
	public float valor() {
		return this.valor;
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
	public void rankingNumerico(StatusCaso status) {
		
		// organiza do menor ao maior
		Collections.sort(this.observacoes, new medicaoComparator());
		// inverte a ordem
		Collections.reverse(this.observacoes);
		for (Medicao medicao : observacoes) {
			//Printa taxa maior que 0
			if (medicao.getStatus().equals(status)) {
				System.out.println(medicao.getPais().getSlug());
				System.out.println(medicao.getCasos());
			}
		}
	}
	
	
	
	
	/**
	 * Organiza lista por taxa de crescimento
	 * pega o valor da data de inicio e fim 
	 * para calcular a taxa de crescimento
	 * @param status tipo de medicao confirmados, recuperados ou mortos
	 */
	public void rankingCrescimento(StatusCaso status) {
		
		float temp = 0;
		
		for (Medicao medicao : observacoes) {
			
			if (medicao.getStatus().equals(status)) {
				
				if (medicao.getMomento().equals(dataInicio())) {
					temp = medicao.getCasos();
					//System.out.println(temp);
				}
				if (medicao.getMomento().equals(dataFim())) {
					//Passa o valor da taxa para a ultima data
					if (temp != 0) {
						//Divide numero diferente de zero
						medicao.setValor((medicao.getCasos() - temp) * 1.0f / temp);
						temp = 0;
					} else {
						medicao.setValor((medicao.getCasos() - 1) * 1.0f);
					}
				
				}
			}
		}
		
		Collections.sort(this.observacoes, new comparatorCrescimento());
		Collections.reverse(this.observacoes);
		//teste taxa
		/*
		for (Medicao medicao : observacoes) {
			//Printa taxa maior que 0
			if (medicao.valor() > 0) {
				System.out.println(medicao.valor());
				System.out.println(medicao.getPais().getSlug());
			}
		}*/
	}
	
	
	
	
	
	/**
	 * Organiza paises pela maior taxa de mortalidade
	 */
	public void rankingMortalidade() {
		
		float temp = 0;
		int casosInicio = 0;
		int casosFim = 0;
		//DadosApi consulta = new DadosApi();
		
		for (Medicao medicao : observacoes) {
			
			
			if (medicao.getStatus().equals(StatusCaso.MORTOS) || 
					(medicao.getStatus().equals(StatusCaso.COMFIRMADOS))) {
				
				//String pais = medicao.getPais().getSlug();
			
				if (medicao.getMomento().equals(dataInicio())) {
					
					if (medicao.getStatus().equals(StatusCaso.COMFIRMADOS)) {
						casosInicio = medicao.getCasos();
					} else {
						temp = medicao.getCasos();						
					}
				}
				if (medicao.getMomento().equals(dataFim())) {
					
					if (medicao.getStatus().equals(StatusCaso.COMFIRMADOS)) {
						casosFim = medicao.getCasos();
					} else {
						if (temp != 0) {
							medicao.setValor((medicao.getCasos() - temp) * 1.0f / (casosFim - casosInicio));	
							System.out.println(medicao.valor());
						} else {
							medicao.setValor((medicao.getCasos() - 1) * 1.0f / (casosFim - casosInicio));
						}
						
					}
				
				}
			
			}
		}
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
	}
	
	
	
	
	public void distanciaKm(float raio) {
		
		//organiza lista por casos confirmados
		rankingCrescimento(StatusCaso.COMFIRMADOS);
		
		//Coordenadas do local com maior crescimento
		float lat = this.observacoes.get(0).getPais().getLatitude();
		float lon = this.observacoes.get(0).getPais().getLongitude();
		
		String pais = this.observacoes.get(0).getPais().getNome();
		String temp = null;
		System.out.println("\n\n" + pais + "\n\n");
		
		for (Medicao medicao : copiaObservacoes) {
			
			float lat2 = medicao.getPais().getLatitude();
			float lon2 = medicao.getPais().getLongitude();
			
			float distancia = haversine(lat,lon,lat2,lon2);
			
			String pais2 = medicao.getPais().getNome();
			
			if (distancia <= raio && !pais.equals(pais2) && !pais2.equals(temp)) {
				System.out.println(pais2);
				System.out.println(distancia);
				temp = pais2;
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
