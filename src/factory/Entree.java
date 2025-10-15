package factory;

public class Entree extends Menu {

    public Entree(String nom, double prix) {
        super(nom, prix);
    }

    @Override
    public void afficher() {
        System.out.println("Entrée : " + nom + " - " + prix + " €");
    }
}
