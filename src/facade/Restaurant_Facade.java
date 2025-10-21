package facade;

import singleton.RestaurantManager;
import state.GestionTables;
import state.Table;
import strategy.*;
import factory.*;
import observer.Commande;
import restaurant.Employe;
import restaurant.Facture;
import restaurant.Ingredient;

import javax.swing.*;

import Reservation.GestionReservations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Restaurant_Facade {

    public final RestaurantManager manager;
    private final Menu menu;
   // private final GestionTables gestionTables;
    private final GestionReservations gestionReservations;

    public Restaurant_Facade() {
        manager = RestaurantManager.getInstance();
        menu = new Menu();
      //  gestionTables = new GestionTables();
        gestionReservations = new GestionReservations();
        
    }
    
 // =====================
 // 👨‍💼 MENU GÉRANT
 // =====================
 public void menuGerant() {
     String[] options = {"Gérer le personnel", "Gérer le Menu","Gérer les Réservations" , "Gérer le stock", "Gérer les factures", "Retour"};
     String choix = (String) JOptionPane.showInputDialog(null, 
             "Menu du Gérant :", "Gérant", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

     if (choix == null || choix.equals("Retour")) return;

     switch (choix) {
         case "Gérer le personnel" -> menuPersonnel();
         case "Gérer le Menu" -> menuGestionMenu();
         case "Gérer les Réservations" -> menuGestionReservations();
         case "Gérer les factures" -> menuFacturation();
         case "Gérer le stock" -> menuStock();
         
     }
 }
 
			//=========================================================
			//🧾 PARTIE 1 : GESTION DU MENU (Factory Pattern + Dialogues)
			//=========================================================
			private void menuGestionMenu() {
			  RestaurantManager manager = RestaurantManager.getInstance(); // ✅ Toujours utiliser le singleton
			
			  String[] options = {"Ajouter", "Modifier", "Supprimer", "Afficher", "Retour"};
			  String choix = (String) JOptionPane.showInputDialog(
			          null,
			          "Que souhaitez-vous faire ?",
			          "Gestion du Menu",
			          JOptionPane.QUESTION_MESSAGE,
			          null,
			          options,
			          options[0]
			  );
			
			  if (choix == null || choix.equals("Retour")) return;
			
			  switch (choix) {
			      case "Ajouter" -> ajouterItemMenu(manager);
			      case "Modifier" -> modifierItemMenu(manager);
			      case "Supprimer" -> supprimerItemMenu(manager);
			      case "Afficher" -> afficherMenu(manager);
			  }
			}
			
			//=========================================================
			//✅ Ajouter un élément au menu
			//=========================================================
			private void ajouterItemMenu(RestaurantManager manager) {
			  String[] types = {"entree", "plat", "dessert", "boisson"};
			  String type = (String) JOptionPane.showInputDialog(
			          null,
			          "Type d'élément :",
			          "Ajout au menu",
			          JOptionPane.QUESTION_MESSAGE,
			          null,
			          types,
			          types[0]
			  );
			  if (type == null) return;
			
			  String nom = JOptionPane.showInputDialog(null, "Nom de l'élément :");
			  if (nom == null || nom.isEmpty()) return;
			
			  String prixStr = JOptionPane.showInputDialog(null, "Prix (€) :");
			  if (prixStr == null) return;
			
			  try {
			      double prix = Double.parseDouble(prixStr);
			      manager.ajouterItemMenu(type, nom, prix); // ✅ Appel direct au RestaurantManager
			      JOptionPane.showMessageDialog(null, "✅ Élément ajouté et sauvegardé dans le fichier !");
			  } catch (NumberFormatException e) {
			      JOptionPane.showMessageDialog(null, "⚠️ Prix invalide !");
			  }
			}
			
			//=========================================================
			//✅ Modifier un élément du menu
			//=========================================================
			private void modifierItemMenu(RestaurantManager manager) {
			  String nom = JOptionPane.showInputDialog(null, "Nom de l'élément à modifier :");
			  if (nom == null || nom.isEmpty()) return;
			
			  String prixStr = JOptionPane.showInputDialog(null, "Nouveau prix (€) :");
			  if (prixStr == null) return;
			
			  try {
			      double nouveauPrix = Double.parseDouble(prixStr);
			      manager.modifierItemMenu(nom, nouveauPrix);
			      JOptionPane.showMessageDialog(null, "✅ Élément modifié et sauvegardé !");
			  } catch (NumberFormatException e) {
			      JOptionPane.showMessageDialog(null, "⚠️ Prix invalide !");
			  }
			}
			
			//=========================================================
			//✅ Supprimer un élément du menu
			//=========================================================
			private void supprimerItemMenu(RestaurantManager manager) {
			  String nom = JOptionPane.showInputDialog(null, "Nom de l'élément à supprimer :");
			  if (nom == null || nom.isEmpty()) return;
			
			  manager.supprimerItemMenu(nom);
			  JOptionPane.showMessageDialog(null, "🗑️ Élément supprimé (si trouvé) et menu sauvegardé !");
			}
			
			//=========================================================
			//✅ Afficher le menu complet
			//=========================================================
			private void afficherMenu(RestaurantManager manager) {
			  List<MenuInterface> liste = manager.getMenuItems();
			
			  if (liste.isEmpty()) {
			      JOptionPane.showMessageDialog(null, "📭 Le menu est vide.");
			      return;
			  }
			
			  StringBuilder sb = new StringBuilder("=== 🧾 MENU DU RESTAURANT ===\n\n");
			  for (MenuInterface item : liste) {
			      sb.append("• ")
			        .append(item.getType()).append(" | ")
			        .append(item.getNom()).append(" | ")
			        .append(item.getPrix()).append("€\n");
			  }
			
			  JOptionPane.showMessageDialog(null, sb.toString());
			}
			

		 

	    
	 // =========================================================
	    // 📅 PARTIE 4 : GESTION DES RÉSERVATIONS
	    // =========================================================
	    private void menuGestionReservations() {
	        String[] options = {"Créer Réservation", "Modifier Réservation", "Annuler Réservation", "Afficher Réservations", "Retour"};
	        String choix = (String) JOptionPane.showInputDialog(null, "Gestion des Réservations :", "Réservations",
	                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

	        if (choix == null || choix.equals("Retour")) return;

	        switch (choix) {
	            case "Créer Réservation" -> creerReservation();
	            case "Modifier Réservation" -> modifierReservation();
	            case "Annuler Réservation" -> annulerReservation();
	            case "Afficher Réservations" -> gestionReservations.afficherReservations();
	        }
	    }

	    private void creerReservation() {
	        RestaurantManager manager = RestaurantManager.getInstance();

	        int numeroTable = Integer.parseInt(JOptionPane.showInputDialog("Numéro de table :"));
	        Table table = manager.trouverTable(numeroTable);

	        if (table == null) {
	            JOptionPane.showMessageDialog(null, "Table introuvable !");
	            return;
	        }

	        String nomClient = JOptionPane.showInputDialog("Nom du client :");
	        int nbPersonnes = Integer.parseInt(JOptionPane.showInputDialog("Nombre de personnes :"));
	        int heure = Integer.parseInt(JOptionPane.showInputDialog("Heure (ex: 19) :"));
	        LocalDateTime date = LocalDateTime.of(2025, 10, 20, heure, 0);

	        manager.getGestionReservations().creerReservation(nomClient, nbPersonnes, table, date);
	        JOptionPane.showMessageDialog(null, "✅ Réservation créée pour Table " + numeroTable + " !");
	    }



	    private void modifierReservation() {
	        String nomClient = JOptionPane.showInputDialog("Nom du client à modifier :");
	        int heure = Integer.parseInt(JOptionPane.showInputDialog("Nouvelle heure (ex: 20) :"));
	        LocalDateTime date = LocalDateTime.of(2025, 10, 20, heure, 0);

	        int numero = Integer.parseInt(JOptionPane.showInputDialog("Nouvelle table (numéro) :"));
	        Table table = manager.getGestionTables().trouverTable(numero); // ✅ utiliser le manager

	        manager.getGestionReservations().modifierReservation(nomClient, date, table);
	    }


	    private void annulerReservation() {
	        String nomClient = JOptionPane.showInputDialog("Nom du client à annuler :");
	        gestionReservations.annulerReservation(nomClient);
	    }
		    
 // =====================
 // 🧑‍🍳 MENU SERVEUR
 // =====================
 public void menuServeur() {
     String[] options = {"Gérer les Tables" ,"Créer facture", "Gérer les Commandes","Afficher factures", "Afficher stock", "Retour"};
     String choix = (String) JOptionPane.showInputDialog(null, 
             "Menu Serveur :", "Serveur", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

     if (choix == null || choix.equals("Retour")) return;

     switch (choix) {
         case "Gérer les Tables" -> menuGestionTables();
	   //  case "Gérer les Commandes" -> menuGestionCommandes();
         case "Créer facture" -> creerFacture();
       //  case "Afficher factures" -> afficherFactures();
         case "Afficher stock" -> afficherStock();
     }
 }
 
 

 /*
 private void menuGestionCommandes() {
	    RestaurantManager manager = RestaurantManager.getInstance();

	    Commande commande = new Commande();
	    commande.ajouterObserver(message -> JOptionPane.showMessageDialog(null, "🔔 " + message));

	    boolean continuer = true;
	    while (continuer) {
	        String[] options = {"Ajouter Item (depuis menu)", "Afficher Commande", "État Suivant", "Quitter"};
	        String choix = (String) JOptionPane.showInputDialog(null,
	                "Gestion de la Commande :", "Commande",
	                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

	        if (choix == null || choix.equals("Quitter")) {
	            continuer = false;
	            continue;
	        }

	        switch (choix) {
	            case "Ajouter Item (depuis menu)" -> {
	                List<MenuInterface> liste = manager.getMenuItems();
	                if (liste.isEmpty()) {
	                    JOptionPane.showMessageDialog(null, "📭 Aucun élément dans le menu !");
	                    break;
	                }

	                String[] noms = liste.stream().map(MenuInterface::getNom).toArray(String[]::new);
	                String nomChoisi = (String) JOptionPane.showInputDialog(null,
	                        "Choisissez un plat :", "Ajout au menu",
	                        JOptionPane.QUESTION_MESSAGE, null, noms, noms[0]);

	                for (MenuInterface item : liste) {
	                    if (item.getNom().equalsIgnoreCase(nomChoisi)) {
	                        commande.ajouterItem(item);
	                        JOptionPane.showMessageDialog(null, "✅ " + item.getNom() + " ajouté à la commande !");
	                        break;
	                    }
	                }
	            }
	            case "Afficher Commande" -> {
	                StringBuilder sb = new StringBuilder("🧾 COMMANDE\n\n");
	                sb.append("État : ").append(commande.getEtat()).append("\n");
	                for (MenuInterface item : commande.getItems()) {
	                    sb.append(" - ").append(item.getNom())
	                      .append(" (").append(item.getPrix()).append("€)\n");
	                }
	                JOptionPane.showMessageDialog(null, sb.toString());
	            }
	            case "État Suivant" -> {
	                commande.etatSuivant();
	                JOptionPane.showMessageDialog(null, "🔁 État mis à jour : " + commande.getEtat());

	                // sauvegarde automatique si livrée
	                if (commande.getEtat().equalsIgnoreCase("LIVREE")) {
	                    manager.notifierCommandeLivree(commande);
	                    JOptionPane.showMessageDialog(null, "💾 Commande sauvegardée !");
	                }
	            }
	        }
	    }
	}
 */
//=====================================================
//🪑 GESTION DES TABLES (avec boîtes de dialogue)
//=====================================================
//======= Dans Restaurant_Facade.java =======

//Menu de gestion des tables
private void menuGestionTables() {
  RestaurantManager manager = RestaurantManager.getInstance();

  String[] options = {"Ajouter", "Supprimer", "Afficher", "Retour"};
  String choix = (String) JOptionPane.showInputDialog(
          null,
          "Que souhaitez-vous faire ?",
          "Gestion des Tables",
          JOptionPane.QUESTION_MESSAGE,
          null,
          options,
          options[0]
  );

  if (choix == null || choix.equals("Retour")) return;

  switch (choix) {
      case "Ajouter" -> ajouterTable(manager);
      case "Supprimer" -> supprimerTable(manager);
      case "Afficher" -> afficherToutesLesTables(); 
  }
}

//Ajouter une table
private void ajouterTable(RestaurantManager manager) {
  try {
      String numeroStr = JOptionPane.showInputDialog("Numéro de la table :");
      if (numeroStr == null) return;
      int numero = Integer.parseInt(numeroStr);

      String placesStr = JOptionPane.showInputDialog("Nombre de places :");
      if (placesStr == null) return;
      int places = Integer.parseInt(placesStr);

      manager.ajouterTable(numero, places);
      JOptionPane.showMessageDialog(null, " Table " + numero + " ajoutée avec " + places + " places !");
  } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, " Valeur invalide !");
  }
}


//Supprimer une table
private void supprimerTable(RestaurantManager manager) {
  try {
      String numeroStr = JOptionPane.showInputDialog("Numéro de la table à supprimer :");
      if (numeroStr == null) return;
      int numero = Integer.parseInt(numeroStr);

      Table t = manager.trouverTable(numero);
      if (t == null) {
          JOptionPane.showMessageDialog(null, "❌ Aucune table trouvée avec ce numéro !");
          return;
      }

      manager.supprimerTable(numero);
      JOptionPane.showMessageDialog(null, "🗑️ Table " + numero + " supprimée !");
  } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "⚠️ Numéro invalide !");
  }


//Afficher toutes les tables
}private void afficherTables(RestaurantManager manager) {
	
    List<Table> tables = manager.getGestionTables().getTables();

    if (tables.isEmpty()) {
        JOptionPane.showMessageDialog(null, "📭 Aucune table enregistrée.");
        return;
    }

    StringBuilder sb = new StringBuilder("=== 🪑 LISTE DES TABLES ===\n\n");
    for (Table t : tables) {
        sb.append("• Table ").append(t.getNumero())
          .append(" — ").append(t.getNbPlaces()).append(" places\n");
    }

    JOptionPane.showMessageDialog(null, sb.toString());
}

private void afficherToutesLesTables() {
    RestaurantManager manager = RestaurantManager.getInstance();
    manager.chargerTables(); // s’assure que le fichier est lu
    afficherTables(manager);
}


		/*
		
		//=====================================================
		//👀 AFFICHER toutes les tables
		//=====================================================
		private void afficherTables(RestaurantManager manager) {
		  List<Table> tables = manager.getGestionTables().getTables();
		
		  if (tables.isEmpty()) {
		      JOptionPane.showMessageDialog(null, " Aucune table enregistrée.");
		      return;
		  }
		
		  StringBuilder sb = new StringBuilder("=== 🪑 LISTE DES TABLES ===\n\n");
		  for (Table t : tables) {
		      sb.append("• Table ").append(t.getNumero())
		        .append(" — ").append(t.getNbPlaces()).append(" places\n");
		  }
		
		  JOptionPane.showMessageDialog(null, sb.toString());
		}
		
		*/

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
        StringBuilder sb = new StringBuilder("Liste du personnel :\n\n");
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
