package observer;

import java.util.ArrayList;
import java.util.List;

import factory.MenuInterface;
import state.CommandeState;
import state.NouvelleState;


public class Commande implements Observable {
    private List<MenuInterface> items;
    private CommandeState etat;
    private List<Observer> Observers;

    public Commande() {
        this.items = new ArrayList<>();
        this.etat = new NouvelleState();
        this.Observers = new ArrayList<>();
    }

    public void ajouterItem(MenuInterface item) {
        items.add(item);
    }

    public void supprimerItem(MenuInterface item) {
        items.remove(item);
    }

    public List<MenuInterface> getItems() {
        return items;
    }

    public String getEtat() {
        return etat.getEtat();
    }

    public void setEtat(CommandeState etat) {
        this.etat = etat;
    }

    public void etatSuivant() {
        etat.suivant(this);
    }

    public void etatPrecedent() {
        etat.precedent(this);
    }

    @Override
    public void ajouterObserver(Observer o) {
        Observers.add(o);
    }

    @Override
    public void supprimerObserver(Observer o) {
        Observers.remove(o);
    }

    @Override
    public void notifierObservers(String message) {
        for (Observer o : Observers) {
            o.notifier(message + " (État actuel : " + etat.getEtat() + ")");
        }
    }

    public void afficherCommande() {
        System.out.println("Commande (" + etat.getEtat() + "):");
        for (MenuInterface item : items) {
            System.out.println(" - " + item);
        }
    }
}
