package projet.gestiondepenses.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.gestiondepenses.model.Limitation;
import projet.gestiondepenses.repository.LimitationRepository;
import projet.gestiondepenses.service.LimitationService;

import java.util.List;
import java.util.Optional;

@Service
public class LimitationServiceImpl implements LimitationService {

    private final LimitationRepository limitationRepository;

    @Autowired
    public LimitationServiceImpl(LimitationRepository limitationRepository) {
        this.limitationRepository = limitationRepository;
    }

    @Override
    public List<Limitation> getAllLimitations() {
        return limitationRepository.findAll();
    }

    @Override
    public Limitation persistLimitation(Limitation limitation) {
        // Logique métier éventuelle
        return limitationRepository.save(limitation);
    }

    @Override
    public Optional<Limitation> getLimitationById(Long id) {
        return limitationRepository.findById(id);
    }

    @Override
    public Limitation updateLimitation(Limitation limitation) {
        // Logique métier éventuelle
        return limitationRepository.save(limitation);
    }

    @Override
    public void deleteLimitationById(Long id) {
        limitationRepository.deleteById(id);
    }

    @Override
    public List<Limitation> getAllOperations() {
        return null;
    }

    @Override
    public Limitation persistOperation(Limitation operation) {
        return null;
    }

    @Override
    public Optional<Limitation> getOperationById(Long id) {
        return Optional.empty();
    }

    @Override
    public Limitation updateOperation(Limitation operation) {
        return null;
    }

    @Override
    public void deleteOperationById(Long id) {

    }
}
