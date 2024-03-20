package projet.gestiondepenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projet.gestiondepenses.model.Limitation;
import projet.gestiondepenses.model.Operation;
import projet.gestiondepenses.model.TypeDepense;
import projet.gestiondepenses.model.Utilisateur;
import projet.gestiondepenses.repository.OperationRepository;
import projet.gestiondepenses.repository.LimitationRepository;
import projet.gestiondepenses.repository.TypeDepenseRepository;
import projet.gestiondepenses.repository.UtilisateurRepository;
import projet.gestiondepenses.service.OperationService;
import projet.gestiondepenses.service.TypeDepenseService;
import projet.gestiondepenses.service.UtilisateurService;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private LimitationRepository limitationRepository;
    @Autowired
    private TypeDepenseRepository typeDepenseRepository;
    @Autowired
    private UtilisateurRepository UtilisateurRepository;

    @Autowired
    private OperationService operationService;

    @Autowired
    private TypeDepenseService typeDepenseService; // Service pour les types de dépenses

    @Autowired
    private UtilisateurService utilisateurService; // Service pour les utilisateurs

    @GetMapping("/home")
    public String afficherAccueilClient(Model model) {
        // Par défaut, afficher les opérations pour le mois et l'année actuels
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        List<Object[]> operationSummaryList = operationRepository.getTotalOperationsSummaryForUser1ByMonthAndYear(currentMonth, currentYear);
        List<List<String>> operationDetailsList = new ArrayList<>();
        for (Object[] summary : operationSummaryList) {
            List<String> details = operationRepository.getOperationDetailsForTypeDepenseByMonthAndYear((String) summary[0], currentMonth, currentYear);
            operationDetailsList.add(details);
        }
        model.addAttribute("operationSummaryList", operationSummaryList);
        model.addAttribute("operationDetailsList", operationDetailsList);
        return "user/home";
    }

    @GetMapping("/home/{year}/{month}")
    public String afficherAccueilClientParMoisAnnee(@PathVariable int year, @PathVariable int month, Model model) {
        List<Object[]> operationSummaryList = operationRepository.getTotalOperationsSummaryForUser1ByMonthAndYear(month, year);
        List<List<String>> operationDetailsList = new ArrayList<>();
        for (Object[] summary : operationSummaryList) {
            List<String> details = operationRepository.getOperationDetailsForTypeDepenseByMonthAndYear((String) summary[0], month, year);
            operationDetailsList.add(details);
        }
        model.addAttribute("operationSummaryList", operationSummaryList);
        model.addAttribute("operationDetailsList", operationDetailsList);
        return "user/home";
    }



    @GetMapping("/modifier-limite/{typeDepenseId}")
    public String afficherFormulaireModificationLimite(@PathVariable Long typeDepenseId, Model model) {
        Limitation limitation = limitationRepository.findByUtilisateurIdAndTypeDepenseId(1L, typeDepenseId);
        Optional<Utilisateur> utilisateurOptional = UtilisateurRepository.findById(1L);
        Utilisateur utilisateur = utilisateurOptional.orElse(null);

        if (limitation != null) {
            model.addAttribute("limitation", limitation);
        } else {
            // Si la limite n'existe pas, créez une nouvelle instance avec les valeurs par défaut
            limitation = new Limitation();
            limitation.setUtilisateur(utilisateur);
            limitation.setTypeDepense(typeDepenseRepository.findById(typeDepenseId).orElse(null));

            model.addAttribute("limitation", limitation);
        }
        model.addAttribute("typeDepenseId", typeDepenseId);
        return "user/modifier_limite"; // Vue HTML
    }

    @PostMapping("/modifier-limite/{typeDepenseId}")
    public String modifierLimite(@PathVariable Long typeDepenseId, @ModelAttribute Limitation nouvelleLimitation) {
        Limitation limitationExistante = limitationRepository.findByUtilisateurIdAndTypeDepenseId(1L, typeDepenseId);
        if (limitationExistante != null) {
            limitationExistante.setMontantLim(nouvelleLimitation.getMontantLim());
        } else {
            limitationExistante = nouvelleLimitation;
            limitationExistante.setUtilisateur(UtilisateurRepository.findById(1L).orElse(null));
            limitationExistante.setTypeDepense(typeDepenseRepository.findById(typeDepenseId).orElse(null));
        }
        limitationRepository.save(limitationExistante);
        return "redirect:/user/home";
    }

    @GetMapping("/operation/add")
    public String addOperationForm(Model model) {
        Operation operation = new Operation();
        // Pré-remplir la date avec la date actuelle
        operation.setDateDep(new Date()); // Utilisation de java.util.Date pour la date actuelle
        // Récupérer tous les types de dépenses
        List<TypeDepense> allTypesDepense = typeDepenseService.getAllTypesDepense();

        // Ajouter les types de dépenses au modèle
        model.addAttribute("allTypesDepense", allTypesDepense);
        model.addAttribute("operation", operation);

        return "user/operation/add"; // Assurez-vous que le chemin vers la vue est correct
    }

    @PostMapping("/operation/add")
    public String addOperation(@ModelAttribute Operation operation, @RequestParam("idTypeDep") Long idTypeDep) {
        TypeDepense typeDepense = typeDepenseService.getTypeDepenseById(idTypeDep)
                .orElseThrow(() -> new RuntimeException("TypeDepense not found with ID: " + idTypeDep));

        Utilisateur utilisateur = utilisateurService.getUtilisateurById(1L)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found with ID: 1"));

        operation.setDateDep(new Date());

        operation.setTypeDepense(typeDepense);
        operation.setUtilisateur(utilisateur);

        // Persistez l'opération avec les détails corrects
        operationService.persistOperation(operation);

        return "redirect:/user/home";
    }

    @GetMapping("/edit")
    public String editUserProfileForm(Model model) {
        Optional<Utilisateur> utilisateurOptional = utilisateurService.getUtilisateurById(1L);

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            model.addAttribute("utilisateur", utilisateur);
            return "user/edit_profile"; // Vue HTML
        } else {
            // Gérez le cas où l'utilisateur avec l'ID 1 n'est pas trouvé
            return "redirect:/user/home"; // Redirigez vers une page appropriée
        }
    }

    @PostMapping("/edit")
    public String editUserProfile(@ModelAttribute Utilisateur utilisateur) {
        Utilisateur existingUtilisateur = utilisateurService.getUtilisateurById(1L).orElse(null);

        if (existingUtilisateur != null) {
            // Mettez à jour les champs du profil
            existingUtilisateur.setNom(utilisateur.getNom());
            existingUtilisateur.setPrenom(utilisateur.getPrenom());
            existingUtilisateur.setMail(utilisateur.getMail());
            // Enregistrez les modifications
            utilisateurService.updateUtilisateur(existingUtilisateur);
            return "redirect:/user/home"; // Redirigez vers une page appropriée
        } else {
            return "redirect:/user/home"; // Redirigez vers une page appropriée
        }
    }

    @GetMapping("/suggestion")
    public String showSuggestionsPage() {
        return "user/suggestion";
    }

}
