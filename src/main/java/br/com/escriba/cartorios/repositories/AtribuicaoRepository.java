package br.com.escriba.cartorios.repositories;

import br.com.escriba.cartorios.domain.atribuicao.Atribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtribuicaoRepository extends JpaRepository<Atribuicao, String> {
    public Optional<Atribuicao> findByNome(String nome);
}
