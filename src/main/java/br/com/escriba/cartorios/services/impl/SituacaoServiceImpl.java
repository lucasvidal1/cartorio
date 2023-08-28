package br.com.escriba.cartorios.services.impl;

import br.com.escriba.cartorios.domain.atribuicao.Atribuicao;
import br.com.escriba.cartorios.domain.exception.ResourceDuplicateException;
import br.com.escriba.cartorios.domain.exception.ResourceIntegrityViolationException;
import br.com.escriba.cartorios.domain.exception.ResourceNotFoundException;
import br.com.escriba.cartorios.domain.situacao.Situacao;
import br.com.escriba.cartorios.dtos.AtribuicaoDTO;
import br.com.escriba.cartorios.dtos.SituacaoDTO;
import br.com.escriba.cartorios.repositories.SituacaoRepository;
import br.com.escriba.cartorios.services.SituacaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SituacaoServiceImpl implements SituacaoService {

    @Autowired
    private SituacaoRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<SituacaoDTO> create(SituacaoDTO request) {
        verificaCamposObrigatorios(request, "", true);

        Situacao situacao = mapper.map(request, Situacao.class);
        situacao = repository.save(situacao);

        return Optional.of(mapper.map(situacao, SituacaoDTO.class));
    }

    @Override
    public List<SituacaoDTO> getAll(Pageable pageable) {
        Page<Situacao> situacoes = repository.findAll(pageable);
        List<SituacaoDTO> responses = new ArrayList<>();

        situacoes.forEach(situacao -> {
            SituacaoDTO response = mapper.map(situacao, SituacaoDTO.class);
            responses.add(response);
        });

        return responses;
    }

    @Override
    public Optional<SituacaoDTO> getById(String id) {
        Optional<Situacao> situacao = repository.findById(id);
        return situacao.map(value -> mapper.map(value, SituacaoDTO.class));
    }

    @Override
    public Optional<SituacaoDTO> getByNome(String nome) {
        Optional<Situacao> situacao = repository.findByNome(nome);
        return situacao.map(value -> mapper.map(value, SituacaoDTO.class));
    }

    @Override
    public Optional<SituacaoDTO> update(String id, SituacaoDTO request) {
        verificaCamposObrigatorios(request, id, false);

        Optional<Situacao> situacao = repository.findById(id);
        if (situacao.isPresent()) {
            situacao.get().setNome(request.getNome());
            Situacao updatedSituacao = repository.save(situacao.get());
            return Optional.of(mapper.map(updatedSituacao, SituacaoDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        Optional<Situacao> situacao = repository.findById(id);

        if (situacao.isEmpty()) {
            throw new ResourceNotFoundException("Registro não encontrado.");
        }

        repository.deleteById(id);
    }

    private void verificaCamposObrigatorios(SituacaoDTO request, String id, boolean create) {
        if ((create) && (request.getId() == null))
            throw new ResourceIntegrityViolationException("O campo Id é obrigatório");
        if (request.getNome() == null)
            throw new ResourceIntegrityViolationException("O campo Nome é obrigatório");

        if (create){
            Optional<Situacao> idCadastrado = repository.findById(id);
            if (idCadastrado.isPresent())
                throw new ResourceDuplicateException("Registro já cadastrado");
        } else {
            Optional<Situacao> idCadastrado = repository.findById(request.getId());
            if (idCadastrado.isEmpty())
                throw new ResourceNotFoundException("Registro não encontrado");
        }

        Optional<Situacao> nomeCadastrado = repository.findByNome(request.getNome());
        if ((nomeCadastrado.isPresent()) && (!Objects.equals(nomeCadastrado.get().getNome(), request.getNome())))
            throw new ResourceDuplicateException("Nome já informado no registro com código: " + nomeCadastrado.get().getId());
    }
}
