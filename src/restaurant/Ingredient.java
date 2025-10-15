package restaurant;

public class Ingredient {
    private String nom;
    private int quantite;

    public Ingredient(String nom, int quantite) {
        this.nom = nom;
        this.quantite = quantite;
    }

    public String getNom() { return nom; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    @Override
    public String toString() {
        return nom + " : " + quantite;
    }
}