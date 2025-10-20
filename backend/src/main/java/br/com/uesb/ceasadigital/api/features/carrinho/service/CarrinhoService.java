package br.com.uesb.ceasadigital.api.features.carrinho.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.common.exceptions.CarrinhoNotFoundException;
import br.com.uesb.ceasadigital.api.common.exceptions.InvalidCarrinhoOperationException;
import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.mapper.CarrinhoMapper;
import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.repository.CarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CarrinhoMapper carrinhoMapper;

    private final Logger logger = LoggerFactory.getLogger(CarrinhoService.class.getName());

    @Transactional(readOnly = true)
    public CarrinhoResponseDTO findCarrinho() {
        Carrinho carrinho = getCarrinho();
        return carrinhoMapper.toResponseDTO(carrinho);
    }

    @Transactional
    public void clearUserCarrinho() {
        Carrinho carrinho = getCarrinho();

        if (carrinho.getItens().isEmpty()) {
            throw new InvalidCarrinhoOperationException("O carrinho já está vazio.");
        }

        carrinho.getItens().clear();
    }

    public Carrinho getCarrinho() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            throw new CarrinhoNotFoundException("Usuário não autenticado.");
        }

        logger.info("Find carrinho by usuario: " + currentUser.getName());
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(currentUser.getId())
                .orElseGet(() -> criarCarrinhoParaUsuario(currentUser));
        return carrinho;
    }

    private Carrinho criarCarrinhoParaUsuario(User usuario) {
        logger.info("Carrinho não encontrado. Criando um novo para o usuário: {}", usuario.getName());
        Carrinho novoCarrinho = new Carrinho(usuario);
        return carrinhoRepository.save(novoCarrinho);
    }
}
