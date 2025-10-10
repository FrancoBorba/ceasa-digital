package br.com.uesb.ceasadigital.api.features.item_carrinho.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.repository.CarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoAddItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoUpdateItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.response.CarrinhoItemResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.mapper.ItemCarrinhoMapper;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;
import br.com.uesb.ceasadigital.api.features.item_carrinho.repository.ItemCarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ItemCarrinhoService {
  

  @Autowired
  CarrinhoService carrinhoService;

  @Autowired 
  ItemCarrinhoMapper mapper;
  
  @Autowired 
  ItemCarrinhoRepository repository;

  @Autowired
  MockOfertaProvider mockOfertaProvider;

  @Autowired
  CarrinhoRepository carrinhoRepository;

    @PersistenceContext
    private EntityManager entityManager;

  
private final Logger logger = LoggerFactory.getLogger(CarrinhoService.class.getName());

  public CarrinhoItemResponseDTO addItemInCarrinho(CarrinhoAddItemRequestDTO item){


    Carrinho carrinho = carrinhoService.getCarrinho();

     Long ofertaId = item.getOfertaProdutorId();

     MockOfertaProvider.OfertaProdutorDTO oferta = mockOfertaProvider.buscarOfertaCompleta(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta com ID " + ofertaId + " não encontrada."));


    Optional<ItemCarrinho> itemOptional = repository.findByCarrinhoAndOfertaProdutorId(carrinho, ofertaId);

    ItemCarrinho itemParaSalvar;
    if(itemOptional.isPresent()){ // O item já estava então aumenta a quantidade

      itemParaSalvar = itemOptional.get();
      BigDecimal quantidadeExistente = itemParaSalvar.getQuantidade();
      BigDecimal quantidadeAdicional = item.getQuantidade();
      itemParaSalvar.setQuantidade(quantidadeExistente.add(quantidadeAdicional));

    }else{
      itemParaSalvar = new ItemCarrinho();
      itemParaSalvar.setCarrinho(carrinho);
      itemParaSalvar.setQuantidade(item.getQuantidade());
  
            BigDecimal precoCongelado = oferta.getEstoqueVirtual().getPrecoDefinido();
            itemParaSalvar.setPrecoUnitarioArmazenado(precoCongelado);

            OfertaProdutor ofertaReferencia = entityManager.getReference(OfertaProdutor.class, ofertaId);

            itemParaSalvar.setOfertaProdutor(ofertaReferencia);
        }

        ItemCarrinho itemSalvo = repository.save(itemParaSalvar);

        return mapper.toResponseDTO(itemSalvo);
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
