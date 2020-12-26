package br.senai.sp.leopizza.util;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PiePlot;

import org.jfree.data.general.DefaultPieDataset;

import br.senai.sp.leopizza.modelo.SAC;

public class GraficoPizza extends JFrame {

	private static final long serialVersionUID = 1L;

	public GraficoPizza(List<SAC> sac) {
		setTitle("Grafico de Barras");
		setSize(1000, 400);
		setLocationRelativeTo(null);

		criarGraficoPizza(sac);
	}

	public void criarGraficoPizza(List<SAC> sac) {
		DefaultPieDataset pizza = new DefaultPieDataset();

		for (SAC s : sac) {
			pizza.setValue(s.getAssunto(), s.getQuantidadeReclamacoes()) ;
		}

		// grafico pizza
		JFreeChart grafico = ChartFactory.createPieChart("Reclamações", pizza, true, true, true);

		// cor
		//PiePlot fatia = (PiePlot) grafico.getPlot();
		//fatia.setSectionPaint("Brasil", Color.blue);

		mostraGrafico(grafico);
	}

	private void mostraGrafico(JFreeChart grafico) {
		ChartPanel painel = new ChartPanel(grafico);
		getContentPane().add(painel);

	}
}