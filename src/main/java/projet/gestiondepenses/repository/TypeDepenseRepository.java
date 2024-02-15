package projet.gestiondepenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.gestiondepenses.model.TypeDepense;

import java.util.List;
import java.util.Optional;

@Repository

public interface TypeDepenseRepository extends JpaRepository<TypeDepense, Long> {
    List<TypeDepense> findAll();

    TypeDepense save(TypeDepense typeDepense);

    Optional<TypeDepense> findById(Long id);

    void deleteById(Long id);
}
