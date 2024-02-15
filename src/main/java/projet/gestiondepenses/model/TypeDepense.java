package projet.gestiondepenses.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "type_depense")
public class TypeDepense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_dep")
    private Long idTypeDep;

    @Column(name = "nom_type")
    private String nomType;

    @OneToMany(mappedBy = "typeDepense", cascade = CascadeType.ALL)
    private List<Operation> operations;

    // Constructeur par défaut
    public TypeDepense() {
    }

    // Constructeur avec nomType
    public TypeDepense(String nomType) {
        this.nomType = nomType;
    }
    public Long getIdTypeDep() {
        return idTypeDep;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeDepense that = (TypeDepense) o;
        return Objects.equals(idTypeDep, that.idTypeDep) && Objects.equals(nomType, that.nomType) && Objects.equals(operations, that.operations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeDep, nomType, operations);
    }

    @Override
    public String toString() {
        return "TypeDepense{" +
                "idTypeDep=" + idTypeDep +
                ", nomType='" + nomType + '\'' +
                ", operations=" + operations +
                '}';
    }

    public void setIdTypeDep(Long id) {
    }
}
