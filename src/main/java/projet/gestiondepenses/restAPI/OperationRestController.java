package projet.gestiondepenses.restAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import projet.gestiondepenses.model.Operation;
import projet.gestiondepenses.service.OperationService;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationRestController {

    private OperationService operationService;

    @Autowired
    public void OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    public OperationRestController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public List<Operation> getAllOperations() {
        return operationService.getAllOperations();
    }

    @GetMapping("/{id}")
    public Operation getOperationById(@PathVariable Long id) {
        return operationService.getOperationById(id).orElse(null);
    }

    @PostMapping
    public Operation createOperation(@RequestBody Operation operation) {
        return operationService.persistOperation(operation);
    }

    @PutMapping("/{id}")
    public Operation updateOperation(@PathVariable Long id, @RequestBody Operation operation) {
        operation.setIdDep(id);
        return operationService.updateOperation(operation);
    }

    @DeleteMapping("/{id}")
    public void deleteOperation(@PathVariable Long id) {
        operationService.deleteOperationById(id);
    }
}
