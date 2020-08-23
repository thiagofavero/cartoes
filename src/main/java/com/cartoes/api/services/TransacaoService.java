package com.cartoes.api.services;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
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

	public Optional<List<Transacao>> buscarPorCartaoNumero(String cartaoNumero) throws ConsistenciaException {
		
		log.info("Service: buscando as transações do cartão de numero: {}", cartaoNumero);
		
		Optional<List<Transacao>> transacoes = Optional.ofNullable(transacaoRepository.findByCartaoNumero(cartaoNumero));
		
		if (!transacoes.isPresent()) {
			
			log.info("Service: Nenhuma transacao foi encontrada para o cartão de numero: {}", cartaoNumero);
			throw new ConsistenciaException("Nenhuma transacao foi encontrada para o cartão de numero: {}", cartaoNumero);
			
		}
		return transacoes;
	}
	
	public Transacao salvar(Transacao transacao) throws ConsistenciaException {
		log.info("Service: Salvando Transacao: {}", transacao);
		if (cartaoRepository.findByNumero(transacao.getCartao().getNumero()) == null) {
			log.info("Service: Nenhum cartao com o numero: {} foi encontrado", transacao.getCartao().getNumero());
			throw new ConsistenciaException("Nenhum cartao com o numero: {} foi encontrado", transacao.getCartao().getNumero());
		}
		if (!transacao.getCartao().getNumero().isEmpty())
			buscarPorCartaoNumero(transacao.getCartao().getNumero());
		try {
			return transacaoRepository.save(transacao);
		} catch (DataIntegrityViolationException e) {
			log.info("Nao existe um cartao com esse numero cadastrado", transacao.getCartao().getNumero());
			throw new ConsistenciaException("Nao existe um cartao com esse numero cadastrado", transacao.getCartao().getNumero());
		}
	}
	



}