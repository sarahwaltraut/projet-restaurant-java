package state;

import observer.Commande;

class CommandePayee implements CommandeState {
    @Override
    public void suivant(Commande commande) {
        System.out.println("La commande est déjà terminée et payée.");
    }

    @Override
    public void precedent(Commande commande) {
        commande.setEtat(new CommandeLivree());
        commande.notifierObservers("Commande repassée à l’état livrée.");
    }

    @Override
    public String getEtat() {
        return "Payée";
    }
}