package projet.gestiondepenses.restAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import projet.gestiondepenses.model.TypeDepense;
import projet.gestiondepenses.service.TypeDepenseService;

import java.util.List;

@RestController
@RequestMapping("/typesdepense")
public class TypeDepenseRestController {

    private TypeDepenseService typeDepenseService;

    @Autowired
    public void TypeDepenseController(TypeDepenseService typeDepenseService) {
        this.typeDepenseService = typeDepenseService;
    }

    public TypeDepenseRestController(TypeDepenseService typeDepenseService) {
        this.typeDepenseService = typeDepenseService;
    }

    @GetMapping
    public List<TypeDepense> getAllTypeDepenses() {
        return typeDepenseService.getAllTypesDepense();
    }

    @GetMapping("/{id}")
    public TypeDepense getTypeDepenseById(@PathVariable Long id) {
        return typeDepenseService.getTypeDepenseById(id).orElse(null);
    }

    @PostMapping
    public TypeDepense createTypeDepense(@RequestBody TypeDepense typeDepense) {
        return typeDepenseService.persistTypeDepense(typeDepense);
    }

    @PutMapping("/{id}")
    public TypeDepense updateTypeDepense(@PathVariable Long id, @RequestBody TypeDepense typeDepense) {
        typeDepense.setIdTypeDep(id);
        return typeDepenseService.updateTypeDepense(typeDepense);
    }

    @DeleteMapping("/{id}")
    public void deleteTypeDepense(@PathVariable Long id) {
        typeDepenseService.deleteTypeDepenseById(id);
    }
}
