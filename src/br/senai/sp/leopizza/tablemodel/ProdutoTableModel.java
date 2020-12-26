package br.senai.sp.leopizza.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sp.leopizza.modelo.Produto;

public class ProdutoTableModel extends AbstractTableModel {
	private List<Produto> produtos;
	private final String[] COLUNAS = { "Nome", "Tipo", "Preço" };

	public ProdutoTableModel(List<Produto> lista) {
		this.produtos = lista;
	}

	@Override
	public int getRowCount() {
		return produtos.size();
	}

	@Override
	public int getColumnCount() {
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Produto p = produtos.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getNome();
		case 1:
			return p.getTipo();
		case 2:
			return String.format("R$ %6.2f", p.getPreco());
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return COLUNAS[column];
	}
}
