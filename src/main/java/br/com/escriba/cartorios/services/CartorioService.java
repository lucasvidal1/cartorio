package br.com.escriba.cartorios.services;

import br.com.escriba.cartorios.dtos.CartorioDTO;
import br.com.escriba.cartorios.dtos.CartorioSimplificadoDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CartorioService {

    Optional<CartorioDTO> create (CartorioDTO request);

    List<CartorioSimplificadoDTO> getAll(Pageable pageable);

    Optional<CartorioDTO> getById(Long id);

    Optional<CartorioDTO> getByNome(String nome);

    Optional<CartorioDTO> update(Long id, CartorioDTO request);

    void delete(Long id);
}
