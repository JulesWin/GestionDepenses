package projet.gestiondepenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projet.gestiondepenses.model.Utilisateur;
import projet.gestiondepenses.service.UtilisateurService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService; // Assurez-vous d'avoir le service correspondant
    @GetMapping("")
    public String listUtilisateurs(Model model) {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        model.addAttribute("utilisateurs", utilisateurs);
        return "utilisateur/list";
    }
    @GetMapping("/add")
    public String addUtilisateurForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "utilisateur/add";
    }

    @PostMapping("/add")
    public String addUtilisateur(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.persistUtilisateur(utilisateur);
        return "redirect:/utilisateur";
    }
    @GetMapping("/edit/{id}")
    public String editUtilisateurForm(@PathVariable Long id, Model model) {
        Optional<Utilisateur> utilisateurOptional = utilisateurService.getUtilisateurById(id);

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            model.addAttribute("utilisateur", utilisateur);
            return "utilisateur/edit";
        } else {
            // Gérez le cas où l'utilisateur avec l'ID spécifié n'est pas trouvé
            return "redirect:/utilisateur/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String editUtilisateur(@PathVariable Long id, @ModelAttribute Utilisateur utilisateur) {
        // Récupérer l'utilisateur existant
        Utilisateur existingUtilisateur = utilisateurService.getUtilisateurById(id).orElse(null);

        if (existingUtilisateur != null) {
            existingUtilisateur.setNom(utilisateur.getNom());
            existingUtilisateur.setPrenom(utilisateur.getPrenom());
            existingUtilisateur.setMail(utilisateur.getMail());

            // Enregistrez les modifications
            utilisateurService.updateUtilisateur(existingUtilisateur);
            return "redirect:/utilisateur";
        } else {
            // Gérer le cas où l'utilisateur avec l'ID spécifié n'est pas trouvé
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
            e.printStackTrace(); // Ajoutez des logs appropriés ici
            return "redirect:/error";
        }
    }
    @PostMapping("/delete/{id}")
    public String deleteUtilisateur(@PathVariable Long id) {
        try {
            // Supprimez l'utilisateur avec le service
            utilisateurService.deleteUtilisateurById(id);
            return "redirect:/utilisateur/list";
        } catch (Exception e) {
            e.printStackTrace(); // Ajoutez des logs appropriés ici
            return "redirect:/error";
        }
    }

}

