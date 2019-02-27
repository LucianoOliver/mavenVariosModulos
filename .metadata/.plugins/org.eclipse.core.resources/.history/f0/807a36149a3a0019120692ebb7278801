package br.gov.serpro.caixa24h;

import java.util.List;

import br.gov.serpro.banco.BancoGeral;
import br.gov.serpro.banco.Extrato;
import br.gov.serpro.caixa24h.exception.BancoInexistenteException;
import br.gov.serpro.caixa24h.exception.ContaInexistenteException;
import br.gov.serpro.caixa24h.exception.SaldoInsuficienteException;
import br.gov.serpro.conta.LimiteDeOperacoesExcedidasException;

public class CaixaVinteQuatroHoras {

	private BancoGeral banco;

	public CaixaVinteQuatroHoras(BancoGeral banco) throws BancoInexistenteException {
		if (banco == null) {
			 throw new BancoInexistenteException("Banco inexistente");
		}
		this.banco = banco;
	}

	public List<Extrato> consultaExtrato(int numeroConta) throws ContaInexistenteException {
		return banco.consultarExtrato(numeroConta);
	}
	
	public Double consultaSaldo(int numeroConta) throws ContaInexistenteException {
		return banco.consultarSaldo(numeroConta);
	}	
	
	public void efetuarTransferencia(int numeroConta, int contaDestino, Double valor) throws SaldoInsuficienteException, ContaInexistenteException, LimiteDeOperacoesExcedidasException {
		 banco.efetuarTransferencia(numeroConta, contaDestino, valor);
	}

	public void efetuarDeposito(int numeroConta, Double valor) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		 banco.efetuarDeposito(numeroConta, valor);
	}

	public void efetuarSaque(int numeroConta, Double valor) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		 banco.efetuarSaque(numeroConta, valor);
	}	
}