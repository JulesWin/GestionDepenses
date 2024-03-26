package projet.gestiondepenses.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT l FROM Limitation l WHERE l.utilisateur.idUser = :userId AND l.typeDepense.idTypeDep = :typeDepenseId")
    Limitation findByUtilisateurIdAndTypeDepenseId(@Param("userId") Long userId, @Param("typeDepenseId") Long typeDepenseId);


    @Query("SELECT l FROM Limitation l WHERE l.utilisateur.idUser = :userId AND l.typeDepense.idTypeDep = :typeDepenseId")
    List<Limitation> findByUtilisateurId(@Param("userId") Long userId);


}