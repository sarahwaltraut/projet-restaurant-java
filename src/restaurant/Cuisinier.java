package restaurant;
import observer.Observer;

public class Cuisinier extends Employe implements Observer {
    public Cuisinier(String id, String nom) {
        super(id, nom, "Cuisinier");
    }

    @Override
    public void notifier(String message) {
        System.out.println("🍳 Notification Cuisinier " + nom + ": " + message);
    }
}
