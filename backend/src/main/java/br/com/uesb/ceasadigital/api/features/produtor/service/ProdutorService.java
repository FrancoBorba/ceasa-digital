package br.com.uesb.ceasadigital.api.features.produtor.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.common.exceptions.DatabaseException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.produtor.dto.request.ProdutorRequestDTO;
import br.com.uesb.ceasadigital.api.features.produtor.dto.response.ProdutorResponseDTO;
import br.com.uesb.ceasadigital.api.features.produtor.mapper.ProdutorMapper;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;
import br.com.uesb.ceasadigital.api.features.produtor.repository.ProdutorRepository;
import br.com.uesb.ceasadigital.api.features.role.model.Role;
import br.com.uesb.ceasadigital.api.features.role.repository.RoleRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.ServerEndpoint;

@Service
public class ProdutorService {

    @Autowired
    private ProdutorRepository produtorRepository;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private UserService userService;

    @Autowired
    private ProdutorMapper mapper;

    @Autowired
    private RoleRepository roleRepository;
    
    @Transactional
    public ProdutorResponseDTO create(ProdutorRequestDTO requestDTO) {
       
        // obtendo o usuário 
        if(!userRepository.existsById(requestDTO.getIdUser())){
            throw new IllegalStateException("Usuario informado nao existe");
        }
        
        User currentUser = userRepository.findById(requestDTO.getIdUser())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + requestDTO.getIdUser() + " não encontrado."));

        // verificando se o usuario ja possui perfil de produtor 
        Optional<Produtor> existingProfile = produtorRepository.findByUsuarioId(requestDTO.getIdUser());
        if (existingProfile.isPresent()) {
            throw new IllegalStateException("O usuário já possui um perfil de produtor cadastrado.");
        }

        // atribuindo a ROLE_PRODUTOR ao usuário 
        if (!currentUser.hasRole("ROLE_PRODUTOR")) {
            Role produtorRole = roleRepository.findByAuthority("ROLE_PRODUTOR")
                .orElseThrow(() -> new DatabaseException("Role 'ROLE_PRODUTOR' não encontrada no sistema."));
            
            currentUser.addRole(produtorRole);
            userRepository.save(currentUser); 
        }

        // 4. Mapear DTO para Entidade
        Produtor entity = new Produtor();
        
        entity.setUsuario(currentUser);
        entity.setNumeroDeIdentificacao(requestDTO.getNumeroDeIdentificacao());
        entity.setTipoDeIdentificacao(requestDTO.getTipoDeIdentificacao());

        // 5. Salvar no banco de dados
        Produtor savedEntity = produtorRepository.save(entity);

        // 6. Retornar DTO de resposta
        return mapper.toResponseDTO(savedEntity);
    }
}
