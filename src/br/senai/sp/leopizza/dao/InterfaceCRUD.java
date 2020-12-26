package br.senai.sp.leopizza.dao;

import java.util.List;

//Interface com as 4 operações básicas do Banco de Dados

public interface InterfaceCRUD<T> {

	// Método para inserir um objeto no BD
	public void inserir(T objeto) throws Exception;

	// Método para atualizar um objeto do BD
	public void atualizar(T objeto) throws Exception;

	// Método para excluir um objeto do BD
	public void excluir(T objeto) throws Exception;

	// Método para listar todos os registros do BD
	public List<T> listar() throws Exception;
}
