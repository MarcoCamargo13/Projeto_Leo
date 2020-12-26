package br.senai.sp.leopizza.view;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;

import com.toedter.calendar.JDateChooser;

import br.senai.sp.leopizza.dao.DAOSac;
import br.senai.sp.leopizza.modelo.SAC;
import br.senai.sp.leopizza.util.EnviarEmail;
import br.senai.sp.leopizza.util.GraficoBarras;
import br.senai.sp.leopizza.util.GraficoLinhas;
import br.senai.sp.leopizza.util.GraficoPizza;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaGraficos extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblDataInicial;
	private JLabel lblDataFinal;
	private JTable table;
	private GraficoPizza pizza;
	private GraficoLinhas linha;
	private GraficoBarras barras;
	private SimpleDateFormat dateFormat;
	private String dataInicial = "";
	private String dataFinal = "";
	DAOSac sac = new DAOSac();

	/**
	 * Create the frame.
	 */

	public TelaGraficos() {
		dateFormat = new SimpleDateFormat("yyy-MM-dd");

		setIconifiable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		setTitle("Cadastro de Produtos");
		setBounds(100, 100, 323, 288);
		contentPane = new JPanel();

		contentPane.setBackground(new Color(250, 235, 215));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblDataInicial = new JLabel("Data Inicial : ");
		lblDataInicial.setBounds(25, 27, 83, 20);
		contentPane.add(lblDataInicial);

		lblDataFinal = new JLabel("Data Final  : ");
		lblDataFinal.setBounds(25, 103, 83, 20);
		contentPane.add(lblDataFinal);

		JDateChooser dateChooserInicial = new JDateChooser();
		dateChooserInicial.setBounds(98, 27, 192, 20);
		contentPane.add(dateChooserInicial);

		JDateChooser dateChooserFinal = new JDateChooser();
		dateChooserFinal.setBounds(98, 103, 192, 20);
		contentPane.add(dateChooserFinal);

		JLabel lblSelecao = new JLabel("Selecione :");
		lblSelecao.setBounds(25, 183, 83, 20);
		contentPane.add(lblSelecao);

		JComboBox cbGrafico = new JComboBox();
		cbGrafico.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cbGrafico.setBackground(Color.WHITE);
		cbGrafico.setBounds(98, 182, 191, 20);
		contentPane.add(cbGrafico);

		getContentPane().add(cbGrafico);
		cbGrafico.addItem("Grafico de Barras");
		cbGrafico.addItem("Grafico de Linhas");
		cbGrafico.addItem("Grafico de Pizza");

		cbGrafico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valor = cbGrafico.getSelectedIndex();
				capturaData();
				if (valor == 0) {
					List<SAC> s = sac.listarReclamacoesGrafico(dataInicial, dataFinal);
					if (!s.isEmpty()) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								barras = new GraficoBarras(s);
								barras.setVisible(true);
							}
						});

					} else {
						JOptionPane.showMessageDialog(null, "Não há dados a apresentar!\nDefina nova data.", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}
				} else if (valor == 1) {
					List<SAC> s = sac.listarReclamacoesGrafico(dataInicial, dataFinal);
					if (!s.isEmpty()) {
						linha = new GraficoLinhas(s);
						linha.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Não há dados a apresentar!\nDefina nova data.", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					List<SAC> s = sac.listarReclamacoesGraficoPizza(dataInicial, dataFinal);
					if (!s.isEmpty()) {
						pizza = new GraficoPizza(s);
						pizza.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Não há dados a apresentar!\nDefina nova data.", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}
				}

			}

			private void capturaData() {
				dataInicial = dateFormat.format(dateChooserInicial.getDate());
				dataFinal = dateFormat.format(dateChooserFinal.getDate());
			}
		});

	}
}
