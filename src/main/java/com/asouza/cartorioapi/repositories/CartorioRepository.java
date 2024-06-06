package com.asouza.cartorioapi.repositories;

import com.asouza.cartorioapi.models.Cartorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartorioRepository extends JpaRepository<Cartorio, Long> {
}
