package restaurant;

import java.time.LocalDate;

public class Facture {
    private String id;
    private double montant;
    private LocalDate date;

    public Facture(String id, double montant, LocalDate date) {
        this.id = id;
        this.montant = montant;
        this.date = date;
    }

    public String getId() { return id; }
    public double getMontant() { return montant; }
    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return id + " - " + montant + "€ - " + date;
    }
}
