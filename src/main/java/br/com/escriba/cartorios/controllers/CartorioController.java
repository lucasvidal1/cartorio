package br.com.escriba.cartorios.controllers;

import br.com.escriba.cartorios.dtos.CartorioDTO;
import br.com.escriba.cartorios.dtos.CartorioSimplificadoDTO;
import br.com.escriba.cartorios.services.CartorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartorios")
public class CartorioController {

    @Autowired
    private CartorioService service;

    @PostMapping
    public ResponseEntity<CartorioDTO> create(@RequestBody CartorioDTO request){
        Optional<CartorioDTO> response = service.create(request);

        return response.map(cartorioDTO -> new ResponseEntity<>(cartorioDTO, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<List<CartorioSimplificadoDTO>> getAll(@PathVariable("page") int page){
        if (page > 0)
            page = page - 1;
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartorioDTO> getById(@PathVariable("id") Long id){
        Optional<CartorioDTO> response = service.getById(id);

        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartorioDTO> update (@PathVariable("id") Long id,
                                               @RequestBody CartorioDTO request){
        Optional<CartorioDTO> response = service.update(id, request);

        return response.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
