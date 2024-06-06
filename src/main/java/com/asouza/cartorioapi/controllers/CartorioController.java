package com.asouza.cartorioapi.controllers;

import com.asouza.cartorioapi.dto.CartorioDTO;
import com.asouza.cartorioapi.dto.CartorioListDTO;
import com.asouza.cartorioapi.services.CartorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartorios")
public class CartorioController {

    private final CartorioService service;

    @Autowired
    public CartorioController(CartorioService service) {
        this.service = service;
    }


    @GetMapping
    public Page<CartorioListDTO> listarTodos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.listarTodas(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartorioDTO> buscarPorId(@PathVariable Long id) {
        CartorioDTO dto = service.buscarPorId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public CartorioDTO criar(@RequestBody CartorioDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartorioDTO> atualizar(@PathVariable Long id, @RequestBody CartorioDTO dto) {
        CartorioDTO cartorio = service.atualizar(id, dto);
        if (cartorio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartorio);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status")
    public ResponseEntity<Void> status() {
        return ResponseEntity.ok().build();
    }
}
