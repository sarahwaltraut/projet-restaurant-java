package state;

public class LibreTable implements TableState {
	
	    @Override
	    public void occuper(Table table) {
	        table.setEtat(new OccupeeTable());
	        System.out.println("Table " + table.getNumero() + " est maintenant occupée.");
	    }
	    
	    @Override
	    public void liberer(Table table) {
	        System.out.println("La table est déjà libre.");
	    }

	    @Override
	    public void reserver(Table table) {
	        table.setEtat(new ReserveeTable());
	        System.out.println("Table " + table.getNumero() + " est maintenant réservée.");
	    }

	    @Override
	    public void nettoyer(Table table) {
	        System.out.println("La table est déjà propre et libre.");
	    }

	    @Override
	    public String getEtat() {
	        return "Libre";
	    }
}


