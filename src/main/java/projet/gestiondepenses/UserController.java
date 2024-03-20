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
        List<Object[]> operationSummaryList = operationRepository.getTotalOperationsSummaryForUser1();

        List<List<String>> operationDetailsList = new ArrayList<>();
        for (Object[] summary : operationSummaryList) {
            List<String> details = operationRepository.getOperationDetailsForTypeDepense((String) summary[0]);
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
            limitation = new Limitation();
            limitation.setUtilisateur(utilisateur);
            limitation.setTypeDepense(typeDepenseRepository.findById(typeDepenseId).orElse(null));
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
        // Convertir l'ID du type de dépense en une instance persistante de TypeDepense
        TypeDepense typeDepense = typeDepenseService.getTypeDepenseById(idTypeDep)
                .orElseThrow(() -> new RuntimeException("TypeDepense not found with ID: " + idTypeDep));

        // Utiliser l'utilisateur avec l'ID 1
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(1L)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found with ID: 1"));

        // Utiliser la date actuelle avec les heures et les minutes
        operation.setDateDep(new Date()); // Utilisation de java.util.Date pour la date actuelle

        // Assigner le type de dépense et l'utilisateur à l'opération
        operation.setTypeDepense(typeDepense);
        operation.setUtilisateur(utilisateur);

        // Persistez l'opération avec les détails corrects
        operationService.persistOperation(operation);

        return "redirect:/user/home"; // Rediriger vers la page d'accueil de l'utilisateur
    }


}
