package br.senai.sp.leopizza.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CoresJTable implements TableCellRenderer {

	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
	int line;
	String valor;

	// @Override

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
				column);
		((JTable) renderer).setOpaque(true);
		Color foreground = null, background = null;

		valor = String.valueOf(value);
		if (isSelected) {
			foreground = new Color(0, 0, 0);
			background = new Color(184, 207, 229);

		} else if (row % 2 == 0) {
			foreground = new Color(0, 0, 0);
			background = new Color(213, 230, 239);
		}

		switch (valor) {
		case "PAR":
			foreground = new Color(251, 173, 48);
			background = new Color(213, 230, 239);
			break;
		case "IMPAR":
			foreground = new Color(250, 0, 0);
			background = new Color(184, 207, 229);
			break;

		}

		renderer.setForeground(foreground);
		renderer.setBackground(background);
		return renderer;
	}
}
