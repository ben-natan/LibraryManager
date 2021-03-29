package com.ensta.librarymanager.test;

import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.dao.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.dao.exceptions.*;

public class TestDao {

    public static void main(String[] args) throws DaoException {
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> empruntList = new ArrayList<Emprunt>();
        empruntList = empruntDao.getList();
        for (Emprunt emprunt: empruntList) {
            System.out.println(emprunt.toString());
        }
    }
    
}
