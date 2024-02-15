package projet.gestiondepenses.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dep")
    private Long idDep;

    @Column(name = "montant")
    private float montant;

    @Column(name = "date_dep")
    private Date dateDep;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_type_dep")
    private TypeDepense typeDepense;

    // Constructeur par défaut
    public Operation() {
    }

    // Constructeur avec montant, date, typeDepense et utilisateur
    public Operation(float montant, Date dateDep, TypeDepense typeDepense, Utilisateur utilisateur) {
        this.montant = montant;
        this.dateDep = dateDep;
        this.typeDepense = typeDepense;
        this.utilisateur = utilisateur;
    }

    public Long getIdDep() {
        return idDep;
    }

    public float getMontant() {
        return montant;
    }

    public Date getDateDep() {
        return dateDep;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public void setDateDep(Date dateDep) {
        this.dateDep = dateDep;
    }

    public TypeDepense getTypeDepense() {
        return typeDepense;
    }

    public void setTypeDepense(TypeDepense typeDepense) {
        this.typeDepense = typeDepense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Float.compare(montant, operation.montant) == 0 && Objects.equals(idDep, operation.idDep) && Objects.equals(dateDep, operation.dateDep) && Objects.equals(utilisateur, operation.utilisateur) && Objects.equals(typeDepense, operation.typeDepense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDep, montant, dateDep, utilisateur, typeDepense);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "idDep=" + idDep +
                ", montant=" + montant +
                ", dateDep=" + dateDep +
                ", utilisateur=" + utilisateur +
                ", typeDepense=" + typeDepense +
                '}';
    }

    public void setIdDep(Long id) {
    }
}