package singleton;

import restaurant.*;
import observer.Observer;
import observer.System_Notification;

import java.io.*;
import java.util.*;

import factory.Employe_Factory;

public class RestaurantManager {
    private static RestaurantManager instance;

    private List<Employe> personnels;
   // private List<Table> tables;
    private List<Ingredient> stock;
   // private List<Reservation> reservations;

    private System_Notification sujetNotifications;

    // fichiers de stockage
    private final String FICHIER_PERSONNEL = "data/personnel.txt";
    private final String FICHIER_TABLES = "data/tables.txt";
    private final String FICHIER_STOCK = "data/stock.txt";
    private final String FICHIER_RESERVATIONS = "data/reservations.txt";

    private RestaurantManager() {
        personnels = new ArrayList<>();
      //  tables = new ArrayList<>();
        stock = new ArrayList<>();
      //  reservations = new ArrayList<>();
        sujetNotifications = new System_Notification();

        chargerPersonnel();
        //chargerTables();
        chargerStock();
      //  chargerReservations();
    }

    // Singleton
    public static RestaurantManager getInstance() {
        if (instance == null) instance = new RestaurantManager();
        return instance;
    }

    public List<Employe> getPersonnels() { return personnels; }
    //public List<Table> getTables() { return tables; }
    public List<Ingredient> getStock() { return stock; }
    //public List<Reservation> getReservations() { return reservations; }
    public System_Notification getSujetNotifications() { return sujetNotifications; }

    // === Gestion du personnel ===
    private void chargerPersonnel() {
        personnels.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FICHIER_PERSONNEL))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length < 3) continue;
                String id = parts[0];
                String nom = parts[1];
                String role = parts[2];

                Employe e = Employe_Factory.creerEmploye(id, nom, role);
                personnels.add(e);

                if (e instanceof Observer) {
                    sujetNotifications.ajouterObservateur((Observer) e);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de chargement du personnel : " + e.getMessage());
        }
    }

    public void sauvegarderPersonnel() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER_PERSONNEL))) {
            for (Employe e : personnels) {
                bw.write(e.getId() + ";" + e.getNom() + ";" + e.getRole());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de sauvegarde du personnel : " + e.getMessage());
        }
    }

    public void ajouterEmploye(Employe e) {
        personnels.add(e);
        if (e instanceof Observer) sujetNotifications.ajouterObservateur((Observer) e);
        sauvegarderPersonnel();
    }

    public void supprimerEmploye(Employe e) {
        if (personnels.remove(e)) {
            if (e instanceof Observer) sujetNotifications.supprimerObservateur((Observer) e);
            sauvegarderPersonnel();
        }
    }


    // === Gestion des tables ===
   /* private void chargerTables() {
        tables.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FICHIER_TABLES))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length < 2) continue;
                tables.add(new Table(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de chargement des tables : " + e.getMessage());
        }
    }

    public void sauvegarderTables() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER_TABLES))) {
            for (Table t : tables) {
                bw.write(t.getId() + ";" + t.getNbPlaces());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de sauvegarde des tables : " + e.getMessage());
        }
    }*/

    // === Gestion du stock ===
    private void chargerStock() {
        stock.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FICHIER_STOCK))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(";");
                if (parts.length < 2) continue;
                stock.add(new Ingredient(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de chargement du stock : " + e.getMessage());
        }
    }
    
    public void ajouterIngredient(Ingredient i) {
        stock.add(i);
        sauvegarderStock();
    }

    public void sauvegarderStock() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER_STOCK))) {
            for (Ingredient i : stock) {
                bw.write(i.getNom() + ";" + i.getQuantite());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de sauvegarde du stock : " + e.getMessage());
        }
    }

    
    public void sauvegarderReservations() {
        // pour l'instant rien à faire
    }

    // === Sauvegarde globale ===
    public void sauvegarderTout() {
        sauvegarderPersonnel();
       // sauvegarderTables();
        sauvegarderStock();
        sauvegarderReservations();
    }
}
