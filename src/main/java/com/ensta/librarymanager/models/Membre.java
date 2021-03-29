package com.ensta.librarymanager.models;

public class Membre {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Abonnement abonnement;

    public Membre() {
        this.id = -1;
        this.nom = "";
        this.prenom = "";
        this.adresse = "";
        this.email = "";
        this.telephone = "";
        this.abonnement = Abonnement.BASIC;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public String toString() {
        return "Nom: " + nom + ", Prénom: " + prenom + ", Adresse: " + adresse + ", email: " + email + ", Tel: " + telephone + " Abonnement: " + abonnement.toString();
    }
}