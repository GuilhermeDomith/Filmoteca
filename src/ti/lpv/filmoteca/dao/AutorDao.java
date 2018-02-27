package ti.lpv.filmoteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ti.lpv.bd.SGBD;
import ti.lpv.filmoteca.modelo.Autor;
import ti.lpv.filmoteca.modelo.Genero;
import br.mos.es.FuncaoES;

public class AutorDao {
	
	private SGBD sgbd;
	
	public AutorDao() {
		sgbd = SGBD.criarConexao("filmoteca","aluno","aluno");
	}

	public void adicionar(Autor autor){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		try{
			preparedStatement = connection.prepareStatement("INSERT INTO autor(nome) values (?);");
			preparedStatement.setString(1, autor.getNome());
			preparedStatement.executeUpdate();
		}catch (SQLException e){
			FuncaoES.msgErro("Erro ao adicinar Autor ao Banco de Dados.", "Erro");
		}
	}
	
	
	public List<Autor> listarAutores() {
		List<Autor> autores = new ArrayList<Autor>();
		
		try{
			sgbd.consulta("SELECT * FROM autor");
		
			ResultSet resultSet = sgbd.getResultSet();  
			
			while(resultSet.next()){
				Autor autor = new Autor();
				autor.setNome(resultSet.getString(1));
				autor.setCodigoAutor(resultSet.getLong(2));
				
				autores.add(autor);
			}
			
		}catch(SQLException e){ 
			FuncaoES.msgErro("Erro ao lista Autores no Banco de Dados.", "Erro");
		}
		
		return autores	;
	}

	public List<Autor> listarAutoresFilme(Long codFilme) {
		List<Autor> autores = listarAutores();
		List<Autor> autoresFilme = new ArrayList<Autor>();
		try{
			sgbd.consulta("SELECT A.codigoautor FROM autorfilme A WHERE codigoFilme="+codFilme+";");
			ResultSet rs = sgbd.getResultSet();
			
			while(rs.next()){
				for(Autor autor : autores){
					if(autor.getCodigoAutor() == rs.getLong(1))
						autoresFilme.add(autor);
				}
			}
			
		}catch(SQLException e){ 
			e.printStackTrace();
		}
		
		
		return autoresFilme;
	}

}
