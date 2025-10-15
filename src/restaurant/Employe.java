package restaurant;

public abstract class Employe {
    protected String id;
    protected String nom;
    protected String role;

    public Employe(String id, String nom, String role) {
        this.id = id;
        this.nom = nom;
        this.role = role;
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getRole() { return role; }

    public void setNom(String nom) { this.nom = nom; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return id + " - " + nom + " (" + role + ")";
    }
}