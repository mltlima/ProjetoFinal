import java.time.LocalDateTime;
import java.util.HashSet;


public class Medicao extends Estatistica{
	
	private Pais pais;
	private LocalDateTime momento;
	private int casos;
	private StatusCaso status;
	
	public Medicao() {
		
	}

	public Pais getPais() {
		return pais;
	}

	public LocalDateTime getMomento() {
		return momento;
	}

	public int getCasos() {
		return casos;
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

	public void setCasos(int casos) {
		this.casos = casos;
	}

	public void setStatus(StatusCaso status) {
		this.status = status;
	}
	
	
	
	
}
