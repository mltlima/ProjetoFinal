import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Estatistica {
	
	private String nome;
	private List<Medicao> observacoes  = new ArrayList<Medicao>();
	
	
	public void inclui(Medicao observacao) {
		this.observacoes.add(observacao);
	}
	
	public LocalDateTime dataInicio() {
		return this.observacoes.get(0).getMomento();
	}
	
	public LocalDateTime dataFim() {
		return this.observacoes.get(this.observacoes.size() - 1).getMomento();
	}
	/*
	public float valor() {
		
	}*/
	
	
	/**
	 * Organiza lista com medicoes do maior numero de casos ao menor
	 */
	public void rankingNumerico() {
		// organiza do menor ao maior
		Collections.sort(this.observacoes, new medicaoComparator());
		// inverte a ordem
		Collections.reverse(this.observacoes);
		
		for (Medicao medicao : observacoes) {
			System.out.println(medicao.getCasos());
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
	
}
