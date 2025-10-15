package factory;

public interface MenuInterface {
	
	 String getNom();
	    void setNom(String nom);
	    double getPrix();
	    void setPrix(double prix);
	    String getType(); // "Entree", "Plat", "Dessert", "Boisson"
	    String toString();
}
