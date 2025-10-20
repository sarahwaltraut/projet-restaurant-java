package state;

interface TableState {
    void occuper(Table table);
    void liberer(Table table);
    void reserver(Table table);
    void nettoyer(Table table);
    String getEtat();
}
