package factory;


public class Plat extends Menu{

    public Plat(String nom, double prix) {
        super(nom, prix);
    }

    @Override
    public void afficher() {
        System.out.println("Plat principal : " + nom + " - " + prix + " €");
    }
}