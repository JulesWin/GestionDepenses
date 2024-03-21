package projet.gestiondepenses.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.gestiondepenses.model.Operation;
import projet.gestiondepenses.repository.OperationRepository;
import projet.gestiondepenses.service.OperationService;

import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public Operation persistOperation(Operation operation) {
        // Logique métier éventuelle
        return operationRepository.save(operation);
    }

    @Override
    public Optional<Operation> getOperationById(Long id) {
        return operationRepository.findById(id);
    }

    @Override
    public Operation updateOperation(Operation operation) {
        // Logique métier éventuelle
        return operationRepository.save(operation);
    }

    @Override
    public void deleteOperationById(Long id) {
        operationRepository.deleteById(id);
    }

    /*@Override
    public List<Object[]> getTotalOperationsSummaryForUser1() {
        return operationRepository.getTotalOperationsSummaryForUser1();
    }

    @Override
    public List<String> getOperationDetailsForTypeDepense(String typeDepense) {
        return operationRepository.getOperationDetailsForTypeDepense(typeDepense);
    }*/

    // Nouvelles méthodes pour les opérations par mois et année
    @Override
    public List<Object[]> getTotalOperationsSummaryForUser1ByMonthAndYear(int month, int year) {
        return operationRepository.getTotalOperationsSummaryForUser1ByMonthAndYear(month, year);
    }

    @Override
    public List<Object[]> getOperationDetailsForTypeDepenseByMonthAndYear(String typeDepense, int month, int year) {
        return operationRepository.getOperationDetailsForTypeDepenseByMonthAndYear(typeDepense, month, year);
    }
}
