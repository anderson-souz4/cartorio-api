package com.asouza.cartorioapi.services.impl;

import com.asouza.cartorioapi.dto.SituacaoDTO;
import com.asouza.cartorioapi.dto.SituacaoListDTO;
import com.asouza.cartorioapi.models.Situacao;
import com.asouza.cartorioapi.repositories.SituacaoCartorioRepository;
import com.asouza.cartorioapi.services.SituacaoCartorioService;
import com.asouza.cartorioapi.services.exceptions.AtributoJaExistenteException;
import com.asouza.cartorioapi.services.exceptions.EntidadeNaoEncontradaException;
import com.asouza.cartorioapi.services.exceptions.ViolacaoDaIntegridadeDosDados;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SituacaoCartorioServiceImpl implements SituacaoCartorioService {

    private final SituacaoCartorioRepository repository;

    public SituacaoCartorioServiceImpl(SituacaoCartorioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<SituacaoListDTO> listarTodas(int page, int size) {
        Page<Situacao> situacoes = repository.findAll(PageRequest.of(page, size));
        return situacoes.map(situacao -> new SituacaoListDTO(situacao.getId(), situacao.getNome()));
    }

    @Override
    public SituacaoDTO buscarPorId(String id) {
        return repository.findById(id)
                .map(situacao -> new SituacaoDTO(situacao.getId(), situacao.getNome()))
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Situacao não encontrada, ID: ", id));
    }

    @Override
    public SituacaoDTO criar(SituacaoDTO situacaoDTO) {
        repository.findById(situacaoDTO.getId()).ifPresent(atribuicao -> {
            throw new AtributoJaExistenteException("Registro já cadastrado.");
        });
        repository.findByNome(situacaoDTO.getNome()).ifPresent(situacao -> {
            throw new AtributoJaExistenteException("Nome já informado no registro com código " + situacao.getId() + ".");
        });

        Situacao situacao = new Situacao();
        situacao.setId(situacaoDTO.getId());
        situacao.setNome(situacaoDTO.getNome());
        return new SituacaoDTO(repository.save(situacao).getId(), situacao.getNome());
    }

    @Override
    public SituacaoDTO atualizar(String id, SituacaoDTO situacaoDTO) {
        return repository.findById(id).map(situacao -> {
            repository.findByNome(situacaoDTO.getNome()).ifPresent(existingSituacao -> {
                if (!existingSituacao.getId().equals(id)) {
                    throw new AtributoJaExistenteException("Nome já informado no registro com código " + existingSituacao.getId() + ".");
                }
            });
            situacao.setNome(situacaoDTO.getNome());
            return new SituacaoDTO(repository.save(situacao).getId(), situacao.getNome());
        }).orElseThrow(() -> new EntidadeNaoEncontradaException("Situacao não encontrada, ID: ", id));
    }


    @Override
    public boolean deletar(String id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (DataIntegrityViolationException ex) {
            throw new ViolacaoDaIntegridadeDosDados("Registro utilizado em outro cadastro.");
        }
    }
}
