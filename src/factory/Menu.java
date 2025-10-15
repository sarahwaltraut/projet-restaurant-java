package factory;


public abstract class Menu {
    protected String nom;
    protected double prix;

    public Menu(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() { return nom; }
    public double getPrix() { return prix; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPrix(double prix) { this.prix = prix; }

    public abstract void afficher();
}
