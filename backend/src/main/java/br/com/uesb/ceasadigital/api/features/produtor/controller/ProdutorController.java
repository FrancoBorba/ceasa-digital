package br.com.uesb.ceasadigital.api.features.produtor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ProdutorResponseDTO> criarPerfilProdutor(@Valid @RequestBody ProdutorRequestDTO requestDTO) {
        ProdutorResponseDTO response = produtorService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
