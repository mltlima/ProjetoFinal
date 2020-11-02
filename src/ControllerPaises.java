import java.util.HashSet;

public class ControllerPaises {
	
	private Pais pais;
	private HashSet<Pais> paises = new HashSet<>();
	
	public ControllerPaises() {
		
	}
	
	public void criarNovoPais() {
		Pais pais = new Pais();
		this.pais = pais;
		paises.add(pais);
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
	
	public HashSet<Pais> getHashSet() {
		return this.paises;
	}
	// Testando HashSet
	public void printHashSet() {
		for (Pais pais : paises) {
			System.out.println(pais.getNome());
			System.out.println(pais.getCodigo());
			System.out.println(pais.getSlug());
			System.out.println("\n");
		}
	}
}
