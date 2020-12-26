package br.senai.sp.leopizza.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.senai.sp.leopizza.util.ImageUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.KeyboardFocusManager;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;

import br.senai.sp.leopizza.dao.DAOProduto;
import br.senai.sp.leopizza.modelo.Cliente;
import br.senai.sp.leopizza.modelo.Produto;
import br.senai.sp.leopizza.modelo.TipoProduto;
import br.senai.sp.leopizza.tablemodel.ClienteTableModel;
import br.senai.sp.leopizza.tablemodel.ProdutoTableModel;

import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.JScrollBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;

import javax.swing.UIManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CadProduto extends JInternalFrame {

	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfPreco;
	private JTextField tfBuscar;
	private JTextField tfCodigo;
	private JTable tbProdutos;
	private JTextArea taDescricao;
	private JComboBox cmbTipo;
	private JLabel lbImagem;
	private JCheckBox chckbxPromocao;
	private JButton btnBuscar;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnSalvar;
	private JScrollPane spTabela;
	private Produto produto;
	private JComboBox cmbTipoBusca;
	// variável para armazenar o vetor de bytes da foto
	private byte[] imgProduto;
	private DAOProduto dao;
	private List<Produto> produtos;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CadProduto() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				try {
					produtos = dao.listar();
					CriarTabela();
					limpar();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		setIconifiable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		dao = new DAOProduto();

		setTitle("Cadastro de Produtos");
		setBounds(100, 100, 902, 580);
		contentPane = new JPanel();

		contentPane.setBackground(new Color(250, 235, 215));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("C\u00F3digo");
		lblNewLabel.setFocusable(false);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 289, 46, 25);
		contentPane.add(lblNewLabel);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFocusable(false);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(10, 361, 46, 25);
		contentPane.add(lblNome);

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFocusable(false);
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipo.setBounds(10, 325, 46, 25);
		contentPane.add(lblTipo);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setFocusable(false);
		lblDescrio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescrio.setBounds(10, 396, 76, 25);
		contentPane.add(lblDescrio);

		JLabel lblPreo = new JLabel("Pre\u00E7o");
		lblPreo.setFocusable(false);
		lblPreo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPreo.setBounds(10, 460, 76, 25);
		contentPane.add(lblPreo);

		tfNome = new JTextField();
		tfNome.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfNome.setBounds(78, 365, 313, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);

		cmbTipo = new JComboBox();
		cmbTipo.setBackground(new Color(255, 255, 255));
		cmbTipo.setModel(new DefaultComboBoxModel(TipoProduto.values()));
		cmbTipo.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cmbTipo.setBounds(78, 329, 313, 20);
		cmbTipo.setSelectedIndex(-1);
		contentPane.add(cmbTipo);

		chckbxPromocao = new JCheckBox("Promoção");
		chckbxPromocao.setBackground(new Color(250, 235, 215));
		chckbxPromocao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxPromocao.setBounds(294, 461, 97, 23);
		contentPane.add(chckbxPromocao);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setFocusable(false);
		scrollPane.setBounds(78, 397, 313, 53);
		contentPane.add(scrollPane);

		taDescricao = new JTextArea();
		taDescricao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		taDescricao.setBorder(new LineBorder(Color.LIGHT_GRAY));
		taDescricao.setLineWrap(true);
		scrollPane.setViewportView(taDescricao);
		Set<KeyStroke> strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("pressed TAB")));
		taDescricao.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, strokes);
		strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("shift pressed TAB")));
		taDescricao.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, strokes);

		tfPreco = new JTextField();
		tfPreco.setHorizontalAlignment(SwingConstants.RIGHT);
		tfPreco.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfPreco.setColumns(10);
		tfPreco.setBounds(78, 464, 97, 20);
		contentPane.add(tfPreco);

		tfPreco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verifica se não é um dígito (caractere numérico)
				if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '.') {
					// "consome" o caractere (não aceita caractere inválido, só número e ponto)
					e.consume();
				}
				// verifica se o caracter é ponto e já tem ponto na caixa de texto
				if (e.getKeyChar() == '.' && tfPreco.getText().contains(".")) {
					e.consume();
				}
				// limita a 6 caracteres
				if (tfPreco.getText().length() == 6) {
					e.consume();
				}
			}
		});

		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setMnemonic('L');
		btnLimpar.setForeground(Color.WHITE);
		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLimpar.setFocusable(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBackground(new Color(255, 140, 0));
		btnLimpar.setBounds(98, 496, 91, 32);
		contentPane.add(btnLimpar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (produto == null) {
					JOptionPane.showMessageDialog(null, "Selecione um produto para excluir", "Selecione",
							JOptionPane.WARNING_MESSAGE);
				} else {
					java.awt.Toolkit.getDefaultToolkit().beep();
					int botao = JOptionPane.showConfirmDialog(null,
							"Deseja excluir o produto " + produto.getNome() + "?", "Confirmar exclusão",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (botao == 0) {
						try {
							dao.excluir(produto);
							produtos = dao.listar();
							CriarTabela();
							limpar();
						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}
			}
		});
		btnExcluir.setMnemonic('E');
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluir.setFocusable(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBackground(Color.RED);
		btnExcluir.setBounds(199, 496, 91, 32);
		contentPane.add(btnExcluir);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verifica se os campos obrigatórios estão preenchidos
				if (tfNome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informe o nome do produto", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					// Coloca o foco na caixa de texto
					tfNome.requestFocus();
				} else if (tfPreco.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Informe o preço", "Aviso", JOptionPane.WARNING_MESSAGE);
					tfNome.requestFocus();
				} else if (cmbTipo.getSelectedIndex() < 0) {
					JOptionPane.showMessageDialog(null, "Informe o tipo", "Aviso", JOptionPane.WARNING_MESSAGE);
					cmbTipo.requestFocus();
				} else {
					if (produto == null) {
						// Instancia o objeto produto
						produto = new Produto();
					}
					// popula o objeto produto
					produto.setNome(tfNome.getText());
					produto.setTipo((TipoProduto) cmbTipo.getSelectedItem());
					produto.setDescricao(taDescricao.getText());
					produto.setPreco(Double.parseDouble(tfPreco.getText()));
					produto.setPromocao(chckbxPromocao.isSelected());
					produto.setImagem(imgProduto);

					try {
						if (produto.getId() == 0) {
							dao.inserir(produto);
						} else {
							dao.atualizar(produto);
						}

						produtos = dao.listar();
						CriarTabela();
						limpar();

					} catch (SQLException erro) {
						erro.printStackTrace();
						JOptionPane.showMessageDialog(null, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSalvar.setMnemonic('S');
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setBorder(null);
		btnSalvar.setBackground(new Color(34, 139, 34));
		btnSalvar.setBounds(300, 496, 91, 32);
		contentPane.add(btnSalvar);

		tfBuscar = new JTextField();
		tfBuscar.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfBuscar.setColumns(10);
		tfBuscar.setBounds(624, 11, 215, 20);
		contentPane.add(tfBuscar);

		// Cria um ImageIcon através da imagem dentro da pasta imagens
		ImageIcon iconeLupa = new ImageIcon(getClass().getResource("/iconeLupa.png"));

		spTabela = new JScrollPane();
		spTabela.setBorder(new LineBorder(Color.LIGHT_GRAY));
		spTabela.setFocusable(false);
		spTabela.setBounds(401, 42, 475, 486);
		contentPane.add(spTabela);

		tbProdutos = new JTable();
		tbProdutos.setFocusable(false);
		spTabela.setViewportView(tbProdutos);

		try {
			produtos = dao.listar();
			CriarTabela();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível carregar os produtos", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}

		lbImagem = new JLabel("");
		lbImagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// verifica se foi um duplo clique
				if (e.getClickCount() == 2) {
					// Cria um JfileChooser para escolher uma imagem
					JFileChooser fc = new JFileChooser();
					// Define a pasta de abertura do JFileChooser
					fc.setCurrentDirectory(new File(System.getProperty("user.home")));
					// cria um filtro para especificar apenas arquivos do tipo imagem
					FileFilter filtroImg = new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes());
					// Aplica o filtro no FileChooser
					fc.setFileFilter(filtroImg);
					// variável int para guardar o retorno fo FileChooser
					int retorno = fc.showOpenDialog(null);
					// Verifica se foi clicado o botão "open"
					if (retorno == JFileChooser.APPROVE_OPTION) {
						// recupera o arquivo selecionado
						File arqSelec = fc.getSelectedFile();
						try {
							// converte o File para Imagem
							BufferedImage foto = ImageIO.read(arqSelec);
							// cria um ImageIcon através do BufferedImage
							ImageIcon imgFoto = new ImageIcon(foto);
							// joga na label a imagem redimensionada
							lbImagem.setIcon(
									ImageUtil.redimensiona(imgFoto, lbImagem.getWidth(), lbImagem.getHeight()));
							// converte a imagem em vetor de bytes
							ByteArrayOutputStream outStream = new ByteArrayOutputStream();
							ImageIO.write(foto, "png", outStream);
							imgProduto = outStream.toByteArray();
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(null, e2.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		lbImagem.setToolTipText("Clique 2 vezes para selecionar a imagem");
		lbImagem.setOpaque(true);
		lbImagem.setFocusable(false);
		lbImagem.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbImagem.setBackground(SystemColor.windowBorder);
		lbImagem.setBounds(78, 11, 313, 271);
		contentPane.add(lbImagem);

		tfCodigo = new JTextField();
		tfCodigo.setBackground(new Color(250, 235, 215));
		tfCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		tfCodigo.setFocusable(false);
		tfCodigo.setEditable(false);
		tfCodigo.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tfCodigo.setColumns(10);
		tfCodigo.setBounds(78, 293, 56, 21);
		contentPane.add(tfCodigo);

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (cmbTipoBusca.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Selecione o tipo de produto", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					} else {
						produtos = dao.buscar(tfBuscar.getText(), (TipoProduto) cmbTipoBusca.getSelectedItem());
						CriarTabela();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBuscar.setFocusable(false);
		btnBuscar.setBorder(null);
		btnBuscar.setBackground(new Color(250, 235, 215));
		btnBuscar.setBounds(844, 11, 32, 29);
		contentPane.add(btnBuscar);

		// Cria um ImageIcon através da imagem dentro da pasta imagens
		//ImageIcon LeoPizza = new ImageIcon(getClass().getResource("/LeoPizza.png"));
		//setIconImage(LeoPizza.getImage());
		// Define o ícone do botão redimensionando o ícone criado anteriormente
		btnBuscar.setIcon(ImageUtil.redimensiona(iconeLupa, btnBuscar.getWidth(), btnBuscar.getHeight()));

		cmbTipoBusca = new JComboBox();
		cmbTipoBusca.setBackground(new Color(255, 255, 255));
		cmbTipoBusca.setModel(new DefaultComboBoxModel(TipoProduto.values()));
		cmbTipoBusca.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cmbTipoBusca.setBounds(401, 11, 213, 20);
		cmbTipoBusca.setSelectedIndex(-1);
		contentPane.add(cmbTipoBusca);
	}

	private void limpar() {
		tfCodigo.setText(null);
		tfNome.setText(null);
		taDescricao.setText(null);
		tfPreco.setText(null);
		cmbTipo.setSelectedIndex(-1);
		cmbTipoBusca.setSelectedIndex(-1);
		chckbxPromocao.setSelected(false);
		imgProduto = null;
		lbImagem.setIcon(null);
		produto = null;
		tfNome.requestFocus();
		try {
			produtos = dao.listar();
			CriarTabela();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void CriarTabela() {
		ProdutoTableModel model = new ProdutoTableModel(produtos);
		tbProdutos.setModel(model);
		tbProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProdutos.getColumnModel().getColumn(0).setPreferredWidth(220);
		tbProdutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (tbProdutos.getSelectedRow() >= 0) {
					produto = produtos.get(tbProdutos.getSelectedRow());
					popular();
				}

			}
		});
	}

	private void popular() {
		imgProduto = produto.getImagem();
		if (imgProduto != null) {
			ImageIcon icon = new ImageIcon(imgProduto);
			lbImagem.setIcon(ImageUtil.redimensiona(icon, lbImagem.getWidth(), lbImagem.getHeight()));
		} else {
			lbImagem.setIcon(null);
		}

		tfNome.setText(produto.getNome());
		taDescricao.setText(produto.getDescricao());
		tfPreco.setText(String.valueOf(produto.getPreco()));
		cmbTipo.setSelectedItem(produto.getTipo());
		chckbxPromocao.setSelected(produto.isPromocao());
		tfCodigo.setText(produto.getId() + "");
	}

}
