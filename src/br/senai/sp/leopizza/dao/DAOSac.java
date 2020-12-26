package br.senai.sp.leopizza.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.senai.sp.leopizza.modelo.Assunto;
import br.senai.sp.leopizza.modelo.Cliente;
import br.senai.sp.leopizza.modelo.FormaPgto;
import br.senai.sp.leopizza.modelo.Funcionario;
import br.senai.sp.leopizza.modelo.ItemPedido;
import br.senai.sp.leopizza.modelo.Pedido;
import br.senai.sp.leopizza.modelo.Produto;
import br.senai.sp.leopizza.modelo.SAC;
import br.senai.sp.leopizza.view.TelaPrincipal;

public class DAOSac implements InterfaceCRUD<SAC> {

	private Connection conexao;

	public DAOSac() {
		this.conexao = ConnectionFactory.getConnection();
	}

	@Override
	public void inserir(SAC s) throws Exception {
		conexao.setAutoCommit(false);
		String sql = "insert into sac (cliente_id, assunto, relato, feedback, funcionario_cpf, entregador, id, nome_cliente, nome_atendente)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		comando.setInt(1, s.getCliente().getId());
		comando.setInt(2, s.getAssunto().ordinal());
		comando.setString(3, s.getRelato());
		comando.setString(4, s.getFeedback());
		comando.setString(5, TelaPrincipal.func.getCpf());
		comando.setString(6, s.getEntregador());
		comando.setInt(7, s.getId_pedido());
		comando.setString(8, s.getNome_cliente());
		comando.setString(9, s.getNome_atendente());
		try {
			comando.execute();
			ResultSet chaves = comando.getGeneratedKeys();
			if (chaves.next()) {
				s.setProtocolo(chaves.getInt(1));
			} else {
				throw new Exception("Erro ao inserir a manifestação");
			}
			comando.close();
			conexao.commit();
		} catch (Exception e) {
			conexao.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conexao.setAutoCommit(true);
		}
	}

	@Override
	public void atualizar(SAC s) throws Exception {
		String sql = "update sac (cliente_id, assunto, relato, feedback, funcionario_cpf)" + "values(?, ?, ?, ?, ?)";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, s.getCliente().getId());
		comando.setInt(2, s.getAssunto().ordinal());
		comando.setString(3, s.getRelato());
		comando.setString(4, s.getFeedback());
		comando.setString(5, TelaPrincipal.func.getCpf());
		comando.execute();
		comando.close();
	}

	public void atualizarReclamacaoPedido(int numero) throws Exception {
		String sql = "update pedido set flag_reclamacao = ? where numero = " + numero;
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, "1");
		comando.execute();
		comando.close();
	}

	@Override
	public void excluir(SAC objeto) throws Exception {
		String sql = "delete from protocolo where numero = ?";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setInt(1, objeto.getProtocolo());
		comando.execute();
		comando.close();
	}

	@Override
	public List<SAC> listar() throws Exception {
		List<SAC> lista = new ArrayList<>();
		String sql = "select * from view_sac order by protocolo desc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		ResultSet rs = comando.executeQuery();
		while (rs.next()) {
			SAC s = new SAC();
			Calendar dataped = Calendar.getInstance();
			// Ajusta a data no Calendar
			dataped.setTimeInMillis(rs.getTimestamp("data").getTime());
			// Seta o calendar no pedido
			s.setData(dataped);
			s.setProtocolo(rs.getInt("protocolo"));
			s.setEntregador(rs.getString("entregador"));
			s.setNome_cliente(rs.getString("nome_cliente"));
			s.setNome_atendente(rs.getString("nome_atendente"));
			s.setAssunto(Assunto.values()[rs.getInt("assunto")]);
			s.setRelato(rs.getString("relato"));
			s.setFeedback(rs.getString("feedback"));
			Cliente c = new Cliente();
			c.setNome(rs.getString("nome_cliente"));
			c.setId(rs.getInt("id"));
			s.setCliente(c);
			Funcionario f = new Funcionario();
			f.setCpf(rs.getString("funcionario_cpf"));
			s.setFuncionario(f);
			lista.add(s);
		}
		rs.close();
		comando.close();
		return lista;
	}

	public List<SAC> procurar(String parametro) throws Exception {
//		if (parametro.contains("/")) {
//			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//			parametro = df.format(parametro);
//		}
		String sql = "select * from view_sac where nome_cliente like ? or entregador like ? or protocolo like ? or id like ? order by protocolo desc";
		PreparedStatement comando = conexao.prepareStatement(sql);
		comando.setString(1, "%" + parametro + "%");
		comando.setString(2, "%" + parametro + "%");
		comando.setString(3, "%" + parametro + "%");
		comando.setString(4, "%" + parametro + "%");
//		comando.setString(5, "%" + parametro + "%");
		List<SAC> reclamacao = new ArrayList<>();
		ResultSet rs = comando.executeQuery();
		while (rs.next()) {
			SAC s = new SAC();
//			s.setData(rs.getDate("data"));

			Calendar dataped = Calendar.getInstance();
			// Ajusta a data no Calendar
			dataped.setTimeInMillis(rs.getTimestamp("data").getTime());
			// Seta o calendar no pedido
			s.setData(dataped);

			s.setProtocolo(rs.getInt("protocolo"));
			s.setId(rs.getInt("id"));
			Cliente c = new Cliente();
			c.setNome(rs.getString("nome_cliente"));
			c.setId(rs.getInt("id"));
			s.setCliente(c);
			s.setAssunto(Assunto.values()[rs.getInt("assunto")]);
			s.setRelato(rs.getString("relato"));
			s.setFeedback(rs.getString("feedback"));
			Funcionario f = new Funcionario();
			f.setCpf(rs.getString("funcionario_cpf"));
			s.setFuncionario(f);
			reclamacao.add(s);
		}
		rs.close();
		comando.close();
		return reclamacao;
	}

	public List<SAC> listarReclamacoesGrafico(String dataInicial, String DataFinal) {

		List<SAC> sac = new ArrayList<>();
		String anterior = null;
		SAC s = null;
		try {// String com a instrução sql
//			String sql = "select count( distinct Assunto) as numeroReclamacoes, DATE_FORMAT (data,'%d-%m-%Y') AS 'data', assunto"
//					+ " from sac where DATE (data) BETWEEN '"+ dataInicial + "'  and '" + DataFinal + "'  group by DATE_FORMAT (data,'%d-%m-%Y')";

			String sql = "select count(Assunto) as numeroReclamacoes, DATE_FORMAT (data,'%d-%m-%Y') AS data, assunto "
					+ "from sac where DATE (data) BETWEEN '" + dataInicial + "'  and '" + DataFinal + "'"
					+ "group by assunto, DATE_FORMAT (data,'%d-%m-%Y') order by data, numeroReclamacoes;";

			// Cria o comando
			PreparedStatement comando = conexao.prepareStatement(sql);

			// Cria o resultSet
			ResultSet rs = comando.executeQuery();

			while (rs.next()) {
				// Cria o pedido
				s = new SAC();

				s.setDataGrafico(rs.getString("data"));
				s.setQuantidadeReclamacoes(rs.getInt("numeroReclamacoes"));
				s.setAssunto(Assunto.values()[rs.getInt("assunto")]);
				sac.add(s);
			}

			// Fecha o Resultset
			rs.close();
			// Fecha o comando
			comando.close();

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return sac;

	}

	public List<SAC> listarReclamacoesGraficoPizza(String dataInicial, String DataFinal) {

		List<SAC> sac = new ArrayList<>();
		String anterior = null;
		SAC s = null;
		try {// String com a instrução sql

			String sql = "select count(Assunto) as numeroReclamacoes, assunto from sac where DATE (data) BETWEEN '"
					+ dataInicial + "' and '" + DataFinal + "'group by assunto order by assunto";

			// Cria o comando
			PreparedStatement comando = conexao.prepareStatement(sql);

			// Cria o resultSet
			ResultSet rs = comando.executeQuery();

			while (rs.next()) {
				// Cria o pedido
				s = new SAC();

				s.setQuantidadeReclamacoes(rs.getInt("numeroReclamacoes"));
				s.setAssunto(Assunto.values()[rs.getInt("assunto")]);
				sac.add(s);
			}

			// Fecha o Resultset
			rs.close();
			// Fecha o comando
			comando.close();

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return sac;

	}
}
