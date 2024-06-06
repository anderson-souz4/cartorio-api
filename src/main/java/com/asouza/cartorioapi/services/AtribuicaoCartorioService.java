package com.asouza.cartorioapi.services;

import com.asouza.cartorioapi.dto.AtribuicaoDTO;
import com.asouza.cartorioapi.dto.AtribuicaoListDTO;
import org.springframework.data.domain.Page;

public interface AtribuicaoCartorioService {
    Page<AtribuicaoListDTO> listarTodas(int page, int size);
    AtribuicaoDTO buscarPorId(String id);
    AtribuicaoDTO criar(AtribuicaoDTO dto);
    AtribuicaoDTO atualizar(String id, AtribuicaoDTO dto);
    boolean deletar(String id);
}
