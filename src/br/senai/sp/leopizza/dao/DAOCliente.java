package br.senai.sp.leopizza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senai.sp.leopizza.modelo.Cliente;

public class DAOCliente implements InterfaceCRUD<Cliente> {

	// Objeto para a conexão
	private Connection conexao;

	public DAOCliente() {
		// Obtém a conexão da fábrica de conexões
		conexao = ConnectionFactory.getConnection();
	}

	@Override
	public void inserir(Cliente objeto) throws SQLException {
		// String com a instrução SQL
		String sql = "insert into cliente(nome, endereco, telefone, email, cep) values(?, ?, ?, ?, ?)";
		// Criando um comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getEndereco());
		comando.setString(3, objeto.getTelefone());
		comando.setString(4, objeto.getEmail());
		comando.setString(5, objeto.getCep());
		// executa o comando
		comando.execute();
		// encerra o comando
		comando.close();
	}

	@Override
	public void atualizar(Cliente objeto) throws SQLException {
		// String sql com a instrução
		String sql = "update cliente set nome = ?, email = ?, endereco = ?, cep = ? where id = ?";
		// Cria o comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// substitui as interrogações pelos valores do objeto
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getEmail());
		comando.setString(3, objeto.getEndereco());
		comando.setString(4, objeto.getCep());
		comando.setInt(5, objeto.getId());
		// executa o comando
		comando.execute();
		// fecha o comando
		comando.close();
	}

	@Override
	public void excluir(Cliente objeto) throws SQLException {
		// String sql com a instrução
		String sql = "delete from cliente where id = ?";
		// cria o comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// Substitui a interrgoação
		comando.setInt(1, objeto.getId());
		// executa o comando
		comando.execute();
		// fecha o comando
		comando.close();
	}

	@Override
	public List<Cliente> listar() throws SQLException {
		// Cria a lista de clientes para retornar
		List<Cliente> lista = new ArrayList<>();
		// String com a instrução SQL - listar clientes em ordem alfabética
		String sql = "select * from cliente order by nome asc";
		// cria um comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// Cria o result set para percorrer os registros do banco
		ResultSet rs = comando.executeQuery();
		// Enquanto houver linhas
		while (rs.next()) {
			// Cria um objeto cliente
			Cliente c = new Cliente();
			// popula o objeto cliente
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setEndereco(rs.getString("endereco"));
			c.setEmail(rs.getString("email"));
			c.setCep(rs.getString("cep"));
			c.setTelefone(rs.getString("telefone"));
			c.setPontos(rs.getInt("pontos"));
			// adiciona cliente à lista
			lista.add(c);
		}
		// fechar o ResultSet
		rs.close();
		// fechar o statement
		comando.close();
		// retorna a lista de clientes
		return lista;
	}

	public List<Cliente> listar(String parametro) throws SQLException {
		// Cria a lista de clientes para retornar
		List<Cliente> lista = new ArrayList<>();
		// String com a instrução SQL - listar clientes em ordem alfabética
		String sql = "select * from cliente where nome like ? or telefone like ? or cep like ? order by nome asc";
		// cria um comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// substituindo as interrogações
		comando.setString(1, "%" + parametro + "%");
		comando.setString(2, "%" + parametro + "%");
		comando.setString(3, "%" + parametro + "%");
		// Cria o result set para percorrer os registros do banco
		ResultSet rs = comando.executeQuery();
		// Enquanto houver linhas
		while (rs.next()) {
			// Cria um objeto cliente
			Cliente c = new Cliente();
			// popula o objeto cliente
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setEndereco(rs.getString("endereco"));
			c.setEmail(rs.getString("email"));
			c.setCep(rs.getString("cep"));
			c.setTelefone(rs.getString("telefone"));
			c.setPontos(rs.getInt("pontos"));
			// adiciona cliente à lista
			lista.add(c);
		}
		// fechar o ResultSet
		rs.close();
		// fechar o statement
		comando.close();
		// retorna a lista de clientes
		return lista;
	}

	public Cliente buscarPorTel(String telefone) throws SQLException {
		// Cria o objeto Cliente para retornar
		Cliente c = null;
		// String com a instrução SQL
		String sql = "select * from cliente where telefone = ?";
		// cria um comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// substituindo a interrogação pelo telefone
		comando.setString(1, telefone);
		// Cria o result set para percorrer os registros do banco
		ResultSet rs = comando.executeQuery();
		// Se houver linhas
		if (rs.next()) {
			// Instancia o objeto cliente
			c = new Cliente();
			// popula o objeto cliente
			c.setId(rs.getInt("id"));
			c.setNome(rs.getString("nome"));
			c.setEndereco(rs.getString("endereco"));
			c.setEmail(rs.getString("email"));
			c.setCep(rs.getString("cep"));
			c.setTelefone(rs.getString("telefone"));
			c.setPontos(rs.getInt("pontos"));
		}
		// fechar o ResultSet
		rs.close();
		// fechar o statement
		comando.close();
		// retorna a lista de clientes
		return c;
	}
	
	public void atualizaPontos(int idCliente, int pontos) throws SQLException {
		//String com a instrução
		String sql = "update cliente set pontos = ? where id = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1,  pontos);
		comando.setInt(2, idCliente);
		comando.execute();
		comando.close();
	}

}
