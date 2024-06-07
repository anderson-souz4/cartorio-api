package com.asouza.cartorioapi;

import com.asouza.cartorioapi.controllers.CartorioController;
import com.asouza.cartorioapi.dto.CartorioDTO;
import com.asouza.cartorioapi.services.CartorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartorioControllerTest {

    @InjectMocks
    private CartorioController controller;

    @Mock
    private CartorioService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void listarTodosRetornaPageOfCartorioListDTO() {
        when(service.listarTodas(anyInt(), anyInt())).thenReturn(mock(Page.class));
        controller.listarTodos(0, 10);
        verify(service, times(1)).listarTodas(0, 10);
    }

    @Test
    public void buscarPorIdRetornaCartorioDTOQuandoCartorioExiste() {
        CartorioDTO dto = new CartorioDTO();
        when(service.buscarPorId(anyLong())).thenReturn(dto);
        ResponseEntity<CartorioDTO> response = controller.buscarPorId(1L);
        assertEquals(dto, response.getBody());
    }

    @Test
    public void buscarPorIdRetornaNotFoundQuandoCartorioNaoExiste() {
        when(service.buscarPorId(anyLong())).thenReturn(null);
        ResponseEntity<CartorioDTO> response = controller.buscarPorId(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void criarRetornaCartorioDTO() {
        CartorioDTO dto = new CartorioDTO();
        when(service.criar(any(CartorioDTO.class))).thenReturn(dto);
        CartorioDTO response = controller.criar(dto);
        assertEquals(dto, response);
    }

    @Test
    public void atualizarRetornaCartorioDTOQuandoCartorioExists() {
        CartorioDTO dto = new CartorioDTO();
        when(service.atualizar(anyLong(), any(CartorioDTO.class))).thenReturn(dto);
        ResponseEntity<CartorioDTO> response = controller.atualizar(1L, dto);
        assertEquals(dto, response.getBody());
    }

    @Test
    public void atualizarRetornaNotFoundQuandoCartorioNaoExiste() {
        when(service.atualizar(anyLong(), any(CartorioDTO.class))).thenReturn(null);
        ResponseEntity<CartorioDTO> response = controller.atualizar(1L, new CartorioDTO());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void deletarChamaServicoDeDeletar() {
        controller.deletar(1L);
        verify(service, times(1)).deletar(1L);
    }

    @Test
    public void statusRetornaOk() {
        ResponseEntity<Void> response = controller.status();
        assertEquals(200, response.getStatusCodeValue());
    }
}