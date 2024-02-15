package projet.gestiondepenses.service;

import projet.gestiondepenses.model.TypeDepense;

import java.util.List;
import java.util.Optional;

public interface TypeDepenseService {

    List<TypeDepense> getAllTypesDepense();

    TypeDepense persistTypeDepense(TypeDepense typeDepense);

    Optional<TypeDepense> getTypeDepenseById(Long id);

    TypeDepense updateTypeDepense(TypeDepense typeDepense);

    void deleteTypeDepenseById(Long id);

}
