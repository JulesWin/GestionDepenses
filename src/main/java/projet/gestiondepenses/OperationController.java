package projet.gestiondepenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projet.gestiondepenses.model.Operation;
import projet.gestiondepenses.model.TypeDepense;
import projet.gestiondepenses.model.Utilisateur;
import projet.gestiondepenses.service.OperationService;
import projet.gestiondepenses.service.TypeDepenseService;
import projet.gestiondepenses.service.UtilisateurService;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private TypeDepenseService typeDepenseService; // Service pour les types de dépenses

    @Autowired
    private UtilisateurService utilisateurService; // Service pour les utilisateurs


    @GetMapping("")
    public String listOperations(Model model) {
        List<Operation> operations = operationService.getAllOperations();
        model.addAttribute("operations", operations);
        return "operation/list";
    }

    @GetMapping("/add")
    public String addOperationForm(Model model) {
        Operation operation = new Operation();
        // Pré-remplir la date avec la date actuelle
        operation.setDateDep(new Date()); // Utilisation de java.util.Date pour la date actuelle
        List<TypeDepense> allTypesDepense = typeDepenseService.getAllTypesDepense();
        List<Utilisateur> allUtilisateurs = utilisateurService.getAllUtilisateurs();

        // Ajouter les types de dépenses et utilisateurs au modèle
        model.addAttribute("allTypesDepense", allTypesDepense);
        model.addAttribute("allUtilisateurs", allUtilisateurs);
        model.addAttribute("operation", operation);

        return "operation/add";
    }
    @PostMapping("/add")
    public String addOperation(@ModelAttribute Operation operation, @RequestParam("idTypeDep") Long idTypeDep, @RequestParam("idUser") Long idUser) {
        TypeDepense typeDepense = typeDepenseService.getTypeDepenseById(idTypeDep)
                .orElseThrow(() -> new RuntimeException("TypeDepense not found with ID: " + idTypeDep));
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(idUser)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found with ID: " + idUser));

        operation.setDateDep(new Date()); // Utilisation de java.util.Date pour la date actuelle

        operation.setTypeDepense(typeDepense);
        operation.setUtilisateur(utilisateur);

        operationService.persistOperation(operation);

        return "redirect:/operation";
    }

    @GetMapping("/edit/{id}")
    public String editOperationForm(@PathVariable Long id, Model model) {
        Optional<Operation> operationOptional = operationService.getOperationById(id);

        if (operationOptional.isPresent()) {
            Operation operation = operationOptional.get();
            List<TypeDepense> allTypesDepense = typeDepenseService.getAllTypesDepense();
            List<Utilisateur> allUtilisateurs = utilisateurService.getAllUtilisateurs();

            model.addAttribute("allTypesDepense", allTypesDepense);
            model.addAttribute("allUtilisateurs", allUtilisateurs);
            model.addAttribute("operation", operation);

            return "operation/edit";
        } else {
            return "redirect:/operation";
        }
    }

    @PostMapping("/edit/{id}")
    public String editOperation(@PathVariable Long id, @ModelAttribute Operation operation,
                                @RequestParam("idTypeDep") Long idTypeDep, @RequestParam("idUser") Long idUser) {
        Optional<Operation> existingOperationOptional = operationService.getOperationById(id);

        if (existingOperationOptional.isPresent()) {
            Operation existingOperation = existingOperationOptional.get();
            TypeDepense typeDepense = typeDepenseService.getTypeDepenseById(idTypeDep)
                    .orElseThrow(() -> new RuntimeException("TypeDepense not found with ID: " + idTypeDep));

            Utilisateur utilisateur = utilisateurService.getUtilisateurById(idUser)
                    .orElseThrow(() -> new RuntimeException("Utilisateur not found with ID: " + idUser));

            existingOperation.setMontant(operation.getMontant());
            existingOperation.setDateDep(new Date());
            existingOperation.setTypeDepense(typeDepense);
            existingOperation.setUtilisateur(utilisateur);

            operationService.persistOperation(existingOperation);
            return "redirect:/operation";
        } else {
            return "redirect:/operation";
        }
    }
   @GetMapping("/delete/{id}")
    public String deleteOperationForm(@PathVariable Long id, Model model) {
        try {
            Optional<Operation> operationOptional = operationService.getOperationById(id);

            if (operationOptional.isPresent()) {
                Operation operation = operationOptional.get();
                model.addAttribute("operation", operation);
                return "operation/delete";
            } else {
                return "redirect:/operation";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteOperation(@PathVariable Long id) {
        try {
            operationService.deleteOperationById(id);
            return "redirect:/operation";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

}