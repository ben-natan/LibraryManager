package com.ensta.librarymanager.test;

import com.ensta.librarymanager.models.*;

class TestModele {
    public static void main(String[] args) {
        Livre livre = new Livre();
        System.out.println(livre.toString());
        Membre member = new Membre();
        System.out.println(member.toString());
        Emprunt emprunt = new Emprunt();
        System.out.println(emprunt.toString());
    }
}