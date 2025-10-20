package state;

public class NettoyageTable implements TableState{

	@Override
    public void occuper(Table table) {
        System.out.println("Impossible d'occuper une table en nettoyage.");
    }

    @Override
    public void liberer(Table table) {
        System.out.println("Table déjà libérée.");
    }

    @Override
    public void reserver(Table table) {
        System.out.println("Impossible de réserver une table en nettoyage.");
    }

    @Override
    public void nettoyer(Table table) {
        table.setEtat(new LibreTable());
        System.out.println("Table " + table.getNumero() + " nettoyée et libre.");
    }

    @Override
    public String getEtat() {
        return "En nettoyage";
    }
}