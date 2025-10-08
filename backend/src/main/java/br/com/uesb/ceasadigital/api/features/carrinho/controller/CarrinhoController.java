package br.com.uesb.ceasadigital.api.features.carrinho.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uesb.ceasadigital.api.features.carrinho.documentation.CarrinhoControllerDocs;
import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("api/v1/carrinhos")
@Tag(name = "Carrinho", description = "Operações relacionadas aos carrinhos de compras")
public class CarrinhoController implements CarrinhoControllerDocs {

    @Autowired
    private CarrinhoService carrinhoService;

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

  
   
}