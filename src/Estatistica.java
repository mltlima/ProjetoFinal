import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
		return this.observacoes.get(this.observacoes.size()).getMomento();
		
	}
	/*
	public float valor() {
		
	}*/
	
}
