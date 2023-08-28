package br.com.escriba.cartorios.dtos;

import br.com.escriba.cartorios.domain.atribuicao.Atribuicao;
import br.com.escriba.cartorios.domain.situacao.Situacao;
import lombok.Data;

import java.util.List;

@Data
public class CartorioDTO {

    private Long id;

    private String nome;

    private String observacao;

    private Situacao situacao;

    private List<Atribuicao> atribuicoes;
}
