package br.gov.serpro.banco.alpha;

import java.util.List;

import br.gov.serpro.banco.BancoGeral;
import br.gov.serpro.banco.Extrato;
import br.gov.serpro.caixa24h.exception.ContaInexistenteException;
import br.gov.serpro.caixa24h.exception.SaldoInsuficienteException;
import br.gov.serpro.conta.ContaCorrente;
import br.gov.serpro.conta.ContaCorrenteComum;
import br.gov.serpro.conta.ContaCorrenteEspecial;
import br.gov.serpro.conta.LimiteDeOperacoesExcedidasException;

public class BancoAlpha implements BancoGeral {

	private static final double VALOR_LIMITE_CONTA_COMUM = 0.0;
	private static final double VALOR_LIMITE_CONTA_ESPECIAL = -1000.00;
	private static final Double TAXA_CONSULTA_EXTRATO = 0.0;
	private static final Double TAXA_TRANSFERENCIA = 0.0;
	private static final Double VALOR_TAXA_SAQUE = 0.0;
	private static final double PERCENTUAL_TAXA_SAQUE = 0.0;
	private Double valorLimite = 0.0;

	ContaCorrente conta;

	public BancoAlpha(ContaCorrente conta) throws ContaInexistenteException {
		if (conta == null) {
			throw new ContaInexistenteException("Conta inexistente");
		}
		this.conta = conta;
		obterValorLimite();
		obterLimiteDeOperacoes();
	}

	public void obterValorLimite() {
		if (conta instanceof ContaCorrenteEspecial) {
			 valorLimite = VALOR_LIMITE_CONTA_ESPECIAL;
		}else {
			valorLimite = VALOR_LIMITE_CONTA_COMUM;
		}
		 
	}

	private void obterLimiteDeOperacoes() {
		if (this.conta instanceof ContaCorrenteComum) {
			this.conta.operacoesPorDia  = 3;
		}else {
			this.conta.operacoesPorDia =24;
		}
		
	}

	public List<Extrato> consultarExtrato(int numeroConta) throws ContaInexistenteException {
		if (numeroConta <= 0) {
			throw new ContaInexistenteException("número da Conta inexistente");
		}
		return this.conta.extrato(numeroConta, TAXA_CONSULTA_EXTRATO);
	}

	public Double consultarSaldo(int numeroConta) throws ContaInexistenteException {

		return conta.obterSaldo();
	}

	public void efetuarTransferencia(int numeroConta, int contaDestino, Double valor) throws SaldoInsuficienteException,
			ContaInexistenteException, LimiteDeOperacoesExcedidasException {

		conta.efetuarTransferencia(numeroConta, contaDestino, valor, TAXA_TRANSFERENCIA, valorLimite);

	}

	public void efetuarDeposito(int numeroConta, Double valor)
			throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		conta.depositar(valor);

	}

	public void efetuarSaque(int numeroConta, Double valor)
			throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		conta.efetuarRetirada(valor, VALOR_TAXA_SAQUE, PERCENTUAL_TAXA_SAQUE, valorLimite);

	}

}
