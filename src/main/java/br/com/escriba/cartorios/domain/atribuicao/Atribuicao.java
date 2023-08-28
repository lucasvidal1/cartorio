package br.com.escriba.cartorios.domain.atribuicao;

import lombok.*;

import javax.persistence.*;

@Entity(name = "atribuicoes")
@Table(name = "atribuicoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Atribuicao {
    @Id
    @Column(name = "id", length = 20)
    private String id;

    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;

    @Column(name = "situacao", nullable = false, columnDefinition = "boolean default true")
    private boolean situacao;
}
