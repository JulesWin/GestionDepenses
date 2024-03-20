package projet.gestiondepenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet.gestiondepenses.model.Limitation;
import projet.gestiondepenses.model.Operation;
import projet.gestiondepenses.model.Utilisateur;


import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>{
    List<Operation> findAll();
    Operation save(Operation operation);
    Optional<Operation> findById(Long id);
    void deleteById(Long id);

    @Query("SELECT td.nomType, ROUND(SUM(o.montant), 2), l.montantLim, td.idTypeDep " +
            "FROM Operation o " +
            "JOIN o.typeDepense td " +
            "LEFT JOIN Limitation l ON td.idTypeDep = l.typeDepense.idTypeDep AND o.utilisateur.idUser = l.utilisateur.idUser " +
            "WHERE o.utilisateur.idUser = 1 " +
            "AND FUNCTION('MONTH', o.dateDep) = :month " +
            "AND FUNCTION('YEAR', o.dateDep) = :year " +
            "GROUP BY td.nomType, l.montantLim")
    List<Object[]> getTotalOperationsSummaryForUser1ByMonthAndYear(@Param("month") int month, @Param("year") int year);

    // Nouvelle méthode pour obtenir la liste des montants et des dates des opérations pour chaque type de dépense pour un mois et une année spécifiques
    @Query("SELECT CONCAT(o.montant, '€ le : ', " +
            "FUNCTION('DATE_FORMAT', o.dateDep, '%m/%d à %Hh%i')) " +
            "FROM Operation o " +
            "JOIN o.typeDepense td " +
            "WHERE o.utilisateur.idUser = 1 " +
            "AND FUNCTION('MONTH', o.dateDep) = :month " +
            "AND FUNCTION('YEAR', o.dateDep) = :year " +
            "AND td.nomType = :typeDepense")
    List<String> getOperationDetailsForTypeDepenseByMonthAndYear(String typeDepense, @Param("month") int month, @Param("year") int year);
}
