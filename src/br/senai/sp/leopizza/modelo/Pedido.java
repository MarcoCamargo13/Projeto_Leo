package br.senai.sp.leopizza.modelo;

import java.util.Calendar;
import java.util.List;

public class Pedido {
	private int numero;
	private Calendar data;
	private Cliente cliente;
	private String endEntrega;
	private String entregador;
	private boolean retirada;
	private double txEntrega;
	private double troco;
	private double vTotal;
	private String observacao;
	private FormaPgto formaPgto;
	private List<ItemPedido> itens;
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private String flagRaclamacao;

	// Incluir funcionário

	// Getters & Setters
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getFlagRaclamacao() {
		return flagRaclamacao;
	}

	public void setFlagRaclamacao(String flagRaclamacao) {
		this.flagRaclamacao = flagRaclamacao;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getEndEntrega() {
		return endEntrega;
	}

	public void setEndEntrega(String endEntrega) {
		this.endEntrega = endEntrega;
	}

	public boolean isRetirada() {
		return retirada;
	}

	public void setRetirada(boolean retirada) {
		this.retirada = retirada;
	}

	public double getTxEntrega() {
		return txEntrega;
	}

	public void setTxEntrega(double txEntrega) {
		this.txEntrega = txEntrega;
	}

	public double getTroco() {
		return troco;
	}

	public void setTroco(double troco) {
		this.troco = troco;
	}

	public double getvTotal() {
		return vTotal;
	}

	public void setvTotal(double vTotal) {
		this.vTotal = vTotal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public FormaPgto getFormaPgto() {
		return formaPgto;
	}

	public void setFormaPgto(FormaPgto formaPgto) {
		this.formaPgto = formaPgto;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	// Método para retornar o número de pontos do pedido
	public int getPontos() {
		// Variável para contar
		int contador = 0;
		// Percorre os itens do pedido
		for (ItemPedido i : itens) {
			// Verifica se é pizza doce ou salgada
			if (i.getProduto().getTipo() == TipoProduto.PIZZA_DOCE
					|| i.getProduto().getTipo() == TipoProduto.PIZZA_SALGADA) {
				contador += i.getQtd();
			}
		}
		return contador;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getEntregador() {
		return entregador;
	}

	public void setEntregador(String entregador) {
		this.entregador = entregador;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

}
