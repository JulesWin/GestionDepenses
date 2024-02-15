package projet.gestiondepenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.gestiondepenses.model.Limitation;
import projet.gestiondepenses.model.Operation;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>{
    List<Operation> findAll();

    Operation save(Operation operation);

    Optional<Operation> findById(Long id);

    void deleteById(Long id);
}
