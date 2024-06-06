package com.asouza.cartorioapi.services;

import com.asouza.cartorioapi.dto.SituacaoDTO;
import com.asouza.cartorioapi.dto.SituacaoListDTO;
import org.springframework.data.domain.Page;

public interface SituacaoCartorioService {
    Page<SituacaoListDTO>listarTodas(int page, int size);
    SituacaoDTO buscarPorId(String id);
    SituacaoDTO criar(SituacaoDTO situacaoCartorio);
    SituacaoDTO atualizar(String id, SituacaoDTO situacaoCartorio);
    boolean deletar(String id);
}
