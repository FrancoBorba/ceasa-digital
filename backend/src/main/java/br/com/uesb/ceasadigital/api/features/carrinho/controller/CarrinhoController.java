package br.com.uesb.ceasadigital.api.features.carrinho.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uesb.ceasadigital.api.features.carrinho.documentation.CarrinhoControllerDocs;
import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoAddItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoUpdateItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.response.CarrinhoItemResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.service.ItemCarrinhoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api/v1/carrinhos")
@Tag(name = "Carrinho", description = "Operações relacionadas aos carrinhos de compras")
public class CarrinhoController implements CarrinhoControllerDocs {

    @Autowired
    private CarrinhoService carrinhoService;


    @Autowired
    private ItemCarrinhoService itemCarrinhoService; 

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarrinhoResponseDTO> findMyCarrinho() {
        return ResponseEntity.ok(carrinhoService.findCarrinho());
    }

    @Override
    @DeleteMapping("/limpar")
    public ResponseEntity<Void> limparMeuCarrinho() {
      carrinhoService.clearUserCarrinho();
      
        return ResponseEntity.noContent().build();
    }

    @Override
     @PostMapping("/itens") 
    public ResponseEntity<CarrinhoItemResponseDTO> adicionarItemAoCarrinho( @RequestBody @Valid
      CarrinhoAddItemRequestDTO itemRequest) {

      CarrinhoItemResponseDTO responseDTO = itemCarrinhoService.addItemInCarrinho(itemRequest);

      return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @PutMapping("/itens/{idItem}") 
    public ResponseEntity<CarrinhoItemResponseDTO> atualizarItemNoCarrinho(
            @PathVariable Long idItem,
            @RequestBody @Valid CarrinhoUpdateItemRequestDTO itemRequest) {
        
     
        CarrinhoItemResponseDTO responseDTO = itemCarrinhoService.updateItemInCarrinho(idItem, itemRequest);
        return ResponseEntity.ok(responseDTO);
    }

    // --- NOVO MÉTODO DE DELEÇÃO ---
    @DeleteMapping("/itens/{idItem}")
    public ResponseEntity<Void> removerItemDoCarrinho(@PathVariable Long idItem) {
        itemCarrinhoService.deleteItemFromCarrinho(idItem);

        return ResponseEntity.noContent().build();
    }
   
}