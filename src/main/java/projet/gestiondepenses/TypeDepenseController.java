package projet.gestiondepenses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projet.gestiondepenses.model.TypeDepense;
import projet.gestiondepenses.service.TypeDepenseService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/typeDepense")
public class TypeDepenseController {

    @Autowired
    private TypeDepenseService typeDepenseService; // Assurez-vous d'avoir le service correspondant

    @GetMapping("")
    public String listTypeDepenses(Model model) {
        List<TypeDepense> typeDepenses = typeDepenseService.getAllTypesDepense();
        model.addAttribute("typeDepenses", typeDepenses);
        return "typeDepense/list";
    }

    @GetMapping("/add")
    public String addTypeDepenseForm(Model model) {
        model.addAttribute("typeDepense", new TypeDepense());
        return "typeDepense/add";
    }

    @PostMapping("/add")
    public String addTypeDepense(@ModelAttribute TypeDepense typeDepense) {
        typeDepenseService.persistTypeDepense(typeDepense);
        return "redirect:/typeDepense";
    }

    @GetMapping("/edit/{id}")
    public String editTypeDepenseForm(@PathVariable Long id, Model model) {
        Optional<TypeDepense> typeDepenseOptional = typeDepenseService.getTypeDepenseById(id);

        if (typeDepenseOptional.isPresent()) {
            TypeDepense typeDepense = typeDepenseOptional.get();
            model.addAttribute("typeDepense", typeDepense);
            return "typeDepense/edit";
        } else {
            // Gérez le cas où le TypeDepense avec l'ID spécifié n'est pas trouvé
            return "redirect:/typeDepense";
        }
    }

    @PostMapping("/edit/{id}")
    public String editTypeDepense(@PathVariable Long id, @ModelAttribute TypeDepense typeDepense) {
        // Récupérer le TypeDepense existant
        TypeDepense existingTypeDepense = typeDepenseService.getTypeDepenseById(id).orElse(null);

        if (existingTypeDepense != null) {
            existingTypeDepense.setNomType(typeDepense.getNomType());
            // Enregistrez les modifications
            typeDepenseService.updateTypeDepense(existingTypeDepense);
            return "redirect:/typeDepense";
        } else {
            return "redirect:/typeDepense";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTypeDepenseForm(@PathVariable Long id) {
        try {
            // Supprimez le TypeDepense avec le service
            typeDepenseService.deleteTypeDepenseById(id);
            return "redirect:/typeDepense";
        } catch (Exception e) {
            e.printStackTrace(); // Ajoutez des logs appropriés ici
            return "redirect:/error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteTypeDepense(@PathVariable Long id) {
        try {
            // Supprimez le TypeDepense avec le service
            typeDepenseService.deleteTypeDepenseById(id);
            return "redirect:/typeDepense/list";
        } catch (Exception e) {
            e.printStackTrace(); // Ajoutez des logs appropriés ici
            return "redirect:/error";
        }
    }
}
