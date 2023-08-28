package br.com.escriba.cartorios.services.impl;

import br.com.escriba.cartorios.domain.atribuicao.Atribuicao;
import br.com.escriba.cartorios.domain.cartorio.Cartorio;
import br.com.escriba.cartorios.domain.exception.ResourceDuplicateException;
import br.com.escriba.cartorios.domain.exception.ResourceIntegrityViolationException;
import br.com.escriba.cartorios.domain.exception.ResourceNotFoundException;
import br.com.escriba.cartorios.domain.situacao.Situacao;
import br.com.escriba.cartorios.dtos.CartorioDTO;
import br.com.escriba.cartorios.dtos.CartorioSimplificadoDTO;
import br.com.escriba.cartorios.repositories.AtribuicaoRepository;
import br.com.escriba.cartorios.repositories.CartorioRepository;
import br.com.escriba.cartorios.repositories.SituacaoRepository;
import br.com.escriba.cartorios.services.CartorioService;
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
public class CartorioServiceImpl implements CartorioService {

    @Autowired
    private CartorioRepository repository;

    @Autowired
    private SituacaoRepository situacaoRepository;

    @Autowired
    private AtribuicaoRepository atribuicaoRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<CartorioDTO> create(CartorioDTO request) {
        verificaCamposObrigatorios(request, 0L, true);

        Cartorio cartorio = mapper.map(request, Cartorio.class);
        cartorio = repository.save(cartorio);

        return repository.findById(cartorio.getId()).map(value -> mapper.map(value, CartorioDTO.class));
    }

    @Override
    public List<CartorioSimplificadoDTO> getAll(Pageable pageable) {
        Page<Cartorio> cartorios = repository.findAll(pageable);
        List<CartorioSimplificadoDTO> responses = new ArrayList<>();

        cartorios.forEach(cartorio -> {
            CartorioSimplificadoDTO response = mapper.map(cartorio, CartorioSimplificadoDTO.class);
            responses.add(response);
        });

        return responses;
    }

    @Override
    public Optional<CartorioDTO> getById(Long id) {
        Optional<Cartorio> cartorio = repository.findById(id);
        return cartorio.map(value -> mapper.map(value, CartorioDTO.class));
    }

    @Override
    public Optional<CartorioDTO> getByNome(String nome) {
        Optional<Cartorio> cartorio = repository.findByNome(nome);
        return cartorio.map(value -> mapper.map(value, CartorioDTO.class));
    }

    @Override
    public Optional<CartorioDTO> update(Long id, CartorioDTO request) {
        verificaCamposObrigatorios(request, id, false);

        Optional<Cartorio> cartorio = repository.findById(id);
        if (cartorio.isPresent()) {
            cartorio.get().setNome(request.getNome());
            cartorio.get().setObservacao(request.getObservacao());
            cartorio.get().setSituacao(request.getSituacao());
            cartorio.get().setAtribuicoes(request.getAtribuicoes());
            Cartorio updatedCartorio = repository.save(cartorio.get());
            return Optional.of(mapper.map(updatedCartorio, CartorioDTO.class));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        Optional<Cartorio> cartorio = repository.findById(id);

        if (cartorio.isEmpty()) {
            throw new ResourceNotFoundException("Registro não encontrado.");
        }

        repository.deleteById(id);
    }

    private void verificaCamposObrigatorios(CartorioDTO request, Long id, boolean create) {
        if (request.getNome() == null)
            throw new ResourceIntegrityViolationException("O campo Nome é obrigatório");
        Situacao situacao = request.getSituacao();
        if ((situacao == null) || (situacao.getId() == null))
            throw new ResourceIntegrityViolationException("O campo Situação é obrigatório");
        Optional<Situacao> existeSituacao = situacaoRepository.findById(situacao.getId());
        if (existeSituacao.isEmpty())
            throw new ResourceIntegrityViolationException("Verifique a Situação informada, o Id: " + situacao.getId() + " não existe");

        List<Atribuicao> atribuicoes = request.getAtribuicoes();
        if (atribuicoes == null)
            throw new ResourceIntegrityViolationException("É obrigatório informar pelo menos uma atribuição");
        atribuicoes.forEach(atribuicao -> {
            if (atribuicao.getId() == null)
                throw new ResourceIntegrityViolationException("Verifique as atribuições informadas, o Id não pode ser nulo");
            Optional<Atribuicao> existeAtribuicao = atribuicaoRepository.findById(atribuicao.getId());
            if (existeAtribuicao.isEmpty())
                throw new ResourceIntegrityViolationException("Verifique as atribuições informadas, o Id: " + atribuicao.getId() + " não existe");
        });

        if (!create){
            Optional<Cartorio> idCadastrado = repository.findById(id);
            if (idCadastrado.isEmpty())
                throw new ResourceNotFoundException("Registro não encontrado");
        }

        Optional<Cartorio> nomeCadastrado = repository.findByNome(request.getNome());
        if ((nomeCadastrado.isPresent()) && (!Objects.equals(nomeCadastrado.get().getNome(), request.getNome())))
            throw new ResourceDuplicateException("Nome já informado no registro com código: " + nomeCadastrado.get().getId());
    }
}
