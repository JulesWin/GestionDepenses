package projet.gestiondepenses.service;

import projet.gestiondepenses.model.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationService {

    List<Operation> getAllOperations();

    Operation persistOperation(Operation operation);

    Optional<Operation> getOperationById(Long id);

    Operation updateOperation(Operation operation);

    void deleteOperationById(Long id);
}
