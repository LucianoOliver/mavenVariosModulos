package br.gov.serpro.banco.beta;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.gov.serpro.banco.Extrato;
import br.gov.serpro.caixa24h.exception.ContaInexistenteException;
import br.gov.serpro.caixa24h.exception.SaldoInsuficienteException;
import br.gov.serpro.conta.ContaCorrente;
import br.gov.serpro.conta.ContaCorrenteComum;
import br.gov.serpro.conta.ContaCorrenteEspecial;
import br.gov.serpro.conta.LimiteDeOperacoesExcedidasException;

public class BancoBetaTest {
	
	ContaCorrente contaEspecial;
	ContaCorrente contaComum;
	
	@Before
	public void inicializa() throws ContaInexistenteException {
		contaEspecial = new ContaCorrenteEspecial();
		contaComum = new ContaCorrenteComum();
		
	}

	@Test
	public void ConsultaExtratoComUmDepositoContaCorrenteEspecialtest() throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(contaEspecial);
		Double valorDeposito = 100.00;
		beta.efetuarDeposito(numeroConta, valorDeposito);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		
		assertEquals(valorDeposito, consultarExtrato.get(0).getCredito(), 0);
		           
	}
	
	@Test
	public void ConsultaExtratoComMaisDeUmDepositoCorrenteEspecialtest() throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(contaEspecial);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		beta.efetuarDeposito(numeroConta, valorDepositoUm);
		beta.efetuarDeposito(numeroConta, valorDepositoDois);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		           
	}
	
	@Test
	public void ConsultaExtratoComMaisDeUmDepositoEUmaRetiradaCorrenteEspecialtest() throws ContaInexistenteException, SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException {
	
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(contaEspecial);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		Double valorSaque = 50.00;
		
		beta.efetuarDeposito(numeroConta, valorDepositoUm);
		beta.efetuarDeposito(numeroConta, valorDepositoDois);
		beta.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		 Double consultarSaldo = beta.consultarSaldo(numeroConta);
		 
		
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals("Deposito", consultarExtrato.get(1).getAcao());
		assertEquals(valorDepositoUm, consultarSaldo, 0);          
	}
	
	@Test
	public void ConsultaExtratoComTransferenciaCorrenteEspecialtest() throws ContaInexistenteException, SaldoInsuficienteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		
		int numeroConta = 1;
		int numeroContaDestino = 2;
		BancoBeta beta = new BancoBeta(contaEspecial);
		Double valorTransferencia = 100.00;
		beta.efetuarDeposito(numeroContaDestino, valorTransferencia);
		
		beta.efetuarTransferencia(numeroConta, numeroContaDestino, valorTransferencia);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroContaDestino);		 
		assertEquals(100.0, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(0, consultarExtrato.get(0).getDebito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());   
		assertEquals(0, consultarExtrato.get(1).getCredito(), 0);
		assertEquals(100.0, consultarExtrato.get(1).getDebito(), 0);
		assertEquals("Transferencia", consultarExtrato.get(1).getAcao());   
	}
	
	@Test
	public void ConsultaExtratoComUmDepositoContaCorrenteComumtest() throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(contaComum);
		Double valorDeposito = 100.00;
		beta.efetuarDeposito(numeroConta, valorDeposito);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		
		assertEquals(valorDeposito, consultarExtrato.get(0).getCredito(), 0);
		           
	}
	
	@Test
	public void ConsultaExtratoComMaisDeUmDepositoContaCorrenteComumtest() throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		ContaCorrente conta = new ContaCorrenteComum();
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(conta);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		beta.efetuarDeposito(numeroConta, valorDepositoUm);
		beta.efetuarDeposito(numeroConta, valorDepositoDois);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		           
	}
	
	@Test
	public void ConsultaExtratoComTransferenciaContaCorrenteComumtest() throws ContaInexistenteException, SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException {
		int numeroConta = 1;
		int numeroContaDestino = 2;
		BancoBeta beta = new BancoBeta(contaComum);
		Double valorTransferencia = 100.00;
		Double valorDeposito = 101.00;
		beta.efetuarDeposito(numeroContaDestino, valorDeposito);
		beta.efetuarTransferencia(numeroConta, numeroContaDestino, valorTransferencia);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroContaDestino);		 
		assertEquals(101, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(0, consultarExtrato.get(0).getDebito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao()); 
		assertEquals(0, consultarExtrato.get(1).getCredito(), 0);
		assertEquals(100.0, consultarExtrato.get(1).getDebito(), 0);
		assertEquals("Transferencia", consultarExtrato.get(1).getAcao());          
	}
	
	@Test
	public void ConsultaExtratoComMaisDeUmDepositoEUmaRetiradaContaCorrenteComumtest() throws ContaInexistenteException, SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException {
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(contaComum);
		Double valorDepositoUm = 100.00;
		Double valorSaque = 50.00;
		beta.efetuarDeposito(numeroConta, valorDepositoUm);
		beta.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		 Double consultarSaldo = beta.consultarSaldo(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals(43.5, consultarSaldo, 0);          
	}
	
	@Test(expected = LimiteDeOperacoesExcedidasException.class)
	public void DeveRetornarExcecaoQuandoMaisDeTresOperacoesNaContaCorrenteComumtest() throws ContaInexistenteException, SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException {
	
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(contaComum);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		Double valorSaque = 50.00;
		beta.efetuarDeposito(numeroConta, valorDepositoUm);
		beta.efetuarDeposito(numeroConta, valorDepositoDois);
		beta.efetuarDeposito(numeroConta, valorDepositoDois);
		beta.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		 Double consultarSaldo = beta.consultarSaldo(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals("Deposito", consultarExtrato.get(1).getAcao());
		assertEquals(valorDepositoUm, consultarSaldo, 0);          
	}
	
	@Test(expected = ContaInexistenteException.class)
	public void DeveRetornarExcecaoQuandoContaInexistenteMaisDeTresOperacoesNaContaCorrenteComumtest() throws ContaInexistenteException, SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException {
		ContaCorrente contaComum = null;
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(contaComum);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		Double valorSaque = 50.00;
		beta.efetuarDeposito(numeroConta, valorDepositoUm);
		beta.efetuarDeposito(numeroConta, valorDepositoDois);
		beta.efetuarDeposito(numeroConta, valorDepositoDois);
		beta.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		 Double consultarSaldo = beta.consultarSaldo(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals("Deposito", consultarExtrato.get(1).getAcao());
		assertEquals(valorDepositoUm, consultarSaldo, 0);          
	}
	
	@Test(expected = SaldoInsuficienteException.class)
	public void DeveRetornarExcecaoQuandoSaldoInsuficienteMaisDeTresOperacoesNaContaCorrenteComumtest() throws ContaInexistenteException, SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException {
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(contaComum);
		Double valorDepositoUm = 100.00;
		Double valorSaque = 400.01;
		beta.efetuarDeposito(numeroConta, valorDepositoUm);
		beta.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		       
	}
	
	@Test
	public void devePermitirSaqueNoLimiteDuzentosReais() throws ContaInexistenteException, SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException {
		int numeroConta = 1;
		BancoBeta beta = new BancoBeta(contaComum);
		Double valorDepositoUm = 107.01;
		Double valorSaque = 300.00;
		beta.efetuarDeposito(numeroConta, valorDepositoUm);
		beta.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = beta.consultarExtrato(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		       
	}
	
	
}
