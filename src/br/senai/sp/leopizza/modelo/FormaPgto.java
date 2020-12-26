package br.senai.sp.leopizza.modelo;

public enum FormaPgto {
	DINHEIRO("Dinheiro"), CARTAO_DEB("Cartão de Débito"), CARTAO_CRED("Cartão de Crédito"), VALE_REFEICAO("Vale Refeição");
	

	FormaPgto(String string) {
		this.nome = string;
	}

	private String nome;

	@Override
	public String toString() {
		return nome;
	}
}
