package br.senai.sp.leopizza.tablemodel;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sp.leopizza.modelo.SAC;

public class RecTableModel extends AbstractTableModel {

	private List<SAC> sac;
	private final String[] COLUNAS = { "Nº Reclamação", "Nº Pedido", "Data", "Cliente" };

	public RecTableModel(List<SAC> lista) {
		this.sac = lista;
	}

	@Override
	public int getRowCount() {
		return sac.size();
	}

	@Override
	public int getColumnCount() {
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SAC s = sac.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return s.getProtocolo();
		case 1:
			return s.getCliente().getId();
		case 2:
//			return s.getData();
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return formatador.format(s.getData().getTime());
		case 3:
			return s.getCliente().getNome();
		}

		return null;
	}

	@Override
	public String getColumnName(int column) {
		return COLUNAS[column];
	}
}
