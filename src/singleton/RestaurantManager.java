package singleton;

import restaurant.*;
import state.CommandeLivree;
import state.GestionTables;
import state.Table;
import observer.Commande;
import observer.Observer;
import observer.System_Notification;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import javax.swing.JOptionPane;

import Reservation.GestionReservations;
import factory.Employe_Factory;
import factory.Menu;
import factory.MenuFactory;
import factory.MenuInterface;
import Reservation.*;

public class RestaurantManager {

	private static RestaurantManager instance;

	private List<Employe> personnels;
	private Menu menu;
	private GestionTables gestionTables;
	private GestionReservations gestionReservations;
	private List<Commande> commandes;

	private List<Ingredient> stock;
	private System_Notification sujetNotifications;

	private List<MenuInterface> menuItems; // 🆕 liste des éléments du menu

	// fichiers de stockage
	private final String FICHIER_PERSONNEL = "data/personnel.txt";
	private final String FICHIER_TABLES = "data/tables.txt";
	private final String FICHIER_STOCK = "data/stock.txt";
	private final String FICHIER_RESERVATIONS = "data/reservations.txt";
	private final String FICHIER_COMMANDES = "data/commandes.txt";
	private final String FICHIER_MENU = "data/menu.txt"; // 🆕 fichier pour le menu

	private RestaurantManager() {
		personnels = new ArrayList<>();
		stock = new ArrayList<>();
		commandes = new ArrayList<>();
		menu = new Menu();
		gestionTables = new GestionTables();
		gestionReservations = new GestionReservations();
		sujetNotifications = new System_Notification();
		menuItems = new ArrayList<>();

		chargerPersonnel();
		chargerStock();
		chargerMenu();
		chargerTables();
		System.out.println("Tables chargées : " + gestionTables.getTables().size());

		chargerCommandes();
	}

	// Singleton
	public static RestaurantManager getInstance() {
		if (instance == null)
			instance = new RestaurantManager();
		return instance;
	}

	// GETTERS

	public List<Employe> getPersonnels() {
		return personnels;
	}

	public List<Ingredient> getStock() {
		return stock;
	}

	public System_Notification getSujetNotifications() {
		return sujetNotifications;
	}

	public Menu getMenu() {
		return menu;
	}

	public GestionTables getGestionTables() {
		return gestionTables;
	}

	public GestionReservations getGestionReservations() {
		return gestionReservations;
	}

	public List<Commande> getCommandes() {
		return commandes;
	}

	// === Gestion du personnel ===
	private void chargerPersonnel() {
		personnels.clear();
		try (BufferedReader br = new BufferedReader(new FileReader(FICHIER_PERSONNEL))) {
			String ligne;
			while ((ligne = br.readLine()) != null) {
				String[] parts = ligne.split(";");
				if (parts.length < 3)
					continue;
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
		if (e instanceof Observer)
			sujetNotifications.ajouterObservateur((Observer) e);
		sauvegarderPersonnel();
	}

	public void supprimerEmploye(Employe e) {
		if (personnels.remove(e)) {
			if (e instanceof Observer)
				sujetNotifications.supprimerObservateur((Observer) e);
			sauvegarderPersonnel();
		}
	}

	// =====================
	// 🧾 GESTION DU MENU
	// =====================

	// Charger le menu depuis le fichier menu.txt
	private void chargerMenu() {
		menuItems.clear();
		File fichier = new File(FICHIER_MENU);
		if (!fichier.exists())
			return;

		try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = br.readLine()) != null) {
				String[] parts = ligne.split(";");
				if (parts.length < 3)
					continue;

				String type = parts[0];
				String nom = parts[1];
				double prix;
				try {
					prix = Double.parseDouble(parts[2]);
				} catch (NumberFormatException e) {
					System.out.println(" Prix invalide pour l'élément : " + ligne);
					continue;
				}

				// Utiliser la factory pour créer l'élément
				MenuInterface item = MenuFactory.create(type, nom, prix);
				if (item != null)
					menuItems.add(item);
			}
		} catch (IOException e) {
			System.out.println("Erreur de chargement du menu : " + e.getMessage());
		}
	}

	// Sauvegarder le menu dans menu.txt
	public void sauvegarderMenu() {
		try {
			File dossier = new File("data");
			if (!dossier.exists())
				dossier.mkdir(); // créer le dossier si absent

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER_MENU))) {
				for (MenuInterface item : menuItems) {
					bw.write(item.getType() + ";" + item.getNom() + ";" + item.getPrix());
					bw.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println("❌ Erreur de sauvegarde du menu : " + e.getMessage());
		}
	}

//=== Ajouter un élément au menu ===
	public void ajouterItemMenu(String type, String nom, double prix) {
		MenuInterface item = MenuFactory.create(type, nom, prix);
		if (item != null) {
			menuItems.add(item); // ajouter à la liste
			sauvegarderMenu(); // sauvegarder dans le fichier
			System.out.println("✅ Élément ajouté : " + nom);
		}
	}

	// Supprimer un élément du menu par nom
	public void supprimerItemMenu(String nom) {
		if (menuItems.removeIf(item -> item.getNom().equalsIgnoreCase(nom))) {
			sauvegarderMenu();
		}
	}

	// Modifier le prix d’un élément du menu
	public void modifierItemMenu(String nom, double nouveauPrix) {
		for (MenuInterface item : menuItems) {
			if (item.getNom().equalsIgnoreCase(nom)) {
				item.setPrix(nouveauPrix);
				sauvegarderMenu();
				return;
			}
		}
		System.out.println("⚠️ Élément non trouvé dans le menu : " + nom);
	}

	// Afficher tout le menu
	public void afficherMenu() {
		System.out.println("\n=== 🧾 MENU DU RESTAURANT ===");
		for (MenuInterface item : menuItems) {
			System.out.println("- " + item.getType() + " | " + item.getNom() + " | " + item.getPrix() + "€");
		}
	}

	// Getter pour accéder à la liste du menu si besoin
	public List<MenuInterface> getMenuItems() {
		return new ArrayList<>(menuItems);
	}

//=====================
//🧾 COMMANDES
//=====================
	private void chargerCommandes() {
		commandes.clear();
		File fichier = new File(FICHIER_COMMANDES);
		if (!fichier.exists())
			return;

		try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
			String ligne;
			while ((ligne = br.readLine()) != null) {
				String[] parts = ligne.split(";");
				if (parts.length < 5)
					continue;

				String id = parts[0];
				String etat = parts[2];
				LocalDateTime date = LocalDateTime.parse(parts[3]);
				String[] plats = parts[4].split(",");

				// Retrouver les plats depuis le menu
				List<MenuInterface> items = new ArrayList<>();
				for (String nomPlat : plats) {
					for (MenuInterface m : menuItems) {
						if (m.getNom().equalsIgnoreCase(nomPlat.trim())) {
							items.add(m);
							break;
						}
					}
				}

				Commande c = new Commande(id, null, items);
				c.setDate(date);
				if (etat.equalsIgnoreCase("LIVREE")) {
					c.setEtat(new CommandeLivree());
				}

				commandes.add(c);
			}
			System.out.println(" Commandes chargées depuis " + FICHIER_COMMANDES);
		} catch (IOException e) {
			System.out.println("❌ Erreur de chargement des commandes : " + e.getMessage());
		}
	}

	public void sauvegarderCommandeLivree(Commande commande) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER_COMMANDES, true))) {
			bw.write(commande.toString());
			bw.newLine();
			System.out.println("Commande enregistrée : " + commande.getId() + "; " + commande.getDate() + "; "
					+ commande.getTable() + "; " + commande.getItemsAsString());
		} catch (IOException e) {
			System.out.println("❌ Erreur lors de la sauvegarde de la commande : " + e.getMessage());
		}
	}

	public void ajouterCommande(Commande commande) {
		commandes.add(commande);
		sauvegarderToutesLesCommandes();
	}

	public void sauvegarderToutesLesCommandes() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER_COMMANDES))) {
			for (Commande c : commandes) {
				bw.write(c.toString());
				bw.newLine();
			}
			System.out.println(" Toutes les commandes sauvegardées !");
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
		}
	}

//Quand une commande devient "Livrée"
	public void notifierCommandeLivree(Commande commande) {
		sauvegarderCommandeLivree(commande);
	}

	// =====================
	// 🪑 TABLES
	// =====================

	public void chargerTables() {
		File fichier = new File(FICHIER_TABLES);
		if (!fichier.exists()) {
			System.out.println("Aucun fichier tables.txt trouvé.");
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(FICHIER_TABLES))) {
			String ligne;
			while ((ligne = br.readLine()) != null) {
				//System.out.println("chargerTables = "+gestionTables.getTables().size());
				String[] parts = ligne.split(";");
				System.out.println("parts = "+parts[0]);
				if (parts.length < 2)
					continue;
				int numero = Integer.parseInt(parts[0]);
				int places = Integer.parseInt(parts[1]);
				Table t = new Table(numero, places);
				gestionTables.ajouterTable(numero, places);
			}
			
		} catch (IOException e) {
			System.out.println("Erreur de chargement des tables : " + e.getMessage());
		}
	}

	public void sauvegarderTables() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER_TABLES, true))) {
			gestionTables.exporterVers(bw);
			bw.close();
		} catch (IOException e) {
			System.out.println("Erreur de sauvegarde des tables : " + e.getMessage());
		}
	}

	// Ajouter une table et sauvegarder
	public void ajouterTable(int numero, int places) {
		gestionTables.ajouterTable(numero, places);
	}
	
	// Supprimer une table et sauvegarder
	public void supprimerTable(int numero) {
		gestionTables.supprimerTable(numero);
		sauvegarderTables();
	}

	// Trouver une table
	public Table trouverTable(int numero) {
		return gestionTables.trouverTable(numero);
	}

	// =====================
	// 📅 RÉSERVATIONS
	// =====================

	public void chargerReservations() {
	    File fichier = new File(FICHIER_RESERVATIONS);
	    if (!fichier.exists()) {
	        System.out.println("Aucun fichier reservations.txt trouvé.");
	        return;
	    }

	    try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
	        String ligne;
	        while ((ligne = br.readLine()) != null) {
	            String[] parts = ligne.split(";");
	            if (parts.length < 5) continue;

	            String nomClient = parts[0];
	            int nbPersonnes = Integer.parseInt(parts[1]);
	            int numeroTable = Integer.parseInt(parts[2]);
	            LocalDateTime dateHeure = LocalDateTime.parse(parts[3]);
	            String etat = parts[4];

	            Table table = gestionTables.trouverTable(numeroTable);
	            if (table != null) {
	                Reservation r = new Reservation(nomClient, nbPersonnes, table, dateHeure);
	                if ("annulee".equalsIgnoreCase(etat)) r.annuler();
	                gestionReservations.getReservations().add(r);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("❌ Erreur de chargement des réservations : " + e.getMessage());
	    }
	}

	public void sauvegarderReservations() {
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIER_RESERVATIONS))) {
	        gestionReservations.exporterVers(bw);
	    } catch (IOException e) {
	        System.out.println("❌ Erreur de sauvegarde des réservations : " + e.getMessage());
	    }
	}

	// === Gestion du stock ===
	private void chargerStock() {
		stock.clear();
		try (BufferedReader br = new BufferedReader(new FileReader(FICHIER_STOCK))) {
			String ligne;
			while ((ligne = br.readLine()) != null) {
				String[] parts = ligne.split(";");
				if (parts.length < 2)
					continue;
				stock.add(new Ingredient(parts[0], Integer.parseInt(parts[1])));
			}
		} catch (IOException e) {
			System.out.println("Erreur de chargement du stock : " + e.getMessage());
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
			System.out.println(" Erreur de sauvegarde du stock : " + e.getMessage());
		}
	}


	// === Sauvegarde globale ===
	public void sauvegarderTout() {
		sauvegarderPersonnel();
		sauvegarderTables();
		sauvegarderStock();
		sauvegarderReservations();
		sauvegarderMenu(); // ✅ ajout manquant
	}

}
