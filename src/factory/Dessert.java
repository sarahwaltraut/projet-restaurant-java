package factory;

class Dessert extends AbstractMenu {
	
    public Dessert(String nom, double prix) { 
    	super(nom, prix); 
    	}
    @Override
    public String getType() { 
    	return "Dessert";
    	}
}
