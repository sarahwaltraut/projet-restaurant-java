package factory;


class Plat extends AbstractMenu {
	
    public Plat(String nom, double prix) {
    	super(nom, prix); 
    	}
    @Override 
    public String getType() { 
    	return "Plat"; 
    	}
}