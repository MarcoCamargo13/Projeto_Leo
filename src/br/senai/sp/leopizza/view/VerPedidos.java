package br.senai.sp.leopizza.view;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.senai.sp.leopizza.dao.DAOPedido;
import br.senai.sp.leopizza.modelo.ItemPedido;
import br.senai.sp.leopizza.modelo.Pedido;
import br.senai.sp.leopizza.tablemodel.PedidoTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VerPedidos extends JInternalFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable tbPedidos;
	// DAO
	private DAOPedido dao;
	// Lista de Pedidos
	private List<Pedido> pedidos;
	// Variável para o pedido selecionado
	private Pedido pedSelec;
	private JScrollPane scrollPane_1;
	private JTextArea taInfo;
	private JPopupMenu popupMenu;
	private JMenuItem itSalvar;
	public static JMenuItem itExcluir;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VerPedidos frame = new VerPedidos();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public VerPedidos() {
		setClosable(true);
		setIconifiable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Pedidos");
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

		itSalvar = new JMenuItem("Salvar Pedido");
		itSalvar.setEnabled(false);
		itSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verifica se existe um pedido selecionado
				if (pedSelec != null) {
					// Habilita a opção salvar
					itSalvar.setEnabled(true);
					// Criar o FileChooser
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Escolha um lugar para salvar");
					// Criar um File com a localização e nome do arquivo
					File arquivo = new File(
							System.getProperty("user.home.") + "/Desktop/pedido" + pedSelec.getNumero() + ".txt");
					// Definir o arquivo como arquivo selecionado no file chooser
					fc.setSelectedFile(arquivo);
					// Abre o Dialog e pega o retorno
					int retorno = fc.showSaveDialog(null);
					// Verifica se foi clicado "Salvar" no dialog
					if (retorno == JFileChooser.APPROVE_OPTION) {
						// Criar um escritor de arquivos
						try {
							FileWriter writer = new FileWriter(fc.getSelectedFile());
							// escreve no arquivo
							writer.write(taInfo.getText());
							// Fecha o escritor
							writer.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		popupMenu.add(itSalvar);

		itExcluir = new JMenuItem("Excluir Pedido");
		if (TelaPrincipal.lbFuncionario.getText().contains("Motoboy")) {
			itExcluir.setVisible(false);
		}
		itExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verifica se tem pedido selecionado
				if (pedSelec != null) {
					// Exibe o ConfirmDialog e guarda a resposta
					int resposta = JOptionPane.showConfirmDialog(null,
							"Deseja realmente excluir o pedido " + pedSelec.getNumero() + "?", "Confirmar Exclusão",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					// Se a resposta for sim
					if (resposta == 0) {
						// Exclui o pedido
						try {
							// Exclui o pedido
							dao.excluir(pedSelec);
							// Recarrega a lista
							pedidos = dao.listar();
							// Recria a tabela
							criarTabela();
							// Limpa a tela
							limpar();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		});
		itExcluir.setEnabled(false);
		popupMenu.add(itExcluir);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(464, 45, 444, 503);
		contentPane.add(scrollPane_1);

		tbPedidos = new JTable();
		scrollPane_1.setViewportView(tbPedidos);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Carregar a lista através do método procurar da DAO
				try {
					pedidos = dao.procurar(textField.getText());
					// Criar a tabela
					criarTabela();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		textField.setBounds(464, 14, 437, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		carregaTabela();

	}

	public void carregaTabela() {
		try {
			// Instancia a DAO
			dao = new DAOPedido();
			// Carrega a lista de pedidos
			pedidos = dao.listar();
			criarTabela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void criarTabela() {
		PedidoTableModel modelPedido = new PedidoTableModel(pedidos);
		// Tira o ajuste automático
		tbPedidos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// Aplica o tablemodel
		tbPedidos.setModel(modelPedido);
		// Define seleção simples na tabela
		tbPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Ajusta a largura das colunas
		tbPedidos.getColumnModel().getColumn(0).setPreferredWidth(40);
		tbPedidos.getColumnModel().getColumn(1).setPreferredWidth(120);
		tbPedidos.getColumnModel().getColumn(2).setPreferredWidth(200);
		tbPedidos.getColumnModel().getColumn(3).setPreferredWidth(80);
		// Listener para pedido selecionado na tabela
		tbPedidos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Verifica se existe uma linha selecionada
				if (tbPedidos.getSelectedRow() >= 0) {
					pedSelec = pedidos.get(tbPedidos.getSelectedRow());
					// Habilita as opções do popup menu
					itSalvar.setEnabled(true);
					itExcluir.setEnabled(true);
					// Popula a área de texto com os dados do pedido
					popular();
				} else {
					pedSelec = null;
				}
			}
		});
	}

	public void popular() {
		// String com as informações do pedido
		String info = "Pizza Leo Ltda\n\nPedido nº: " + pedSelec.getNumero() + "\n";
		info += "Cliente: " + pedSelec.getCliente().getNome() + "\n";
		info += "Telefone: " + pedSelec.getCliente().getTelefone() + "\n";
		if (pedSelec.isRetirada()) {
			info += "Cliente vai retirar\n";
		} else {
			info += "Endereço de entrega: " + pedSelec.getEndEntrega() + "\n";
			info += String.format("Taxa de Entrega: R$ %5.2f%n", pedSelec.getTxEntrega());
		}
		info += "\nITENS\n";
		// Cabeçalho da tabela de itens
		info += String.format("%-5s|%-30s|%-30s|%-10s%n", "QTD", "Produto", "Observação", "Total");
		for (ItemPedido i : pedSelec.getItens()) {
			info += String.format("%-5d|%-30s|%-30s|R$ %5.2f%n", i.getQtd(), i.getProduto().getNome(),
					i.getObservacao(), i.getTotal());
		}
		info += String.format("%nValor Total: R$ %5.2f%n", pedSelec.getvTotal());
		info += String.format("Troco para: R$ %5.2f%n", pedSelec.getTroco());
		if (!pedSelec.getObservacao().isEmpty()) {
			info += "Observação: " + pedSelec.getObservacao() + "\n";
		}
		info += "\nAtendente    : " + pedSelec.getFuncionario().getNome();
		info += "\nEntregue por : " + pedSelec.getEntregador() + "\n\n";

		taInfo.setText(info);
	}

	private void limpar() {
		// Zera a variável pedSelec
		pedSelec = null;
		// Limpa a TextArea
		taInfo.setText(null);
		// Desseleciona o pedido
		tbPedidos.clearSelection();
		// Inabilita os dois itens do popup menu
		itSalvar.setEnabled(false);
		itExcluir.setEnabled(false);
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
