
public class Animal {
	
	private int id_animal;
	private String nome;
	private String especie;
	private String descricao;
	private String sexo;
	private int idade;
	private String raca;
	private String condicao;
	private String image_name;	
	private Pessoa dono;
	
	/**
	 * M�todo set do atributo id_animal
	 * @param id - o que sera setado no atributo
	 */
	public void setIdAnimal(int id) {
		this.id_animal = id;
	}
	
	/**
	 * M�todo get do atributo id_animal
	 * @return - atributo id_animal do animal
	 */
	public int getIdAnimal() {
		return this.id_animal;
	}
	
	/**
	 * M�todo set do atributo nome
	 * @param nome - o que sera setado no atributo
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * M�todo get do atributo nome
	 * @return - atributo nome do animal
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * M�todo set do atributo especie
	 * @param especie - o que sera setado no atributo
	 */
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	
	/**
	 * M�todo get do atributo especie
	 * @return - atributo especie do animal
	 */
	public String getEspecie() {
		return this.especie;
	}
	
	/**
	 * M�todo set do atributo descricao
	 * @param descricao - o que sera setado no atributo
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * M�todo get do atributo descricao
	 * @return - atributo descricao do animal
	 */
	public String getDescricao() {
		return this.descricao;
	}
	
	/**
	 * M�todo set do atributo sexo
	 * @param sexo - o que sera setado no atributo
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	/**
	 * M�todo get do atributo sexo
	 * @return - atributo sexo do animal
	 */
	public String getSexo() {
		return this.sexo;
	}
	
	/**
	 * M�todo set do atributo idade
	 * @param idade - o que sera setado no atributo
	 */
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	/**
	 * M�todo get do atributo idade
	 * @return - atributo idade do animal
	 */
	public int getIdade() {
		return this.idade;
	}
	
	/**
	 * M�todo set do atributo raca
	 * @param raca - o que sera setado no atributo
	 */
	public void setRaca(String raca) {
		this.raca = raca;
	}
	
	/**
	 * M�todo get do atributo raca
	 * @return - atributo raca do animal
	 */
	public String getRaca() {
		return this.raca;
	}
	
	/**
	 * M�todo set do atributo condicao
	 * @param condicao - o que sera setado no atributo
	 */
	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}
	
	/**
	 * M�todo get do atributo condicao
	 * @return - atributo condicao do animal
	 */
	public String getCondicao() {
		return this.condicao;
	}
	
	/**
	 * M�todo set do atributo image_name
	 * @param image_url - o que sera setado no atributo
	 */
	public void setImageName(String image_name) {
		this.image_name = image_name;
	}
	
	/**
	 * M�todo get do atributo image_name
	 * @return - atributo image_name do animal
	 */
	public String getImageName() {
		return this.image_name;
	}
	
	/**
	 * M�todo set do atributo dono
	 * @param dono - o que sera setado no atributo
	 */
	public void setDono(Pessoa dono) {
		this.dono = dono;
	}
	
	/**
	 * M�todo get do atributo dono
	 * @return - atributo dono do animal
	 */
	public Pessoa getDono() {
		return this.dono;
	}
	
}
