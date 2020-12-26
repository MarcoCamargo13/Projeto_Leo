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
public class GraficoBarras extends JFrame {
	private static final long serialVersionUID = 1L;

	public GraficoBarras(List<SAC> sac) {
		setTitle("Grafico de Barras");
		setSize(1000, 400);
		setLocationRelativeTo(null);
		criarGraficosBarras(sac);
	}

	public void criarGraficosBarras(List<SAC> sac) {
		DefaultCategoryDataset barra = new DefaultCategoryDataset();
		for (SAC s : sac) {
			barra.setValue(s.getQuantidadeReclamacoes(), s.getAssunto(), s.getDataGrafico());
		}

		// grafico normal ChartFactory.createBarChart --barras
		JFreeChart grafico = ChartFactory.createBarChart3D("Tipo de Reclamação", "Datas", "Quantidade de Reclamação",
				barra, PlotOrientation.VERTICAL, true, true, true);
		// cor
//		CategoryPlot barraItem = grafico.getCategoryPlot();
//		barraItem.getRenderer().setSeriesPaint(2, Color.BLACK);

		mostraGrafico(grafico);
	}

	private void mostraGrafico(JFreeChart grafico) {
		ChartPanel painel = new ChartPanel(grafico);
		getContentPane().add(painel);

	}

}
