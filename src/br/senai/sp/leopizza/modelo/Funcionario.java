package br.senai.sp.leopizza.modelo;

import br.senai.sp.leopizza.util.PasswordUtil;

public class Funcionario {
	private String nome;
	private String senha;
	private String cpf;
	private Cargo cargo;

	// Getters & Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = PasswordUtil.criptografa256(senha);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}
