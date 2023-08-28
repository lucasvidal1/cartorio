package br.com.escriba.cartorios.domain.situacao;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "situacoes")
@Table(name = "situacoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Situacao {
    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;
}