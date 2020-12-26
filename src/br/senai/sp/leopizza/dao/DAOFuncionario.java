package br.senai.sp.leopizza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senai.sp.leopizza.modelo.Cargo;
import br.senai.sp.leopizza.modelo.Funcionario;
import br.senai.sp.leopizza.modelo.Produto;

public class DAOFuncionario implements InterfaceCRUD<Funcionario> {
	// objeto para a conexão
	private Connection conexao;

	public DAOFuncionario() {
		// obtém a conexão da Fábrica de Conexões
		conexao = ConnectionFactory.getConnection();
	}

	@Override
	public void inserir(Funcionario objeto) throws SQLException {
		// string com a instrução SQL
		String sql = "insert into funcionario(cpf, nome, cargo, senha) values(?,?,?,?)";
		// cria um comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// substitui as ? pelos valores do objeto a ser inserido
		comando.setString(1, objeto.getCpf());
		comando.setString(2, objeto.getNome());
		comando.setInt(3, objeto.getCargo().ordinal());
		comando.setString(4, objeto.getSenha());

		// executa o comando
		comando.execute();
		// encerra o comando
		comando.close();
	}

	@Override
	public void atualizar(Funcionario objeto) throws SQLException {
		// String sql com a instrução
		String sql = "update funcionario set nome = ?, cargo = ?, senha = ? where cpf = ?";
		// cria o comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// substitui as ? pelos valores do objeto
		comando.setString(1, objeto.getNome());
		comando.setInt(2, objeto.getCargo().ordinal());
		comando.setString(3, objeto.getSenha());
		comando.setString(4, objeto.getCpf());
		// executa o comando
		comando.execute();
		// fecha o comando
		comando.close();
	}

	public void excluir(Funcionario objeto) throws SQLException {
		// String sql com a instrução
		String sql = "delete from funcionario where cpf = ?";
		// cria o comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// subtstitui a ?
		comando.setString(1, objeto.getCpf());
		// executa o comando
		comando.execute();
		// fecha o comando
		comando.close();

	}

	@Override
	public List<Funcionario> listar() throws SQLException {
		// cria a lista de clientes para retornar
		List<Funcionario> lista = new ArrayList<>();
		// string com a instrução SQL
		String sql = "select * from funcionario order by nome asc";
		// cria o comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// cria o ResultSet para percorrer os registros do BD
		ResultSet rs = comando.executeQuery();
		// enquanto houver linhas
		while (rs.next()) {
			// cria um objeto funcionario
			Funcionario f = new Funcionario();
			// popula o objeto funcionario
			f.setNome(rs.getString("nome"));
			f.setCpf(rs.getString("cpf"));
			f.setCargo(Cargo.values()[rs.getInt("cargo")]);
			// adicionar o cliente à lista
			lista.add(f);
		}
		// fechar o ResultSet
		rs.close();
		// fechar o Statement
		comando.close();
		// retorna a lista de clientes
		return lista;
	}


}