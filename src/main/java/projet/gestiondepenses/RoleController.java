package projet.gestiondepenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projet.gestiondepenses.model.Role;
import projet.gestiondepenses.service.RoleService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService; // Assurez-vous d'avoir le service correspondant

    @GetMapping("")
    public String listRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "role/list";
    }

    @GetMapping("/add")
    public String addRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "role/add";
    }

    @PostMapping("/add")
    public String addRole(@ModelAttribute Role role) {
        roleService.persistRole(role);
        return "redirect:/role";
    }

    @GetMapping("/edit/{id}")
    public String editRoleForm(@PathVariable Long id, Model model) {
        Optional<Role> roleOptional = roleService.getRoleById(id);

        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            model.addAttribute("role", role);
            return "role/edit";
        } else {
            // Gérez le cas où le rôle avec l'ID spécifié n'est pas trouvé
            return "redirect:/role/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String editRole(@PathVariable Long id, @ModelAttribute Role role) {
        // Récupérer le rôle existant
        Role existingRole = roleService.getRoleById(id).orElse(null);

        if (existingRole != null) {
            existingRole.setIntituleRole(role.getIntituleRole());

            // Enregistrez les modifications
            roleService.updateRole(existingRole);
            return "redirect:/role";
        } else {
            // Gérer le cas où le rôle avec l'ID spécifié n'est pas trouvé
            return "redirect:/role";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRoleForm(@PathVariable Long id) {
        try {
            // Supprimez le TypeDepense avec le service
            roleService.deleteRoleById(id);
            return "redirect:/role";
        } catch (Exception e) {
            e.printStackTrace(); // Ajoutez des logs appropriés ici
            return "redirect:/error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        try {
            // Supprimez le rôle avec le service
            roleService.deleteRoleById(id);
            return "redirect:/role/list";
        } catch (Exception e) {
            e.printStackTrace(); // Ajoutez des logs appropriés ici
            return "redirect:/error";
        }
    }
}