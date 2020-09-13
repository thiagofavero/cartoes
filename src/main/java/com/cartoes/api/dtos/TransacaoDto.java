package com.cartoes.api.dtos;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

public class TransacaoDto {

	private String id;
	
	private String dataTransacao;

	@NotEmpty(message = "CNPJ não pode ser vazio.")
	@CNPJ(message = "CNPJ inválido.")	
	private String cnpj;
	
	@NotEmpty(message = "Valor não pode ser vazio.")
	private String valor;

	@NotEmpty(message = "Parcelas não pode ser vazia.")
	@Length(min = 1, max = 24, message = "Quantidade de parcelas devem ser entre 1 a 24 vezes.")	
	private String qdtParcelas;

	@NotEmpty(message = "Juros não pode ser vazio.")
	private String juros;

	private String numeroCartao;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(String dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getQdtParcelas() {
		return qdtParcelas;
	}

	public void setQdtParcelas(String qdtParcelas) {
		this.qdtParcelas = qdtParcelas;
	}

	public String getJuros() {
		return juros;
	}

	public void setJuros(String juros) {
		this.juros = juros;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	@Override
	public String toString() {
		return "Cliente[id=" + id + "," + "data Transacao=" + dataTransacao + "," +  "CNPJ=" + cnpj + "," + "valor=" + valor + ","
				+ "quantidade parcela =" + qdtParcelas + "," + "juros=" + juros + "numero cartão= " + numeroCartao + "]";
	}


}
