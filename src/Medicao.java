import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * 
 * @author Miguel
 *
 */
public class Medicao extends Estatistica{
	
	private Pais pais;
	private LocalDateTime momento;
	private int casos;
	private StatusCaso status;
	
	/**
	 * Acesso ao atributo pais
	 * @return atributo pais
	 */
	public Pais getPais() {
		return pais;
	}

	/**
	 * acesso ao atributo momento
	 * @return atributo momento
	 */
	public LocalDateTime getMomento() {
		return momento;
	}

	/**
	 * acesso ao atributo casos
	 * @return atributo casos
	 */
	public int getCasos() {
		return casos;
	}

	/**
	 * acessoao atributo status
	 * @return atributo status
	 */
	public StatusCaso getStatus() {
		return status;
	}

	/**
	 * modifica atributo pais
	 * @param pais valor a ser atribuído
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	/**
	 * modifica atributo momento
	 * @param momento valor a ser atribuído
	 */
	public void setMomento(LocalDateTime momento) {
		this.momento = momento;
	}

	/**
	 * modifica atributo casos
	 * @param casos valor a ser atribuído
	 */
	public void setCasos(int casos) {
		this.casos = casos;
	}

	/**
	 * modifica atributo status
	 * @param status valor a ser atrubuído
	 */
	public void setStatus(StatusCaso status) {
		this.status = status;
	}
	
}
