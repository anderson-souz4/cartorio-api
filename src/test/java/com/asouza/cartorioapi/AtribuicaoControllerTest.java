package com.asouza.cartorioapi;

import com.asouza.cartorioapi.controllers.AtribuicaoController;
import com.asouza.cartorioapi.dto.AtribuicaoDTO;
import com.asouza.cartorioapi.dto.AtribuicaoListDTO;
import com.asouza.cartorioapi.services.AtribuicaoCartorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AtribuicaoControllerTest {

    @InjectMocks
    private AtribuicaoController controller;

    @Mock
    private AtribuicaoCartorioService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void listarTodosRetornaPageOfAtribuicaoListDTO() {
        when(service.listarTodas(anyInt(), anyInt())).thenReturn(mock(Page.class));
        controller.listarTodos(0, 10);
        verify(service, times(1)).listarTodas(0, 10);
    }

    @Test
    public void buscarPorIdRetornaAtribuicaoDTOQuandoAtribuicaoExiste() {
        AtribuicaoDTO dto = new AtribuicaoDTO();
        when(service.buscarPorId(anyString())).thenReturn(dto);
        ResponseEntity<AtribuicaoDTO> response = controller.buscarPorId("1");
        assertEquals(dto, response.getBody());
    }

    @Test
    public void buscarPorIdretornaNotFoundQuandoAtribuicaoNaoExiste() {
        when(service.buscarPorId(anyString())).thenReturn(null);
        ResponseEntity<AtribuicaoDTO> response = controller.buscarPorId("1");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void criarRetornaAtribuicaoDTO() {
        AtribuicaoDTO dto = new AtribuicaoDTO();
        when(service.criar(any(AtribuicaoDTO.class))).thenReturn(dto);
        AtribuicaoDTO response = controller.criar(dto);
        assertEquals(dto, response);
    }

    @Test
    public void atualizarRetornaAtribuicaoDTOQuandoAtribuicaoExists() {
        AtribuicaoDTO dto = new AtribuicaoDTO();
        when(service.atualizar(anyString(), any(AtribuicaoDTO.class))).thenReturn(dto);
        ResponseEntity<AtribuicaoDTO> response = controller.atualizar("1", dto);
        assertEquals(dto, response.getBody());
    }

    @Test
    public void atualizarRetornaNotFoundQuandoAtribuicaoNao() {
        when(service.atualizar(anyString(), any(AtribuicaoDTO.class))).thenReturn(null);
        ResponseEntity<AtribuicaoDTO> response = controller.atualizar("1", new AtribuicaoDTO());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void deletarChamaServicoDeDeletar() {
        controller.deletar("1");
        verify(service, times(1)).deletar("1");
    }

    @Test
    public void statusRetornaOk() {
        ResponseEntity<Void> response = controller.status();
        assertEquals(200, response.getStatusCodeValue());
    }
}