package com.asouza.cartorioapi.services.impl;

import com.asouza.cartorioapi.dto.AtribuicaoDTO;
import com.asouza.cartorioapi.dto.AtribuicaoListDTO;
import com.asouza.cartorioapi.models.Atribuicao;
import com.asouza.cartorioapi.repositories.AtribuicaoCartorioRepository;
import com.asouza.cartorioapi.services.AtribuicaoCartorioService;
import com.asouza.cartorioapi.services.exceptions.AtributoJaExistenteException;
import com.asouza.cartorioapi.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AtribuicaoCartorioServiceImpl implements AtribuicaoCartorioService {

    private final AtribuicaoCartorioRepository repository;

    public AtribuicaoCartorioServiceImpl(AtribuicaoCartorioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<AtribuicaoListDTO> listarTodas(int page, int size) {
        Page<Atribuicao> atribuicoes = repository.findAll(PageRequest.of(page, size));
        return atribuicoes.map(atribuicao -> new AtribuicaoListDTO(atribuicao.getId(), atribuicao.getNome()));
    }


    @Override
    public AtribuicaoDTO buscarPorId(String id) {
        return repository.findById(id)
                .map(atribuicao -> new AtribuicaoDTO(atribuicao.getId(), atribuicao.getNome(), atribuicao.isSituacao()))
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Atribuição não encontrada, ID: ", id));
    }

    @Override
    public AtribuicaoDTO criar(AtribuicaoDTO dto) {
        repository.findById(dto.getId()).ifPresent(atribuicao -> {
            throw new AtributoJaExistenteException("Registro já cadastrado.");
        });

        repository.findByNome(dto.getNome()).ifPresent(atribuicao -> {
            throw new AtributoJaExistenteException("Nome já informado no registro com código " + atribuicao.getId() + ".");
        });

        Atribuicao atribuicao = new Atribuicao();
        atribuicao.setId(dto.getId());
        atribuicao.setNome(dto.getNome());
        atribuicao.setSituacao(true);
        repository.save(atribuicao);
        return new AtribuicaoDTO(atribuicao.getId(), atribuicao.getNome());
    }

    @Override
    public AtribuicaoDTO atualizar(String id, AtribuicaoDTO dto) {
        return repository.findById(id).map(atribuicao -> {
            repository.findByNome(dto.getNome()).ifPresent(existingAtribuicao -> {
                if (!existingAtribuicao.getId().equals(id)) {
                    throw new AtributoJaExistenteException("Nome já informado no registro com código " + existingAtribuicao.getId() + ".");
                }
            });
            atribuicao.setNome(dto.getNome());
            return new AtribuicaoDTO(repository.save(atribuicao).getId(), atribuicao.getNome());
        }).orElseThrow(() -> new EntidadeNaoEncontradaException("Atribuição não encontrada, ID: ", id));
    }

    @Override
    public boolean deletar(String id) {
        repository.deleteById(id);
        return false;
    }
}
