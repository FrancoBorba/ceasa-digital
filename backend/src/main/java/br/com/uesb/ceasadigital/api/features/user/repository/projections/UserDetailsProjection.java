package br.com.uesb.ceasadigital.api.features.user.repository.projections;

public interface UserDetailsProjection {
  String getUsername();
  String getPassword();
  Long getRoleId();
  String getAuthority();
}
