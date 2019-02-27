package br.gov.serpro.banco.alpha;

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

public class BancoAlphaTest {
	ContaCorrente contaEspecial;
	BancoAlpha alpha;
	ContaCorrente contaCumum;

	@Before
	public void inicializa() throws ContaInexistenteException {
		contaEspecial = new ContaCorrenteEspecial();
		alpha = new BancoAlpha(contaEspecial);
		contaCumum = new ContaCorrenteComum();

	}

	@Test
	public void ConsultaExtratoComUmDepositoContaContaCorrenteEspecialtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;

		Double valorDeposito = 100.00;
		alpha.efetuarDeposito(numeroConta, valorDeposito);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroConta);

		assertEquals(valorDeposito, consultarExtrato.get(0).getCredito(), 0);

	}

	@Test
	public void ConsultaExtratoComMaisDeUmDepositoContaCorrenteEspecialtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;

		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		alpha.efetuarDeposito(numeroConta, valorDepositoUm);
		alpha.efetuarDeposito(numeroConta, valorDepositoDois);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);

	}

	@Test
	public void ConsultaExtratoComMaisDeUmDepositoEUmaRetiradaContaCorrenteEspecialtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;

		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		Double valorSaque = 50.00;
		alpha.efetuarDeposito(numeroConta, valorDepositoUm);
		alpha.efetuarDeposito(numeroConta, valorDepositoDois);
		alpha.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroConta);
		Double consultarSaldo = alpha.consultarSaldo(numeroConta);

		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals("Deposito", consultarExtrato.get(1).getAcao());
		assertEquals(valorDepositoUm, consultarSaldo, 0);
	}

	@Test
	public void ConsultaExtratoComTransferenciaContaCorrenteEspecialtest() throws ContaInexistenteException,
			SaldoInsuficienteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;
		int numeroContaDestino = 2;
		Double valorDeposito = 101.00;
		Double valorTransferencia = 100.00;
		alpha.efetuarDeposito(numeroConta, valorDeposito);
		alpha.efetuarTransferencia(numeroConta, numeroContaDestino, valorTransferencia);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroContaDestino);
		assertEquals(101.0, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(0, consultarExtrato.get(0).getDebito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		
		assertEquals(0, consultarExtrato.get(1).getCredito(), 0);
		assertEquals(100.0, consultarExtrato.get(1).getDebito(), 0);
		assertEquals("Transferencia", consultarExtrato.get(1).getAcao());
	}

	@Test
	public void ConsultaExtratoComUmDepositoContaCorrenteComumtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;
		alpha = new BancoAlpha(contaCumum);
		Double valorDeposito = 100.00;
		alpha.efetuarDeposito(numeroConta, valorDeposito);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroConta);

		assertEquals(valorDeposito, consultarExtrato.get(0).getCredito(), 0);

	}

	@Test
	public void ConsultaExtratoComMaisDeUmDepositoContaCorrenteComumtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;
		alpha = new BancoAlpha(contaCumum);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		alpha.efetuarDeposito(numeroConta, valorDepositoUm);
		alpha.efetuarDeposito(numeroConta, valorDepositoDois);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);

	}

	@Test
	public void ConsultaExtratoComTransferenciaContaCorrenteComumtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;
		int numeroContaDestino = 2;
		alpha = new BancoAlpha(contaCumum);
		Double valorTransferencia = 100.00;
		Double valorDeposito = 100.00;
		alpha.efetuarDeposito(numeroContaDestino, valorDeposito);
		alpha.efetuarTransferencia(numeroConta, numeroContaDestino, valorTransferencia);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroContaDestino);
		assertEquals(100, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(0, consultarExtrato.get(0).getDebito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals("Transferencia", consultarExtrato.get(1).getAcao());
	}

	@Test
	public void ConsultaExtratoComMaisDeUmDepositoEUmaRetiradaContaCorrenteComumtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;
		alpha = new BancoAlpha(contaCumum);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		Double valorSaque = 50.00;
		alpha.efetuarDeposito(numeroConta, valorDepositoUm);
		alpha.efetuarDeposito(numeroConta, valorDepositoDois);
		alpha.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroConta);
		Double consultarSaldo = alpha.consultarSaldo(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals("Deposito", consultarExtrato.get(1).getAcao());
		assertEquals(valorDepositoUm, consultarSaldo, 0);
	}

	@Test(expected = LimiteDeOperacoesExcedidasException.class)
	public void DeveRetornarExcecaoQuandoMaisDeTresOperacoesNaContaCorrenteComumtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;
		alpha = new BancoAlpha(contaCumum);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		Double valorSaque = 50.00;
		alpha.efetuarDeposito(numeroConta, valorDepositoUm);
		alpha.efetuarDeposito(numeroConta, valorDepositoDois);
		alpha.efetuarDeposito(numeroConta, valorDepositoDois);
		alpha.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroConta);
		Double consultarSaldo = alpha.consultarSaldo(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals("Deposito", consultarExtrato.get(1).getAcao());
		assertEquals(valorDepositoUm, consultarSaldo, 0);
	}

	@Test(expected = ContaInexistenteException.class)
	public void DeveRetornarExcecaoQuandoContaInexistenteMaisDeTresOperacoesNaContaCorrenteComumtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		ContaCorrente conta = null;
		int numeroConta = 1;
		alpha = new BancoAlpha(conta);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 50.00;
		Double valorSaque = 50.00;
		alpha.efetuarDeposito(numeroConta, valorDepositoUm);
		alpha.efetuarDeposito(numeroConta, valorDepositoDois);
		alpha.efetuarDeposito(numeroConta, valorDepositoDois);
		alpha.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroConta);
		Double consultarSaldo = alpha.consultarSaldo(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals("Deposito", consultarExtrato.get(1).getAcao());
		assertEquals(valorDepositoUm, consultarSaldo, 0);
	}

	@Test(expected = SaldoInsuficienteException.class)
	public void DeveRetornarExcecaoQuandoSaldoInsuficienteMaisDeTresOperacoesNaContaCorrenteComumtest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;
		BancoAlpha alpha = new BancoAlpha(contaCumum);
		Double valorDepositoUm = 100.00;
		Double valorDepositoDois = 100.01;
		Double valorSaque = 100.01;
		alpha.efetuarDeposito(numeroConta, valorDepositoUm);
		alpha.efetuarSaque(numeroConta, valorSaque);
		List<Extrato> consultarExtrato = alpha.consultarExtrato(numeroConta);
		Double consultarSaldo = alpha.consultarSaldo(numeroConta);
		assertEquals(valorDepositoUm, consultarExtrato.get(0).getCredito(), 0);
		assertEquals(valorDepositoDois, consultarExtrato.get(1).getCredito(), 0);
		assertEquals("Deposito", consultarExtrato.get(0).getAcao());
		assertEquals("Deposito", consultarExtrato.get(1).getAcao());
		assertEquals(valorDepositoUm, consultarSaldo, 0);
	}
	
	@Test
	public void devePermitirSaqueNoValorLimiteTest()
			throws ContaInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {

		int numeroConta = 1;
		BancoAlpha alpha = new BancoAlpha(contaCumum);
		Double valorDepositoUm = 100.00;
		Double valorSaque = 100.00;
		alpha.efetuarDeposito(numeroConta, valorDepositoUm);
		alpha.efetuarSaque(numeroConta, valorSaque);
		Double consultarSaldo = alpha.consultarSaldo(numeroConta);
		assertEquals(0.0, consultarSaldo, 0);
	}

}
