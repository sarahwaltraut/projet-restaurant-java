package state;

public class ReserveeTable implements TableState {

	 @Override
	    public void occuper(Table table) {
	        table.setEtat(new OccupeeTable());
	        System.out.println("Table " + table.getNumero() + " réservée maintenant occupée.");
	    }

	    @Override
	    public void liberer(Table table) {
	        table.setEtat(new LibreTable());
	        System.out.println("Réservation annulée. Table " + table.getNumero() + " est libre.");
	    }

	    @Override
	    public void reserver(Table table) {
	        System.out.println("Table déjà réservée.");
	    }

	    @Override
	    public void nettoyer(Table table) {
	        System.out.println("Impossible de nettoyer une table réservée.");
	    }

	    @Override
	    public String getEtat() {
	        return "Réservée";
	    }
	}

