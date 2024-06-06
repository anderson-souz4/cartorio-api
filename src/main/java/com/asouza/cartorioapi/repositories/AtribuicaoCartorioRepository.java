package com.asouza.cartorioapi.repositories;

import com.asouza.cartorioapi.models.Atribuicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtribuicaoCartorioRepository extends JpaRepository<Atribuicao, String> {
    Optional<Atribuicao> findByNome(String nome);
}
