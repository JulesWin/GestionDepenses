package projet.gestiondepenses.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    public Utilisateur(Long idUser) {
        this.idUser = idUser;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "mail")
    private String mail;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "date_changement_mois")
    private Date dateChangementMois;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Operation> operations;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "appartient",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Limitation> limitations = new ArrayList<>();

    // Constructeur par défaut
    public Utilisateur() {
    }

    // Constructeur avec nom, prénom, email et mot de passe
    public Utilisateur(String nom, String prenom, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motDePasse = motDePasse;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public Date getDateChangementMois() {
        return dateChangementMois;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setDateChangementMois(Date dateChangementMois) {
        this.dateChangementMois = dateChangementMois;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUtilisateurs().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUtilisateurs().remove(this);
    }

    public List<Limitation> getLimitations() {
        return limitations;
    }

    public void setLimitations(List<Limitation> limitations) {
        this.limitations = limitations;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(idUser, that.idUser) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && Objects.equals(mail, that.mail) && Objects.equals(motDePasse, that.motDePasse) && Objects.equals(dateChangementMois, that.dateChangementMois) && Objects.equals(operations, that.operations) && Objects.equals(roles, that.roles) && Objects.equals(limitations, that.limitations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, nom, prenom, mail, motDePasse, dateChangementMois, operations, roles, limitations);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUser=" + idUser +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", dateChangementMois=" + dateChangementMois +
                ", operations=" + operations +
                ", roles=" + roles +
                ", limitations=" + limitations +
                '}';
    }

    public void setIdUser(Long id) {
    }
}
