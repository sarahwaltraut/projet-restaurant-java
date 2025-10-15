package facade;

import singleton.RestaurantManager;
import strategy.*;
import factory.*;
import restaurant.Employe;
import restaurant.Facture;
import restaurant.Ingredient;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class Restaurant_Facade {

    public final RestaurantManager manager;

    public Restaurant_Facade() {
        manager = RestaurantManager.getInstance();
    }
    
 // =====================
 // 👨‍💼 MENU GÉRANT
 // =====================
 public void menuGerant() {
     String[] options = {"Gérer le personnel", "Gérer le stock", "Gérer les factures", "Retour"};
     String choix = (String) JOptionPane.showInputDialog(null, 
             "Menu du Gérant :", "Gérant", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

     if (choix == null || choix.equals("Retour")) return;

     switch (choix) {
         case "Gérer le personnel" -> menuPersonnel();
         case "Gérer le stock" -> menuStock();
         case "Gérer les factures" -> menuFacturation();
     }
 }

 // =====================
 // 🧑‍🍳 MENU SERVEUR
 // =====================
 public void menuServeur() {
     String[] options = {"Créer facture", "Afficher factures", "Afficher stock", "Retour"};
     String choix = (String) JOptionPane.showInputDialog(null, 
             "Menu Serveur :", "Serveur", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

     if (choix == null || choix.equals("Retour")) return;

     switch (choix) {
         case "Créer facture" -> creerFacture();
       //  case "Afficher factures" -> afficherFactures();
         case "Afficher stock" -> afficherStock();
     }
 }

 // =====================
 // 👩‍🍳 MENU CUISINIER
 // =====================
 public void menuCuisinier() {
     String[] options = {"Afficher stock", "Ajouter ingrédient", "Retour"};
     String choix = (String) JOptionPane.showInputDialog(null, 
             "Menu Cuisinier :", "Cuisinier", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

     if (choix == null || choix.equals("Retour")) return;

     switch (choix) {
         case "Afficher stock" -> afficherStock();
         case "Ajouter ingrédient" -> ajouterIngredient();
     }
 }

    // ========================
    // 👩‍🍳 GESTION DU PERSONNEL
    // ========================

    public void ajouterEmploye() {
        String id = JOptionPane.showInputDialog("ID de l'employé :");
        String nom = JOptionPane.showInputDialog("Nom de l'employé :");
        String role = JOptionPane.showInputDialog("Rôle (Serveur, Cuisinier, etc.) :");

        Employe employe = Employe_Factory.creerEmploye(id, nom, role);
        manager.ajouterEmploye(employe);
        JOptionPane.showMessageDialog(null, "✅ Employé ajouté !");
    }

    public void supprimerEmploye() {
        List<Employe> liste = manager.getPersonnels();
        if (liste.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun employé enregistré !");
            return;
        }

        String[] options = liste.stream().map(Employe::toString).toArray(String[]::new);
        String choix = (String) JOptionPane.showInputDialog(null, "Sélectionner un employé à supprimer :", 
                "Suppression Employé", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choix != null) {
            for (Employe e : liste) {
                if (choix.contains(e.getId())) {
                    manager.supprimerEmploye(e);
                    JOptionPane.showMessageDialog(null, "❌ Employé supprimé !");
                    break;
                }
            }
        }
    }

    public void afficherEmployes() {
        List<Employe> liste = manager.getPersonnels();
        if (liste.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun employé disponible.");
            return;
        }
        StringBuilder sb = new StringBuilder("👩‍🍳 Liste du personnel :\n\n");
        for (Employe e : liste) {
            sb.append("• ").append(e.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // ==========================
    // 💵 FACTURATION ET PAIEMENT
    // ==========================

    public void creerFacture() {
        String id = JOptionPane.showInputDialog("ID Facture :");
        double montant = Double.parseDouble(JOptionPane.showInputDialog("Montant total (€) :"));

        String[] options = {"Carte", "Espèces", "Mobile"};
        String choix = (String) JOptionPane.showInputDialog(null, "Méthode de paiement :", 
                "Paiement", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        Paiement_Strategy paiement;
        switch (choix) {
            case "Espèces" -> paiement = new Paiement_espèce();
            default -> paiement = new Paiement_par_carte();
        }

        paiement.payer(montant);
        Facture facture = new Facture(id, montant, LocalDate.now());
      //  manager.ajouterFacture(facture);
        JOptionPane.showMessageDialog(null, "✅ Facture enregistrée !");
    }

    /*public void afficherFactures() {
       // List<Facture> factures = manager.getFactures();
        if (factures.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucune facture disponible.");
            return;
        }
        StringBuilder sb = new StringBuilder("📄 Liste des factures :\n\n");
        for (Facture f : factures) {
            sb.append(f.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }*/

    // ==================
    // 🥬 GESTION DU STOCK
    // ==================

    public void ajouterIngredient() {
        String nom = JOptionPane.showInputDialog("Nom de l’ingrédient :");
        int quantite = Integer.parseInt(JOptionPane.showInputDialog("Quantité :"));

        Ingredient ingredient = IngredientFactory.creerIngredient(nom, quantite);
        manager.ajouterIngredient(ingredient);
        JOptionPane.showMessageDialog(null, "✅ Ingrédient ajouté !");
    }

    public void afficherStock() {
        List<Ingredient> ingredients = manager.getStock();
        if (ingredients.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le stock est vide.");
            return;
        }

        StringBuilder sb = new StringBuilder("🥦 Stock actuel :\n\n");
        for (Ingredient i : ingredients) {
            sb.append("• ").append(i.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // ==================
    // 🎬 MENU PRINCIPAL
    // ==================

    public void lancerApplication() {
        String[] options = {
                "👩‍🍳 Gestion du Personnel",
                "💵 Facturation & Paiement",
                "🥬 Gestion du Stock",
                "📊 Reporting",
                "🚪 Quitter"
        };

        boolean continuer = true;

        while (continuer) {
            String choix = (String) JOptionPane.showInputDialog(
                    null, "Bienvenue dans le système du restaurant !\nQue souhaitez-vous faire ?",
                    "Menu Principal", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choix == null || choix.equals("🚪 Quitter")) {
                continuer = false;
                JOptionPane.showMessageDialog(null, "👋 À bientôt !");
                break;
            }

            switch (choix) {
                case "👩‍🍳 Gestion du Personnel" -> menuPersonnel();
                case "💵 Facturation & Paiement" -> menuFacturation();
                case "🥬 Gestion du Stock" -> menuStock();
             //   case "📊 Reporting" -> genererRapport();
            }
        }
    }

    private void menuPersonnel() {
        String[] options = {"Ajouter", "Supprimer", "Afficher", "Retour"};
        String choix = (String) JOptionPane.showInputDialog(null, "Gestion du personnel :", 
                "Personnel", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choix == null || choix.equals("Retour")) return;

        switch (choix) {
            case "Ajouter" -> ajouterEmploye();
            case "Supprimer" -> supprimerEmploye();
            case "Afficher" -> afficherEmployes();
        }
    }

    private void menuFacturation() {
        String[] options = {"Créer Facture", "Afficher Factures", "Retour"};
        String choix = (String) JOptionPane.showInputDialog(null, "Facturation :", 
                "Paiement", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choix == null || choix.equals("Retour")) return;

        switch (choix) {
            case "Créer Facture" -> creerFacture();
           // case "Afficher Factures" -> afficherFactures();
        }
    }

    private void menuStock() {
        String[] options = {"Ajouter Ingrédient", "Afficher Stock", "Retour"};
        String choix = (String) JOptionPane.showInputDialog(null, "Gestion du Stock :", 
                "Stock", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choix == null || choix.equals("Retour")) return;

        switch (choix) {
            case "Ajouter Ingrédient" -> ajouterIngredient();
            case "Afficher Stock" -> afficherStock();
        }
    }
}
