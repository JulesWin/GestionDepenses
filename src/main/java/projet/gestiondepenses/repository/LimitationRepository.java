package projet.gestiondepenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.gestiondepenses.model.Limitation;

import java.util.List;
import java.util.Optional;

@Repository
public interface LimitationRepository extends JpaRepository<Limitation, Long> {
    Limitation save(Limitation limitation);

    List<Limitation> findAll();

    Optional<Limitation> findById(Long id);

    void deleteById(Long id);
}
