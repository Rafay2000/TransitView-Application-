package hiof_project.domain.model;

import jakarta.persistence.*;

// Klasse for brukere i db, @Entity gjør det til en tabell.
@Entity
@Table(name = "users")
public class User {

    // Primærnøkkel som genereres av db.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    //Passord lagres som en hash istedetfor tekst av sikkerhetsårsaker
    private String passwordHash;


    // Get- og set-metoder for å lese eller lagre data
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
