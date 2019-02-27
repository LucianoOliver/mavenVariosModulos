package br.gov.serpro.banco;

import java.util.List;

import br.gov.serpro.caixa24h.exception.ContaInexistenteException;
import br.gov.serpro.caixa24h.exception.SaldoInsuficienteException;
import br.gov.serpro.conta.LimiteDeOperacoesExcedidasException;

public interface BancoGeral {
	 List<Extrato> consultarExtrato(int numeroConta) throws ContaInexistenteException;	
	 Double consultarSaldo(int numeroConta)throws ContaInexistenteException;	
	 void efetuarTransferencia(int numeroConta, int contaDestino, Double valor) throws SaldoInsuficienteException, ContaInexistenteException, LimiteDeOperacoesExcedidasException;	
	 void efetuarDeposito(int numeroConta, Double valor) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException;	
	 void efetuarSaque(int numeroConta, Double valor) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException;
}
	