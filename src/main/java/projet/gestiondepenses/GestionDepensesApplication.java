package projet.gestiondepenses;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import projet.gestiondepenses.model.*;
import projet.gestiondepenses.repository.RoleRepository;
import projet.gestiondepenses.repository.TypeDepenseRepository;
import projet.gestiondepenses.repository.UtilisateurRepository;
import projet.gestiondepenses.repository.LimitationRepository;
import projet.gestiondepenses.repository.OperationRepository;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class GestionDepensesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDepensesApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner initData(
            RoleRepository roleRepository,
            UtilisateurRepository utilisateurRepository,
            TypeDepenseRepository typeDepenseRepository,
            OperationRepository operationRepository,
            LimitationRepository limitationRepository) {
        return args -> {
            // Ajouter des rôles
            Role adminRole = new Role("ADMIN");
            Role userRole = new Role("USER");
            roleRepository.saveAll(Arrays.asList(adminRole, userRole));

            // Ajouter des utilisateurs avec des rôles
            Utilisateur adminUser = new Utilisateur("Admin", "User", "admin@example.com", "adminPassword");
            adminUser.setRoles(Arrays.asList(adminRole));
            utilisateurRepository.save(adminUser);

            Utilisateur normalUser = new Utilisateur("Normal", "User", "user@example.com", "userPassword");
            normalUser.setRoles(Arrays.asList(userRole));
            utilisateurRepository.save(normalUser);

            // Ajouter des types de dépense
            TypeDepense alimentationType = new TypeDepense("Alimentation");
            TypeDepense loisirsType = new TypeDepense("Loisirs");
            typeDepenseRepository.saveAll(Arrays.asList(alimentationType, loisirsType));

            // Ajouter des opérations avec des utilisateurs et des types de dépense
            Operation operation1 = new Operation(100, new Date(), alimentationType, normalUser);
            Operation operation2 = new Operation(500, new Date(), loisirsType, adminUser);
            operationRepository.saveAll(Arrays.asList(operation1, operation2));

            // Ajouter des limitations avec des utilisateurs et des types de dépense
            Limitation limitation1 = new Limitation(normalUser, alimentationType, 200.0);
            Limitation limitation2 = new Limitation(adminUser, loisirsType, 100.0);
            limitationRepository.saveAll(Arrays.asList(limitation1, limitation2));

            // Ajoutez d'autres données au besoin
        };
    }*/



}
