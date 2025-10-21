package observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import factory.MenuInterface;
import singleton.RestaurantManager;
import state.CommandeLivree;
import state.CommandeState;
import state.NouvelleState;
import state.Table;

public class Commande implements Observable {

    private String id;
    private Table table;
    private List<MenuInterface> items;
    private CommandeState etat;
    private List<Observer> observers;
    private LocalDateTime date; // 🕒

    // === Constructeurs ===
    public Commande() {
        this("CMD-" + System.currentTimeMillis(), null, new ArrayList<>());
    }

    public Commande(String id, Table table, List<MenuInterface> items) {
        this.id = id;
        this.table = table;
        this.items = items != null ? items : new ArrayList<>();
        this.etat = new NouvelleState();
        this.observers = new ArrayList<>();
        this.date = LocalDateTime.now();
    }

    // === Getters / Setters ===
    public String getId() { return id; }
    public Table getTable() { return table; }
    public void setTable(Table table) { this.table = table; }
    public List<MenuInterface> getItems() { return items; }
    public LocalDateTime getDate() { return date; }

    public String getEtat() { return etat.getEtat(); }

    public void setEtat(CommandeState etat) {
        this.etat = etat;
        if (etat instanceof CommandeLivree) {
            RestaurantManager.getInstance().notifierCommandeLivree(this);
        }
    }

    // === Gestion des items ===
    public void ajouterItem(MenuInterface item) { items.add(item); }
    public void supprimerItem(MenuInterface item) { items.remove(item); }

    // === Gestion de l’état ===
    public void etatSuivant() {
        etat.suivant(this);
        if (etat instanceof CommandeLivree) {
            RestaurantManager.getInstance().notifierCommandeLivree(this);
        }
    }

    public void etatPrecedent() { etat.precedent(this); }

    // === Observer pattern ===
    @Override
    public void ajouterObserver(Observer o) { observers.add(o); }
    @Override
    public void supprimerObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifierObservers(String message) {
        for (Observer o : observers) {
            o.notifier(message + " (État actuel : " + etat.getEtat() + ")");
        }
    }

    // === Utilitaires ===
    public String getItemsAsString() {
        return items.stream()
                .map(MenuInterface::getNom)
                .collect(Collectors.joining(","));
    }

    @Override
    public String toString() {
        String tableNum = (table != null) ? String.valueOf(table.getNumero()) : "AUCUNE";
        return id + ";" + tableNum + ";" + etat.getEtat() + ";" + date + ";" + getItemsAsString();
    }

    public void afficherCommande() {
        StringBuilder sb = new StringBuilder("🧾 Commande " + id + " (" + etat.getEtat() + ")\n");
        sb.append("Table : ").append(table != null ? table.getNumero() : "Non assignée").append("\n");
        sb.append("Date : ").append(date).append("\n");
        for (MenuInterface item : items) {
            sb.append(" - ").append(item.getNom()).append(" (").append(item.getPrix()).append("€)\n");
        }
        System.out.println(sb);
    }

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
    
    
}
