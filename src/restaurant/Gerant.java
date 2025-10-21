package restaurant;

public class Gerant extends Employe {
    public Gerant(String id, String nom) {
        super(id, nom, "Gerant");
    }

    public void genererRapport() {
        System.out.println("📊 Le gérant génère un rapport de gestion...");
    }
    
}

