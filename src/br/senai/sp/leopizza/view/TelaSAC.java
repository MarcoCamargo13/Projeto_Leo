package br.senai.sp.leopizza.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import br.senai.sp.leopizza.dao.DAOCliente;
import br.senai.sp.leopizza.dao.DAOPedido;
import br.senai.sp.leopizza.dao.DAOSac;
import br.senai.sp.leopizza.modelo.Assunto;
import br.senai.sp.leopizza.modelo.Cliente;
import br.senai.sp.leopizza.modelo.Funcionario;
import br.senai.sp.leopizza.modelo.ItemPedido;
import br.senai.sp.leopizza.modelo.Manifestacao;
import br.senai.sp.leopizza.modelo.Pedido;
import br.senai.sp.leopizza.modelo.SAC;
import br.senai.sp.leopizza.tablemodel.ItemPedidoTableModel;
import br.senai.sp.leopizza.tablemodel.PedidoTableModel;
import br.senai.sp.leopizza.tablemodel.SACItemsTableModel;
import br.senai.sp.leopizza.tablemodel.SACTableModel;
import br.senai.sp.leopizza.util.CoresJTable;
import br.senai.sp.leopizza.util.EnviarEmail;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.Font;
import java.awt.KeyboardFocusManager;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TelaSAC extends JFrame {

	private JPanel contentPane;
	private JTextField tfTelefone;
	private JTextField tfNome;
	private JTable tbPedidos;
	private JTable tbItens;
	private Cliente cliente;
	private DAOPedido daop;
	private DAOCliente daoCliente;
	private DAOSac dao;
	private SAC sac;
	private JTextArea taEndereco;
	private JComboBox cmbAssunto;
	private JTextArea taRelato;
	private JTextArea taFeedback;
	private JTextField tfData;
	private JButton Salvar;
	private JButton btCadastrar;
	private JButton btLimpar;
	private JTextField tfFuncionario;
	private SAC protocolo;
	private List<Pedido> pedidos;
	private Pedido pedSelec;
	private List<ItemPedido> itens = new ArrayList<>();
	private List<ItemPedido> itemSelec = new ArrayList<>();
	SACTableModel modelPedido;
	SACItemsTableModel modelItem;
	private DefaultTableCellRenderer renderCenter;

	public TelaSAC() {
		setTitle("SAC");
		setBounds(100, 100, 1110, 398);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.setBackground(new Color(250, 235, 215));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		dao = new DAOSac();
		daoCliente = new DAOCliente();
		daop = new DAOPedido();

		JLabel lbTelefone = new JLabel("Telefone");
		lbTelefone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbTelefone.setBounds(10, 11, 67, 14);
		contentPane.add(lbTelefone);

		JLabel lbNome = new JLabel("Nome");
		lbNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbNome.setBounds(192, 11, 44, 14);
		contentPane.add(lbNome);

		JLabel lbEndereco = new JLabel("Endere\u00E7o");
		lbEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbEndereco.setBounds(10, 36, 67, 14);
		contentPane.add(lbEndereco);

		tfTelefone = new JTextField();
		tfTelefone.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					cliente = daoCliente.buscarPorTel(tfTelefone.getText().trim());
					if (cliente == null) {
						int opcao = JOptionPane.showConfirmDialog(null, "Cliente não encontrado. Deseja cadastrá-lo?",
								"Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (opcao == 0) {
							CadCliente frame = new CadCliente(tfTelefone.getText());
							frame.setVisible(true);
						}
					} else {
						tfNome.setText(cliente.getNome());
						taEndereco.setText(cliente.getEndereco());
						tbPedidos.setVisible(true);
						tbItens.setVisible(true);
						cmbAssunto.setEnabled(true);
						try {
							pedidos = daop.procurar(cliente.getNome());
							criarTabela();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if (tbPedidos.getSelectedRow() >= 0) {
							pedSelec = pedidos.get(tbPedidos.getSelectedRow());
							daop.listarItem(pedSelec.getNumero());
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		tfTelefone.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {

				if (!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
				if (tfTelefone.getText().length() == 11) {
					e.consume();
				}

			}
		});

		tfTelefone.setToolTipText("Digite o telefone e pressione ENTER para buscar");
		tfTelefone.setBounds(94, 8, 88, 20);
		contentPane.add(tfTelefone);
		tfTelefone.setColumns(10);

		tfNome = new JTextField();
		tfNome.setEditable(false);
		tfNome.setBounds(238, 8, 319, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);

		JScrollPane spEndereco = new JScrollPane();
		spEndereco.setBounds(95, 36, 462, 45);
		contentPane.add(spEndereco);

		taEndereco = new JTextArea();
		taEndereco.setEditable(false);
		taEndereco.setLineWrap(true);
		spEndereco.setViewportView(taEndereco);

		JLabel lbAssunto = new JLabel("Assunto");
		lbAssunto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbAssunto.setBounds(10, 94, 78, 14);
		contentPane.add(lbAssunto);

		cmbAssunto = new JComboBox();
		cmbAssunto.setEnabled(false);
		cmbAssunto.setModel(new DefaultComboBoxModel(Assunto.values()));
		cmbAssunto.setSelectedIndex(-1);
		cmbAssunto.setBounds(94, 92, 238, 22);
		contentPane.add(cmbAssunto);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(94, 125, 241, 100);
		contentPane.add(scrollPane);

		taRelato = new JTextArea();
		taRelato.setLineWrap(true);
		scrollPane.setViewportView(taRelato);
		Set<KeyStroke> strokes1 = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("pressed TAB")));
		taRelato.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, strokes1);
		strokes1 = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("shift pressed TAB")));
		taRelato.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, strokes1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(94, 236, 241, 102);
		contentPane.add(scrollPane_1);

		taFeedback = new JTextArea();
		taFeedback.setLineWrap(true);
		scrollPane_1.setViewportView(taFeedback);
		Set<KeyStroke> strokes2 = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("pressed TAB")));
		taFeedback.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, strokes2);
		strokes2 = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("shift pressed TAB")));
		taFeedback.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, strokes2);

		JLabel lbRelato = new JLabel("Relato");
		lbRelato.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbRelato.setBounds(10, 129, 78, 14);
		contentPane.add(lbRelato);

		JLabel lbFeedback = new JLabel("Feedback");
		lbFeedback.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbFeedback.setBounds(10, 242, 78, 14);
		contentPane.add(lbFeedback);

		JLabel lblData = new JLabel("Data");
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(908, 11, 65, 17);
		contentPane.add(lblData);

		tfData = new JTextField();
		tfData.setOpaque(false);
		tfData.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfData.setForeground(Color.BLACK);
		tfData.setBorder(null);
		tfData.setHorizontalAlignment(SwingConstants.RIGHT);
		tfData.setEditable(false);
		tfData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())));
		tfData.setColumns(10);
		tfData.setBounds(984, 11, 88, 20);
		contentPane.add(tfData);

//		Salvar = new JButton("Salvar");
//		Salvar.setBorder(null);
//		Salvar.setForeground(Color.WHITE);
//		Salvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		Salvar.setBackground(new Color(34, 139, 34));
//		Salvar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					if (sac == null) {
//						JOptionPane.showMessageDialog(null, "Não existe manifestação a ser salva", "Sem informações",
//								JOptionPane.WARNING_MESSAGE);
//					} else {
//						dao.atualizar(sac);
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		Salvar.setMnemonic('S');
//		Salvar.setBounds(882, 315, 91, 32);
//		contentPane.add(Salvar);

		btCadastrar = new JButton("Cadastrar");
		btCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btCadastrar.setForeground(Color.WHITE);
		btCadastrar.setBackground(Color.BLUE);
		btCadastrar.setBorder(null);
		btCadastrar.setMnemonic('C');
		btCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cliente == null || pedSelec == null) {
					JOptionPane.showMessageDialog(null, "Informe o cliente e o evento para realizar a manifestação",
							"Cliente não informado", JOptionPane.ERROR_MESSAGE);
					tfTelefone.requestFocus();
				} else {
					sac = new SAC();
					sac.setCliente(cliente);
					sac.setAssunto((Assunto) cmbAssunto.getSelectedItem());
					sac.setRelato(taRelato.getText());
					sac.setFeedback(taFeedback.getText());
					sac.setFuncionario(tfFuncionario.getText());
					sac.setId_pedido(pedSelec.getNumero());
					sac.setEntregador(pedSelec.getEntregador());
					sac.setNome_atendente(pedSelec.getFuncionario().getNome());
					sac.setNome_cliente(pedSelec.getCliente().getNome());
					Funcionario f = new Funcionario();
					f.setCpf(pedSelec.getFuncionario().getCpf());
					sac.setFuncionario(f);
					try {
						dao.inserir(sac);

						JOptionPane.showMessageDialog(null, "Manifestação cadastrada com sucesso!",
								"Manifestação Cadastrada", JOptionPane.INFORMATION_MESSAGE);

						if (TelaPrincipal.verReclamacoes != null) {
							TelaPrincipal.verReclamacoes.carregaDadosTabela();
							TelaPrincipal.verReclamacoes.criarTabela();
						}

						// flag da reclamacao
						dao.atualizarReclamacaoPedido(pedSelec.getNumero());
//						System.out.println(sac.getProtocolo() + " == sac.getProtocolo()");
//						System.out.println(pedSelec.getNumero() + " == pedSelec.getNumero()");

						geraEmail();

						limpar();

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Erro ao cadastrar a manifestação", "Erro",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			}
		});
		btCadastrar.setBounds(981, 315, 91, 32);
		contentPane.add(btCadastrar);

		btLimpar = new JButton("Limpar");
		btLimpar.setBorder(null);
		btLimpar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btLimpar.setBackground(new Color(255, 140, 0));
		btLimpar.setForeground(Color.WHITE);
		btLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpar();
			}
		});
		btLimpar.setMnemonic('L');
		btLimpar.setBounds(851, 315, 91, 32);
		contentPane.add(btLimpar);

		JLabel lbPedido = new JLabel("Pedidos");
		lbPedido.setHorizontalAlignment(SwingConstants.CENTER);
		lbPedido.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbPedido.setBounds(345, 94, 336, 14);
		contentPane.add(lbPedido);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(345, 121, 336, 217);
		contentPane.add(scrollPane_2);

		tbPedidos = new JTable();
		scrollPane_2.setViewportView(tbPedidos);
		tbPedidos.setDefaultRenderer(Object.class, new MyTableCellRenderer());

		JScrollPane scrollPane_2_1 = new JScrollPane();
		scrollPane_2_1.setBounds(691, 121, 382, 182);
		contentPane.add(scrollPane_2_1);

		tbItens = new JTable();
		scrollPane_2_1.setViewportView(tbItens);
		tbItens.setDefaultRenderer(Object.class, new MyTableCellRenderer());

		JLabel lbItem = new JLabel("Itens do Pedido");
		lbItem.setHorizontalAlignment(SwingConstants.CENTER);
		lbItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbItem.setBounds(691, 94, 381, 14);
		contentPane.add(lbItem);

		JLabel lbFunc = new JLabel("Funcion\u00E1rio");
		lbFunc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbFunc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbFunc.setBounds(868, 34, 107, 19);
		contentPane.add(lbFunc);

		tfFuncionario = new JTextField();
		tfFuncionario.setOpaque(false);
		tfFuncionario.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfFuncionario.setForeground(Color.BLACK);
		tfFuncionario.setBorder(null);
		tfFuncionario.setHorizontalAlignment(SwingConstants.RIGHT);
		tfFuncionario.setText(TelaPrincipal.func.getCpf());
		tfFuncionario.setEditable(false);
		tfFuncionario.setColumns(10);
		tfFuncionario.setBounds(983, 36, 89, 20);
		contentPane.add(tfFuncionario);
	}

	private void limpar() {
		cliente = null;
		tfTelefone.setText(null);
		tfNome.setText(null);
		taEndereco.setText(null);
		cmbAssunto.setSelectedIndex(-1);
		cmbAssunto.setEnabled(false);
		taRelato.setText(null);
		taFeedback.setText(null);
		pedSelec = null;
		tbPedidos.setVisible(false);
		tbItens.setVisible(false);
		//criarTabela();
		//criarTabelaItens();
	}

	private void geraProtocolo(SAC sac) throws IOException {
		FileWriter arq = new FileWriter("Protocolo" + protocolo.getProtocolo() + ".txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.println("Leo Pizza Ltda");
		gravarArq.println("\nProtocolo nº: " + protocolo.getProtocolo());
		gravarArq.println("\nData: " + sac.getData());
		gravarArq.println("Cliente: " + sac.getCliente().getNome());
		gravarArq.println("Telefone: " + sac.getCliente().getTelefone());
		gravarArq.println("Endereço: " + sac.getCliente().getEndereco());
		gravarArq.println("Manifestação: " + sac.getManifestacao().toString());
		gravarArq.println("Assunto: " + sac.getAssunto().toString());
		gravarArq.println("Prazo: " + sac.getPrazo());
		gravarArq.println("Status: " + sac.getStatus());
		gravarArq.println("Status: " + sac.getFuncionario());
		// gravarArq.println("\nFuncionário: " + TelaPrincipal.func.getNome());
		arq.close();
		Runtime.getRuntime().exec("notepad Protocolo" + protocolo.getProtocolo() + ".txt");
	}

	private void criarTabela() {
		modelPedido = new SACTableModel(pedidos);
		tbPedidos.setModel(modelPedido);
		tbPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbPedidos.getColumnModel().getColumn(0).setPreferredWidth(40);
		tbPedidos.getColumnModel().getColumn(1).setPreferredWidth(120);
		tbPedidos.getColumnModel().getColumn(2).setPreferredWidth(40);

		tbPedidos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbPedidos.getSelectedRow() >= 0) {
					pedSelec = pedidos.get(tbPedidos.getSelectedRow());
					criarTabelaItens();
				} else {
					pedSelec = null;
				}
			}
		});
		getContentPane().add(defineCor(modelPedido));
	}

	private JComponent defineCor(TableModel tableModel) {
		JTable jTable = new JTable(tableModel) {

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				component.setBackground(Color.RED);
				return component;

			}
		};
		return new JScrollPane(jTable);

	}

	private void criarTabelaItens() {
		itens = pedSelec.getItens();
		modelItem = new SACItemsTableModel(itens);
		tbItens.setModel(modelItem);
		tbItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbItens.getColumnModel().getColumn(0).setPreferredWidth(30);
		tbItens.getColumnModel().getColumn(1).setPreferredWidth(200);
		tbItens.getColumnModel().getColumn(2).setPreferredWidth(80);
		tbItens.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbItens.getSelectedRow() >= 0 && pedSelec != null) {
					itemSelec.add(itens.get(tbItens.getSelectedRow()));
					System.out.println(itemSelec.get(itemSelec.size() - 1).toString());
					pedSelec = null;
					return;
				} else {
					itemSelec = null;
				}
			}
		});
	}

	private void geraEmail() {
		Runnable email = new EnviarEmail("Pizza Leo Ltda - Reclamação nº: " + sac.getProtocolo(), mensagemEmail(),
				cliente.getEmail());
		Thread threadEmail = new Thread(email);
		threadEmail.start();

	}

	private String mensagemEmail() {
		String msg = "\nPedimos desculpas pelo transtorno Sr./Sra. " + sac.getCliente().getNome() + "!!\n\n";
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date data = new Date();
		msg += String.format("%-30s", "Pizza Leo Ltda") + "Data/Hora : " + df.format(data);
		msg += "\nReclamação nº: " + sac.getProtocolo();
		msg += "\nCliente  : " + sac.getCliente().getNome();
		msg += "\nTelefone : " + sac.getCliente().getTelefone();
		msg += "\n\nPedido : " + sac.getId_pedido();
		msg += "\nDescrição da Reclamação : ";
		msg += "\n" + sac.getRelato();
		msg += "\nAtendente  : " + sac.getNome_atendente();
		msg += "\nEntregador : " + sac.getEntregador();

		return msg;

	}

	public class MyTableCellRenderer implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JTextField editor = new JTextField();
			if (column == 1) {
				editor.setHorizontalAlignment(SwingConstants.LEFT);
			} else {
				editor.setHorizontalAlignment(SwingConstants.CENTER);
			}
			if (value != null)
				editor.setText(value.toString());
			try {
				SACTableModel model = (SACTableModel) table.getModel();
				if (!isSelected) {
					editor.setBackground(model.getRowColor(row));
				} else {
					editor.setBackground(new Color(102, 178, 255));
				}
				return editor;
			} catch (Exception e) {
				editor.setBackground(new Color(102, 178, 255));

				return editor;
			}
		}
	}
}
