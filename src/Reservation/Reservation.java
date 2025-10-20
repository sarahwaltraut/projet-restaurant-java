package Reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import state.Table;

class Reservation {
    private String nomClient;
    private int nbPersonnes;
    private Table table;
    private LocalDateTime dateHeure;
    private boolean active;

    public Reservation(String nomClient, int nbPersonnes, Table table, LocalDateTime dateHeure) {
        this.nomClient = nomClient;
        this.nbPersonnes = nbPersonnes;
        this.table = table;
        this.dateHeure = dateHeure;
        this.active = true; // par défaut, la réservation est active
        // La table passe à l’état réservée :
        table.reserver();
    }

    public String getNomClient() {
        return nomClient;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public Table getTable() {
        return table;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public boolean isActive() {
        return active;
    }

    public void setDateHeure(LocalDateTime nouvelleDate) {
        this.dateHeure = nouvelleDate;
    }

    public void annuler() {
        this.active = false;
        table.liberer();
        System.out.println("Réservation pour " + nomClient + " annulée.");
    }

    public void modifier(LocalDateTime nouvelleDate, Table nouvelleTable) {
        if (!active) {
            System.out.println("Impossible de modifier une réservation annulée.");
            return;
        }
        this.dateHeure = nouvelleDate;
        if (nouvelleTable != null && nouvelleTable != table) {
            this.table.liberer();
            this.table = nouvelleTable;
            this.table.reserver();
        }
        System.out.println("Réservation modifiée pour " + nomClient);
    }

    @Override
    public String toString() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Réservation : " + nomClient + " (" + nbPersonnes + " pers.) - " +
               "Table " + table.getNumero() + " - " + dateHeure.format(f) +
               " - " + (active ? "Active" : "Annulée");
    }
}
