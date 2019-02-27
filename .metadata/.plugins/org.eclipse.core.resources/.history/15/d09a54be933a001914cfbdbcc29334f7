package br.gov.serpro.caixa24h;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.gov.serpro.banco.BancoGeral;
import br.gov.serpro.banco.Extrato;
import br.gov.serpro.banco.beta.BancoBeta;
import br.gov.serpro.caixa24h.exception.BancoInexistenteException;
import br.gov.serpro.caixa24h.exception.ContaInexistenteException;
import br.gov.serpro.caixa24h.exception.SaldoInsuficienteException;
import br.gov.serpro.conta.ContaCorrente;
import br.gov.serpro.conta.ContaCorrenteComum;
import br.gov.serpro.conta.LimiteDeOperacoesExcedidasException;

public class CaixaVinteQuatroHorasIntegradoBancoBetaContaRegularTest {

	BancoGeral bancoBeta;
	private int numeroConta;
	private int contaDestino;
	private ContaCorrente conta;

	@Before
	public void inicializa() throws ContaInexistenteException {
		conta = new ContaCorrenteComum();
		bancoBeta =  new BancoBeta(conta);
		numeroConta = 123456;
		contaDestino = 12345678;
	}

	@Test
	public void deveRetornarUmaConsultaDoExtrato() throws ContaInexistenteException, BancoInexistenteException, SaldoInsuficienteException, LimiteDeOperacoesExcedidasException {
		//dado
		Double valorDeposito = 100.00;
		LocalDate data = LocalDate.now();
	
		//quando
		CaixaVinteQuatroHoras caixa24horas = new CaixaVinteQuatroHoras(bancoBeta);
		caixa24horas.efetuarDeposito(numeroConta, valorDeposito);
		List<Extrato> consultaExtrato = caixa24horas.consultaExtrato(123456);

		//entao
		assertEquals(data, consultaExtrato.get(0).getData());
		assertEquals(100.00, consultaExtrato.get(0).getCredito(), 0);
		assertEquals(0, consultaExtrato.get(0).getDebito(), 0);
		assertEquals(1, consultaExtrato.size());
	}

	@Test(expected = ContaInexistenteException.class)
	public void naoDevePermitirNumeroContaInexistenteParaConsultaExtrato()
			throws ContaInexistenteException, BancoInexistenteException {
		bancoBeta =  new BancoBeta(null);
		CaixaVinteQuatroHoras caixa24horas = new CaixaVinteQuatroHoras(bancoBeta);
		caixa24horas.consultaExtrato(numeroConta);

	}

	@Test(expected = SaldoInsuficienteException.class)
	public void naoDevePermitirSaldoInsuficienteAoEfetuarTransferencia()
			throws ContaInexistenteException, BancoInexistenteException, SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException {
		Double valorDepositado = 100.00;
		Double valorTransferido = 1100.01;
		
		CaixaVinteQuatroHoras caixa24horas = new CaixaVinteQuatroHoras(bancoBeta);
		caixa24horas.efetuarDeposito(numeroConta, valorDepositado);
		caixa24horas.efetuarTransferencia(numeroConta, contaDestino, valorTransferido);

	}

	@Test(expected = SaldoInsuficienteException.class)
	public void naoDevePermitirSaldoInsuficienteAoEfetuarSaque()
			throws ContaInexistenteException, BancoInexistenteException, SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException {
		Double valorDepositado = 100.00;
		Double valorSaque = 1100.01;
		
		CaixaVinteQuatroHoras caixa24horas = new CaixaVinteQuatroHoras(bancoBeta);
		caixa24horas.efetuarDeposito(numeroConta, valorDepositado);
		caixa24horas.efetuarSaque(numeroConta, valorSaque);

	}

	@Test(expected = BancoInexistenteException.class)
	public void deveRetornarExcecaoQuandoInformadoBancoNulo()
			throws ContaInexistenteException, BancoInexistenteException {
		bancoBeta = null;
		CaixaVinteQuatroHoras caixa24horas = new CaixaVinteQuatroHoras(bancoBeta);
		caixa24horas.consultaExtrato(numeroConta);


	}

	@Test
	public void deveRetornarSaldoAoInformarNumeroDaConta() throws ContaInexistenteException, BancoInexistenteException,  LimiteDeOperacoesExcedidasException, SaldoInsuficienteException {
		Double valorDepositado = 100.00;
		Double saldoRetornado = 100.00;
	
		CaixaVinteQuatroHoras caixa24horas = new CaixaVinteQuatroHoras(bancoBeta);
		caixa24horas.efetuarDeposito(numeroConta, valorDepositado);
		Double consultaSaldo = caixa24horas.consultaSaldo(numeroConta);
		assertEquals(saldoRetornado, consultaSaldo);
	}

	@Test
	public void deveRetornarSeTransferenciaFoiEfetuadaComSucesso()
			throws SaldoInsuficienteException, ContaInexistenteException, BancoInexistenteException,  LimiteDeOperacoesExcedidasException {
		Double valorDepositado = 101.00;
		Double valorTransferido = 100.00;
	
		CaixaVinteQuatroHoras caixa24horas = new CaixaVinteQuatroHoras(bancoBeta);
		caixa24horas.efetuarDeposito(numeroConta, valorDepositado);
		Double consultaSaldo = caixa24horas.consultaSaldo(numeroConta);
		assertEquals(101.0, consultaSaldo,0);
		caixa24horas.efetuarTransferencia(numeroConta, contaDestino, valorTransferido);
		assertEquals(0, caixa24horas.consultaSaldo(numeroConta), 0);
		
		
	}

	@Test
	public void deveRetornarSeDepositoEfetuadoComSucesso() throws  LimiteDeOperacoesExcedidasException, ContaInexistenteException, BancoInexistenteException, SaldoInsuficienteException {
		Double valorDepositado = 100.00;
	
		CaixaVinteQuatroHoras caixa24horas = new CaixaVinteQuatroHoras(bancoBeta);
		caixa24horas.efetuarDeposito(numeroConta, valorDepositado);
		Double consultaSaldo = caixa24horas.consultaSaldo(numeroConta);
		assertEquals(valorDepositado, consultaSaldo);
	}

	@Test
	public void deveRetornarSeSaqueEfetuadoComSucesso() throws SaldoInsuficienteException,  LimiteDeOperacoesExcedidasException, BancoInexistenteException, ContaInexistenteException {
		Double valorDepositado = 100.00;
		Double valorSaque = 50.00;
	
		CaixaVinteQuatroHoras caixa24horas = new CaixaVinteQuatroHoras(bancoBeta);
		caixa24horas.efetuarDeposito(numeroConta, valorDepositado);
		caixa24horas.efetuarSaque(numeroConta, valorSaque);
		Double consultaSaldo = caixa24horas.consultaSaldo(numeroConta);
		assertEquals(44.0, consultaSaldo, 0);
	}

}
