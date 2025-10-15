package observer;


interface Observable {
    void ajouterObserver(Observer o);
    void supprimerObserver(Observer o);
    void notifierObservers(String message);
}
