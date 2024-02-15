package projet.gestiondepenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.gestiondepenses.model.Utilisateur;

import java.util.List;
import java.util.Optional;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    List<Utilisateur> findAll();

    Utilisateur save(Utilisateur utilisateur);

    Optional<Utilisateur> findById(Long id);

    void deleteById(Long id);
}
