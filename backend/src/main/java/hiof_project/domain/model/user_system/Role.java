package hiof_project.domain.model.user_system;

import jakarta.persistence.*;

@Entity

//Lagrer alle roller i samme tabell
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Role() { }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Viser navn og rolle til bruker
    @Override
    public String toString() {
        return "Navn: " + getName() + " | Rolle: " + getClass().getSimpleName();
    }

    // Ekstra metode for Debugging
    public String fullUserInfo() {
        return "Role{ userId=" + getId() +
                ", Name='" + getName() + '\'' +
                ", role='" + getClass().getSimpleName() + '\'' +
                '}';
    }
}
