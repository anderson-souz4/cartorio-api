package com.asouza.cartorioapi.services;

import com.asouza.cartorioapi.dto.CartorioDTO;
import com.asouza.cartorioapi.dto.CartorioListDTO;
import org.springframework.data.domain.Page;

public interface CartorioService {
    Page<CartorioListDTO> listarTodas(int page, int size);
    CartorioDTO buscarPorId(Long id);
    CartorioDTO criar(CartorioDTO dto);
    CartorioDTO atualizar(Long id, CartorioDTO dto);
    boolean deletar(Long id);
}
