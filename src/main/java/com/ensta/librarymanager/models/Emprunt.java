package com.ensta.librarymanager.models;

import java.time.LocalDate;

public class Emprunt {
    private int id;
    private Membre membre;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt() {
        this.id = -1;
        this.membre = new Membre();
        this.livre = new Livre();
        this.dateEmprunt = LocalDate.now();
        this.dateRetour = null;
    }

    public int getId() {
        return id;
    }

    public Membre getMembre() {
        return membre;
    }

    public Livre getLivre() {
        return livre;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String toString() {
        return "Membre: " + membre + ", Livre: " + livre + ", Date d'emprunt: " + dateEmprunt.toString() + ", Date de retour: " + dateRetour.toString();
    }
}