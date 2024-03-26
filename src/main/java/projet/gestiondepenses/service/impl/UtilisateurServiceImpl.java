package projet.gestiondepenses.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.gestiondepenses.model.Utilisateur;
import projet.gestiondepenses.repository.UtilisateurRepository;
import projet.gestiondepenses.service.UtilisateurService;
import projet.gestiondepenses.model.Role;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    //@Override
    //public List<Utilisateur> getAllUtilisateurs() {
     //   return utilisateurRepository.findAll();
    //}

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        for (Utilisateur utilisateur : utilisateurs) {
            utilisateur.getRoles().size(); // Forcer le chargement paresseux des rôles
            System.out.println("Utilisateur " + utilisateur.getIdUser() + " a les rôles suivants : " + utilisateur.getRoles());
        }
        return utilisateurs;
    }



    @Override
    public Utilisateur persistUtilisateur(Utilisateur utilisateur) {
        // Logique métier éventuelle
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        // Logique métier éventuelle
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void deleteUtilisateurById(Long id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public List<Utilisateur> getAllUtilisateursWithRoles() {
        return utilisateurRepository.findAllWithRoles();
    }

    @Override
    public List<String> getRolesNamesByUserId(Long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId).orElse(null);
        if (utilisateur != null) {
            return utilisateur.getRoles()
                    .stream()
                    .map(Role::getIntituleRole)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}