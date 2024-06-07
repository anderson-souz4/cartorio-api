package com.asouza.cartorioapi;

import com.asouza.cartorioapi.dto.CartorioDTO;
import com.asouza.cartorioapi.models.Cartorio;
import com.asouza.cartorioapi.repositories.CartorioRepository;
import com.asouza.cartorioapi.services.exceptions.AtributoJaExistenteException;
import com.asouza.cartorioapi.services.exceptions.EntidadeNaoEncontradaException;
import com.asouza.cartorioapi.services.impl.CartorioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartorioServiceImplTest {

    @Mock
    private CartorioRepository repository;

    private CartorioServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CartorioServiceImpl(repository);

        // Mock the findById method of the repository
        when(repository.findById(anyLong())).thenAnswer(invocation -> {
            Cartorio cartorio = new Cartorio();
            cartorio.setId(invocation.getArgument(0));
            cartorio.setNome("Test");
            return Optional.of(cartorio);
        });

        // Mock the findByNome method of the repository
        when(repository.findByNome(anyString())).thenAnswer(invocation -> {
            if ("Test".equals(invocation.getArgument(0))) {
                Cartorio cartorio = new Cartorio();
                cartorio.setId(1L);
                cartorio.setNome("Test");
                return Optional.of(cartorio);
            } else {
                return Optional.empty();
            }
        });

        // Mock the save method of the repository
        when(repository.save(any(Cartorio.class))).thenAnswer(invocation -> {
            Cartorio cartorio = invocation.getArgument(0);
            cartorio.setId(1L);
            return cartorio;
        });
    }

    @Test
    public void listarTodas_returnsPageOfCartorioListDTO() {
        Cartorio cartorio = new Cartorio();
        cartorio.setId(1L);
        cartorio.setNome("Test");
        Page<Cartorio> page = new PageImpl<>(Arrays.asList(cartorio));

        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        var result = service.listarTodas(0, 10);

        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void buscarPorId_returnsCartorioDTO_whenCartorioExists() {
        Cartorio cartorio = new Cartorio();
        cartorio.setId(1L);
        cartorio.setNome("Test");

        when(repository.findById(1L)).thenReturn(Optional.of(cartorio));

        CartorioDTO result = service.buscarPorId(1L);

        assertEquals(1L, result.getId());
        assertEquals("Test", result.getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void buscarPorId_throwsEntidadeNaoEncontradaException_whenCartorioDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscarPorId(1L));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void criarSalvaERetornaCartorioDTO() {
        CartorioDTO dto = new CartorioDTO(null, "UniqueTestName", null, null, null);

        Cartorio cartorio = new Cartorio();
        cartorio.setId(1L);
        cartorio.setNome(dto.getNome());

        when(repository.save(any(Cartorio.class))).thenReturn(cartorio);

        CartorioDTO savedDto = service.criar(dto);

        assertEquals(dto.getNome(), savedDto.getNome());
        verify(repository, times(1)).save(any(Cartorio.class));
    }

    @Test
    public void criarLanacAtributoJaExistenteExceptionQuandoNomeJaExiste() {
        CartorioDTO dto = new CartorioDTO(1L, "Test", null, null, null);

        when(repository.findByNome("Test")).thenReturn(Optional.of(new Cartorio()));

        assertThrows(AtributoJaExistenteException.class, () -> service.criar(dto));

        verify(repository, times(1)).findByNome("Test");
    }

    @Test
    public void atualizarLancaAtributoJaExistenteExceptionQuandoNomeJaExisteParaIdDiferente() {
        CartorioDTO dto = new CartorioDTO(2L, "Test", null, null, null);

        Cartorio existingCartorio = new Cartorio();
        existingCartorio.setId(1L);
        existingCartorio.setNome("Test");

        Cartorio cartorioToUpdate = new Cartorio();
        cartorioToUpdate.setId(2L);
        cartorioToUpdate.setNome("Different");

        when(repository.findById(2L)).thenReturn(Optional.of(cartorioToUpdate));
        when(repository.findByNome("Test")).thenReturn(Optional.of(existingCartorio));

        assertThrows(AtributoJaExistenteException.class, () -> service.atualizar(2L, dto));
    }


    @Test
    public void atualizarLancaEntidadeNaoEncontradaExceptionQuandoCartorioDoesNotExist() {
        CartorioDTO dto = new CartorioDTO(1L, "Test", null, null, null);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.atualizar(1L, dto));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void deletar_deletesCartorio_whenCartorioExists() {
        doNothing().when(repository).deleteById(1L);

        service.deletar(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void deletarLancaEntidadeNaoEncontradaExceptionQuandoCartorioDoesNotExist() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(1L);

        assertThrows(EntidadeNaoEncontradaException.class, () -> service.deletar(1L));

        verify(repository, times(1)).deleteById(1L);
    }

}