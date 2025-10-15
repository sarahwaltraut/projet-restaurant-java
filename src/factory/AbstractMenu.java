package factory;

public abstract class AbstractMenu implements MenuInterface{
		
	 protected String nom;
	    protected double prix;

	    public AbstractMenu(String nom, double prix) {
	        this.nom = nom;
	        this.prix = prix;
	    }

	    @Override
	    public String getNom() {
	        return nom;
	    }

	    @Override
	    public void setNom(String nom) {
	        this.nom = nom;
	    }

	    @Override
	    public double getPrix() {
	        return prix;
	    }

	    @Override
	    public void setPrix(double prix) {
	        this.prix = prix;
	    }

	    @Override
	    public String toString() {
	        return String.format("%s - %s : %.2f€", getType(), nom, prix);
	    }
	}

