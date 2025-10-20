package restaurant;
import facade.Restaurant_Facade;

import java.time.LocalDateTime;

import javax.swing.*;

import Reservation.GestionReservations;
import facade.*;
import factory.*;
import observer.*;
import state.*;

public class Zemainnn {
    public static void main(String[] args) {
        Restaurant_Facade restaurant = new Restaurant_Facade();

        boolean quitter = false;

        while (!quitter) {
            String[] options = {"Gérant", "Serveur", "Cuisinier", "Quitter"};
            int choix = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenue dans le système de gestion du restaurant 🍽️",
                    "Menu Principal",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choix) {
                case 0 -> restaurant.menuGerant();
                case 1 -> restaurant.menuServeur();
                case 2 -> restaurant.menuCuisinier();
                default -> quitter = true;
            }
        }

        restaurant.manager.sauvegarderTout(); // sauvegarde globale
        JOptionPane.showMessageDialog(null, "Données sauvegardées, au revoir 👋");
        
        
        System.out.println("=== TEST GESTION DU MENU ===");
        Menu menu = new Menu();
        menu.ajouter(MenuFactory.create("entree", "Salade verte", 4.5));
        menu.ajouter(MenuFactory.create("plat", "Poulet rôti", 12.0));
        menu.ajouter(MenuFactory.create("dessert", "Tarte aux pommes", 5.0));
        menu.ajouter(MenuFactory.create("boisson", "Eau minérale", 2.0));

        menu.afficherMenu();
        menu.modifierParNom("Poulet rôti", "Poulet rôti au four", 13.0);
        menu.supprimerParNom("Eau minérale");
        menu.afficherMenu();

        System.out.println("\n=== TEST GESTION DES TABLES ===");
        GestionTables gestionTables = new GestionTables();
        gestionTables.ajouterTable(1, 4);
        gestionTables.ajouterTable(2, 2);

        Table t1 = gestionTables.trouverTable(1);
        Table t2 = gestionTables.trouverTable(2);

        t1.reserver();
        t2.occuper();
        t1.liberer();
        t2.liberer();
        t1.nettoyer();
        gestionTables.afficherTables();

        System.out.println("\n=== TEST GESTION DES COMMANDES ===");
        // Création de la commande
        Commande commande = new Commande();
        // Observateurs fictifs (test)
        commande.ajouterObserver(message -> System.out.println("[Observateur Test] " + message));

        commande.ajouterItem(MenuFactory.create("plat", "Pizza Margherita", 10.0));
        commande.ajouterItem(MenuFactory.create("boisson", "Coca-Cola", 3.0));
        commande.afficherCommande();

        // Transition des états
        commande.etatSuivant(); // Nouvelle -> En cours
        commande.etatSuivant(); // En cours -> Prête
        commande.etatSuivant(); // Prête -> Livrée
        commande.etatSuivant(); // Livrée -> Payée

        System.out.println("\n=== TEST GESTION DES RÉSERVATIONS ===");
        GestionReservations gestionReservations = new GestionReservations();

        // Création réservation
        gestionReservations.creerReservation("Alice Dupont", 3, t1, LocalDateTime.of(2025, 10, 19, 19, 0));
        gestionReservations.creerReservation("Bob Martin", 2, t2, LocalDateTime.of(2025, 10, 19, 20, 0));

        // Affichage
        gestionReservations.afficherReservations();

        // Modification
        gestionReservations.modifierReservation("Alice Dupont", LocalDateTime.of(2025, 10, 19, 20, 30), t2);

        // Annulation
        gestionReservations.annulerReservation("Bob Martin");

        // Affichage final
        gestionReservations.afficherReservations();
        
        System.out.println("On a réussiiiii!!!!!!!!");
    
    	
    	
    	
    	
    	
    	
    	
    	
    }
}
