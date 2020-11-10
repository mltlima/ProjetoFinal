import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;


public class Medicao extends Estatistica{
	
	private Pais pais;
	private LocalDateTime momento;
	private int casos;
	private int casosInicial;
	private StatusCaso status;
	
	public Medicao(LocalDate dataFim, LocalDate dataInicio) {
		super(dataFim, dataInicio);
	}
	public Medicao() {}

	public Pais getPais() {
		return pais;
	}

	public LocalDateTime getMomento() {
		return momento;
	}


	public StatusCaso getStatus() {
		return status;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public void setMomento(LocalDateTime momento) {
		this.momento = momento;
	}


	public void setStatus(StatusCaso status) {
		this.status = status;
	}
//	@Override
//	public float valor() {
//		if(this.casosInicial == 0) {
//			return (this.casos - 1) * 1.0f;
//		}else {
//			return (this.casos - this.casosInicial)/this.casos;
//		}
//	}
	/**
	 * @return the casos
	 */
	public int getCasos() {
		return casos;
	}
	/**
	 * @param casos the casos to set
	 */
	public void setCasos(int casos) {
		this.casos = casos;
	}
	
	/**
	 * @return the casosFinal
	 */
	public int getCasosInicial() {
		return casosInicial;
	}

	/**
	 * @param casosFinal the casosFinal to set
	 */
	public void setCasosIniciall(int casosInicial) {
		this.casosInicial = casosInicial;
	}
	
}
