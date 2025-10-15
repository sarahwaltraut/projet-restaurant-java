package strategy;

public class Paiement_par_carte implements Paiement_Strategy{
	@Override
    public void payer(double montant) {
        System.out.println("💳 Paiement par carte de " + montant + "€ effectué.");
    }
}
