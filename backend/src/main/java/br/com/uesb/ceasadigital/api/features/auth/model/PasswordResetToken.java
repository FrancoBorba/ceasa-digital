package br.com.uesb.ceasadigital.api.features.auth.model;

import java.time.LocalDateTime;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import jakarta.persistence.*; 

@Entity
@Table(name = "tb_password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy para não carregar o User sempre
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Construtores, Getters e Setters
    public PasswordResetToken() {}

    public PasswordResetToken(String token, LocalDateTime expiresAt, User user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}