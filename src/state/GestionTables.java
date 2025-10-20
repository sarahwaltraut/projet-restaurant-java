package state;

import java.util.ArrayList;
import java.util.List;

public class GestionTables {
    private List<Table> tables;

    public GestionTables() {
        this.tables = new ArrayList<>();
    }

    public void ajouterTable(int numero, int nbPlaces) {
        for (Table t : tables) {
            if (t.getNumero() == numero) {
                System.out.println("Une table avec ce numéro existe déjà !");
                return;
            }
        }
        tables.add(new Table(numero, nbPlaces));
        System.out.println("Table " + numero + " ajoutée (" + nbPlaces + " places).");
    }

    public void supprimerTable(int numero) {
        tables.removeIf(t -> t.getNumero() == numero);
        System.out.println("Table " + numero + " supprimée.");
    }

    public Table trouverTable(int numero) {
        for (Table t : tables) {
            if (t.getNumero() == numero) return t;
        }
        System.out.println("Table " + numero + " introuvable.");
        return null;
    }

    public void afficherTables() {
        if (tables.isEmpty()) {
            System.out.println("Aucune table enregistrée.");
            return;
        }
        System.out.println("---- Liste des tables ----");
        for (Table t : tables) {
            System.out.println(t);
        }
    }

    public void afficherParEtat(String etatRecherche) {
        boolean trouve = false;
        for (Table t : tables) {
            if (t.getEtat().equalsIgnoreCase(etatRecherche)) {
                if (!trouve) {
                    System.out.println("Tables " + etatRecherche + " :");
                    trouve = true;
                }
                System.out.println(t);
            }
        }
        if (!trouve) {
            System.out.println("Aucune table avec l’état : " + etatRecherche);
        }
    }
}
