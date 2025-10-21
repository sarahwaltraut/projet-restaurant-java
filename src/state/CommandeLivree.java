package state;

import observer.Commande;

public class CommandeLivree implements CommandeState {
    @Override
    public void suivant(Commande commande) {
        commande.setEtat(new CommandePayee());
        commande.notifierObservers("Commande payée. Merci !");
    }

    @Override
    public void precedent(Commande commande) {
        commande.setEtat(new CommandePrete());
        commande.notifierObservers("Commande de nouveau marquée comme prête.");
    }

    @Override
    public String getEtat() {
        return "Livrée";
    }
}