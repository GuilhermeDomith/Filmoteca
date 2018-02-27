package ti.lpv.filmoteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ti.lpv.bd.SGBD;
import ti.lpv.filmoteca.modelo.Diretor;
import ti.lpv.filmoteca.modelo.Genero;
import br.mos.es.FuncaoES;

public class DiretorDao {

	private SGBD sgbd;
	
	public DiretorDao() {
		sgbd = SGBD.criarConexao("filmoteca","aluno","aluno");
	}

	public void adicionar(Diretor diretor){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		try{
			preparedStatement = connection.prepareStatement("INSERT INTO diretor(nome) values (?);");
			preparedStatement.setString(1, diretor.getNome());
			preparedStatement.executeUpdate();
		}catch (SQLException e){
			FuncaoES.msgErro("Erro ao adicinar Diretor ao Banco de Dados.", "Erro");
		}
	}
	
	
	public void excluir(Diretor diretor){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		try{
			preparedStatement = connection.prepareStatement("DELETE FROM diretor WHERE codigoDiretor = ?;");
			preparedStatement.setLong(1, diretor.getCodigoDiretor());
			preparedStatement.executeUpdate();
		}catch (SQLException e){
			FuncaoES.msgErro("Erro ao excluir Diretor no Banco de Dados.", "Erro");
		}
	}
	
	public List<Diretor> listarDiretores() {
		List<Diretor> diretores = new ArrayList<Diretor>();
		
		try{
			sgbd.consulta("SELECT * FROM diretor");
		
			ResultSet resultSet = sgbd.getResultSet();  
			
			while(resultSet.next()){
				Diretor diretor = new Diretor();
				diretor.setCodigoDiretor(resultSet.getLong(2));
				diretor.setNome(resultSet.getString(1));
				
				diretores.add(diretor);
			}
			
		}catch(SQLException e){ 
			FuncaoES.msgErro("Erro ao listar Diretores no Banco de Dados.", "Erro");
		}
		
		return diretores	;
	}
	
	public List<Diretor> listarDiretoresFilme(Long codFilme) {
		List<Diretor> diretores = listarDiretores();
		List<Diretor> diretoresFilme = new ArrayList<Diretor>();
		
		try{
			sgbd.consulta("SELECT D.codigodiretor FROM diretorfilme D WHERE codigoFilme="+codFilme+";");
			ResultSet rs = sgbd.getResultSet();
			
			while(rs.next()){
				for(Diretor diretor : diretores){
					if(diretor.getCodigoDiretor() == rs.getLong(1))
						diretoresFilme.add(diretor);
				}
			}
			
		}catch(SQLException e){ 
			e.printStackTrace();
		}
		
		
		return diretoresFilme;
		
	}

}
