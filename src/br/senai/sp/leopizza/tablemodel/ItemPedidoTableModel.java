package br.senai.sp.leopizza.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sp.leopizza.modelo.ItemPedido;

public class ItemPedidoTableModel extends AbstractTableModel {

	private List<ItemPedido> itens;
	private String[] colunas = { "Qtd", "Produto", "Observação", "Total" };

	public ItemPedidoTableModel(List<ItemPedido> lista) {
		this.itens = lista; 
	}

	@Override
	public int getRowCount() {
		// tamanho das colunas
		return itens.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	// metodo que da nome as colunas, que esta no vetor
	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		ItemPedido item = itens.get(linha);
		
		switch (coluna) {
		case 0:
			return item.getQtd();
		case 1:
			return item.getProduto().getNome();
		case 2:
			return item.getObservacao();
		case 3:
			return String.format("R$ %6.2f", item.getTotal());
		default:
			return null;
		}
	
	}
}
