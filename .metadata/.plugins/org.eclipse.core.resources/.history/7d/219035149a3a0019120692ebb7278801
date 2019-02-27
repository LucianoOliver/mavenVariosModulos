package br.gov.serpro.conta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.gov.serpro.banco.Extrato;
import br.gov.serpro.caixa24h.exception.SaldoInsuficienteException;

public class ContaCorrenteComum extends ContaCorrente {
	
	 private List<Extrato> listaExtratos = new ArrayList<Extrato>();


	@Override
	public void depositar(Double valor) throws LimiteDeOperacoesExcedidasException {
		
		verificarLimiteOperacoesPorDia();
		this.saldo = this.saldo + valor;
		Extrato extrato = new Extrato("Deposito", LocalDate.now(), valor, 0);
		listaExtratos.add(extrato);
		operacoesPorDia =  operacoesPorDia -1;
	}

	private void verificarLimiteOperacoesPorDia() throws LimiteDeOperacoesExcedidasException {
		if (operacoesPorDia <= 0) {
			throw new LimiteDeOperacoesExcedidasException("Número de Operações excedidas");
		}
	}

	@Override
	public void efetuarRetirada(Double valorRetirada, Double valorTaxa, Double percentualTaxa, Double valorLimite) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		
		retirarTaxaSaque(valorTaxa, percentualTaxa);
		verificarLimiteOperacoesPorDia();
		this.saldo = this.saldo - valorRetirada;
		verificarValorLimite(valorLimite);
		operacoesPorDia =  operacoesPorDia -1;
	}

	
	@Override
	public List<Extrato> extrato(int numeroConta, Double valorTaxa) {
		retirarTaxa(valorTaxa);
		return listaExtratos;
		
	}

	private void retirarTaxa(Double valorTaxa) {
		this.saldo = this.saldo - valorTaxa;
	}
	
	
	private void retirarTaxaSaque(Double valorTaxa, Double percentualTaxa) {
		Double valorDesconto = valorDesconto(valorTaxa, percentualTaxa);
		this.saldo = this.saldo - valorDesconto;
	}

	private Double valorDesconto(Double valorTaxa, Double percentualTaxa) {
		Double valorDesconto = (this.saldo * percentualTaxa) + valorTaxa;
		return valorDesconto;
	}

	@Override
	public void efetuarTransferencia(int numeroConta, int contaDestino, Double valor, Double valorTaxa, Double valorLimite)
			throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		retirarTaxa(valorTaxa);
		verificarLimiteOperacoesPorDia();
		this.saldo = this.saldo - valor;
		verificarValorLimite(valorLimite);
		
		operacoesPorDia =  operacoesPorDia -1;
		Extrato extrato = new Extrato("Transferencia", LocalDate.now(), 0, valor);
		listaExtratos.add(extrato);
	}

	private void verificarValorLimite(Double valorLimite) throws SaldoInsuficienteException {
		if (saldo < valorLimite) {
			throw new SaldoInsuficienteException("Saldo insuficiente");
		}
	}

}
