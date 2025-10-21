package restaurant;
import facade.Restaurant_Facade;

import java.time.LocalDateTime;

import javax.swing.*;

import Reservation.GestionReservations;
import facade.*;
import factory.*;
import observer.*;
import state.*;

public class Zemainnn {
    public static void main(String[] args) {
        Restaurant_Facade restaurant = new Restaurant_Facade();

        boolean quitter = false;

        while (!quitter) {
            String[] options = {"Gérant", "Serveur", "Cuisinier", "Quitter"};
            int choix = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenue dans le système de gestion du restaurant ",
                    "Menu Principal",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choix) {
                case 0 -> restaurant.menuGerant();
                case 1 -> restaurant.menuServeur();
                case 2 -> restaurant.menuCuisinier();
                default -> quitter = true;
            }
        }

        restaurant.manager.sauvegarderTout(); // sauvegarde globale
        JOptionPane.showMessageDialog(null, "Données sauvegardées, au revoir ");
        
    	
    	
    	
    	
    	
    }
}
