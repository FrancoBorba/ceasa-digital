package br.com.uesb.ceasadigital.api.features.produtor_produto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.uesb.ceasadigital.api.features.produtor_produto.dto.request.ProdutorProdutoRequestVendaDTO;
import br.com.uesb.ceasadigital.api.features.produtor_produto.dto.response.ProdutorProdutoResponseDTO;
import br.com.uesb.ceasadigital.api.features.produtor_produto.dto.response.ProdutorProdutoResponseVendaDTO;
import br.com.uesb.ceasadigital.api.features.produtor_produto.service.ProdutorProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/produtor-produtos")
@Tag(name = "Produtor-Produto", description = "Endpoints para gerenciar as permissões de venda de produtos pelos produtores.")
public class ProdutorProdutoController {

    @Autowired
    ProdutorProdutoService produtorProdutoService;

    @PostMapping(
        value = "/solicitar-venda",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_PRODUTOR')")
    public ResponseEntity<ProdutorProdutoResponseVendaDTO> requestVenda(@Valid @RequestBody ProdutorProdutoRequestVendaDTO requestDTO) {
        List<ProdutorProdutoResponseDTO> results = produtorProdutoService.requestVenda(requestDTO.getProdutosIds());
        ProdutorProdutoResponseVendaDTO response = new ProdutorProdutoResponseVendaDTO(results);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(
        value = "/me",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_PRODUTOR')")
    public ResponseEntity<List<ProdutorProdutoResponseDTO>> listMySolicitacoesVenda(@RequestParam(required = false) String status) {
        List<ProdutorProdutoResponseDTO> response = produtorProdutoService.findMySolicitacoesVenda(status);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/desativar/{id}") 
    @PreAuthorize("hasRole('ROLE_PRODUTOR')")
    public ResponseEntity<ProdutorProdutoResponseDTO> deactivateMySolicitacaoVenda(@PathVariable Long id) {
        ProdutorProdutoResponseDTO response = produtorProdutoService.desativarMinhaSolicitacaoVenda(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) 
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProdutorProdutoResponseDTO>> listAll(@RequestParam(required = false) String status) { 
        List<ProdutorProdutoResponseDTO> response = produtorProdutoService.findAll(status); 
        return ResponseEntity.ok(response);
    }
}
