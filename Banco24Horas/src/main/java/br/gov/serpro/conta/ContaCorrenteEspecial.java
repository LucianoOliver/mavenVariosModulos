package br.gov.serpro.conta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.gov.serpro.banco.Extrato;
import br.gov.serpro.caixa24h.exception.SaldoInsuficienteException;

public class ContaCorrenteEspecial extends ContaCorrente {
	
    private List<Extrato> listaExtratos = new ArrayList<Extrato>();


	@Override
	public void depositar(Double valor) throws LimiteDeOperacoesExcedidasException {
		
		verificarLimiteOperacoesPorDia();
		this.saldo = this.saldo + valor;
		Extrato extrato = new Extrato("Deposito", LocalDate.now(), valor, 0);
		listaExtratos.add(extrato);
	}
	
	@Override
	public void efetuarTransferencia(int numeroConta, int contaDestino, Double valor, Double taxa, Double valorLimite) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		
		verificarLimiteOperacoesPorDia();
		this.saldo = this.saldo - valor;
		verificarValorLimite(valorLimite);
		Extrato extrato = new Extrato("Transferencia", LocalDate.now(), 0, valor);
		listaExtratos.add(extrato);
		operacoesPorDia =  operacoesPorDia -1;
	}

	@Override
	public void efetuarRetirada(Double valorRetirada, Double valorTaxa, Double percentualTaxa, Double valorLimite) throws SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		verificarLimiteOperacoesPorDia();
		this.saldo = this.saldo - valorRetirada;
		verificarValorLimite(valorLimite);
		operacoesPorDia =  operacoesPorDia -1;
	}

	@Override
	public List<Extrato> extrato(int numeroConta, Double valorTaxa) {
		return listaExtratos;
		
	}
	
	private void verificarLimiteOperacoesPorDia() throws LimiteDeOperacoesExcedidasException {
		if (operacoesPorDia <= 0) {
			throw new LimiteDeOperacoesExcedidasException("Número de Operações excedidas");
		}
	}
	
	private void verificarValorLimite(Double valorLimite) throws SaldoInsuficienteException {
		if(saldo < valorLimite ) {
			 throw new SaldoInsuficienteException("Saldo insuficiente");
		}
	}
}
