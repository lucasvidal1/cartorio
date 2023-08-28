package br.com.escriba.cartorios.controllers;

import br.com.escriba.cartorios.dtos.AtribuicaoDTO;
import br.com.escriba.cartorios.dtos.AtribuicaoSimplificadoDTO;
import br.com.escriba.cartorios.services.AtribuicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atribuicoes")
public class AtribuicaoController {

    @Autowired
    private AtribuicaoService service;

    @PostMapping
    public ResponseEntity<AtribuicaoDTO> create(@RequestBody AtribuicaoDTO request){
        Optional<AtribuicaoDTO> response = service.create(request);

        return response.map(atribuicaoDTO -> new ResponseEntity<>(atribuicaoDTO, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<List<AtribuicaoSimplificadoDTO>> getAll(@PathVariable("page") int page){
        if (page > 0)
            page = page - 1;
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtribuicaoDTO> getById(@PathVariable("id") String id){
        Optional<AtribuicaoDTO> response = service.getById(id);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtribuicaoDTO> update (@PathVariable("id") String id,
                                                 @RequestBody AtribuicaoDTO request){
        Optional<AtribuicaoDTO> response = service.update(id, request);

        return response.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") String id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
