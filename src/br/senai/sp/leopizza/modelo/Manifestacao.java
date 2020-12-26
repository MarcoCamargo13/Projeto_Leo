package br.senai.sp.leopizza.modelo;

public enum Manifestacao {
	RECLAMACAO("Reclamação"),
	ELOGIO("Elogio"),
	SUGESTAO("Sugestão"),
	DUVIDA("Dúvida");

	Manifestacao(String string) {
		this.nome = string;
	}

	private String nome;

	@Override
	public String toString() {
		return nome;
	}
}
