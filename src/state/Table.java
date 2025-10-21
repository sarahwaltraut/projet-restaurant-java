package state;
import observer.*;

public class Table {
    private int numero;
    private int nbPlaces;
    private TableState etat;
    private Commande commande; // lien optionnel

    public Table(int numero, int nbPlaces) {
        this.numero = numero;
        this.nbPlaces = nbPlaces;
        this.etat = new LibreTable(); // état initial
    }

    public int getNumero() {
        return numero;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public String getEtat() {
        return etat.getEtat();
    }

    public void setEtat(TableState etat) {
        this.etat = etat;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    
    
    // Actions déléguées à l’état
    public void occuper() { etat.occuper(this); }
    public void liberer() { etat.liberer(this); }
    public void reserver() { etat.reserver(this); }
    public void nettoyer() { etat.nettoyer(this); }

    @Override
    public String toString() {
        return "Table " + numero + " (" + nbPlaces + " places) - État : " + etat.getEtat();
    }
}
