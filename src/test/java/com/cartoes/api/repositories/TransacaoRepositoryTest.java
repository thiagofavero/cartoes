package com.cartoes.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cartoes.api.entities.Cliente;
import com.cartoes.api.entities.Cartao;
import com.cartoes.api.entities.Transacao;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransacaoRepositoryTest {

	@Autowired
	private TransacaoRepository transacaoRepository;  
	@Autowired
	private ClienteRepository clienteRepository; 
	
	@Autowired
	private CartaoRepository cartaoRepository; 
	
	private Transacao transacaoTeste;
	private Cliente clienteTeste;
	private Cartao cartaoTeste;
	
	
	private void CriarTransacaoTestes() throws ParseException {
		
		clienteTeste = new Cliente();	
		clienteTeste.setNome("Nome Teste");
		clienteTeste.setCpf("05887098082");
		clienteTeste.setUf("CE");
		clienteTeste.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		
		cartaoTeste = new Cartao();
		cartaoTeste.setBloqueado(false);
		cartaoTeste.setCliente(clienteTeste);;
		cartaoTeste.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		cartaoTeste.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		cartaoTeste.setNumero("1111111111111111");
		
		transacaoTeste = new Transacao();
		transacaoTeste.setCartao(cartaoTeste);
		transacaoTeste.setCnpj("22222222222222");
		transacaoTeste.setDataTransacao(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020"));
		transacaoTeste.setJuros(0.8);
		transacaoTeste.setQtdParcelas(2);
		transacaoTeste.setValor(200.20);
		
	}
	
	@Before
	public void setUp() throws Exception {
		
		CriarTransacaoTestes();
		clienteRepository.save(clienteTeste);		
		cartaoRepository.save(cartaoTeste);
		transacaoRepository.save(transacaoTeste);
		
	}
	
	@After
	public void tearDown() throws Exception {
		
		transacaoRepository.deleteAll();
		clienteRepository.deleteAll();
		cartaoRepository.deleteAll();
	}

	@Test
	public void testFindById() {

		Transacao transacao = transacaoRepository.findById(transacaoTeste.getId()).get();
		assertEquals(transacaoTeste.getId(), transacao.getId());			
				
	}
	
	@Test
	public void testFindByCartaoNumero() {
		
		List<Transacao> lstTransacao = transacaoRepository.findByCartaoNumero(cartaoTeste.getNumero());
		
		if (lstTransacao.size() != 1) {
			fail();
		}
		
		Transacao transacao = lstTransacao.get(0);
		
		assertTrue(transacao.getCartao().getNumero().equals(transacaoTeste.getCartao().getNumero()));
				
	}

}
