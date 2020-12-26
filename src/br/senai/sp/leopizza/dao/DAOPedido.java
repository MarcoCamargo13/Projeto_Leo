package br.senai.sp.leopizza.dao;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.senai.sp.leopizza.modelo.Cliente;
import br.senai.sp.leopizza.modelo.FormaPgto;
import br.senai.sp.leopizza.modelo.Funcionario;
import br.senai.sp.leopizza.modelo.ItemPedido;
import br.senai.sp.leopizza.modelo.Pedido;
import br.senai.sp.leopizza.modelo.Produto;
import br.senai.sp.leopizza.view.TelaPrincipal;

public class DAOPedido implements InterfaceCRUD<Pedido> {

	private Connection conexao;

	public DAOPedido() {
		this.conexao = ConnectionFactory.getConnection();
	}

	@Override
	public void inserir(Pedido p) throws Exception {
		// Desabilita o commit automático
		conexao.setAutoCommit(false);
		// String com a instrução sql
		String sql = "insert into pedido (cliente_id, end_entrega, retirada, tx_entrega, troco, vtotal, observacao, forma_pgto, func_cpf, entregador)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		// Cria o comando
		PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		// Substituindo as Interrogações(?)
		comando.setInt(1, p.getCliente().getId());
		comando.setString(2, p.getEndEntrega());
		comando.setBoolean(3, p.isRetirada());
		comando.setDouble(4, p.getTxEntrega());
		comando.setDouble(5, p.getTroco());
		comando.setDouble(6, p.getvTotal());
		comando.setString(7, p.getObservacao());
		comando.setInt(8, p.getFormaPgto().ordinal());

		if (TelaPrincipal.func.getCpf().equals("1")) {
			// String com a instrução sql
			sql = "select cpf from funcionario where cargo = 1";
			// Cria o comando
			PreparedStatement comando1 = conexao.prepareStatement(sql);
			// Cria o resultSet
			ResultSet rs = comando1.executeQuery();
			if (rs.next()) {
				comando.setString(9, rs.getString("cpf"));
			}
		} else {
			comando.setString(9, TelaPrincipal.func.getCpf());
		}
		comando.setString(10, p.getEntregador());
		try {
			// Tenta executar o comando
			comando.execute();
			// Resultsepara obter as chaves geradas (o número do pedido gerado no
			// auto-incremento
			ResultSet chaves = comando.getGeneratedKeys();
			// Verifica se existe chave gerada
			if (chaves.next()) {
				// Seta no pedido o número gerado pelo banco
				p.setNumero(chaves.getInt(1));
			} else {
				// Lança uma Exception
				throw new Exception("Erro ao inserir o pedido");
			}

			// String com a instrução para inserir o item do pedido
			String sql2 = "insert into itempedido(produto_id, qtd, total, observacao, pedido_numero) values(?, ?, ?, ?, ?)";
			// Percorrer os itens do pedido
			for (ItemPedido i : p.getItens()) {
				// Cria um novo comando
				PreparedStatement comando2 = conexao.prepareStatement(sql2);
				// Substitui as interrogações(?)
				comando2.setInt(1, i.getProduto().getId());
				comando2.setInt(2, i.getQtd());
				comando2.setDouble(3, i.getTotal());
				comando2.setString(4, i.getObservacao());
				comando2.setInt(5, p.getNumero());
				// Executa o comando2;
				comando2.execute();
				comando2.close();
			}
			// Encerra o comando
			comando.close();
			conexao.commit();
		} catch (Exception e) {
			// Desfaz as instruções, caso ocorra algum erro
			conexao.rollback();
			// Exibe o erro no console
			e.printStackTrace();
			// Joga o erro pra cima
			throw e;
		} finally {
			// Habilita novamente o commit automático
			conexao.setAutoCommit(true);
		}
	}

	@Override
	public void atualizar(Pedido objeto) {

	}

	@Override
	public void excluir(Pedido objeto) throws Exception {
		String sql = "delete from pedido where numero = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getNumero());
		comando.execute();
		comando.close();
	}

	@Override
	public List<Pedido> listar() throws Exception {
		// String com a instrução sql
		String sql = "select * from view_pedidos order by numero desc";
		// Cria o comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// Cria a lista de pedidos
		List<Pedido> pedidos = new ArrayList<>();
		// Cria o resultSet
		ResultSet rs = comando.executeQuery();
		while (rs.next()) {
			// Cria o pedido
			Pedido p = new Pedido();
			// Seta o número do pedido
			p.setNumero(rs.getInt("numero"));
			// Criar um Calendar
			Calendar dataped = Calendar.getInstance();
			// Ajusta a data no Calendar
			dataped.setTimeInMillis(rs.getTimestamp("data").getTime());
			// Seta o calendar no pedido
			p.setData(dataped);
			p.setRetirada(rs.getBoolean("retirada"));
			p.setEndEntrega(rs.getString("end_entrega"));
			p.setTxEntrega(rs.getDouble("tx_entrega"));
			p.setTroco(rs.getDouble("troco"));
			p.setvTotal(rs.getDouble("vtotal"));
			p.setObservacao(rs.getString("observacao"));
			p.setEntregador(rs.getString("entregador"));
			p.setFormaPgto(FormaPgto.values()[rs.getInt("forma_pgto")]);
			// Cria o cliente
			Cliente c = new Cliente();
			c.setNome(rs.getString("nome_cliente"));
			c.setTelefone(rs.getString("telefone"));
			// Seta o cliente no pedido
			p.setCliente(c);
			// Cria o funcionário
			Funcionario f = new Funcionario();
			f.setNome(rs.getString("nome_func"));
			// Seta o funcionário no pedido
			p.setFuncionario(f);
			// Cria uma lista de itens
			List<ItemPedido> itens = new ArrayList<>();
			do {
				// Verifica se é o mesmo pedido da linha anterior
				if (p.getNumero() != rs.getInt("numero")) {
					// Volta uma linha
					rs.previous();
					// Encerra o Do...While
					break;
				} else {
					// Cria um item do pedido
					ItemPedido item = new ItemPedido();
					// Setar o id
					item.setId(rs.getInt("id"));
					// Seta a quantidade
					item.setQtd(rs.getInt("qtd"));
					// Seta o total
					item.setTotal(rs.getDouble("total"));
					// Seta a observação do item
					item.setObservacao(rs.getString("obs_item"));
					// Cria o produto do item
					Produto pr = new Produto();
					// Popula o produto
					pr.setNome(rs.getString("produto"));
					// Seta o produto no item
					item.setProduto(pr);
					// Adiciona o item à lista
					itens.add(item);
				}
			} while (rs.next());
			// Setar os itens no pedido
			p.setItens(itens);
			// Adicionar o pedido à lista
			pedidos.add(p);
		}
		// Fecha o Resultset
		rs.close();
		// Fecha o comando
		comando.close();
		return pedidos;
	}

	public List<Pedido> procurar(String parametro) throws Exception {
		// String com a instrução sql
		String sql = "select * from view_pedidos where nome_cliente like ? or numero like ? order by numero desc";
		// Cria o comando
		PreparedStatement comando = conexao.prepareStatement(sql);
		// Substituir as interrogações
		comando.setString(1, "%" + parametro + "%");
		comando.setString(2, "%" + parametro + "%");
//		comando.setString(3, "%" + parametro + "%");
		// Cria a lista de pedidos
		List<Pedido> pedidos = new ArrayList<>();
		// Cria o resultSet
		ResultSet rs = comando.executeQuery();
		while (rs.next()) {
			// Cria o pedido
			Pedido p = new Pedido();
			// Seta o número do pedido
			p.setNumero(rs.getInt("numero"));
			// Criar um Calendar
			Calendar dataped = Calendar.getInstance();
			// Ajusta a data no Calendar
			dataped.setTimeInMillis(rs.getTimestamp("data").getTime());
			// Seta o calendar no pedido
			p.setData(dataped);
			p.setRetirada(rs.getBoolean("retirada"));
			p.setEndEntrega(rs.getString("end_entrega"));
			p.setTxEntrega(rs.getDouble("tx_entrega"));
			p.setEntregador(rs.getString("entregador"));
			p.setNumero(rs.getInt("numero"));
			p.setTroco(rs.getDouble("troco"));
			p.setvTotal(rs.getDouble("vtotal"));
			p.setObservacao(rs.getString("observacao"));
			p.setFormaPgto(FormaPgto.values()[rs.getInt("forma_pgto")]);
			p.setFlagRaclamacao(rs.getString("flag_reclamacao"));
			// Cria o cliente
			Cliente c = new Cliente();
			c.setNome(rs.getString("nome_cliente"));
			c.setTelefone(rs.getString("telefone"));
			// Seta o cliente no pedido
			p.setCliente(c);
			// Cria o funcionário
			Funcionario f = new Funcionario();
			f.setNome(rs.getString("nome_func"));
			// Seta o funcionário no pedido
			p.setFuncionario(f);
			// Cria uma lista de itens
			List<ItemPedido> itens = new ArrayList<>();
			do {
				// Verifica se é o mesmo pedido da linha anterior
				if (p.getNumero() != rs.getInt("numero")) {
					// Volta uma linha
					rs.previous();
					// Encerra o Do...While
					break;
				} else {
					// Cria um item do pedido
					ItemPedido item = new ItemPedido();
					// Setar o id
					item.setId(rs.getInt("id"));
					// Seta a quantidade
					item.setQtd(rs.getInt("qtd"));
					// Seta o total
					item.setTotal(rs.getDouble("total"));
					// Seta a observação do item
					item.setObservacao(rs.getString("obs_item"));
					// Cria o produto do item
					Produto pr = new Produto();
					// Popula o produto
					pr.setNome(rs.getString("produto"));
					// Seta o produto no item
					item.setProduto(pr);
					// Adiciona o item à lista
					itens.add(item);
				}
			} while (rs.next());
			// Setar os itens no pedido
			p.setItens(itens);
			// Adicionar o pedido à lista
			pedidos.add(p);
		}
		// Fecha o Resultset
		rs.close();
		// Fecha o comando
		comando.close();
		return pedidos;
	}

	public List<ItemPedido> listarItem(int pedido) throws SQLException {
		List<ItemPedido> itens = new ArrayList<>();
		String sql = "select * from itempedido where pedido_numero = ? order by id asc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, pedido);
		ResultSet rs = comando.executeQuery();
		if (rs.next()) {
			ItemPedido item = new ItemPedido();
			Produto pr = new Produto();
			item.setId(rs.getInt("id"));
			pr.setNome(rs.getString("produto"));
			item.setProduto(pr);
			item.setQtd(rs.getInt("qtd"));
			item.setTotal(rs.getFloat("total"));
			item.setObservacao(rs.getString("observacao"));
			itens.add(item);
		}
		rs.close();
		comando.close();
		return itens;
	}
}
