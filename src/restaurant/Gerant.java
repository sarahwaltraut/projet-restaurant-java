package restaurant;

public class Gerant extends Employe {
    public Gerant(String id, String nom) {
        super(id, nom, "Gérant");
    }

    public void genererRapport() {
        System.out.println("📊 Le gérant génère un rapport de gestion...");
    }
    
}

