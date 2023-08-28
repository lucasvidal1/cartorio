package br.com.escriba.cartorios.domain.cartorio;

import br.com.escriba.cartorios.domain.atribuicao.Atribuicao;
import br.com.escriba.cartorios.domain.situacao.Situacao;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "cartorios")
@Table(name = "cartorios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cartorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true, length = 150)
    private String nome;

    @Column(name = "observacao", length = 250)
    private String observacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "situacao_id", nullable = false)
    private Situacao situacao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "atribuicao_id", nullable = false)
    private List<Atribuicao> atribuicoes;
}
