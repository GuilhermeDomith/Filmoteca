package ti.lpv.filmoteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ti.lpv.bd.SGBD;
import ti.lpv.filmoteca.modelo.Genero;
import ti.lpv.filmoteca.modelo.Pais;
import br.mos.es.FuncaoES;

public class PaisDao {

private SGBD sgbd;
	
	public PaisDao() {
		sgbd = SGBD.criarConexao("filmoteca","aluno","aluno");
	}

	public void adicionar(Pais pais){
		Connection connection = sgbd.getConnection();
		PreparedStatement preparedStatement;
		try{
			preparedStatement = connection.prepareStatement("INSERT INTO pais(nome) values (?);");
			preparedStatement.setString(1, pais.getNome());
			preparedStatement.executeUpdate();
		}catch (SQLException e){
			FuncaoES.msgErro("Erro ao adicinar País ao Banco de Dados.", "Erro");
		}
	}
	
	
	public List<Pais> listarPaises() {
		List<Pais> paises = new ArrayList<Pais>();
		
		try{
			sgbd.consulta("SELECT * FROM pais");
		
			ResultSet resultSet = sgbd.getResultSet();  
			
			while(resultSet.next()){
				Pais pais = new Pais();
				pais.setNome(resultSet.getString(1));
				pais.setCodigoPais(resultSet.getLong(2));
				
				paises.add(pais);
			}
			
		}catch(SQLException e){ 
			FuncaoES.msgErro("Erro ao listar Países no Banco de Dados.", "Erro");
		}
		
		return paises;
	}

	public List<Pais> listarPaisesFilme(Long codFilme) {
		List<Pais> paises = listarPaises();
		List<Pais> paisesFilme = new ArrayList<Pais>();
		try{
			sgbd.consulta("SELECT P.codigopais FROM paisfilme P WHERE codigoFilme="+codFilme+";");
			ResultSet rs = sgbd.getResultSet();
			
			while(rs.next()){
				for(Pais pais : paises){
					if(pais.getCodigoPais() == rs.getLong(1))
						paisesFilme.add(pais);
				}
			}
			
		}catch(SQLException e){ 
			e.printStackTrace();
		}
		
		
		return paisesFilme;
	}
}
