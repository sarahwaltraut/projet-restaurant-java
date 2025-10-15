package state;
import observer.Commande;

public class NouvelleState implements CommandeState {
    @Override
    
    public void suivant(Commande commande) {
        commande.setEtat(new CommandeEncours());
        commande.notifierObservers("Commande passée en cours de préparation.");
    }

    @Override
    public void precedent(Commande commande) {
        System.out.println("La commande est déjà au premier état (nouvelle).");
    }

    @Override
    public String getEtat() {
        return "Nouvelle";
    }
}