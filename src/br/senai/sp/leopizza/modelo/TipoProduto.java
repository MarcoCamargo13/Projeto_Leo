package br.senai.sp.leopizza.modelo;

public enum TipoProduto {
	BEBIDAS("Bebida"), PIZZA_DOCE("Pizza Doce"), PIZZA_SALGADA("Pizza Salgada"), BROTO_DOCE("Broto Doce"),
	BROTO_SALGADA("Broto Salgada"), BORDA_RECHEADA("Borda Recheada"), MISTA("Pizza Mista");

	TipoProduto(String string) {
		this.nome = string;
	}

	private String nome;

	@Override
	public String toString() {
		return nome;
	}
}
