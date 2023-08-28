package br.com.escriba.cartorios.services;

import br.com.escriba.cartorios.dtos.AtribuicaoDTO;
import br.com.escriba.cartorios.dtos.AtribuicaoSimplificadoDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AtribuicaoService {

    Optional<AtribuicaoDTO> create (AtribuicaoDTO request);

    List<AtribuicaoSimplificadoDTO> getAll(Pageable pageable);

    Optional<AtribuicaoDTO> getById(String id);

    Optional<AtribuicaoDTO> getByNome(String nome);

    Optional<AtribuicaoDTO> update(String id, AtribuicaoDTO request);

    void delete(String id);
}
