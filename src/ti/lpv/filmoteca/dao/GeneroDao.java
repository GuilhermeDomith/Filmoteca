package ti.lpv.filmoteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ti.lpv.bd.SGBD;
import ti.lpv.filmoteca.modelo.Artista;
import ti.lpv.filmoteca.modelo.Genero;
import br.mos.es.FuncaoES;

public class GeneroDao {
	

	private SGBD sgbd;
	
	public GeneroDao() {
		sgbd = SGBD.criarConexao("filmoteca","aluno","aluno");
	}

	public void adicionar(Genero genero){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		try{
			preparedStatement = connection.prepareStatement("INSERT INTO genero(descricao) values (?);");
			preparedStatement.setString(1, genero.getDescricao());
			preparedStatement.executeUpdate();
		}catch (SQLException e){
			FuncaoES.msgErro("Erro ao adicinar Genero ao Banco de Dados.", "Erro");
		}
	}
	
	
	public List<Genero> listarGeneros() {
		List<Genero> generos = new ArrayList<Genero>();
		
		try{
			sgbd.consulta("SELECT * FROM genero");
		
			ResultSet resultSet = sgbd.getResultSet();  
			
			while(resultSet.next()){
				Genero genero = new Genero();
				genero.setCodigoGenero(resultSet.getLong(2));
				genero.setDescricao(resultSet.getString(1));
				
				generos.add(genero);
			}
			
		}catch(SQLException e){ 
			FuncaoES.msgErro("Erro ao listar Generoes no Banco de Dados.", "Erro");
		}
		
		return generos	;
	}
	
	public List<Genero> listarGenerosFilme(Long codFilme) {
		List<Genero> generos = listarGeneros();
		List<Genero> generosFilme = new ArrayList<Genero>();
		try{
			sgbd.consulta("SELECT G.codigogenero FROM generofilme G WHERE codigoFilme="+codFilme+";");
			ResultSet rs = sgbd.getResultSet();
			
			while(rs.next()){
				for(Genero genero : generos){
					if(genero.getCodigoGenero() == rs.getLong(1))
						generosFilme.add(genero);
				}
			}
			
		}catch(SQLException e){ 
			e.printStackTrace();
		}
		
		
		return generosFilme;
	}


}
