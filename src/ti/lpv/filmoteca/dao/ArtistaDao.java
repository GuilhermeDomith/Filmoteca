package ti.lpv.filmoteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.mos.es.FuncaoES;
import ti.lpv.bd.SGBD;
import ti.lpv.filmoteca.modelo.Artista;
import ti.lpv.filmoteca.modelo.Genero;

public class ArtistaDao {
	
	private SGBD sgbd;
	
	public ArtistaDao() {
		sgbd = SGBD.criarConexao("filmoteca","aluno","aluno");
	}

	public void adicionar(Artista artista){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		try{
			preparedStatement = connection.prepareStatement("INSERT INTO artista(nome) values (?);");
			preparedStatement.setString(1, artista.getNome());
			preparedStatement.executeUpdate();
		}catch (SQLException e){
			FuncaoES.msgErro("Erro ao adicinar Artista ao Banco de Dados.", "Erro");
		}
	}
	
	public void alterar(Artista artista){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		try{
			preparedStatement = connection.prepareStatement("UPDATE artista SET nome = ? WHERE codigoArtista = ?;");
			preparedStatement.setString(1, artista.getNome());
			preparedStatement.setLong(2, artista.getCodigoArtista());
			preparedStatement.executeUpdate();
		}catch (SQLException e){
			FuncaoES.msgErro("Erro ao alterar Artista no Banco de Dados.", "Erro");
		}
	}
	
	public void excluir(Artista artista){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		try{
			preparedStatement = connection.prepareStatement("DELETE FROM artista WHERE codigoArtista = ?;");
			preparedStatement.setLong(1, artista.getCodigoArtista());
			preparedStatement.executeUpdate();
		}catch (SQLException e){
			FuncaoES.msgErro("Erro ao alterar Artista no Banco de Dados.", "Erro");
		}
	}
	
	public List<Artista> listarArtistas() {
		
		List<Artista> artistas = new ArrayList<Artista>();
		
		try{
			sgbd.consulta("SELECT * FROM artista");
		
			ResultSet resultSet = sgbd.getResultSet();  
			
			while(resultSet.next()){
				Artista artista = new Artista();
				artista.setCodigoArtista(resultSet.getLong(1));
				artista.setNome(resultSet.getString(2));
				
				artistas.add(artista);
			}
			
		}catch(SQLException e){ 
			e.printStackTrace();
		}
		
		return artistas;
	}
	
	/**
	 * Obtem do banco de dados os artistas que estão associados ao filme correspondente ao
	 * codigo do filme passado por parametro.
	 */
	public List<Artista> listarArtistasFilme(Long codFilme) {
		
		List<Artista> artistas = listarArtistas();
		List<Artista> artistasFilme = new ArrayList<Artista>();
		try{
			sgbd.consulta("SELECT elenco.codigoartista FROM elenco WHERE codigoFilme="+codFilme+";");
			ResultSet rs = sgbd.getResultSet();
			
			while(rs.next()){
				for(Artista artista : artistas){
					if(artista.getCodigoArtista() == rs.getLong(1))
						artistasFilme.add(artista);
				}
			}
			
		}catch(SQLException e){ 
			e.printStackTrace();
		}
		
		
		return artistasFilme;
	}

}
