package br.senai.sp.leopizza.tablemodel;

import java.awt.Color;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sp.leopizza.modelo.ItemPedido;
import br.senai.sp.leopizza.modelo.Pedido;

public class SACItemsTableModel extends AbstractTableModel {
	private List<ItemPedido> itens;
	private String[] colunas = { "Qtd", "Produto", "Total" };

	public SACItemsTableModel(List<ItemPedido> lista) {
		this.itens = lista;
	}

	public int getRowCount() {
		// tamanho das colunas
		return itens.size();
	}

	public int getColumnCount() {
		return colunas.length;
	}

	// metodo que da nome as colunas, que esta no vetor
	public String getColumnName(int column) {
		return colunas[column];
	}

	public Object getValueAt(int linha, int coluna) {
		ItemPedido item = itens.get(linha);

		switch (coluna) {
		case 0:
			return item.getQtd();
		case 1:
			return item.getProduto().getNome();
		case 2:
			return String.format("R$ %6.2f", item.getTotal());
		default:
			return null;
		}

	}

}
