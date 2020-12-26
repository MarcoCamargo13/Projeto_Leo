package br.senai.sp.leopizza.tablemodel;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sp.leopizza.modelo.Pedido;

public class SACTableModel extends AbstractTableModel{
	
	private List<Pedido> pedidos;
	// criar vetor para o numero de coluna
	private final String[] COLUNAS = { "Nº", "Data", "Valor" };

	public SACTableModel(List<Pedido> lista) {

		this.pedidos = lista;
	}

	@Override
	public int getRowCount() {
		return pedidos.size();
	}

	@Override
	public int getColumnCount() {
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pedido p = pedidos.get(rowIndex);

		// verificar qual a coluna
		switch (columnIndex) {
		case 0:
			return p.getNumero();
		case 1:
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return formatador.format(p.getData().getTime());
		case 2:
			return String.format("R$ %6.2f", p.getvTotal());
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return COLUNAS[column];
	}
	
	public Color getRowColor(int row) {
		Pedido p = pedidos.get(row);
		switch (Integer.parseInt(p.getFlagRaclamacao())) {
		case 1:
			return new Color(250, 235, 120);
		default:
			return Color.white;
		}
	}
}
