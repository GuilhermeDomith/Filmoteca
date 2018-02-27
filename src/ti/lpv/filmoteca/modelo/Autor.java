package ti.lpv.filmoteca.modelo;

public class Autor {
	
	private Long codigoAutor;
	private String nome;
	
	public Autor() {
		super();
	}

	public Long getCodigoAutor() {
		return codigoAutor;
	}

	public void setCodigoAutor(Long codigoAutor) {
		this.codigoAutor = codigoAutor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
