package br.com.escriba.cartorios.services.impl;

import br.com.escriba.cartorios.domain.atribuicao.Atribuicao;
import br.com.escriba.cartorios.domain.exception.ResourceDuplicateException;
import br.com.escriba.cartorios.domain.exception.ResourceIntegrityViolationException;
import br.com.escriba.cartorios.domain.exception.ResourceNotFoundException;
import br.com.escriba.cartorios.dtos.AtribuicaoDTO;
import br.com.escriba.cartorios.dtos.AtribuicaoSimplificadoDTO;
import br.com.escriba.cartorios.repositories.AtribuicaoRepository;
import br.com.escriba.cartorios.services.AtribuicaoService;
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
public class AtribuicaoServiceImpl implements AtribuicaoService {

    @Autowired
    private AtribuicaoRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<AtribuicaoDTO> create(AtribuicaoDTO request) {
        request.setSituacao(true);

        verificaCamposObrigatorios(request, "", true);

        Atribuicao atribuicao = mapper.map(request, Atribuicao.class);
        atribuicao = repository.save(atribuicao);

        return Optional.of(mapper.map(atribuicao, AtribuicaoDTO.class));
    }

    @Override
    public List<AtribuicaoSimplificadoDTO> getAll(Pageable pageable) {
        Page<Atribuicao> atribuicoes = repository.findAll(pageable);
        List<AtribuicaoSimplificadoDTO> responses = new ArrayList<>();

        atribuicoes.forEach(atribuicao -> {
            AtribuicaoSimplificadoDTO response = mapper.map(atribuicao, AtribuicaoSimplificadoDTO.class);
            responses.add(response);
        });

        return responses;
    }

    @Override
    public Optional<AtribuicaoDTO> getById(String id) {
        Optional<Atribuicao> atribuicao = repository.findById(id);
        return atribuicao.map(value -> mapper.map(value, AtribuicaoDTO.class));
    }

    @Override
    public Optional<AtribuicaoDTO> getByNome(String nome) {
        Optional<Atribuicao> atribuicao = repository.findByNome(nome);
        return atribuicao.map(value -> mapper.map(value, AtribuicaoDTO.class));
    }

    @Override
    public Optional<AtribuicaoDTO> update(String id, AtribuicaoDTO request) {
        verificaCamposObrigatorios(request, id, false);

        Optional<Atribuicao> atribuicao = repository.findById(id);
        if (atribuicao.isPresent()) {
            atribuicao.get().setNome(request.getNome());
            atribuicao.get().setSituacao(request.isSituacao());
            Atribuicao updatedAtribuicao = repository.save(atribuicao.get());
            return Optional.of(mapper.map(updatedAtribuicao, AtribuicaoDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        Optional<Atribuicao> atribuicao = repository.findById(id);

        if (atribuicao.isEmpty()) {
            throw new ResourceNotFoundException("Registro não encontrado.");
        }

        repository.deleteById(id);
    }

    private void verificaCamposObrigatorios(AtribuicaoDTO request, String id, boolean create) {
        if ((create) && (request.getId() == null))
            throw new ResourceIntegrityViolationException("O campo Id é obrigatório");
        if (request.getNome() == null)
            throw new ResourceIntegrityViolationException("O campo Nome é obrigatório");

        if (create){
            Optional<Atribuicao> idCadastrado = repository.findById(request.getId());
            if (idCadastrado.isPresent())
                throw new ResourceDuplicateException("Registro já cadastrado");
        } else {
            Optional<Atribuicao> idCadastrado = repository.findById(id);
            if (idCadastrado.isEmpty())
                throw new ResourceNotFoundException("Registro não encontrado");
        }

        Optional<Atribuicao> nomeCadastrado = repository.findByNome(request.getNome());
        if ((nomeCadastrado.isPresent()) && (!Objects.equals(nomeCadastrado.get().getNome(), request.getNome())))
            throw new ResourceDuplicateException("Nome já informado no registro com código: " + nomeCadastrado.get().getId());
    }
}
