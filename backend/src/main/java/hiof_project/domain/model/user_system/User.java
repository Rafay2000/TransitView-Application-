package hiof_project.domain.model.user_system;

import jakarta.persistence.*;

// Klasse for brukere i db, @Entity gjør det til en tabell..
@Entity
@Table(name = "users")
public class User {

    // Primærnøkkel som genereres av db.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    //Passord lagres som en hash istedetfor tekst av sikkerhetsårsaker
    @Column(nullable = false)
    private String passwordHash;

    // Mange User-objekter kan ha én rolle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {}

    public User(String email, String passwordHash, Role role) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Get- og set-metoder for å lese eller lagre data
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void changePassword(String newHash) {
        this.passwordHash = newHash;
    }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
