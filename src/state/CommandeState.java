package state;
import observer.Commande;

public interface CommandeState {
    void suivant(Commande commande);
    void precedent(Commande commande);
    String getEtat();
}
