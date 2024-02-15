package projet.gestiondepenses.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.gestiondepenses.model.TypeDepense;
import projet.gestiondepenses.repository.TypeDepenseRepository;
import projet.gestiondepenses.service.TypeDepenseService;

import java.util.List;
import java.util.Optional;

@Service
public class TypeDepenseServiceImpl implements TypeDepenseService {

    private final TypeDepenseRepository typeDepenseRepository;

    @Autowired
    public TypeDepenseServiceImpl(TypeDepenseRepository typeDepenseRepository) {
        this.typeDepenseRepository = typeDepenseRepository;
    }

    @Override
    public List<TypeDepense> getAllTypesDepense() {
        return typeDepenseRepository.findAll();
    }

    @Override
    public TypeDepense persistTypeDepense(TypeDepense typeDepense) {
        // Logique métier éventuelle
        return typeDepenseRepository.save(typeDepense);
    }

    @Override
    public Optional<TypeDepense> getTypeDepenseById(Long id) {
        return typeDepenseRepository.findById(id);
    }

    @Override
    public TypeDepense updateTypeDepense(TypeDepense typeDepense) {
        // Logique métier éventuelle
        return typeDepenseRepository.save(typeDepense);
    }

    @Override
    public void deleteTypeDepenseById(Long id) {
        typeDepenseRepository.deleteById(id);
    }
}
