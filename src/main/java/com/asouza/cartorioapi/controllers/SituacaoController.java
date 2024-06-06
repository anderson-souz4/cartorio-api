package com.asouza.cartorioapi.controllers;

import com.asouza.cartorioapi.dto.SituacaoDTO;
import com.asouza.cartorioapi.dto.SituacaoListDTO;
import com.asouza.cartorioapi.services.SituacaoCartorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/situacoes")
public class SituacaoController {

    private final SituacaoCartorioService service;

    @Autowired
    public SituacaoController(SituacaoCartorioService service) {
        this.service = service;
    }


    @GetMapping
    public Page<SituacaoListDTO> listarTodos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.listarTodas(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SituacaoDTO> buscarPorId(@PathVariable String id) {
        SituacaoDTO situacaoCartorio = service.buscarPorId(id);
        if (situacaoCartorio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(situacaoCartorio);
    }

    @PostMapping
    public SituacaoDTO criar(@RequestBody SituacaoDTO situacaoCartorio) {
        return service.criar(situacaoCartorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SituacaoDTO> atualizar(@PathVariable String id, @RequestBody SituacaoDTO situacaoCartorio) {
        SituacaoDTO situacao = service.atualizar(id, situacaoCartorio);
        if (situacao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(situacao);


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
