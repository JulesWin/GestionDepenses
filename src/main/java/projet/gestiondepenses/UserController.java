package projet.gestiondepenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        List<List<Object[]>> operationDetailsList = new ArrayList<>();
        for (Object[] summary : operationSummaryList) {
            List<Object[]> details = operationRepository.getOperationDetailsForTypeDepenseByMonthAndYear((String) summary[0], currentMonth, currentYear);
            operationDetailsList.add(details);
        }

        // Calcule la somme des dépenses totales et des limites totales
        double totalExpenses = 0.0;
        double totalLimits = 0.0;

        for (Object[] summary : operationSummaryList) {
            if (summary[1] != null) {
                totalExpenses += (double) summary[1];
            }
            if (summary[2] != null) {
                totalLimits += (double) summary[2];
            }
        }
        model.addAttribute("operationSummaryList", operationSummaryList);
        model.addAttribute("operationDetailsList", operationDetailsList);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("totalLimits", totalLimits);

        return "user/home";
    }


    @GetMapping("/home/{year}/{month}")
    public String afficherAccueilClientParMoisAnnee(@PathVariable int year, @PathVariable int month, Model model) {
        List<Object[]> operationSummaryList = operationRepository.getTotalOperationsSummaryForUser1ByMonthAndYear(month, year);
        List<List<Object[]>> operationDetailsList = new ArrayList<>();
        for (Object[] summary : operationSummaryList) {
            List<Object[]> details = operationRepository.getOperationDetailsForTypeDepenseByMonthAndYear((String) summary[0], month, year);
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
        List<TypeDepense> allTypesDepense = typeDepenseService.getAllTypesDepense();

        // Ajouter les types de dépenses au modèle
        model.addAttribute("allTypesDepense", allTypesDepense);
        model.addAttribute("operation", operation);

        return "user/operation/add";
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

        operationService.persistOperation(operation);

        return "redirect:/user/home";
    }

    @GetMapping("/operation/edit/{id}")
    public String editOperationForm(@PathVariable Long id, Model model) {
        Long idUser = 1L; // Forcer l'utilisation de l'utilisateur avec l'ID 1
        Optional<Operation> operationOptional = operationRepository.findById(id);

        if (operationOptional.isPresent()) {
            Operation operation = operationOptional.get();
            List<TypeDepense> allTypesDepense = typeDepenseRepository.findAll();
            model.addAttribute("allTypesDepense", allTypesDepense);
            model.addAttribute("operation", operation);
            model.addAttribute("idUser", idUser); // Ajouter l'idUser au modèle


            return "user/operation/edit";
        } else {
            return "redirect:/user/home";
        }
    }

    @PostMapping("/operation/edit/{id}")
    public String editOperation(@PathVariable Long id, @ModelAttribute Operation operation,
                                @RequestParam("idTypeDep") Long idTypeDep) {
        // Récupérer l'opération existante
        Optional<Operation> existingOperationOptional = operationRepository.findById(id);

        if (existingOperationOptional.isPresent()) {
            Operation existingOperation = existingOperationOptional.get();

            // Récupérer le type de dépense en fonction de l'ID fourni
            Optional<TypeDepense> typeDepenseOptional = typeDepenseRepository.findById(idTypeDep);
            if (typeDepenseOptional.isPresent()) {
                TypeDepense typeDepense = typeDepenseOptional.get();
                existingOperation.setMontant(operation.getMontant()); // Mettre à jour le montant de l'opération
                existingOperation.setTypeDepense(typeDepense); // Mettre à jour le type de dépense de l'opération

                // Récupérer l'utilisateur avec l'ID 1
                Optional<Utilisateur> utilisateurOptional = UtilisateurRepository.findById(1L);
                if (utilisateurOptional.isPresent()) {
                    Utilisateur utilisateur = utilisateurOptional.get();
                    existingOperation.setUtilisateur(utilisateur); // Utiliser l'utilisateur avec l'ID 1
                } else {

                    return "redirect:/user/home";
                }

                operationRepository.save(existingOperation); // Sauvegarder les modifications de l'opération

                return "redirect:/user/home";
            } else {
                return "redirect:/user/home";
            }
        } else {
            return "redirect:/user/home";
        }
    }

    @GetMapping("/operation/delete/{id}")
    public String deleteOperationForm(@PathVariable Long id, Model model) {
        try {
            Optional<Operation> operationOptional = operationService.getOperationById(id);

            if (operationOptional.isPresent()) {
                Operation operation = operationOptional.get();
                model.addAttribute("operation", operation);
                return "user/operation/delete";
            } else {
                return "redirect:/user/home";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/operation/delete/{id}")
    public String deleteOperation(@PathVariable Long id) {
        try {
            operationService.deleteOperationById(id);
            return "redirect:/user/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }



    @GetMapping("/edit")
    public String editUserProfileForm(Model model) {
        Optional<Utilisateur> utilisateurOptional = utilisateurService.getUtilisateurById(1L);

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            model.addAttribute("utilisateur", utilisateur);
            return "user/edit_profile";
        } else {
            return "redirect:/user/home";
        }
    }

    @PostMapping("/edit")
    public String editUserProfile(@ModelAttribute Utilisateur utilisateur) {
        Utilisateur existingUtilisateur = utilisateurService.getUtilisateurById(1L).orElse(null);

        if (existingUtilisateur != null) {
            existingUtilisateur.setNom(utilisateur.getNom());
            existingUtilisateur.setPrenom(utilisateur.getPrenom());
            existingUtilisateur.setMail(utilisateur.getMail());
            // Enregistrez les modifications
            utilisateurService.updateUtilisateur(existingUtilisateur);
            return "redirect:/user/home";
        } else {
            return "redirect:/user/home";
        }
    }

    @GetMapping("/suggestion")
    public String showSuggestionsPage() {
        return "user/suggestion";
    }

}
