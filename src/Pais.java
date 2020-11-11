/**
 * Classe Model 
 * @author Miguel
 *
 */
public class Pais {
	private String nome;
	private String codigo;
	private String slug;
	private float latitude;
	private float longitude;
	/**
	 * Gera uma instânica de pais
	 * @param nome modifica o atributo nome
	 * @param codigo modifica o atributo codigo
	 * @param slug modifica o atributo slug
	 * @param latitude modifica o atributo latiude
	 * @param longitude modifica o atributo longitude
	 */
	public Pais(String nome, String codigo, String slug, float latitude, float longitude) {
		this.nome = nome;
		this.codigo = codigo;
		this.slug = slug;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * Gera uma instânica de pais
	 * @param nome modifica o atributo nome
	 * @param codigo modifica o atributo codigo
	 * @param slug modifica o atributo slug
	 */
	public Pais(String nome, String codigo, String slug) {
		this.nome = nome;
		this.codigo = codigo;
		this.slug = slug;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}
	/**
	 * @param slug the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}
	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
}
