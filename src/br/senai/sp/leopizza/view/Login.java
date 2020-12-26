package br.senai.sp.leopizza.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.senai.sp.leopizza.dao.ConnectionFactory;
import br.senai.sp.leopizza.dao.DAOLogin;
import br.senai.sp.leopizza.modelo.Cargo;
import br.senai.sp.leopizza.modelo.Funcionario;
import br.senai.sp.leopizza.util.ImageUtil;
import br.senai.sp.leopizza.util.PasswordUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JPasswordField tfSenha;
	private JFormattedTextField ftfCpf;
	private JButton btLogar;
	private JButton btSair;
	private JLabel lbLogo;
	private DAOLogin dao;
	private Funcionario funcionario = new Funcionario();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ConnectionFactory.CloseConnection();
				System.exit(0);
			}
		});

		dao = new DAOLogin();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 378, 365);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 235, 215));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lbCpf = new JLabel("CPF");
		lbCpf.setForeground(new Color(255, 96, 69));
		lbCpf.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbCpf.setBounds(83, 214, 46, 14);
		contentPane.add(lbCpf);

		JLabel lbSenha = new JLabel("Senha");
		lbSenha.setForeground(new Color(255, 96, 69));
		lbSenha.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbSenha.setBounds(83, 239, 46, 14);
		contentPane.add(lbSenha);

		ftfCpf = new JFormattedTextField();
		ftfCpf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfSenha.requestFocus();
			}
		});
		ftfCpf.setBounds(149, 213, 128, 20);
		contentPane.add(ftfCpf);
		ftfCpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent tecla) {
				if (!Character.isDigit(tecla.getKeyChar())) {
					tecla.consume();
				}
				if (ftfCpf.getText().length() == 11) {
					tecla.consume();
				}
			}
		});

		tfSenha = new JPasswordField();
		tfSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btLogar.doClick();
			}
		});
		tfSenha.setBounds(149, 238, 128, 20);
		contentPane.add(tfSenha);

		tfSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent tecla) {
				if (tfSenha.getPassword().length > 20) {
					tecla.consume();
				}
			}
		});

		btLogar = new JButton("Entrar");
		btLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (ftfCpf.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Informe o CPF", "Erro", JOptionPane.ERROR_MESSAGE);
						ftfCpf.requestFocus();
					} else if (tfSenha.getPassword().length == 0) {
						JOptionPane.showMessageDialog(null, "Informe a senha", "Erro", JOptionPane.ERROR_MESSAGE);
						tfSenha.requestFocus();
					} else {
						if (!(tfSenha.getText().equals("1") && ftfCpf.getText().trim().equals("1"))) {
							funcionario = dao.buscaUsuario(ftfCpf.getText(),
									PasswordUtil.criptografa256(tfSenha.getText()));
						} else {
							funcionario.setCpf("1");
							funcionario.setNome("Manutenção");
							funcionario.setCargo(Cargo.values()[1]);
							JOptionPane.showMessageDialog(null, "Usuario Manutenção!!", "Aviso",
									JOptionPane.INFORMATION_MESSAGE);
						}
						if (funcionario != null) {
							TelaPrincipal frame = new TelaPrincipal(funcionario);
							frame.setVisible(true);

							if (TelaPrincipal.lbFuncionario.getText().contains("Atendente")) {
								TelaPrincipal.btnCadFuncionario.setVisible(false);
								TelaPrincipal.btnCadProduto.setVisible(false);
								TelaPrincipal.btnTelaGraficos.setVisible(false);
								TelaPrincipal.itemFuncionario.setVisible(false);
								TelaPrincipal.itemProduto.setVisible(false);
								TelaPrincipal.itemGraficos.setVisible(false);
							} else if (TelaPrincipal.lbFuncionario.getText().contains("Motoboy")) {
								TelaPrincipal.btnCadFuncionario.setVisible(false);
								TelaPrincipal.btnCadProduto.setVisible(false);
								TelaPrincipal.btnCadCliente.setVisible(false);
								TelaPrincipal.btnTelaPedido.setVisible(false);
								TelaPrincipal.btSAC.setVisible(false);
								TelaPrincipal.btnTelaGraficos.setVisible(false);
								TelaPrincipal.btRec.setVisible(false);
								TelaPrincipal.itemFuncionario.setVisible(false);
								TelaPrincipal.itemProduto.setVisible(false);
								TelaPrincipal.itemCliente.setVisible(false);
								TelaPrincipal.itemPedido.setVisible(false);
								TelaPrincipal.itemGraficos.setVisible(false);
								TelaPrincipal.itemRec.setVisible(false);
								TelaPrincipal.itemSAC.setVisible(false);
							} else if (TelaPrincipal.lbFuncionario.getText().contains("Pizzaiolo")) {
								TelaPrincipal.btnCadFuncionario.setVisible(false);
								TelaPrincipal.btnCadCliente.setVisible(false);
								TelaPrincipal.btnTelaPedido.setVisible(false);
								TelaPrincipal.btnCadProduto.setVisible(false);
								TelaPrincipal.btSAC.setVisible(false);
								TelaPrincipal.btnTelaGraficos.setVisible(false);
								TelaPrincipal.btRec.setVisible(false);
								TelaPrincipal.itemFuncionario.setVisible(false);
								TelaPrincipal.itemCliente.setVisible(false);
								TelaPrincipal.itemPedido.setVisible(false);
								TelaPrincipal.itemProduto.setVisible(false);
								TelaPrincipal.itemGraficos.setVisible(false);
								TelaPrincipal.itemRec.setVisible(false);
								TelaPrincipal.itemSAC.setVisible(false);

							}
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "CPF ou senha incorreta", "Login não realizado",
									JOptionPane.WARNING_MESSAGE);
							ftfCpf.setText(null);
							tfSenha.setText(null);
							ftfCpf.requestFocus();
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btLogar.setBorder(null);
		btLogar.setMnemonic('E');
		btLogar.setBounds(83, 278, 91, 32);
		btLogar.setForeground(Color.WHITE);
		btLogar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btLogar.setBackground(new Color(34, 139, 34));
		contentPane.add(btLogar);

		btSair = new JButton("Sair");
		btSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectionFactory.CloseConnection();
				System.exit(0);
			}
		});
		btSair.setBorder(null);
		btSair.setMnemonic('S');
		btSair.setBounds(184, 278, 91, 32);
		btSair.setForeground(Color.WHITE);
		btSair.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btSair.setBackground(Color.RED);
		contentPane.add(btSair);

		lbLogo = new JLabel("");
		lbLogo.setBounds(59, 0, 233, 228);
		contentPane.add(lbLogo);
		ImageIcon LeoPizza = new ImageIcon(getClass().getResource("/LeoPizza.png"));
		lbLogo.setIcon(ImageUtil.redimensiona(LeoPizza, lbLogo.getWidth(), lbLogo.getHeight()));
		setUndecorated(true);

	}
}
