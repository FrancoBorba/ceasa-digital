package br.com.uesb.ceasadigital.api.features.entregador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.uesb.ceasadigital.api.common.exceptions.DatabaseException;
import br.com.uesb.ceasadigital.api.common.exceptions.ForbiddenException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.entregador.dto.EntregadorRequestDTO;
import br.com.uesb.ceasadigital.api.features.entregador.dto.EntregadorResponseDTO;
import br.com.uesb.ceasadigital.api.features.entregador.mapper.EntregadorMapper;
import br.com.uesb.ceasadigital.api.features.entregador.model.Entregador;
import br.com.uesb.ceasadigital.api.features.entregador.repository.EntregadorRepository;
import br.com.uesb.ceasadigital.api.features.role.model.Role; // Import Role
import br.com.uesb.ceasadigital.api.features.role.repository.RoleRepository; // Import RoleRepository
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository; // Import UserRepository
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

@Service
public class EntregadorService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EntregadorMapper mapper;

    @Autowired
    private RoleRepository roleRepository; // Necessário para adicionar a role

    @Autowired
    private UserRepository userRepository; // Necessário para salvar o usuário com a nova role

    @Transactional
    public EntregadorResponseDTO criarOuAtualizarPerfil(EntregadorRequestDTO requestDTO) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new ResourceNotFoundException("Usuário não autenticado.");
        }

        // Verifica se o usuário já tem perfil de entregador
        Entregador entregador = entregadorRepository.findByUsuarioId(currentUser.getId())
            .orElse(new Entregador()); // Cria um novo se não existir

        // Atualiza os dados do DTO na entidade
        mapper.updateEntityFromDto(requestDTO, entregador);
        entregador.setUsuario(currentUser); // Garante que o usuário está associado

        // Adiciona a role ROLE_ENTREGADOR se o usuário ainda não tiver
        if (!currentUser.hasRole("ROLE_ENTREGADOR")) {
            Role entregadorRole = roleRepository.findByAuthority("ROLE_ENTREGADOR")
                .orElseThrow(() -> new DatabaseException("Role 'ROLE_ENTREGADOR' não encontrada no sistema."));
            currentUser.addRole(entregadorRole);
            userRepository.save(currentUser); // Salva o usuário com a nova role
        }

        Entregador perfilSalvo = entregadorRepository.save(entregador);
        return mapper.toResponseDTO(perfilSalvo);
    }

    @Transactional(readOnly = true)
    public EntregadorResponseDTO buscarMeuPerfil() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new ResourceNotFoundException("Usuário não autenticado.");
        }

        Entregador entregador = entregadorRepository.findByUsuarioId(currentUser.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Perfil de entregador não encontrado para o usuário atual. Crie um perfil primeiro."));

        return mapper.toResponseDTO(entregador);
    }
}