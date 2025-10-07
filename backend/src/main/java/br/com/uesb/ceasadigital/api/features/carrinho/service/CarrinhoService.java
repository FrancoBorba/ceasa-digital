package br.com.uesb.ceasadigital.api.features.carrinho.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.features.carrinho.dto.request.CarrinhoRequestDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.repository.CarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(CarrinhoService.class.getName());

    public List<CarrinhoResponseDTO> findAllCarrinhos() {
        logger.info("Find All Carrinhos");
        
        List<Carrinho> allCarrinhos = carrinhoRepository.findAll();
        
        return allCarrinhos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public CarrinhoResponseDTO findCarrinhoById(Long id) {
        logger.info("Find carrinho with id: " + id);
        
        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado com id: " + id));
        
        return convertToResponseDTO(carrinho);
    }

    public CarrinhoResponseDTO findCarrinhoByUsuarioId(Long usuarioId) {
        logger.info("Find carrinho by usuario id: " + usuarioId);
        
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado para usuário com id: " + usuarioId));
        
        return convertToResponseDTO(carrinho);
    }

    public CarrinhoResponseDTO createCarrinho(CarrinhoRequestDTO carrinhoRequestDTO) {
        logger.info("Create a carrinho");
        
        User usuario = userRepository.findById(carrinhoRequestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + carrinhoRequestDTO.getUsuarioId()));
        
        Carrinho carrinho = new Carrinho(usuario);
        Carrinho savedCarrinho = carrinhoRepository.save(carrinho);
        
        return convertToResponseDTO(savedCarrinho);
    }

    public CarrinhoResponseDTO updateCarrinho(Long id, CarrinhoRequestDTO carrinhoRequestDTO) {
        logger.info("Update carrinho with id: " + id);
        
        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado com id: " + id));
        
        User usuario = userRepository.findById(carrinhoRequestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + carrinhoRequestDTO.getUsuarioId()));
        
        carrinho.setUsuario(usuario);
        Carrinho updatedCarrinho = carrinhoRepository.save(carrinho);
        
        return convertToResponseDTO(updatedCarrinho);
    }

    public void deleteCarrinho(Long id) {
        logger.info("Delete carrinho with id: " + id);
        
        if (!carrinhoRepository.existsById(id)) {
            throw new RuntimeException("Carrinho não encontrado com id: " + id);
        }
        
        carrinhoRepository.deleteById(id);
    }

    private CarrinhoResponseDTO convertToResponseDTO(Carrinho carrinho) {
        CarrinhoResponseDTO responseDTO = new CarrinhoResponseDTO();
        responseDTO.setId(carrinho.getId());
        responseDTO.setUsuarioId(carrinho.getUsuario().getId());
        responseDTO.setUsuarioNome(carrinho.getUsuario().getName());
        responseDTO.setCriadoEm(carrinho.getCriadoEm());
        responseDTO.setAtualizadoEm(carrinho.getAtualizadoEm());
        return responseDTO;
    }
}