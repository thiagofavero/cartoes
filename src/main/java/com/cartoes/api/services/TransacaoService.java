package com.cartoes.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cartoes.api.entities.Cartao;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.repositories.CartaoRepository;
import com.cartoes.api.repositories.TransacaoRepository;
import com.cartoes.api.utils.ConsistenciaException;

@Service
public class TransacaoService {
	
	private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	@Autowired
	private CartaoRepository cartaoRepository;

	public Optional<List<Transacao>> buscarPorNumeroCartao(String numeroCartao) throws ConsistenciaException {
		
		log.info("Service: buscando as transações do cartão de numero: {}", numeroCartao);
		
		Optional<List<Transacao>> transacoes = transacaoRepository.findByNumeroCartao(numeroCartao);
		
		if (!transacoes.isPresent() || transacoes.get().size() < 1) {
			
			log.info("Service: Nenhuma transacao foi encontrada para o cartão de numero: {}", numeroCartao);
			throw new ConsistenciaException("Nenhuma transacao foi encontrada para o cartão de numero: {}", numeroCartao);
			
		}
		return transacoes;
	}
	
	public Transacao salvar(Transacao transacao) throws ConsistenciaException {
		
		log.info("Service: Salvando Transacao: {}", transacao);
		
		Optional<Cartao> cartao = cartaoRepository.findByNumero(transacao.getCartao().getNumero());
		
		if (!cartao.isPresent()) {
			
			log.info("Service: Nenhum cartao com o numero: {} foi encontrado", transacao.getCartao().getNumero());
			throw new ConsistenciaException("Nenhum cartao com o numero: {} foi encontrado", transacao.getCartao().getNumero());
		}
		
		if (transacao.getId() > 0) {
			
			log.info("Service: Transacoes nao podem ser alteradas, apenas incluidas");
			throw new ConsistenciaException(" Transacoes nao podem ser alteradas, apenas incluidas");
		}
		
		if (cartao.get().getBloqueado()) {
			
			log.info("Service: Nao é possivel incluir transacoes para este cartao, pois o mesmo encontra-se bloqueado");
			throw new ConsistenciaException("Nao é possivel incluir transacoes para este cartao, pois o mesmo encontra-se bloqueado");
		}
		
		if (cartao.get().getDataValidade().before(new Date())) {
			
			log.info("Service: Nao é possivel incluir transacoes para este cartao, pois o mesmo encontra-se vencido");
			throw new ConsistenciaException("Nao é possivel incluir transacoes para este cartao, pois o mesmo encontra-se vencido");
		}
		
		transacao.setCartao(cartao.get());
		return transacaoRepository.save(transacao);

	}
	



}