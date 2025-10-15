package factory;

import restaurant.Cuisinier;
import restaurant.Employe;
import restaurant.Gerant;
import restaurant.Serveur;

public class Employe_Factory {
	public static Employe creerEmploye(String id, String nom, String role) {
        switch (role.toLowerCase()) {
            case "gerant":
                return new Gerant(id, nom);
            case "serveur":
                return new Serveur(id, nom);
            case "cuisinier":
                return new Cuisinier(id, nom);
            default:
                throw new IllegalArgumentException("Rôle inconnu : " + role);
        }
    }
}

