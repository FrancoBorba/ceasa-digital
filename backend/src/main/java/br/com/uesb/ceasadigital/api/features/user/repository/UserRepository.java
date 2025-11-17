package br.com.uesb.ceasadigital.api.features.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.projections.UserDetailsProjection;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query(nativeQuery = true, value = """
    SELECT tb_user.email AS username, tb_user.password AS password, tb_role.id AS roleId, tb_user.email_confirmado AS emailConfirmado, tb_role.authority
    FROM tb_user
    INNER JOIN tb_user_roles ON tb_user.id = tb_user_roles.user_id
    INNER JOIN tb_role ON tb_user_roles.role_id = tb_role.id
    WHERE tb_user.email = :email
  """)
  List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
  
  Optional<User> findByEmail(String email);
  Optional<User> findByCpf(String cpf);
  boolean existsById(Long id);
}
