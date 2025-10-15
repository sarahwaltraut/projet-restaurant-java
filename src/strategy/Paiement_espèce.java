package strategy;

public class Paiement_espèce implements Paiement_Strategy{
	@Override
    public void payer(double montant) {
        System.out.println("💵 Paiement en espèces de " + montant + "€ reçu.");
    }
}
