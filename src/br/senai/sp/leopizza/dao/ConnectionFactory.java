package br.senai.sp.leopizza.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static Connection conexao;
	private static String usuario = "root";
	private static String senha = "1234";
	//private static String strConexao = "jdbc:mysql://10.92.198.11/leopizza?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	private static String strConexao = "jdbc:mysql://localhost/leopizza?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";// como é porta ádrão

	public static Connection getConnection() {
		// verifica se a conexão é nula
		if (conexao == null) {
			// tenta abrir a conexão
			try {
				conexao = DriverManager.getConnection(strConexao, usuario, senha);
			} catch (SQLException e) {
				// exibe informações do erro
				e.printStackTrace();
				System.out.println("Erro na conexão: " + e.getMessage());
			}
		}
		// retorna o objeto conexão
		return conexao;
	}

	public static void CloseConnection() {
		// verifica se a conexão existe
		if (conexao != null) {
			try {
				// tenta fechar a conexão
				conexao.close();
			} catch (SQLException e) {
				// exibe informações do erro
				e.printStackTrace();
				System.out.println("Erro ao tentar fechar a conexão: " + e.getMessage());
			}
		}
	}

}
