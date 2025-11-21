package hiof_project.infrastructure.adapters.api.dto;

// DTO for bytte av rolle til bruker
public record ChangeRoleDTO(String role) {

    public String roleName() {
        return role;
    }
}
