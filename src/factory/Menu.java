package factory;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuInterface> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void ajouter(MenuInterface item) {
        items.add(item);
        System.out.println("Ajouté : " + item);
    }

    
    
    /**
     * Supprime le premier élément correspondant au nom (exact, sensible à la casse).
     * Retourne true si supprimé, false sinon.
     */
    public boolean supprimerParNom(String nom) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getNom().equals(nom)) {
                MenuInterface removed = items.remove(i);
                System.out.println("Supprimé : " + removed);
                return true;
            }
        }
        System.out.println("Aucun élément trouvé avec le nom : " + nom);
        return false;
    }

    /**
     * Modifie le premier élément trouvé par nom.
     * Si newNom est null, ne change pas le nom; si newPrix < 0, ne change pas le prix.
     */
    public boolean modifierParNom(String nom, String newNom, double newPrix) {
        for (MenuInterface item : items) {
            if (item.getNom().equals(nom)) {
                if (newNom != null && !newNom.trim().isEmpty()) {
                    item.setNom(newNom);
                }
                if (newPrix >= 0) {
                    item.setPrix(newPrix);
                }
                System.out.println("Modifié : " + item);
                return true;
            }
        }
        System.out.println("Aucun élément trouvé avec le nom : " + nom);
        return false;
    }

    public void afficherMenu() {
        if (items.isEmpty()) {
            System.out.println("Le menu est vide.");
            return;
        }
        System.out.println("----- Menu -----");
        for (MenuInterface item : items) {
            System.out.println(item.getType() + " | " + item.getNom() + " | " + item.getPrix() + "€");
        }
        System.out.println("----------------");
    }


    public void afficherParType(String type) {
        boolean any = false;
        for (MenuInterface item : items) {
            if (item.getType().equalsIgnoreCase(type)) {
                if (!any) {
                    System.out.println("----- " + type + "s -----");
                    any = true;
                }
                System.out.println(item);
            }
        }
        if (!any) {
            System.out.println("Aucun élément de type : " + type);
        } else {
            System.out.println("----------------");
        }
    }

}
