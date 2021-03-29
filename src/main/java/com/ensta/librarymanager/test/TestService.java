package com.ensta.librarymanager.test;

import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.ServiceException;
import com.ensta.librarymanager.services.implementations.EmpruntServiceImpl;

public class TestService {
    public static void main( String[] args) {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> empruntList = new ArrayList<Emprunt>();
        try {
            empruntList = empruntService.getList();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
        
        for (Emprunt emprunt: empruntList) {
            System.out.println(emprunt.toString());
        }
    }
}
