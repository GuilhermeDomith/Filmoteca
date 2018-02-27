package ti.lpv.filmoteca.modelo;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Filme {

	private Long codigoFilme;
	private String titulo;
	private Integer duracao;
	private String ano;
	private Calendar dataLancamento;
	private String sinopse;
	private String classificacaoEtaria;
	private Integer classificacaoIMDB;
	private Integer classificacaoPessoal;
	private String midia;
	private File poster;
	
	private List<Artista> artistas;
	private List<Autor> autores;
	private List<Diretor> diretores;
	private List<Genero> generos;
	private List<Pais> paises;
	
	
	public Filme() {
		artistas = new ArrayList<Artista>();
		autores = new ArrayList<Autor>();
		diretores = new ArrayList<Diretor>();
		generos = new ArrayList<Genero>();
		paises = new ArrayList<Pais>();
		dataLancamento = Calendar.getInstance();
	}

	public Long getCodigoFilme() {
		return codigoFilme;
	}

	public void setCodigoFilme(Long codigoFilme) {
		this.codigoFilme = codigoFilme;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public Integer getClassificacaoIMDB() {
		return classificacaoIMDB;
	}

	public void setClassificacaoIMDB(Integer classificacaoIMDB) {
		this.classificacaoIMDB = classificacaoIMDB;
	}

	public Integer getClassificacaoPessoal() {
		return classificacaoPessoal;
	}

	public void setClassificacaoPessoal(Integer classificacaoPessoal) {
		this.classificacaoPessoal = classificacaoPessoal;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getClassificacaoEtaria() {
		return classificacaoEtaria;
	}

	public void setClassificacaoEtaria(String classificacaoEtaria) {
		this.classificacaoEtaria = classificacaoEtaria;
	}

	public String getMidia() {
		return midia;
	}

	public void setMidia(String midia) {
		this.midia = midia;
	}

	
	public File getPoster() {
		return poster;
	}

	public void setPoster(File poster) {
		this.poster = poster;
	}

	public List<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(List<Artista> artistas) {
		this.artistas = artistas;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public List<Diretor> getDiretores() {
		return diretores;
	}

	public void setDiretores(List<Diretor> diretores) {
		this.diretores = diretores;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

}
