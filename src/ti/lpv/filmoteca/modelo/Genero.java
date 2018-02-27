package ti.lpv.filmoteca.modelo;

public class Genero {

	private Long codigoGenero;
	private String descricao;
	
	public Genero() { }
	
	public Genero(String descricao) {
		this.descricao = descricao;
	}

	public Long getCodigoGenero() {
		return codigoGenero;
	}

	public void setCodigoGenero(Long codigoGenero) {
		this.codigoGenero = codigoGenero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
