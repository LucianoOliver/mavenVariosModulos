package br.gov.serpro.conta;

import java.util.List;

import br.gov.serpro.banco.Extrato;
import br.gov.serpro.caixa24h.exception.SaldoInsuficienteException;

public abstract class ContaCorrente {
	protected int numero;
	protected double saldo;
	public int operacoesPorDia = 0;

	public abstract void efetuarRetirada(Double valorRetirada, Double valorTaxa, Double percentualTaxa,
			Double valorLimite) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException;

	public abstract void depositar(Double valor) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException;

	public double obterSaldo() {
		return saldo;
	}

	public abstract List<Extrato> extrato(int numeroConta, Double valorTaxa);

	public abstract void efetuarTransferencia(int numeroConta, int contaDestino, Double valor, Double taxa,
			Double valorLimite) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException;

}
