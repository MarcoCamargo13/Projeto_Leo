package br.senai.sp.leopizza.util;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import br.senai.sp.leopizza.modelo.SAC;

/**
 * @author marco
 *
 */
public class GraficoLinhas extends JFrame {

	private static final long serialVersionUID = 1L;

	public GraficoLinhas(List<SAC> sac) {
		setTitle("Grafico de Barras");
		setSize(1000, 400);
		setLocationRelativeTo(null);

		criarGraficosLinhas(sac);

	}

	public void criarGraficosLinhas(List<SAC> sac) {
		DefaultCategoryDataset linha = new DefaultCategoryDataset();
		
		for (SAC s : sac) {
			linha.setValue(s.getQuantidadeReclamacoes(), s.getAssunto(), s.getDataGrafico());
		}

//		linha.addValue(10.1, "Maxino", "Horas 1");
//		linha.addValue(20.1, "Maxino", "Horas 2");
//		linha.addValue(30.1, "Maxino", "Horas 3");
//		linha.addValue(40.1, "Maxino", "Horas 4");
//		linha.addValue(50.1, "Maxino", "Horas 5");

		JFreeChart grafico = ChartFactory.createLineChart("Tipo de Reclamação", "Datas", "Quantidade de Reclamação", linha,
				PlotOrientation.VERTICAL, true, true, false);

		mostraGrafico(grafico);
	}

	private void mostraGrafico(JFreeChart grafico) {
		ChartPanel painel = new ChartPanel(grafico);
		getContentPane().add(painel);

	}
}