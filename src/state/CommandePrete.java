package state;

import observer.Commande;

class CommandePrete implements CommandeState {
    @Override
    public void suivant(Commande commande) {
        commande.setEtat(new CommandeLivree());
        commande.notifierObservers("Commande livrée au client.");
    }

    @Override
    public void precedent(Commande commande) {
        commande.setEtat(new CommandeEncours());
        commande.notifierObservers("Commande renvoyée en préparation.");
    }

    @Override
    public String getEtat() {
        return "Prête";
    }
}
