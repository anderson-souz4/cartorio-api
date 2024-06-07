package com.asouza.cartorioapi.repositories;

import com.asouza.cartorioapi.models.Cartorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartorioRepository extends JpaRepository<Cartorio, Long> {
    Optional<Cartorio> findByNome(String test);
}
