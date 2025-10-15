package restaurant;

import observer.Observer;

public class Serveur extends Employe implements Observer {
    private String idTableAffectee;

    public Serveur(String id, String nom) {
        super(id, nom, "Serveur");
        this.idTableAffectee = "";
    }

    public String getIdTableAffectee() { return idTableAffectee; }
    public void setIdTableAffectee(String idTableAffectee) { this.idTableAffectee = idTableAffectee; }

    @Override
    public void notifier(String message) {
        System.out.println("🔔 Notification Serveur " + nom + ": " + message);
    }
}
