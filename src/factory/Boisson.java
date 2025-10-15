package factory;

class Boisson extends AbstractMenu {
	
    public Boisson(String nom, double prix) { 
    	super(nom, prix); 
    	}
    @Override
    public String getType() { 
    	return "Boisson"; 
    	}
}