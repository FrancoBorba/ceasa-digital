package br.com.uesb.ceasadigital.api.features.role.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uesb.ceasadigital.api.features.role.model.Role;

public  interface RoleRepository extends JpaRepository<Role, Long>  {
    Optional<Role> findByAuthority(String authority);
}
