package br.senai.sp.leopizza.modelo;

public class Cliente {
	private int id;
	private String nome;
	private String endereco;
	private String telefone;
	private String email;
	private int pontos;
	private String cep;
	

	// Getters & Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isBrinde() {
		//Se o cliente tem mais que 15 pontos
		if(this.pontos >= 15) {
			return true;
		}else {
			return false;
		}
	}
	
	public void retiraBrinde() {
		//Diminui em 15 os pontos
		this.pontos -= 15;
	}

	public void adicionaPontos(int pontos) {
		//Aumenta os pontos do cliente
		this.pontos += pontos;
	}
}
