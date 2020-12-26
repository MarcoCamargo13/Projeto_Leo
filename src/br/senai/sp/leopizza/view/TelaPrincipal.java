package br.senai.sp.leopizza.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.senai.sp.leopizza.dao.ConnectionFactory;
import br.senai.sp.leopizza.modelo.Funcionario;
import br.senai.sp.leopizza.util.ImageUtil;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class TelaPrincipal extends JFrame {

	private JDesktopPane contentPane;
	public static Funcionario func;
	// Declaração das telas
	private static CadCliente cadCliente;
	private static CadFuncionario cadFuncionario;
	private static CadProduto cadProduto;
	public static TelaPedido telaPedido;
	private static TelaGraficos telaGraficos;
	private static TelaSAC telaSAC;
	public static VerReclamacoes verReclamacoes;
	
	public static JButton btnCadFuncionario;
	public static JButton btnCadCliente;
	public static JButton btnCadProduto;
	public static JButton btnTelaPedido;
	public static JButton btnTelaGraficos;
	public static JButton btVerPedidos;
	public static JButton btSAC;
	public static JButton btRec;
	
	public static JLabel lbFuncionario;
	public static VerPedidos VerPedidos;

	public static JMenuItem itemCliente;
	public static JMenuItem itemProduto;
	public static JMenuItem itemFuncionario;
	public static JMenuItem itemPedido;
	public static JMenuItem itemVerPedidos;
	public static JMenuItem itemFechar;
	public static JMenuItem itemGraficos;
	public static JMenuItem itemSAC;
	public static JMenuItem itemRec;

	/*
	 * Launch the application.
	 * 
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { TelaPrincipal frame = new
	 * TelaPrincipal(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 * 
	 * /* Create the frame.
	 */
	public TelaPrincipal(Funcionario funcionario) {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ConnectionFactory.CloseConnection();
			}
		});

		func = funcionario;

		// Lógica de Permissão de acesso às funcionalidades

		setTitle("Sistema de Gest\u00E3o de Pizzaria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1228, 1100);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuCadastro = new JMenu("Cadastros");
		menuBar.add(menuCadastro);

		itemCliente = new JMenuItem("Cliente");
		itemCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCadCliente.doClick();
			}
		});
		menuCadastro.add(itemCliente);

		itemProduto = new JMenuItem("Produto");
		itemProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCadProduto.doClick();
			}
		});
		menuCadastro.add(itemProduto);

		itemFuncionario = new JMenuItem("Funcionário");
		itemFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCadFuncionario.doClick();
			}
		});
		menuCadastro.add(itemFuncionario);

		itemPedido = new JMenuItem("Pedido");
		itemPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTelaPedido.doClick();
			}
		});

		itemGraficos = new JMenuItem("Graficos");
		itemGraficos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTelaGraficos.doClick();
			}
		});
		menuCadastro.add(itemGraficos);

		itemVerPedidos = new JMenuItem("Ver Pedidos");
		itemVerPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btVerPedidos.doClick();
			}
		});
		menuCadastro.add(itemVerPedidos);
		
		itemSAC = new JMenuItem("Fazer Reclamação");
		itemSAC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSAC.doClick();
			}
		});
		menuCadastro.add(itemSAC);

		itemRec = new JMenuItem("Ver Reclamações");
		itemRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btRec.doClick();
			}
		});
		menuCadastro.add(itemRec);
		
		itemFechar = new JMenuItem("Fechar");
		itemFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectionFactory.CloseConnection();
				System.exit(0);
			}
		});
		menuCadastro.add(itemFechar);


		contentPane = new PainelPizza();
		contentPane.setBackground(new Color(250, 235, 215));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnCadCliente = new JButton("Cadastro de Cliente");
		btnCadCliente.setBorderPainted(false);
		btnCadCliente.setBorder(null);
		btnCadCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadCliente frame = new CadCliente();
				frame.setVisible(true);
			}
		});
		
		btnCadCliente.setForeground(Color.WHITE);
		btnCadCliente.setBackground(new Color(60, 179, 113));
		btnCadCliente.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		btnCadCliente.setBounds(115, 116, 200, 50);
		contentPane.add(btnCadCliente);

		btnCadProduto = new JButton("Cadastro de Produto");
		btnCadProduto.setBorderPainted(false);
		btnCadProduto.setBorder(null);
		btnCadProduto.setForeground(Color.WHITE);
		btnCadProduto.setBackground(new Color(255, 215, 0));
		btnCadProduto.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		btnCadProduto.setBounds(115, 177, 200, 50);
		contentPane.add(btnCadProduto);
		btnCadProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verificar se a tela é nula
				if (cadProduto == null) {
					cadProduto = new CadProduto();
					contentPane.add(cadProduto);
					cadProduto.setVisible(true);
				}
				cadProduto.show();
			}
		});


		btnCadFuncionario = new JButton("Cadastro de Funcionario");
		btnCadFuncionario.setBorderPainted(false);
		btnCadFuncionario.setBorder(null);
		btnCadFuncionario.setForeground(Color.WHITE);
		btnCadFuncionario.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		btnCadFuncionario.setBackground(new Color(30, 144, 255));
		btnCadFuncionario.setBounds(115, 55, 200, 50);
		contentPane.add(btnCadFuncionario);
		btnCadFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verificar se a tela é nula
				if (cadFuncionario == null) {
					cadFuncionario = new CadFuncionario();
					contentPane.add(cadFuncionario);
					cadFuncionario.setVisible(true);
				}
				cadFuncionario.show();
			}
		});


		btnTelaPedido = new JButton("Pedido");
		btnTelaPedido.setBorderPainted(false);
		btnTelaPedido.setBorder(null);
		btnTelaPedido.setForeground(Color.WHITE);
		btnTelaPedido.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		btnTelaPedido.setBackground(new Color(255, 0, 0));
		btnTelaPedido.setBounds(115, 238, 200, 50);
		contentPane.add(btnTelaPedido);
		btnTelaPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verificar se a tela é nula
				if (telaPedido == null) {
					telaPedido = new TelaPedido();
					contentPane.add(telaPedido);
					telaPedido.setVisible(true);
				}
				telaPedido.show();
			}
		});
		
		btnTelaGraficos = new JButton("Graficos");
		btnTelaGraficos.setBorderPainted(false);
		btnTelaGraficos.setBorder(null);
		btnTelaGraficos.setForeground(Color.WHITE);
		btnTelaGraficos.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		btnTelaGraficos.setBackground(new Color(90, 0, 0));
		btnTelaGraficos.setBounds(115, 360, 200, 50);
		contentPane.add(btnTelaGraficos);
		btnTelaGraficos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Verificar se a tela é nula
				if (telaGraficos == null) {
					telaGraficos = new TelaGraficos();
					contentPane.add(telaGraficos);
					telaGraficos.setVisible(true);
				}
				telaGraficos.show();
			}
		});

		btVerPedidos = new JButton("Ver Pedidos");
		btVerPedidos.setForeground(Color.WHITE);
		btVerPedidos.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		btVerPedidos.setBorderPainted(false);
		btVerPedidos.setBorder(null);
		btVerPedidos.setBackground(Color.PINK);
		btVerPedidos.setBounds(115, 299, 200, 50);
		contentPane.add(btVerPedidos);
		btVerPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (VerPedidos == null) {
					VerPedidos = new VerPedidos();
					contentPane.add(VerPedidos);
					VerPedidos.setVisible(true);
				}
				VerPedidos.show();
			}
		});
		
		btSAC = new JButton("Fazer Reclamação");
		btSAC.setForeground(Color.WHITE);
		btSAC.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		btSAC.setBorderPainted(false);
		btSAC.setBorder(null);
		btSAC.setBackground(Color.BLUE);
		btSAC.setBounds(115, 420, 200, 50);
		contentPane.add(btSAC);
		btSAC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaSAC frame = new TelaSAC();
				frame.setVisible(true);
			}
		});
		
		btRec = new JButton("Ver Reclamações");
		btRec.setForeground(Color.WHITE);
		btRec.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		btRec.setBorderPainted(false);
		btRec.setBorder(null);
		btRec.setBackground(Color.DARK_GRAY);
		btRec.setBounds(115, 480, 200, 50);
		contentPane.add(btRec);
		btRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (verReclamacoes == null) {
					verReclamacoes = new VerReclamacoes();
					contentPane.add(verReclamacoes);
					verReclamacoes.setVisible(true);
				}
				verReclamacoes.show();
			}
		});

		lbFuncionario = new JLabel("Funcionario");
		lbFuncionario.setHorizontalAlignment(SwingConstants.LEFT);
		lbFuncionario.setForeground(Color.GRAY);
		lbFuncionario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbFuncionario.setBounds(115, 11, 505, 20);
		contentPane.add(lbFuncionario);
		lbFuncionario.setText("Funcionário: " + TelaPrincipal.func.getNome().toString() + " - "
				+ TelaPrincipal.func.getCargo().toString());
		ImageIcon LeoPizza = new ImageIcon(getClass().getResource("/LeoPizza.png"));

//		JLabel lbFundo = new JLabel("New label");
//		lbFundo.setBounds(0, 0, 1920, 1200);
//		contentPane.add(lbFundo);
//		ImageIcon Pizza = new ImageIcon(getClass().getResource("/pizza.jpg"));
//		lbFundo.setIcon(ImageUtil.redimensiona(Pizza, lbFundo.getWidth(), lbFundo.getHeight()));

	
		btnTelaPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaPedido frame = new TelaPedido();
				frame.setVisible(true);
			}
		});
	}

	class PainelPizza extends JDesktopPane {

		@Override
		public void paintComponent(Graphics g) {

			try {
				// Extrair do Graphics g, uma referência para Graphics 2g
				Graphics2D g2d = (Graphics2D) g;
				// Carregar a imagem do background
				BufferedImage imagem = ImageIO.read(getClass().getResource("/pizza.jpg"));
				// Desenha a imagem
				g2d.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
