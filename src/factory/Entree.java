package factory;

public class Entree extends AbstractMenu {

	    public Entree(String nom, double prix) { 
	    	super(nom, prix);
	    	}
	    @Override
	    public String getType() { 
	    	return "Entree";
	    	}
	
}
