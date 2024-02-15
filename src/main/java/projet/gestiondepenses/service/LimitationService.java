package projet.gestiondepenses.service;

import projet.gestiondepenses.model.Limitation;

import java.util.List;
import java.util.Optional;

public interface LimitationService {

    List<Limitation> getAllLimitations();

    Limitation persistLimitation(Limitation limitation);

    Optional<Limitation> getLimitationById(Long id);

    Limitation updateLimitation(Limitation limitation);

    void deleteLimitationById(Long id);

    List<Limitation> getAllOperations();

    Limitation persistOperation(Limitation operation);

    Optional<Limitation> getOperationById(Long id);

    Limitation updateOperation(Limitation operation);

    void deleteOperationById(Long id);
}
