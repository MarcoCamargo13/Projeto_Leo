package br.senai.sp.leopizza.modelo;

public enum Assunto {
	ATRASO_NA_ENTREGA("Atraso na Entrega"),
	FALTOU_REFRIGERANTE("Faltou Refrigerante"),
	INGREDIENTE_ESTRAGADO("Ingrediente Estragado"),
	PIZZA_FRIA("Pizza Fria"),
	PIZZA_QUEIMADA("Pizza Queimada"),
	PIZZA_REVIRADA("Pizza Revirada"),
	SABOR_TROCADO("Sabor Trocado");

	Assunto(String string) {
		this.nome = string;
	}

	private String nome;

	@Override
	public String toString() {
		return nome;
	}
}