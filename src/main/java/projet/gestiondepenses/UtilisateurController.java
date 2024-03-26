package projet.gestiondepenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projet.gestiondepenses.model.Utilisateur;
import projet.gestiondepenses.model.Role;

import projet.gestiondepenses.service.UtilisateurService;
import projet.gestiondepenses.service.RoleService;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService; // Assurez-vous d'avoir le service correspondant

    @Autowired
    private RoleService roleService;
    /*@GetMapping("")
    public String listUtilisateurs(Model model) {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        //List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateursWithRoles();
        model.addAttribute("utilisateurs", utilisateurs);
        return "utilisateur/list";
    }*/

    @GetMapping("")
    public String listUtilisateurs(Model model) {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        Map<Long, List<String>> rolesByUserId = new HashMap<>();
        for (Utilisateur utilisateur : utilisateurs) {
            rolesByUserId.put(utilisateur.getIdUser(), utilisateurService.getRolesNamesByUserId(utilisateur.getIdUser()));
        }
        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("rolesByUserId", rolesByUserId);
        return "utilisateur/list";
    }

    @GetMapping("/add")
    public String addUtilisateurForm(Model model) {

        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        model.addAttribute("utilisateur", new Utilisateur());
        return "utilisateur/add";
    }

    @PostMapping("/add")
    public String addUtilisateur(@ModelAttribute Utilisateur utilisateur, @RequestParam("roleIds") List<Long> roleIds) {
        // Récupérer les rôles à partir des identifiants
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Optional<Role> role = roleService.getRoleById(roleId);
            role.ifPresent(roles::add);
        }

        utilisateur.setRoles(roles);

        // Persister l'utilisateur
        utilisateurService.persistUtilisateur(utilisateur);

        return "redirect:/utilisateur";
    }


    @GetMapping("/edit/{id}")
    public String editUtilisateurForm(@PathVariable Long id, Model model) {
        Optional<Utilisateur> utilisateurOptional = utilisateurService.getUtilisateurById(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            model.addAttribute("utilisateur", utilisateur);
            return "utilisateur/edit";
        } else {
            return "redirect:/utilisateur/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String editUtilisateur(@PathVariable Long id,
                                  @ModelAttribute Utilisateur utilisateur,
                                  @RequestParam(name = "selectedRoleIds", required = false) List<Long> selectedRoleIds) {

        // Récupérer l'utilisateur existant
        Utilisateur existingUtilisateur = utilisateurService.getUtilisateurById(id).orElse(null);

        if (existingUtilisateur != null) {
            existingUtilisateur.setNom(utilisateur.getNom());
            existingUtilisateur.setPrenom(utilisateur.getPrenom());
            existingUtilisateur.setMail(utilisateur.getMail());

            // Si aucun rôle n'est sélectionné, initialiser une liste vide
            if (selectedRoleIds == null) {
                selectedRoleIds = new ArrayList<>();
            }

            // Récupérer les rôles à partir des IDs sélectionnés
            Set<Role> selectedRoles = new HashSet<>();
            for (Long roleId : selectedRoleIds) {
                Optional<Role> role = roleService.getRoleById(roleId);
                role.ifPresent(selectedRoles::add);
            }

            // Associer les rôles sélectionnés à l'utilisateur
            existingUtilisateur.setRoles(selectedRoles);

            utilisateurService.updateUtilisateur(existingUtilisateur);

            return "redirect:/utilisateur";
        } else {
            return "redirect:/utilisateur";
        }
    }



    @GetMapping("/delete/{id}")
    public String deleteUtilisateurForm(@PathVariable Long id) {
        try {
            // Supprimez l'utilisateur avec le service
            utilisateurService.deleteUtilisateurById(id);
            return "redirect:/utilisateur";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
    @PostMapping("/delete/{id}")
    public String deleteUtilisateur(@PathVariable Long id) {
        try {
            utilisateurService.deleteUtilisateurById(id);
            return "redirect:/utilisateur/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

}

