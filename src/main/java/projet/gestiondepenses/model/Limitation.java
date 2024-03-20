package projet.gestiondepenses.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Limitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idTypeDep")
    private TypeDepense typeDepense;

    /*@ManyToOne
    @JoinColumn(name = "id_user") // Nom de la colonne dans la base de données pour l'utilisateur
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_type_dep") // Nom de la colonne dans la base de données pour le type de dépense
    private TypeDepense typeDepense;*/


    private Double montantLim;

    // Constructeur par défaut
    public Limitation() {
    }

    // Constructeur avec utilisateur, typeDepense et montantLim
    public Limitation(Utilisateur utilisateur, TypeDepense typeDepense, double montantLim) {
        this.utilisateur = utilisateur;
        this.typeDepense = typeDepense;
        this.montantLim = montantLim;
    }

    public Long getId() {
        return id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public TypeDepense getTypeDepense() {
        return typeDepense;
    }

    public void setTypeDepense(TypeDepense typeDepense) {
        this.typeDepense = typeDepense;
    }

    public Double getMontantLim() {
        return montantLim;
    }

    public void setMontantLim(Double montantLim) {
        this.montantLim = montantLim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Limitation that = (Limitation) o;
        return Objects.equals(id, that.id) && Objects.equals(utilisateur, that.utilisateur) && Objects.equals(typeDepense, that.typeDepense) && Objects.equals(montantLim, that.montantLim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, utilisateur, typeDepense, montantLim);
    }

    @Override
    public String toString() {
        return "Limitation{" +
                "id=" + id +
                ", utilisateur=" + utilisateur +
                ", typeDepense=" + typeDepense +
                ", montantLim=" + montantLim +
                '}';
    }

    public void setIdLimitation(Long id) {
    }
}
