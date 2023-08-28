package br.com.escriba.cartorios.repositories;

import br.com.escriba.cartorios.domain.situacao.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SituacaoRepository extends JpaRepository<Situacao, String> {

    public Optional<Situacao> findByNome(String nome);
}
