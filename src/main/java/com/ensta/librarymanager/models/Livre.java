package com.ensta.librarymanager.models;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String isbn;

    public Livre() {
        this.id = -1;
        this.titre = "";
        this.auteur = "";
        this.isbn = "";
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String toString() {
        return "Titre: " + titre + ", Auteur: " + auteur + ", isbn: " + isbn;
    }
}