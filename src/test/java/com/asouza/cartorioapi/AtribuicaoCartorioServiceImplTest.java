package com.asouza.cartorioapi;

import com.asouza.cartorioapi.dto.AtribuicaoDTO;
import com.asouza.cartorioapi.dto.AtribuicaoListDTO;
import com.asouza.cartorioapi.models.Atribuicao;
import com.asouza.cartorioapi.repositories.AtribuicaoCartorioRepository;
import com.asouza.cartorioapi.services.exceptions.AtributoJaExistenteException;
import com.asouza.cartorioapi.services.exceptions.EntidadeNaoEncontradaException;
import com.asouza.cartorioapi.services.exceptions.ViolacaoDaIntegridadeDosDados;
import com.asouza.cartorioapi.services.impl.AtribuicaoCartorioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AtribuicaoCartorioServiceImplTest {

    @Mock
    private AtribuicaoCartorioRepository repository;

    private AtribuicaoCartorioServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new AtribuicaoCartorioServiceImpl(repository);
    }

    @Test
    public void listarTodasRetornaPageDeAtribuicaoListDTO() {
        Atribuicao atribuicao = new Atribuicao();
        atribuicao.setId("1");
        atribuicao.setNome("ATRIB1");
        Page<Atribuicao> page = new PageImpl<>(Arrays.asList(atribuicao));

        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<AtribuicaoListDTO> result = service.listarTodas(0, 10);

        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void buscarPorIdRetornaAtribuicaoDTOQuandoAtribuicaoExiste() {
        Atribuicao atribuicao = new Atribuicao();
        atribuicao.setId("1");
        atribuicao.setNome("ATRIB1");

        when(repository.findById("1")).thenReturn(Optional.of(atribuicao));

        AtribuicaoDTO result = service.buscarPorId("1");

        assertEquals("1", result.getId());
        assertEquals("ATRIB1", result.getNome());
        verify(repository, times(1)).findById("1");
    }

    @Test
    public void buscarPorIdLancaEntidadeNaoEncontradaExceptionQuandoAtribuicaoNaoExistir() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscarPorId("1"));

        verify(repository, times(1)).findById("1");
    }

    @Test
    public void criarLancaAtributoJaExistenteExceptionQuandoAtribuicaoComMesmoIdExiste() {
        AtribuicaoDTO dto = new AtribuicaoDTO("1", "ATRIB1");

        when(repository.findById("1")).thenReturn(Optional.of(new Atribuicao()));

        assertThrows(AtributoJaExistenteException.class, () -> service.criar(dto));

        verify(repository, times(1)).findById("1");
    }

    @Test
    public void criarSalvaERetornaAtribuicaoDTOQuandoAtribuicaoNaoExiste() {
        AtribuicaoDTO dto = new AtribuicaoDTO("1", "ATRIB1");

        when(repository.findById("1")).thenReturn(Optional.empty());
        when(repository.save(any(Atribuicao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AtribuicaoDTO result = service.criar(dto);

        assertEquals("1", result.getId());
        assertEquals("ATRIB1", result.getNome());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(any(Atribuicao.class));
    }
    @Test
    public void criarLancaAtributoJaExistenteExceptionQuandoNomeJaExiste() {
        AtribuicaoDTO dto = new AtribuicaoDTO("1", "ATRIB1");

        when(repository.findByNome("ATRIB1")).thenReturn(Optional.of(new Atribuicao()));

        assertThrows(AtributoJaExistenteException.class, () -> service.criar(dto));

        verify(repository, times(1)).findByNome("ATRIB1");
    }

    @Test
    public void atualizarLancaAtributoJaExistenteExceptionQuandoNomeJaExisteParaIdDiferente() {
        AtribuicaoDTO dto = new AtribuicaoDTO("2", "ATRIB1");

        Atribuicao existingAtribuicao = new Atribuicao();
        existingAtribuicao.setId("1");
        existingAtribuicao.setNome("ATRIB1");

        when(repository.findById("2")).thenReturn(Optional.of(new Atribuicao()));
        when(repository.findByNome("ATRIB1")).thenReturn(Optional.of(existingAtribuicao));

        assertThrows(AtributoJaExistenteException.class, () -> service.atualizar("2", dto));

        verify(repository, times(1)).findById("2");
        verify(repository, times(1)).findByNome("ATRIB1");
    }

    @Test
    public void atualizarLancaEntidadeNaoEncontradaExceptionQuandoAtribuicaoNaoExiste() {
        AtribuicaoDTO dto = new AtribuicaoDTO("1", "ATRIB1");

        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.atualizar("1", dto));

        verify(repository, times(1)).findById("1");
    }

    @Test
    public void deletarRetornaTrueQuandoDeletarAtribuicao() {
        doNothing().when(repository).deleteById("1");

        boolean result = service.deletar("1");

        assertTrue(result);
        verify(repository, times(1)).deleteById("1");
    }

    @Test
    public void deletarLancaViolacaoDaIntegridadeDosDadosQuandoOcorreDataIntegrityViolation() {
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById("1");

        assertThrows(ViolacaoDaIntegridadeDosDados.class, () -> service.deletar("1"));

        verify(repository, times(1)).deleteById("1");
    }
}