package com.asouza.cartorioapi.services.impl;

import com.asouza.cartorioapi.dto.CartorioDTO;
import com.asouza.cartorioapi.dto.CartorioListDTO;
import com.asouza.cartorioapi.models.Cartorio;
import com.asouza.cartorioapi.repositories.CartorioRepository;
import com.asouza.cartorioapi.services.CartorioService;
import com.asouza.cartorioapi.services.exceptions.EntidadeNaoEncontradaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CartorioServiceImpl implements CartorioService {

    private final CartorioRepository repository;

    public CartorioServiceImpl(CartorioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<CartorioListDTO> listarTodas(int page, int size) {
        Page<Cartorio> cartorios = repository.findAll(PageRequest.of(page, size));
        return cartorios.map(cartorio -> new CartorioListDTO(cartorio.getId(), cartorio.getNome()));
    }

    @Override
    public CartorioDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(atribuicao -> new CartorioDTO(atribuicao.getId(), atribuicao.getNome(), atribuicao.getObservacao(), atribuicao.getSituacao(), atribuicao.getAtribuicoes()))
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cartório não encontrada, ID: ", id.toString()));
    }

    @Override
    public CartorioDTO criar(CartorioDTO dto) {
        Cartorio cartorio = new Cartorio();
        cartorio.setNome(dto.getNome());
        return new CartorioDTO(repository.save(cartorio).getId(), cartorio.getNome(), cartorio.getObservacao(), cartorio.getSituacao(), cartorio.getAtribuicoes());
    }

    @Override
    public CartorioDTO atualizar(Long id, CartorioDTO dto) {
        return repository.findById(id).map(cartorio -> {
            cartorio.setNome(dto.getNome());
            cartorio.setObservacao(dto.getObservacao());
            cartorio.setSituacao(dto.getSituacao());
            cartorio.setAtribuicoes(dto.getAtribuicoes());
            return new CartorioDTO(repository.save(cartorio).getId(), cartorio.getNome(), cartorio.getObservacao(), cartorio.getSituacao(), cartorio.getAtribuicoes());
        }).orElseThrow(() -> new EntidadeNaoEncontradaException("Atribuição não encontrada, ID: ", id.toString()));
    }

    @Override
    public boolean deletar(Long id) {
        repository.deleteById(id);
        return false;
    }
}
