package com.cartoes.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cartoes.api.entities.Cartao;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.repositories.CartaoRepository;
import com.cartoes.api.repositories.TransacaoRepository;
import com.cartoes.api.utils.ConsistenciaException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransacaoServiceTest {

	@MockBean
	private TransacaoRepository transacaoRepository;

	@Autowired
	private TransacaoService transacaoService;

	@MockBean
	private CartaoRepository cartaoRepository;

	@Test
	public void testBuscarPorNumeroCartaoExistente() throws ConsistenciaException {

		List<Transacao> lstTransacao = new ArrayList<Transacao>();
		lstTransacao.add(new Transacao());

		BDDMockito.given(transacaoRepository.findByNumeroCartao(Mockito.anyString()))
				.willReturn(Optional.of(lstTransacao));

		Optional<List<Transacao>> resultado = transacaoService.buscarPorNumeroCartao("258258258");

		assertTrue(resultado.isPresent());

	}

	@Test(expected = ConsistenciaException.class)
	public void testBuscarPorNumeroCartaoNaoExistente() throws ConsistenciaException {

		BDDMockito.given(transacaoRepository.findByNumeroCartao(Mockito.anyString())).willReturn(Optional.empty());

		transacaoService.buscarPorNumeroCartao("9876543213");

	}

	@Test
	public void testSalvarComSucesso() throws ConsistenciaException, ParseException {

		Cartao cartao = new Cartao();
		cartao.setNumero("8785858585");
		cartao.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2021"));
		Transacao transacao = new Transacao();
		transacao.setCartao(cartao);

		BDDMockito.given(cartaoRepository.findByNumero(Mockito.anyString())).willReturn(Optional.of(cartao));
		BDDMockito.given(transacaoRepository.save(Mockito.any(Transacao.class))).willReturn(new Transacao());

		Transacao resultado = transacaoService.salvar(transacao);

		assertNotNull(resultado);
	}

	@Test(expected = ConsistenciaException.class)
	public void testSalvarNumeroCartaoNaoEncontrado() throws ConsistenciaException {

		BDDMockito.given(transacaoRepository.findByNumeroCartao(Mockito.anyString())).willReturn(Optional.empty());

		Cartao a = new Cartao();
		a.setNumero("123456123");
		Transacao c = new Transacao();

		c.setCartao(a);

		transacaoService.salvar(c);

	}
	
	@Test(expected = ConsistenciaException.class)
	public void testSalvarIdDuplicado() throws ConsistenciaException {

		BDDMockito.given(transacaoRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());

		Cartao c = new Cartao();
		c.setNumero("123456123");
		Transacao t = new Transacao();

		t.setCartao(c);

		transacaoService.salvar(t);

	}
	
	
}