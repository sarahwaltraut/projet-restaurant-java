package factory;

import restaurant.Ingredient;

public class IngredientFactory {
	public static Ingredient creerIngredient(String nom, int quantite) {
        return new Ingredient(nom, quantite);
    }
}
