package br.senai.sp.leopizza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senai.sp.leopizza.modelo.Cliente;
import br.senai.sp.leopizza.modelo.Produto;
import br.senai.sp.leopizza.modelo.TipoProduto;

public class DAOProduto implements InterfaceCRUD<Produto> {
	private Connection conexao;

	public DAOProduto() {
		conexao = ConnectionFactory.getConnection();
	}

	@Override
	public void inserir(Produto objeto) throws SQLException {
		String sql = "insert into produto(nome, descricao, tipo, promocao, imagem, preco) values(?, ?, ?, ?, ?, ?)";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getDescricao());
		comando.setInt(3, objeto.getTipo().ordinal());
		comando.setBoolean(4, objeto.isPromocao());
		comando.setBytes(5, objeto.getImagem());
		comando.setDouble(6, objeto.getPreco());
		comando.execute();
		comando.close();
	}

	@Override
	public void atualizar(Produto objeto) throws SQLException {
		String sql = "update produto set nome = ?, descricao = ?, tipo = ?, promocao = ?, imagem = ?, preco = ? where id = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, objeto.getNome());
		comando.setString(2, objeto.getDescricao());
		comando.setInt(3, objeto.getTipo().ordinal());
		comando.setBoolean(4, objeto.isPromocao());
		comando.setBytes(5, objeto.getImagem());
		comando.setDouble(6, objeto.getPreco());
		comando.setInt(7, objeto.getId());
		comando.execute();
		comando.close();
	}

	@Override
	public void excluir(Produto objeto) throws SQLException {
		String sql = "delete from produto where id = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getId());
		comando.execute();
		comando.close();
	}

	@Override
	public List<Produto> listar() throws SQLException {
		List<Produto> lista = new ArrayList<>();
		String sql = "select * from produto order by nome asc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		ResultSet rs = comando.executeQuery();
		while (rs.next()) {
			Produto p = new Produto();
			p.setId(rs.getInt("id"));
			p.setNome(rs.getString("nome"));
			p.setDescricao(rs.getString("descricao"));
			p.setTipo(TipoProduto.values()[rs.getInt("tipo")]);
			p.setPromocao(rs.getBoolean("promocao"));
			p.setImagem(rs.getBytes("imagem"));
			p.setPreco(rs.getDouble("preco"));
			lista.add(p);
		}
		rs.close();
		comando.close();
		return lista;
	}

	public List<Produto> listar(String parametro) throws SQLException {
		List<Produto> lista = new ArrayList<>();
		String sql = "select * from produto where nome like ? order by nome asc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, "%" + parametro + "%");
		ResultSet rs = comando.executeQuery();
		while (rs.next()) {
			Produto p = new Produto();
			p.setId(rs.getInt("id"));
			p.setNome(rs.getString("nome"));
			p.setDescricao(rs.getString("descricao"));
			p.setTipo(TipoProduto.values()[rs.getInt("tipo")]);
			p.setPromocao(rs.getBoolean("promocao"));
			p.setImagem(rs.getBytes("imagem"));
			p.setPreco(rs.getDouble("preco"));
			lista.add(p);
		}
		rs.close();
		comando.close();
		return lista;
	}

	public List<Produto> buscar(String parametro, TipoProduto tipo) throws SQLException {
		List<Produto> lista = new ArrayList<>();
		String sql = "select * from produto where tipo = ? and nome like ? order by nome asc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, tipo.ordinal());
		comando.setString(2, "%" + parametro + "%");
		ResultSet rs = comando.executeQuery();
		while (rs.next()) {
			Produto p = new Produto();
			p.setId(rs.getInt("id"));
			p.setNome(rs.getString("nome"));
			p.setDescricao(rs.getString("descricao"));
			p.setTipo(TipoProduto.values()[rs.getInt("tipo")]);
			p.setPromocao(rs.getBoolean("promocao"));
			p.setImagem(rs.getBytes("imagem"));
			p.setPreco(rs.getDouble("preco"));
			lista.add(p);
		}
		rs.close();
		comando.close();
		return lista;
	}

	public boolean buscarPizza(String parametro, TipoProduto tipo) throws SQLException {
		try {
			boolean retorno = false;
			String sql = "select * from produto where tipo = ? and (nome like ? && nome like ?)  order by nome asc";
			PreparedStatement comando = conexao.prepareStatement(sql);
			comando.setInt(1, tipo.ordinal());
			comando.setString(2, "%" + parametro.substring(0, parametro.indexOf("+") - 1).trim() + "%");
			comando.setString(3,
					"%" + parametro.substring(parametro.indexOf("+") + 1, parametro.length()).trim() + "%");
			ResultSet rs = comando.executeQuery();

			if (rs.next()) {
				retorno = true;
			}

			rs.close();
			comando.close();
			return retorno;
		} catch (Exception e) {
			return false;
		}
	}

	public Produto buscarPizzaId(String parametro) throws SQLException {
		Produto p = null;
		String sql = "select * from produto where (nome like (?) || nome like (?))";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, "%" + parametro.substring(0, parametro.indexOf("+") - 1).trim() + "%" + parametro.substring(parametro.indexOf("+") + 1, parametro.length()).trim() + "%");
		comando.setString(2, "%" + parametro.substring(parametro.indexOf("+") + 1, parametro.length()).trim() + "%" + parametro.substring(0, parametro.indexOf("+") - 1).trim() + "%");
		
		ResultSet rs = comando.executeQuery();

		if (rs.next()) {
			p = new Produto();
			p.setId(rs.getInt("id"));
			p.setNome(rs.getString("nome"));
			p.setDescricao(rs.getString("descricao"));
			p.setTipo(TipoProduto.values()[rs.getInt("tipo")]);
			p.setPromocao(rs.getBoolean("promocao"));
			p.setImagem(rs.getBytes("imagem"));
			p.setPreco(rs.getDouble("preco"));
		}

		rs.close();
		comando.close();
		return p;

	}


}
