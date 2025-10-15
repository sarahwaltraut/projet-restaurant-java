package factory;

class MenuFactory {
    /**
     * type peut être : "entree", "plat", "dessert", "boisson" (insensible à la casse)
     */
    public static MenuInterface create(String type, String nom, double prix) {
    	
        String t = type == null ? "" : type.trim().toLowerCase();
        switch (t) {
            case "entree":  return new Entree(nom, prix);
            case "plat":    return new Plat(nom, prix);
            case "dessert": return new Dessert(nom, prix);
            case "boisson": return new Boisson(nom, prix);
            default:
                throw new IllegalArgumentException("Type inconnu : " + type);
        }
    }
}