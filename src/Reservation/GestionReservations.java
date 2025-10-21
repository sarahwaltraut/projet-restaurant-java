package Reservation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import state.Table;

public class GestionReservations {
    private List<Reservation> reservations;

    public GestionReservations() {
        this.reservations = new ArrayList<>();
    }

    // ===============================
    // 🎯 CRUD (Créer / Modifier / Supprimer)
    // ===============================

    public void creerReservation(String nomClient, int nbPersonnes, Table table, LocalDateTime dateHeure) {
        if (table == null) {
            System.out.println("Table invalide !");
            return;
        }
        if (!table.getEtat().equalsIgnoreCase("Libre")) {
            System.out.println("Impossible : la table " + table.getNumero() + " n'est pas libre.");
            return;
        }
        if (nbPersonnes > table.getNbPlaces()) {
            System.out.println("Erreur : la table " + table.getNumero() + " ne peut accueillir que " + table.getNbPlaces() + " personnes.");
            return;
        }
        Reservation r = new Reservation(nomClient, nbPersonnes, table, dateHeure);
        reservations.add(r);
        System.out.println(" Réservation créée avec succès pour " + nomClient);
    }

    public void annulerReservation(String nomClient) {
        for (Reservation r : reservations) {
            if (r.getNomClient().equalsIgnoreCase(nomClient) && r.isActive()) {
                r.annuler();
                System.out.println(" Réservation annulée pour " + nomClient);
                return;
            }
        }
        System.out.println("Aucune réservation active trouvée pour " + nomClient);
    }

    public void modifierReservation(String nomClient, LocalDateTime nouvelleDate, Table nouvelleTable) {
        for (Reservation r : reservations) {
            if (r.getNomClient().equalsIgnoreCase(nomClient) && r.isActive()) {
                r.modifier(nouvelleDate, nouvelleTable);
                System.out.println(" Réservation modifiée pour " + nomClient);
                return;
            }
        }
        System.out.println("Aucune réservation active trouvée pour " + nomClient);
    }

    // ===============================
    // 🧾 AFFICHAGE
    // ===============================

    public void afficherReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Aucune réservation enregistrée.");
            return;
        }
        System.out.println("---- Liste des réservations ----");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void afficherReservationsActives() {
        boolean found = false;
        for (Reservation r : reservations) {
            if (r.isActive()) {
                if (!found) {
                    System.out.println("---- Réservations actives ----");
                    found = true;
                }
                System.out.println(r);
            }
        }
        if (!found) System.out.println("Aucune réservation active.");
    }

    // ===============================
    // 💾 SAUVEGARDE / EXPORT
    // ===============================

    public void exporterVers(BufferedWriter bw) throws IOException {
        for (Reservation r : reservations) {
            // Format : nomClient;nbPersonnes;numeroTable;dateHeure;etat
            bw.write(r.getNomClient() + ";" +
                    r.getNbPersonnes() + ";" +
                    r.getTable().getNumero() + ";" +
                    r.getDateHeure() + ";" +
                    (r.isActive() ? "active" : "annulee"));
            bw.newLine();
        }
    }

    // ===============================
    // ⚙️ ACCÈS AUX DONNÉES
    // ===============================

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
