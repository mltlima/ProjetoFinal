import java.util.HashMap;
import java.util.HashSet;
/**
 * Classe Controller Paises
 * @author Miguel
 *
 */
public class ControllerPaises {
	
	private Pais pais;
	private HashMap<String,Pais> paises = new HashMap<>();
	
	public ControllerPaises() {
		
	}
	/*
	public ControllerPaises(Pais pais) {
		this.pais = pais;
	}
	*/
	
	/**
	 * Criando os objetos de pais baseados na DB, que será usado para os rankings
	 * @param nome nome do pais requerido
	 */
	public void criarNovoPais(String nome) {
		Pais novoPais = new Pais();
		this.pais = novoPais;
		paises.put(nome, novoPais);
	}
	/**
	 * Modifica o atributo nome
	 * @param nome
	 */
	
	public void setNome(String nome) {
		this.pais.setNome(nome);
	}
	
	/**
	 * Modifica o atributo Codigo do objeto pais
	 * @param nome
	 */
	public void setCodigo(String codigo) {
		this.pais.setCodigo(codigo);
	}
	
	/**
	 * Modifica o atributo Slug do objeto pais
	 * @param nome
	 */
	public void setSlug(String slug) {
		this.pais.setSlug(slug);
	}
	/**
	 * Modifica o atributo Latitude do objeto pais
	 * @param nome
	 */
	public void setLatitude(float latitude) {
		this.pais.setLatitude(latitude);
	}
	/**
	 * Modifica o atributo Longitude do objeto pais
	 * @param nome
	 */
	public void setLongitude(float longitude) {
		this.pais.setLongitude(longitude);
	}
	/**
	 * Acesso ao atributo pais
	 */
	
	public HashMap<String,Pais> getHashMap() {
		return this.paises;
	}
	/**
	 * Modifica o atributo nome
	 * @param nome
	 */
	public void getPais(String nome) {
		
		Pais temp = this.paises.get(nome);
		this.pais = temp;
	}
	
	/**
	 * Modifica o atributo nome
	 * @param nome
	 */
	// Testando HashSet
	public void printHashMap() {
		for (String key: paises.keySet()) {
			System.out.println(paises.get(key).getNome());
			System.out.println(paises.get(key).getCodigo());
			System.out.println(paises.get(key).getSlug());
			System.out.println(paises.get(key).getLatitude());
			System.out.println(paises.get(key).getLongitude());
			System.out.println("\n");
		}
	}
}
