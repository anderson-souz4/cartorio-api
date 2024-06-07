package com.asouza.cartorioapi;

import com.asouza.cartorioapi.controllers.SituacaoController;
import com.asouza.cartorioapi.dto.SituacaoDTO;
import com.asouza.cartorioapi.dto.SituacaoListDTO;
import com.asouza.cartorioapi.services.SituacaoCartorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SituacaoControllerTest {

    @InjectMocks
    private SituacaoController controller;

    @Mock
    private SituacaoCartorioService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void listarTodosRetornaPageOfSituacaoListDTO() {
        when(service.listarTodas(anyInt(), anyInt())).thenReturn(mock(Page.class));
        controller.listarTodos(0, 10);
        verify(service, times(1)).listarTodas(0, 10);
    }

    @Test
    public void buscarPorIdRetornaSituacaoDTO_whenSituacaoExiste() {
        SituacaoDTO dto = new SituacaoDTO();
        when(service.buscarPorId(anyString())).thenReturn(dto);
        ResponseEntity<SituacaoDTO> response = controller.buscarPorId("1");
        assertEquals(dto, response.getBody());
    }

    @Test
    public void buscarPorIdRetornaNotFoundQuandoSituacaoNaoExiste() {
        when(service.buscarPorId(anyString())).thenReturn(null);
        ResponseEntity<SituacaoDTO> response = controller.buscarPorId("1");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void criarRetornaSituacaoDTO() {
        SituacaoDTO dto = new SituacaoDTO();
        when(service.criar(any(SituacaoDTO.class))).thenReturn(dto);
        SituacaoDTO response = controller.criar(dto);
        assertEquals(dto, response);
    }

    @Test
    public void atualizarRetornaSituacaoDTOQuandoSituacaoExiste() {
        SituacaoDTO dto = new SituacaoDTO();
        when(service.atualizar(anyString(), any(SituacaoDTO.class))).thenReturn(dto);
        ResponseEntity<SituacaoDTO> response = controller.atualizar("1", dto);
        assertEquals(dto, response.getBody());
    }

    @Test
    public void atualizarRetornaNotFound_whenSituacaoNaoExiste() {
        when(service.atualizar(anyString(), any(SituacaoDTO.class))).thenReturn(null);
        ResponseEntity<SituacaoDTO> response = controller.atualizar("1", new SituacaoDTO());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void deletarRetornaNoContentQuandoSituacaoExiste() {
        when(service.deletar(anyString())).thenReturn(true);
        ResponseEntity<Void> response = controller.deletar("1");
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void deletarRetornaNotFoundQuandoSituacaoNaoExiste() {
        when(service.deletar(anyString())).thenReturn(false);
        ResponseEntity<Void> response = controller.deletar("1");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void statusRetornaOk() {
        ResponseEntity<Void> response = controller.status();
        assertEquals(200, response.getStatusCodeValue());
    }
}