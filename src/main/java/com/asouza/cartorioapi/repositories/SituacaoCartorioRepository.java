package com.asouza.cartorioapi.repositories;

import com.asouza.cartorioapi.models.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SituacaoCartorioRepository extends JpaRepository<Situacao, String> {
    Optional<Situacao> findByNome(String nome);
}
