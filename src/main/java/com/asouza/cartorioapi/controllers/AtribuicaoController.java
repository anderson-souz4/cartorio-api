package com.asouza.cartorioapi.controllers;

import com.asouza.cartorioapi.dto.AtribuicaoListDTO;
import com.asouza.cartorioapi.dto.AtribuicaoDTO;
import com.asouza.cartorioapi.services.AtribuicaoCartorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atribuicoes")
public class AtribuicaoController {

    private final AtribuicaoCartorioService service;

    @Autowired
    public AtribuicaoController(AtribuicaoCartorioService service) {
        this.service = service;
    }


    @GetMapping
    public Page<AtribuicaoListDTO> listarTodos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.listarTodas(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtribuicaoDTO> buscarPorId(@PathVariable String id) {
        AtribuicaoDTO dto = service.buscarPorId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public AtribuicaoDTO criar(@RequestBody AtribuicaoDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtribuicaoDTO> atualizar(@PathVariable String id, @RequestBody AtribuicaoDTO dto) {
        AtribuicaoDTO atribuicao = service.atualizar(id, dto);
        if (atribuicao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atribuicao);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
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
