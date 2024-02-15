package projet.gestiondepenses.service;

import projet.gestiondepenses.model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    List<Utilisateur> getAllUtilisateurs();
    Utilisateur persistUtilisateur(Utilisateur utilisateur);
    Optional<Utilisateur> getUtilisateurById(Long id);
    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateurById(Long id);
}
