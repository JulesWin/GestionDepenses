package projet.gestiondepenses.restAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import projet.gestiondepenses.model.Limitation;
import projet.gestiondepenses.service.LimitationService;

import java.util.List;

@RestController
@RequestMapping("/limitations")
public class LimitationRestController {

    private LimitationService limitationService;

    @Autowired
    public void LimitationController(LimitationService limitationService) {
        this.limitationService = limitationService;
    }

    public LimitationRestController(LimitationService limitationService) {
        this.limitationService = limitationService;
    }

    @GetMapping
    public List<Limitation> getAllLimitations() {
        return limitationService.getAllLimitations();
    }

    @GetMapping("/{id}")
    public Limitation getLimitationById(@PathVariable Long id) {
        return limitationService.getLimitationById(id).orElse(null);
    }

    @PostMapping
    public Limitation createLimitation(@RequestBody Limitation limitation) {
        return limitationService.persistLimitation(limitation);
    }

    @PutMapping("/{id}")
    public Limitation updateLimitation(@PathVariable Long id, @RequestBody Limitation limitation) {
        limitation.setIdLimitation(id);
        return limitationService.updateLimitation(limitation);
    }

    @DeleteMapping("/{id}")
    public void deleteLimitation(@PathVariable Long id) {
        limitationService.deleteLimitationById(id);
    }
}
