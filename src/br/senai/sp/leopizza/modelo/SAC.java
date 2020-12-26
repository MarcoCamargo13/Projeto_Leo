package br.senai.sp.leopizza.modelo;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class SAC {
	private int protocolo;
	private int numero;
	private Funcionario funcionario;
	private Cliente cliente;
	private Pedido pedido;
	private List<ItemPedido> itens;
	private Manifestacao manifestacao;
	private Assunto assunto;
	private String relato;
	private String Feedback;
	private Calendar data;
	private String status;
	private String prazo;
	private int quantidadeReclamacoes;
	private String dataGrafico;
	private int id;
	private String entregador;
	private String nome_cliente;
	private String nome_atendente;
	private int id_pedido;

	public SAC() {

	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public String getNome_atendente() {
		return nome_atendente;
	}

	public void setNome_atendente(String nome_atendente) {
		this.nome_atendente = nome_atendente;
	}

	public String getEntregador() {
		return entregador;
	}

	public void setEntregador(String entregador) {
		this.entregador = entregador;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantidadeReclamacoes() {
		return quantidadeReclamacoes;
	}

	public void setQuantidadeReclamacoes(int quantidadeReclamacoes) {
		this.quantidadeReclamacoes = quantidadeReclamacoes;
	}

	public String getDataGrafico() {
		return dataGrafico;
	}

	public void setDataGrafico(String dataGrafico) {
		this.dataGrafico = dataGrafico;
	}

	public int getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(int protocolo) {
		this.protocolo = protocolo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Manifestacao getManifestacao() {
		return manifestacao;
	}

	public void setManifestacao(Manifestacao manifestacao) {
		this.manifestacao = manifestacao;
	}

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	public String getRelato() {
		return relato;
	}

	public void setRelato(String relato) {
		this.relato = relato;
	}

	public String getFeedback() {
		return Feedback;
	}

	public void setFeedback(String feedback) {
		Feedback = feedback;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String string) {
		this.status = string;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String string) {
		this.prazo = string;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public void setFuncionario(String text) {
		this.funcionario = funcionario;

	}

}
