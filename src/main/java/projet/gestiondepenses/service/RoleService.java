package projet.gestiondepenses.service;

import projet.gestiondepenses.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Role persistRole(Role role);
    Optional<Role> getRoleById(Long id);
    Role updateRole(Role role);
    void deleteRoleById(Long id);
}