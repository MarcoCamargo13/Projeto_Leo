package br.senai.sp.leopizza.tablemodel;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sp.leopizza.modelo.Pedido;

public class PedidoTableModel extends AbstractTableModel {

	private List<Pedido> pedidos;
	// criar vetor para o numero de coluna
	private final String[] COLUNAS = { "Nº", "Data", "Cliente", "Valor" };

	public PedidoTableModel(List<Pedido> lista) {

		this.pedidos = lista;
	}

	@Override
	public int getRowCount() {
		// inseri o numero de linhas de acordo com cliente
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
			return p.getCliente().getNome();
		case 3:
			return String.format("R$ %6.2f", p.getvTotal());
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		// retorna o valor da posição colum no vetor
		// cabeçalho
		return COLUNAS[column];
	}

}
