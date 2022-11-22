package com.example.americanas.rpa.model;

import org.slf4j.LoggerFactory;

public class Produto {
	
	private String titulo;
	private String preco;
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Produto.class);
	
	public Produto() {
		super();
		LOGGER.info("Inicializando classe Produto vazio");
	}

	public Produto(String titulo, String preco) {
		super();
		this.titulo = titulo;
		this.preco = preco;
		LOGGER.info("Inicializando classe Produto com título e preço");
	}

	public String getTitulo() {
		LOGGER.info("Pegando título do Produto");
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
		LOGGER.info("Setando título do Produto");
	}

	public String getPreco() {
		LOGGER.info("Pegando preço do Produto");
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
		LOGGER.info("Setando preço do Produto");
	}

}
