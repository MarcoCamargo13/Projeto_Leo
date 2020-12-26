package br.senai.sp.leopizza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.senai.sp.leopizza.modelo.Cargo;
import br.senai.sp.leopizza.modelo.Cliente;
import br.senai.sp.leopizza.modelo.Funcionario;

public class DAOLogin {
	// objeto para a conexão
	private Connection conexao;

	public DAOLogin() {
		// obtém a conexão da Fábrica de Conexões
		conexao = ConnectionFactory.getConnection();
	}

	public Funcionario buscaUsuario(String cpf, String senha) throws SQLException {
		Funcionario f = null;
		String sql = "select * from funcionario where cpf = ? and senha = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, cpf);
		comando.setString(2, senha);
		ResultSet rs = comando.executeQuery();
		if (rs.next()) {
			f = new Funcionario();
			f.setCpf(rs.getString("cpf"));
			f.setSenha(rs.getString("senha"));
			f.setNome(rs.getString("nome"));
			f.setCargo(Cargo.values()[rs.getInt("cargo")]);
		}
		rs.close();
		comando.close();
		return f;
	}
}
