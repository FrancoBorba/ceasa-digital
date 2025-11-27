package br.com.uesb.ceasadigital.api.features.produtor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping; // NOVO IMPORT
import org.springframework.web.bind.annotation.RequestParam; // NOVO IMPORT, necessário para o método findByUsername

import br.com.uesb.ceasadigital.api.features.produtor.dto.request.ProdutorRequestDTO;
import br.com.uesb.ceasadigital.api.features.produtor.dto.response.ProdutorResponseDTO;
import br.com.uesb.ceasadigital.api.features.produtor.service.ProdutorService;

import org.springframework.http.MediaType;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtor")
public class ProdutorController {

    @Autowired
    ProdutorService produtorService;
    
    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE 
    )
    //@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ProdutorResponseDTO> criarPerfilProdutor(@Valid @RequestBody ProdutorRequestDTO requestDTO) {
        ProdutorResponseDTO response = produtorService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    
    @GetMapping("/username") 
    public ResponseEntity<ProdutorResponseDTO> findByUsername(@RequestParam(value = "email") String email) {
        ProdutorResponseDTO response = produtorService.findProdutorByEmail(email);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_PRODUTOR')")
    @GetMapping("/me")
    public ResponseEntity<ProdutorResponseDTO> retornaPerfil() {
        ProdutorResponseDTO response = produtorService.retornaPerfil();
        return ResponseEntity.ok(response);
    }
}