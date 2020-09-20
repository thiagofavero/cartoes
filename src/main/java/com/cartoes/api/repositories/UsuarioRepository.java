package com.cartoes.api.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.cartoes.api.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	@Transactional(readOnly = true)
	Usuario findByCpfAndAtivo(String cpf, boolean ativo);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Usuario SET senha = :novasenha WHERE id = :idusuario")
	void alterarSenhaUsuario(@Param("novasenha") String novasenha, @Param("idusuario") int idusuario);
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Usuario SET ativo = 0 WHERE datediff(now(), ultimoacesso) > 30")
    void desativarUsuariosInativos();

	@Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Usuario SET ultimoAcesso = :dataAtual WHERE cpf = :cpf")
    void atualizarData(@Param("dataAtual") Date dataAtual, @Param("cpf") String cpf);	
	
}