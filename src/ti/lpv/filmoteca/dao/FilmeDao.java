package ti.lpv.filmoteca.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ti.lpv.bd.SGBD;
import ti.lpv.filmoteca.modelo.Artista;
import ti.lpv.filmoteca.modelo.Autor;
import ti.lpv.filmoteca.modelo.Diretor;
import ti.lpv.filmoteca.modelo.Filme;
import ti.lpv.filmoteca.modelo.Genero;
import ti.lpv.filmoteca.modelo.Pais;
import br.mos.es.FuncaoES;

public class FilmeDao {

	private SGBD sgbd;
	
	public FilmeDao() {
		sgbd = SGBD.criarConexao("filmoteca","aluno","aluno");
	}
	
	/**
	 * Adiciona o filme passado por parâmetro no banco de dados.
	 */
	public boolean adicionarFilme(Filme filme) {
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		
		try{
			preparedStatement = connection.prepareStatement("INSERT INTO filme(titulo,duracao,ano,datalancamento,"
					+ "sinopse,classificacaoetaria,classificacaoimdb,classificacaopessoal,midia,poster) values (?,?,?,?,?,?,?,?,?,?)");
			
			preparedStatement.setString(1, filme.getTitulo());
			preparedStatement.setInt(2, filme.getDuracao());
			preparedStatement.setString(3, filme.getAno());
			preparedStatement.setDate(4, new java.sql.Date(filme.getDataLancamento().getTimeInMillis()));
			preparedStatement.setString(5, filme.getSinopse());
			preparedStatement.setString(6, filme.getClassificacaoEtaria());
			preparedStatement.setInt(7, filme.getClassificacaoIMDB());
			preparedStatement.setInt(8, filme.getClassificacaoPessoal());
			preparedStatement.setString(9, filme.getMidia());
			preparedStatement.setBytes(10, converterParaByte( filme.getPoster()) );
			preparedStatement.executeUpdate();
			
			//Obtem o código do último filme adicionado. 
			sgbd.consulta("SELECT max(codigofilme) FROM filme;");
			ResultSet rs = sgbd.getResultSet();
			Long codFilme = null;
			while(rs.next())
				codFilme = rs.getLong(1);
			
			adicionarArtistaFilme(filme.getArtistas(), codFilme);
			adicionarAutorFilme(filme.getAutores(), codFilme);
			adicionarDiretorFilme(filme.getDiretores(), codFilme);
			adicionarGeneroFilme(filme.getGeneros(), codFilme);
			adicionarPaisFilme(filme.getPaises(), codFilme);
			
		}catch(SQLException e){
			FuncaoES.msgErro("Erro ao adicionar Filme", "ERRO");
			e.printStackTrace();
		}
		
		
		return true;
	}
	
	/**
	 * Adiciona os artistas ao filme, correspondente ao código passado por parâmetro.
	 * Insere o código de ambos na tabela do banco de dados 'elenco'.
	 */
	private void adicionarArtistaFilme(List<Artista> artistas, Long codFilme){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		
		for(Artista artista : artistas){
			try{
				preparedStatement = connection.prepareStatement("INSERT INTO elenco(codigofilme,codigoartista) values (?,?)");
				preparedStatement.setLong(1, codFilme);
				preparedStatement.setLong(2, artista.getCodigoArtista());
				preparedStatement.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Adiciona os autores ao filme, correspondente ao código passado por parâmetro.
	 * Insere o código de ambos na tabela do banco de dados 'autorFilme'.
	 */
	private void adicionarAutorFilme(List<Autor> autores , Long codFilme){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		
		for(Autor autor : autores){
			try{
				preparedStatement = connection.prepareStatement("INSERT INTO autorFilme(codigofilme,codigoautor) values (?,?)");
				preparedStatement.setLong(1, codFilme);
				preparedStatement.setLong(2, autor.getCodigoAutor());
				preparedStatement.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Adiciona os diretores ao filme, correspondente ao código passado por parâmetro.
	 * Insere o código de ambos na tabela do banco de dados 'diretorFilme'.
	 */
	private void adicionarDiretorFilme(List<Diretor> diretores , Long codFilme){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		
		for(Diretor diretor : diretores){
			try{
				preparedStatement = connection.prepareStatement("INSERT INTO diretorFilme(codigofilme,codigodiretor) values (?,?)");
				preparedStatement.setLong(1, codFilme);
				preparedStatement.setLong(2, diretor.getCodigoDiretor());
				preparedStatement.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Adiciona os generos ao filme, correspondente ao código passado por parâmetro.
	 * Insere o código de ambos na tabela do banco de dados 'generoFilme'.
	 */
	private void adicionarGeneroFilme(List<Genero> generos, Long codFilme){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		
		for(Genero genero : generos){
			try{
				preparedStatement = connection.prepareStatement("INSERT INTO generoFilme(codigofilme,codigogenero) values (?,?)");
				preparedStatement.setLong(1, codFilme);
				preparedStatement.setLong(2, genero.getCodigoGenero());
				preparedStatement.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

	}
	

	/**
	 * Adiciona os países ao filme, correspondente ao código passado por parâmetro.
	 * Insere o código de ambos na tabela do banco de dados 'paisFilme'.
	 */
	private void adicionarPaisFilme(List<Pais> paises, Long codFilme){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		
		for(Pais pais : paises){
			try{
				preparedStatement = connection.prepareStatement("INSERT INTO paisFilme(codigofilme,codigopais) values (?,?)");
				preparedStatement.setLong(1, codFilme);
				preparedStatement.setLong(2, pais.getCodigoPais());
				preparedStatement.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

	}
	

	/**
	 * Lista todos filmes do Banco de dados.
	 */
	public List<Filme> listarFilmes() {
		
		List<Filme> filmes = new ArrayList<Filme>();
		
		try{
			sgbd.consulta("SELECT * FROM filme");
		
			filmes = montarObjetoFilme( sgbd.getResultSet() );
			
		}catch(SQLException e){	e.printStackTrace(); }
		
		return filmes;
	}
	

	/**
	 * Lista os filmes que possuem o artista correspondente ao código 
	 * passado por parâmetro.
	 */
	public List<Filme> listarFilmesComArtista(Long codArtista) {
		List<Filme> filmes = new FilmeDao().listarFilmes(),
				filmesComArtista = new ArrayList<Filme>();
		
		for(Filme filme : filmes)
			for(Artista artista : filme.getArtistas())
				if(artista.getCodigoArtista() == codArtista)
					filmesComArtista.add(filme);
		
		
		return filmesComArtista;
	}
	

	/**
	 * Lista os filmes que possuem o autor correspondente ao código 
	 * passado por parâmetro.
	 */
	public List<Filme> listarFilmesComAutor(Long codAutor) {
		List<Filme> filmes = new FilmeDao().listarFilmes(),
				filmesComAutor = new ArrayList<Filme>();
		
		for(Filme filme : filmes)
			for(Autor autor : filme.getAutores())
				if(autor.getCodigoAutor() == codAutor)
					filmesComAutor.add(filme);
		
		
		return filmesComAutor;
	}


	/**
	 * Lista os filmes que possuem o diretor correspondente ao código 
	 * passado por parâmetro.
	 */
	public List<Filme> listarFilmesComDiretor(Long codDiretor) {
		List<Filme> filmes = new FilmeDao().listarFilmes(),
				filmesComDiretor = new ArrayList<Filme>();
		
		for(Filme filme : filmes)
			for(Diretor diretor : filme.getDiretores())
				if(diretor.getCodigoDiretor() == codDiretor)
					filmesComDiretor.add(filme);
		
		
		return filmesComDiretor;
	}
	
	/**
	 * Lista os filmes que possuem o gênero correspondente ao código 
	 * passado por parâmetro.
	 */
	public List<Filme> listarFilmesComGenero(Long codGenero) {
		List<Filme> filmes = new FilmeDao().listarFilmes(),
				filmesComGenero = new ArrayList<Filme>();
		
		for(Filme filme : filmes)
			for(Genero genero : filme.getGeneros())
				if(genero.getCodigoGenero() == codGenero)
					filmesComGenero.add(filme);
		
		
		return filmesComGenero;
	}
	
	/**
	 * Lista os filmes do BD, ordenados de acordo com o nome da coluna passado 
	 * por parâmentro.
	 */
	public List<Filme> listarFilmesPor(String coluna , String ordem) {
		List<Filme> filmes = new ArrayList<Filme>();
		try{
			sgbd.consulta("SELECT * FROM filme ORDER BY "+coluna+" "+ ordem + ";");
		
			filmes  = montarObjetoFilme(sgbd.getResultSet());
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return filmes;
	}
	
	
	/**
	 * Obtem os dados do ResultSet passado por parâmetro e os adiciona
	 * ao objeto filme. Adiciona cada objeto filme ao Array de filmes e 
	 * o retorna. 
	 */
	public List<Filme> montarObjetoFilme(ResultSet resultSet){
		List<Filme> filmes = new ArrayList<Filme>();
		
		try{
			while(resultSet.next()){
				Filme filme = new Filme();
				
				Long codigoFilme = resultSet.getLong(1);
				filme.setCodigoFilme(codigoFilme);
				filme.setTitulo(resultSet.getString(2));
				filme.setDuracao(resultSet.getInt(3));
				filme.setAno(resultSet.getString(4));
				
				Date data = resultSet.getDate(5);
				Calendar dataCalendar = Calendar.getInstance();
				dataCalendar.setTime(data);
				filme.setDataLancamento(dataCalendar);
				filme.setSinopse(resultSet.getString(6));
				filme.setClassificacaoEtaria(resultSet.getString(7));
				filme.setClassificacaoPessoal(resultSet.getInt(8));
				filme.setMidia(resultSet.getString(9));
				filme.setPoster( converterParaFile(resultSet.getBytes(10),"imagem_"+codigoFilme) );
				filme.setClassificacaoIMDB(resultSet.getInt(11));
	
				filmes.add(filme);
			}
			
		}catch(SQLException e){ 
			e.printStackTrace();
		}
	
		//Obtem Artistas, Autores, Diretores, Generos e Paises de cada filme.
		for(Filme filme : filmes){
			Long codigoFilme = filme.getCodigoFilme();
			filme.setArtistas(new ArtistaDao().listarArtistasFilme(codigoFilme));
			filme.setAutores(new AutorDao().listarAutoresFilme(codigoFilme));
			filme.setDiretores(new DiretorDao().listarDiretoresFilme(codigoFilme));
			filme.setGeneros(new GeneroDao().listarGenerosFilme(codigoFilme));
			filme.setPaises(new PaisDao().listarPaisesFilme(codigoFilme));
		}
		
		return filmes;
	}
	
	/**
	 * Converte um arquivo File para um Array de bytes.
	 */
	public byte[] converterParaByte(File file) {
		
		byte[] bytes = new byte[(int)file.length() ];
		try {
			InputStream fileInput = new FileInputStream(file);
	        int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length && (numRead=fileInput.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }
        
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return bytes;
	}
	
	/**
	 * Converte um Array de bytes para File e o salva no diretório da aplicação.
	 */
	public File converterParaFile(byte[] bytes, String nomeArquivo) {
		 File file = null;
		 try {
			 //Salva a imagem no diretório imagens da aplicação.
			 file =  new File("imagens" + File.separator + nomeArquivo);
			 FileOutputStream fileOutput = new FileOutputStream(file);
			 //converte o array de bytes em file
			 fileOutput.write( bytes );
			 fileOutput.close();
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return file;
	}


}
