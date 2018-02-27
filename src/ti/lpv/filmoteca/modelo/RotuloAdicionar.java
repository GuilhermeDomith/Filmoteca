package ti.lpv.filmoteca.modelo;

public enum RotuloAdicionar {

	ARTISTA("Adicionar Artistas","Artistas","Novo Artista","Insira o nome do novo atista:"),
	GENERO("Adicionar G\u00EAneros","G�neros","Novo G�nero:", "Insira a descri��o do novo g�nero:"),
	AUTOR("Adicionar Autores","Autores","Novo Autor", "Insira o nome do novo Autor"),
	DIRETOR("Adicionar Diretores","Diretores","Novo Diretor", "Insira o nome do novo Diretor"),
	PAIS("Adicionar Pa�ses","Pa�ses", "Novo Pa�s", "Insira o nome do novo Pa�s:" );

	String tituloJanela, labelLista, tituloJanela2, mensagem;

	private RotuloAdicionar(String tituloJanela, String labelLista,
			String tituloJanela2, String mensagem) {
		this.tituloJanela = tituloJanela;
		this.labelLista = labelLista;
		this.tituloJanela2 = tituloJanela2;
		this.mensagem = mensagem;
	}

	public String getTituloJanela() {
		return tituloJanela;
	}

	public void setTituloJanela(String tituloJanela) {
		this.tituloJanela = tituloJanela;
	}

	public String getLabelLista() {
		return labelLista;
	}

	public void setLabelLista(String labelLista) {
		this.labelLista = labelLista;
	}

	public String getTituloJanela2() {
		return tituloJanela2;
	}

	public void setTituloJanela2(String tituloJanela2) {
		this.tituloJanela2 = tituloJanela2;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
