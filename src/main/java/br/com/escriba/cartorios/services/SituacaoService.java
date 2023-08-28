package br.com.escriba.cartorios.services;

import br.com.escriba.cartorios.dtos.SituacaoDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SituacaoService {

    Optional<SituacaoDTO> create (SituacaoDTO request);

    List<SituacaoDTO> getAll(Pageable pageable);

    Optional<SituacaoDTO> getById(String id);

    Optional<SituacaoDTO> getByNome(String nome);

    Optional<SituacaoDTO> update(String id, SituacaoDTO request);

    void delete(String id);
}
