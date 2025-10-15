package state;
import observer.Commande;
class CommandeEncours implements CommandeState {
    @Override
    public void suivant(Commande commande) {
        commande.setEtat(new CommandePrete());
        commande.notifierObservers("Commande prête !");
    }

    @Override
    public void precedent(Commande commande) {
        commande.setEtat(new NouvelleState());
        commande.notifierObservers("Commande remise à l’état nouvelle.");
    }

    @Override
    public String getEtat() {
        return "En cours";
    }
}