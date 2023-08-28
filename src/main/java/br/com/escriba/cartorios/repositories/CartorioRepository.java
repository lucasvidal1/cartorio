package br.com.escriba.cartorios.repositories;

import br.com.escriba.cartorios.domain.cartorio.Cartorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartorioRepository extends JpaRepository<Cartorio, Long> {
    public Optional<Cartorio> findByNome(String nome);
}
