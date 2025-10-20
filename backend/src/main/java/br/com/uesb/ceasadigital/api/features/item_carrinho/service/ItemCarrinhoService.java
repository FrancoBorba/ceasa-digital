package br.com.uesb.ceasadigital.api.features.item_carrinho.service;

import java.math.BigDecimal;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.repository.CarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoAddItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoUpdateItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.response.CarrinhoItemResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.mapper.ItemCarrinhoMapper;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;
import br.com.uesb.ceasadigital.api.features.item_carrinho.repository.ItemCarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class ItemCarrinhoService {
  

  @Autowired
  CarrinhoService carrinhoService;

  @Autowired 
  ItemCarrinhoMapper mapper;
  
  @Autowired 
  ItemCarrinhoRepository repository;

  @Autowired
  CarrinhoRepository carrinhoRepository;

  @Autowired
  ProductRepository productRepository;

  @PersistenceContext
  private EntityManager entityManager;
  
  private final Logger logger = LoggerFactory.getLogger(CarrinhoService.class.getName());

  // Removido: não precisamos mais criar ofertas mock
  // A coluna oferta_produtor_id agora é nullable

  @Transactional
  public CarrinhoResponseDTO addItemInCarrinho(CarrinhoAddItemRequestDTO itemToAdd){

    Carrinho carrinho = carrinhoService.getCarrinho();

    ItemCarrinho itemParaSalvar;

    Optional<ItemCarrinho> itemOptional = repository.findByCarrinhoAndProdutoId(carrinho, itemToAdd.getProdutoID());

    if(itemOptional.isPresent()){ // O item já estava então aumenta a quantidade

      itemParaSalvar = itemOptional.get();
      BigDecimal quantidadeExistente = itemParaSalvar.getQuantidade();
      BigDecimal quantidadeAdicional = itemToAdd.getQuantidade();
      itemParaSalvar.setQuantidade(quantidadeExistente.add(quantidadeAdicional));

    }else{
      itemParaSalvar = new ItemCarrinho();

      Product produto = productRepository.findById(itemToAdd.getProdutoID())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

      itemParaSalvar.setCarrinho(carrinho);
      itemParaSalvar.setQuantidade(itemToAdd.getQuantidade());
      itemParaSalvar.setProduto(produto);
      itemParaSalvar.setPrecoUnitarioArmazenado(produto.getPreco());
      itemParaSalvar.setOfertaProdutor(null); // Null permitido agora
        }

        repository.save(itemParaSalvar);
        repository.flush();
        
        entityManager.clear();

        return carrinhoService.findCarrinho();
    }

    public CarrinhoItemResponseDTO updateItemInCarrinho(Long idItem , CarrinhoUpdateItemRequestDTO item){

      logger.info("Update amount about iten with id " + idItem);

  

      ItemCarrinho itemAtualizado = repository.findById(idItem).orElseThrow();

      itemAtualizado.setQuantidade(item.getQuantidade());

      ItemCarrinho itemSalvo = repository.save(itemAtualizado);

      return mapper.toResponseDTO(itemSalvo);
    }

    public void deleteItemFromCarrinho(Long idItem){

        logger.info("Delete  iten with id " + idItem);
 /*
         Carrinho carrinho = carrinhoService.getCarrinho();
         TODO VERIFICAR SE O ITEM TA NO CARRINHO

    ItemCarrinho itemParaDeletar = repository.findByIdAndCarrinho(idItem, carrinho)
        .orElseThrow(() -> new RuntimeException("Item não encontrado no seu carrinho."));
        
        */


        repository.deleteById(idItem);


    }
}
