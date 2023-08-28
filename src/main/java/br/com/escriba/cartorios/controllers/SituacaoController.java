package br.com.escriba.cartorios.controllers;

import br.com.escriba.cartorios.dtos.SituacaoDTO;
import br.com.escriba.cartorios.services.SituacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/situacoes")
public class SituacaoController {

    @Autowired
    private SituacaoService service;

    @PostMapping
    public ResponseEntity<SituacaoDTO> create(@RequestBody SituacaoDTO request){
        Optional<SituacaoDTO> response = service.create(request);

        return response.map(situacaoDTO -> new ResponseEntity<>(situacaoDTO, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<List<SituacaoDTO>> getAll(@PathVariable("page") int page){
        if (page > 0)
            page = page - 1;
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SituacaoDTO> getById(@PathVariable("id") String id){
        Optional<SituacaoDTO> response = service.getById(id);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SituacaoDTO> update (@PathVariable("id") String id,
                                                 @RequestBody SituacaoDTO request){
        Optional<SituacaoDTO> response = service.update(id, request);

        return response.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") String id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
