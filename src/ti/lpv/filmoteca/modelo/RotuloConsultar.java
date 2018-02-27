package ti.lpv.filmoteca.modelo;

public enum RotuloConsultar {

	ARTISTA("Consultar Artista","Forneça o nome do artista:", "Artista não encontrado!"),
	GENERO("Consultar Gênero","Forneça a descrição do gênero:","Gênero não encontrado!"),
	AUTOR("Consultar Autor","Forneça o nome do autor:", "Autor não encontrado!"),
	DIRETOR("Consultar Diretor","Forneça o nome do diretor:", "Diretor não encontrado!"),
	FILME("Consultar Filme","Forneça o nome do filme:","Filme não encontrado!");

	String tituloJanela, mensagem, msgNaoEncontrado;

	private RotuloConsultar(String tituloJanela, String fraseDeEntrada,String msgNaoEncontrado) {
		this.tituloJanela = tituloJanela;
		this.mensagem = fraseDeEntrada;
		this.msgNaoEncontrado = msgNaoEncontrado;
	}

	public String getTituloJanela() {
		return tituloJanela;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getMsgNaoEncontrado() {
		return msgNaoEncontrado;
	}
	
}
