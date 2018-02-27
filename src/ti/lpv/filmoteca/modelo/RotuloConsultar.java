package ti.lpv.filmoteca.modelo;

public enum RotuloConsultar {

	ARTISTA("Consultar Artista","Forne�a o nome do artista:", "Artista n�o encontrado!"),
	GENERO("Consultar G�nero","Forne�a a descri��o do g�nero:","G�nero n�o encontrado!"),
	AUTOR("Consultar Autor","Forne�a o nome do autor:", "Autor n�o encontrado!"),
	DIRETOR("Consultar Diretor","Forne�a o nome do diretor:", "Diretor n�o encontrado!"),
	FILME("Consultar Filme","Forne�a o nome do filme:","Filme n�o encontrado!");

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
