package projet.gestiondepenses.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;

    @Column(name = "intitule_role")
    private String intituleRole;

    @ManyToMany(mappedBy = "roles")
    private Set<Utilisateur> utilisateurs = new HashSet<>();

    // Constructeur par défaut
    public Role() {
    }

    // Constructeur avec intituléRole
    public Role(String intituleRole) {
        this.intituleRole = intituleRole;
    }

    public Long getIdRole() {
        return idRole;
    }

    public String getIntituleRole() {
        return intituleRole;
    }

    public void setIntituleRole(String intituleRole) {
        this.intituleRole = intituleRole;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(idRole, role.idRole) && Objects.equals(intituleRole, role.intituleRole) && Objects.equals(utilisateurs, role.utilisateurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, intituleRole, utilisateurs);
    }

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + idRole +
                ", intituleRole='" + intituleRole + '\'' +
                ", utilisateurs=" + utilisateurs +
                '}';
    }

    public void setIdRole(Long id) {
    }
}