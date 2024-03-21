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
    //List<Object[]>getTotalOperationsSummaryForUser1();
    //List<String> getOperationDetailsForTypeDepense(String typeDepense);

    // Nouvelles méthodes pour les opérations par mois et année
    List<Object[]> getTotalOperationsSummaryForUser1ByMonthAndYear(int month, int year);

    List<Object[]> getOperationDetailsForTypeDepenseByMonthAndYear(String typeDepense, int month, int year);
}
