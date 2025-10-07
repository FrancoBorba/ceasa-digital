package br.com.uesb.ceasadigital.api.features.carrinho.controller;

import java.util.List;

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

import br.com.uesb.ceasadigital.api.features.carrinho.dto.request.CarrinhoRequestDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/carrinhos")
@Tag(name = "Carrinho", description = "Operações relacionadas aos carrinhos de compras")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Operation(summary = "Listar todos os carrinhos", description = "Retorna uma lista com todos os carrinhos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de carrinhos retornada com sucesso")
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<CarrinhoResponseDTO>> findAll() {
        List<CarrinhoResponseDTO> carrinhos = carrinhoService.findAllCarrinhos();
        return ResponseEntity.ok(carrinhos);
    }

    @Operation(summary = "Buscar carrinho por ID", description = "Retorna um carrinho específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrinho encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Carrinho não encontrado")
    })
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CarrinhoResponseDTO> findById(@PathVariable(value = "id") Long id) {
        CarrinhoResponseDTO carrinho = carrinhoService.findCarrinhoById(id);
        return ResponseEntity.ok(carrinho);
    }

    @Operation(summary = "Buscar carrinho por ID do usuário", description = "Retorna o carrinho de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrinho encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Carrinho não encontrado para o usuário")
    })
    @GetMapping(value = "/usuario/{usuarioId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CarrinhoResponseDTO> findByUsuarioId(@PathVariable(value = "usuarioId") Long usuarioId) {
        CarrinhoResponseDTO carrinho = carrinhoService.findCarrinhoByUsuarioId(usuarioId);
        return ResponseEntity.ok(carrinho);
    }

    @Operation(summary = "Criar novo carrinho", description = "Cria um novo carrinho para um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Carrinho criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping(
        produces = { MediaType.APPLICATION_JSON_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<CarrinhoResponseDTO> createCarrinho(@RequestBody @Valid CarrinhoRequestDTO carrinhoRequest) {
        CarrinhoResponseDTO carrinho = carrinhoService.createCarrinho(carrinhoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinho);
    }

    @Operation(summary = "Atualizar carrinho", description = "Atualiza os dados de um carrinho existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrinho atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Carrinho ou usuário não encontrado")
    })
    @PutMapping(
        value = "/{id}",
        produces = { MediaType.APPLICATION_JSON_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<CarrinhoResponseDTO> updateCarrinho(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid CarrinhoRequestDTO carrinhoRequestDTO) {
        CarrinhoResponseDTO carrinho = carrinhoService.updateCarrinho(id, carrinhoRequestDTO);
        return ResponseEntity.ok(carrinho);
    }

    @Operation(summary = "Deletar carrinho", description = "Remove um carrinho do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Carrinho deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Carrinho não encontrado")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCarrinho(@PathVariable(value = "id") Long id) {
        carrinhoService.deleteCarrinho(id);
        return ResponseEntity.noContent().build();
    }
}