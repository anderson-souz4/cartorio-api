package com.asouza.cartorioapi;

import com.asouza.cartorioapi.dto.SituacaoDTO;
import com.asouza.cartorioapi.models.Situacao;
import com.asouza.cartorioapi.repositories.SituacaoCartorioRepository;
import com.asouza.cartorioapi.services.exceptions.AtributoJaExistenteException;
import com.asouza.cartorioapi.services.exceptions.EntidadeNaoEncontradaException;
import com.asouza.cartorioapi.services.exceptions.ViolacaoDaIntegridadeDosDados;
import com.asouza.cartorioapi.services.impl.SituacaoCartorioServiceImpl;
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

public class SituacaoCartorioServiceImplTest {

    @Mock
    private SituacaoCartorioRepository repository;

    private SituacaoCartorioServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new SituacaoCartorioServiceImpl(repository);
    }

    @Test
    public void listarTodasRetornaPageDeSituacaoListDTO() {
        Situacao situacao = new Situacao();
        situacao.setId("1");
        situacao.setNome("SIT_ATIVO");
        Page<Situacao> page = new PageImpl<>(Arrays.asList(situacao));

        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        var result = service.listarTodas(0, 10);

        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void buscarPorIdRetornaSituacaoDTOQuandoSituacaoExiste() {
        Situacao situacao = new Situacao();
        situacao.setId("1");
        situacao.setNome("SIT_ATIVO");

        when(repository.findById("1")).thenReturn(Optional.of(situacao));

        SituacaoDTO result = service.buscarPorId("1");

        assertEquals("1", result.getId());
        assertEquals("SIT_ATIVO", result.getNome());
        verify(repository, times(1)).findById("1");
    }

    @Test
    public void buscarPorIdLancaEntidadeNaoEncontradaExceptionQunandoSituacaoNaoExiste() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscarPorId("1"));

        verify(repository, times(1)).findById("1");
    }

    @Test
    public void criarLancaAtributoJaExistenteExceptionQuandoSituacaoComMesmoIdExiste() {
        SituacaoDTO dto = new SituacaoDTO("1", "SIT_ATIVO");

        when(repository.findById("1")).thenReturn(Optional.of(new Situacao()));

        assertThrows(AtributoJaExistenteException.class, () -> service.criar(dto));

        verify(repository, times(1)).findById("1");
    }

    @Test
    public void criarSalvaERetornaSituacaoDTOQuandoSituacaoNaoExiste() {
        SituacaoDTO dto = new SituacaoDTO("1", "SIT_ATIVO");

        when(repository.findById("1")).thenReturn(Optional.empty());
        when(repository.save(any(Situacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SituacaoDTO result = service.criar(dto);

        assertEquals("1", result.getId());
        assertEquals("SIT_ATIVO", result.getNome());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(any(Situacao.class));
    }

    @Test
    public void criarLancaAtributoJaExistenteExceptionQuandoSituacaoComMesmoNomeExiste() {
        SituacaoDTO dto = new SituacaoDTO("1", "SIT_ATIVO");

        when(repository.findByNome("SIT_ATIVO")).thenReturn(Optional.of(new Situacao()));

        assertThrows(AtributoJaExistenteException.class, () -> service.criar(dto));

        verify(repository, times(1)).findByNome("SIT_ATIVO");
    }

    @Test
    public void atualizarLancaAtributoJaExistenteExceptionQuandoNomeJaExisteParaIdDiferente() {
        SituacaoDTO dto = new SituacaoDTO("1", "SIT_ATIVO");

        Situacao existingSituacao = new Situacao();
        existingSituacao.setId("2");
        existingSituacao.setNome("SIT_ATIVO");

        when(repository.findById("1")).thenReturn(Optional.of(new Situacao()));
        when(repository.findByNome("SIT_ATIVO")).thenReturn(Optional.of(existingSituacao));

        assertThrows(AtributoJaExistenteException.class, () -> service.atualizar("1", dto));

        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).findByNome("SIT_ATIVO");
    }
    @Test
    public void atualizarLancaEntidadeNaoEncontradaExceptionQuandoSituacaoNaoExiste() {
        SituacaoDTO dto = new SituacaoDTO("1", "SIT_ATIVO");

        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.atualizar("1", dto));

        verify(repository, times(1)).findById("1");
    }

    @Test
    public void deletarRetornaTrueQuandoOcorreADelecao() {
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