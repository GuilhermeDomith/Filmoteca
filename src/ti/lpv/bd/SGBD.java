package ti.lpv.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SGBD {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData resultSetMetaData;
	private String nomeBD;
	private String usuarioBD;
	private String senhaBD;
	private final String DRIVER_BD = "jdbc:postgresql:";
	private static SGBD sgbd;
	
	private SGBD(String nomeBD, String usuarioBD, String senhaBD) throws SQLException {
		this.nomeBD = nomeBD;
		this.usuarioBD = usuarioBD;
		this.senhaBD = senhaBD;
		
		connection = DriverManager.getConnection(DRIVER_BD + nomeBD, usuarioBD, senhaBD);
		statement = connection.createStatement();
	}
	
	/** Obtém uma conexão única com o banco de dados. Se não for possível estabelecer uma conexão
	 * com o banco de dados retorna null.
	 */
	public static SGBD criarConexao(String nomeBD, String usuarioBD, String senhaBD){
		
		if(sgbd != null) return sgbd;
		
		try{
			sgbd = new SGBD(nomeBD, usuarioBD, senhaBD);
		}catch(SQLException e){ return null;}
		
		return sgbd;
	}
	
	/** Fecha a conexão com o banco de dados. */
	public void fecharConexao() throws SQLException{
		if(statement != null) statement.close();
		if(connection != null) connection.close();
	}
	
	public void consulta(String instrucaoSql) throws SQLException{
		try{
			resultSet = statement.executeQuery(instrucaoSql);
			resultSetMetaData = resultSet.getMetaData();
		}catch (SQLException e){
			throw new SQLException("Erro ao submeter uma consulta SQL SELECT ao banco de dados.");
		}
	}
	
	public void atualiza(String instrucaoSql) throws SQLException{
		try{
			statement.executeUpdate(instrucaoSql);
		}catch (SQLException e){
			throw new SQLException("Erro ao submeter uma consulta SQL INSERT, UPDATE ou DELETE ao banco de dados.");
		}
		
	}

	public String getNomeBD() {
		return nomeBD;
	}

	public void setNomeBD(String nomeBD) {
		this.nomeBD = nomeBD;
	}

	public String getUsuarioBD() {
		return usuarioBD;
	}

	public void setUsuarioBD(String usuarioBD) {
		this.usuarioBD = usuarioBD;
	}

	public String getSenhaBD() {
		return senhaBD;
	}

	public void setSenhaBD(String senhaBD) {
		this.senhaBD = senhaBD;
	}

	public Connection getConnection() {
		return connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}

	public String getDRIVER_BD() {
		return DRIVER_BD;
	}

	public static SGBD getSgbd() {
		return sgbd;
	}
	
}//class SGBD
