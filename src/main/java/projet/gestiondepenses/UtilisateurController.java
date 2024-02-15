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
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);
        model.addAttribute("utilisateur", utilisateur);
        return "utilisateur/edit";
    }

    @PostMapping("/edit/{id}")
    public String editUtilisateur(@PathVariable Long id, @ModelAttribute Utilisateur utilisateur) {
        // Mettez à jour les informations de l'utilisateur avec le service
        utilisateurService.updateUtilisateur(utilisateur);
        return "redirect:/utilisateur/list";
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

