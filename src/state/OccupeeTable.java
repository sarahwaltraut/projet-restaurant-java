package state;

public class OccupeeTable implements TableState{
		
	@Override
    public void occuper(Table table) {
        System.out.println("Table déjà occupée.");
    }
    @Override
    public void liberer(Table table) {
        table.setEtat(new NettoyageTable());
        System.out.println("Table " + table.getNumero() + " libérée, à nettoyer.");
    }

	    @Override
	    public void reserver(Table table) {
	        System.out.println("Impossible de réserver une table déjà occupée.");
	    }

	    @Override
	    public void nettoyer(Table table) {
	        System.out.println("Impossible de nettoyer une table occupée.");
	    }

	    @Override
	    public String getEtat() {
	        return "Occupée";
	    }
	
}
