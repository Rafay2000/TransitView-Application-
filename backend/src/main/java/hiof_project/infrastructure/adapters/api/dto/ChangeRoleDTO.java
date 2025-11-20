package hiof_project.infrastructure.adapters.api.dto;

public record ChangeRoleDTO(String role) {

    public String roleName() {
        return role;
    }
}
