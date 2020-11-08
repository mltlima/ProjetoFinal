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
			
			
				System.out.println(medicao.getPais().getLatitude() + "  " + medicao.getPais().getLongitude());
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
		for (Medicao medicao : observacoes) {
			//Printa taxa maior que 0
			if (medicao.valor() > 0) {
				System.out.println(medicao.valor());
				System.out.println(medicao.getPais().getSlug());
			}
		}
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

				String pais = medicao.getPais().getSlug();
			
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
