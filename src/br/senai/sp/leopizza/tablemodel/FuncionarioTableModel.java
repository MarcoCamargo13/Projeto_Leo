package br.senai.sp.leopizza.tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sp.leopizza.modelo.Funcionario;

public class FuncionarioTableModel extends AbstractTableModel {
	private List<Funcionario> funcionarios;
	private String[] colunas = { "Nome", "Cargo"};

	public FuncionarioTableModel(List<Funcionario> lista) {
		this.funcionarios = lista;
	}

	@Override
	public int getRowCount() {
		return funcionarios.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Funcionario funcionario = funcionarios.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return funcionario.getNome();
		case 1:
			return funcionario.getCargo();		
		}
		return null;
	}
	

}
