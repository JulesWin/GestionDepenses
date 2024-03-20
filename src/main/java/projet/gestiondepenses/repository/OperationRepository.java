package projet.gestiondepenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    // Étape 1 : Obtenir les informations agrégées sur les opérations
    @Query("SELECT td.nomType, ROUND(SUM(o.montant), 2), l.montantLim, td.idTypeDep " +
            "FROM Operation o " +
            "JOIN o.typeDepense td " +
            "LEFT JOIN Limitation l ON td.idTypeDep = l.typeDepense.idTypeDep AND o.utilisateur.idUser = l.utilisateur.idUser " +
            "WHERE o.utilisateur.idUser = 1 " +
            "GROUP BY td.nomType, l.montantLim")
    List<Object[]> getTotalOperationsSummaryForUser1();

    // Étape 2 : Obtenir la liste des montants et des dates des opérations pour chaque type de dépense
    @Query("SELECT CONCAT(o.montant, '€ le : ', " +
            "FUNCTION('DATE_FORMAT', o.dateDep, '%m/%d à %Hh%i')) " +
            "FROM Operation o " +
            "JOIN o.typeDepense td " +
            "WHERE o.utilisateur.idUser = 1 AND td.nomType = :typeDepense")
    List<String> getOperationDetailsForTypeDepense(String typeDepense);

}
