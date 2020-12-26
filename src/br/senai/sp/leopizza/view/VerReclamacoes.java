package br.senai.sp.leopizza.view;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import br.senai.sp.leopizza.dao.DAOSac;
import br.senai.sp.leopizza.modelo.SAC;
import br.senai.sp.leopizza.tablemodel.RecTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JPopupMenu;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VerReclamacoes extends JInternalFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable tbReclamacao;
	private List<SAC> reclamacao;
	private SAC recSelec;
	private JScrollPane scrollPane_1;
	private JTextArea taInfo;
	private JPopupMenu popupMenu;
	private JMenuItem itSalvar;
//	public static JMenuItem itExcluir;
	private DAOSac daoSac;
	public RecTableModel modelSAC;

	public VerReclamacoes() {
		setClosable(true);
		setIconifiable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Reclamações");
		setBounds(100, 100, 927, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 444, 537);
		contentPane.add(scrollPane);

		taInfo = new JTextArea();
		taInfo.setFont(new Font("Monospaced", Font.PLAIN, 14));
		taInfo.setEditable(false);
		scrollPane.setViewportView(taInfo);

		popupMenu = new JPopupMenu();
		addPopup(taInfo, popupMenu);

		itSalvar = new JMenuItem("Salvar Reclamação");
		itSalvar.setEnabled(false);
		itSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (recSelec != null) {
					itSalvar.setEnabled(true);
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Escolha um lugar para salvar");
					File arquivo = new File(
							System.getProperty("user.home.") + "/Desktop/protocolo" + recSelec.getProtocolo() + ".txt");
					fc.setSelectedFile(arquivo);
					int retorno = fc.showSaveDialog(null);
					if (retorno == JFileChooser.APPROVE_OPTION) {
						try {
							FileWriter writer = new FileWriter(fc.getSelectedFile());
							writer.write(taInfo.getText());
							writer.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		popupMenu.add(itSalvar);

//		itExcluir = new JMenuItem("Excluir Reclamação");
//		if (TelaPrincipal.lbFuncionario.getText().contains("Motoboy")) {
//			itExcluir.setVisible(false);
//		}
//		itExcluir.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (recSelec != null) {
//					int resposta = JOptionPane.showConfirmDialog(null,
//							"Deseja realmente excluir a reclamação " + recSelec.getProtocolo() + "?",
//							"Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//					if (resposta == 0) {
//						try {
//							daoSac.excluir(recSelec);
//							reclamacao = daoSac.listar();
//							criarTabela();
//							limpar();
//						} catch (Exception e2) {
//							e2.printStackTrace();
//						}
//					}
//				}
//			}
//		});
//		itExcluir.setEnabled(false);
//		popupMenu.add(itExcluir);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(464, 45, 444, 503);
		contentPane.add(scrollPane_1);

		tbReclamacao = new JTable();
		scrollPane_1.setViewportView(tbReclamacao);
		//tbReclamacao.setBackground(Color.blue);
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					reclamacao = daoSac.procurar(textField.getText());
					criarTabela();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		textField.setBounds(464, 14, 437, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		try {
			carregaDadosTabela();
			criarTabela();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void carregaDadosTabela() {

		try {
			daoSac = new DAOSac();
			reclamacao = daoSac.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void criarTabela() {
		modelSAC = new RecTableModel(reclamacao);
		tbReclamacao.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tbReclamacao.setModel(modelSAC);
		tbReclamacao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReclamacao.getColumnModel().getColumn(0).setPreferredWidth(95);
		tbReclamacao.getColumnModel().getColumn(1).setPreferredWidth(70);
		tbReclamacao.getColumnModel().getColumn(2).setPreferredWidth(120);
		tbReclamacao.getColumnModel().getColumn(3).setPreferredWidth(200);
		tbReclamacao.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbReclamacao.getSelectedRow() >= 0) {
					recSelec = reclamacao.get(tbReclamacao.getSelectedRow());
					itSalvar.setEnabled(true);
//					itExcluir.setEnabled(true);
					popular();
				} else {
					recSelec = null;
				}
			}
		});
	}
	
	private void popular() {

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		 Calendar cal = Calendar.getInstance();

		String info = "\n\n------------------Leo Pizza Ltda-------------------\n\n";
		info += String.format("%-23s", "Protocolo nº: " + recSelec.getProtocolo()) + "DATA : " + df.format(recSelec.getData().getTime()) + "\n";
		info += "Cliente    : " + recSelec.getCliente().getNome() + "\n";
		info += "Assunto    : " + recSelec.getAssunto().name() + "\n";
		info += "Relato     : " + recSelec.getRelato() + "\n";
		info += "Feedback   : " + recSelec.getFeedback() + "\n";
		info += "Atendente  : " + recSelec.getNome_atendente() + "\n";
		info += "Entregador : " + recSelec.getEntregador();
		taInfo.setText(info);
	}

	private void limpar() {
		recSelec = null;
		taInfo.setText(null);
		tbReclamacao.clearSelection();
		itSalvar.setEnabled(false);
		//itExcluir.setEnabled(false);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}