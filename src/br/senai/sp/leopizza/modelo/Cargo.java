package br.senai.sp.leopizza.modelo;

public enum Cargo {
	ATENDENTE("Atendente"), GERENTE("Gerente"), MOTOBOY("Motoboy"), PIZZAIOLO("Pizzaiolo");
	
	Cargo(String string) {
		this.nome = string;
	}

	private String nome;

	@Override
	public String toString() {
		return nome;
	}
}
