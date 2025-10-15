package observer;

import java.util.ArrayList;
import java.util.List;

public class System_Notification {
	private List<Observer> observateurs = new ArrayList<>();

    public void ajouterObservateur(Observer o) {
        observateurs.add(o);
    }

    public void supprimerObservateur(Observer o) {
        observateurs.remove(o);
    }

    public void notifierTous(String message) {
        for (Observer o : observateurs) {
            o.notifier(message);
        }
    }
}
