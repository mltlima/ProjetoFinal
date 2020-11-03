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
	public void criarNovoPais(String nome) {
		Pais novoPais = new Pais();
		this.pais = novoPais;
		paises.put(nome, novoPais);
	}
	
	public void setNome(String nome) {
		this.pais.setNome(nome);
	}
	
	public void setCodigo(String codigo) {
		this.pais.setCodigo(codigo);
	}
	
	public void setSlug(String slug) {
		this.pais.setSlug(slug);
	}
	
	public void setLatitude(float latitude) {
		this.pais.setLatitude(latitude);
	}
	
	public void setLongitude(float longitude) {
		this.pais.setLongitude(longitude);
	}
	
	public HashMap<String,Pais> getHashMap() {
		return this.paises;
	}
	
	public void getPais(String nome) {
		
		Pais temp = this.paises.get(nome);
		this.pais = temp;
	}
	
	
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
