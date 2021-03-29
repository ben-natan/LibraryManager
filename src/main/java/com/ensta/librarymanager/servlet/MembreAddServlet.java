package com.ensta.librarymanager.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/membre_add")
public class MembreAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_add.jsp");

        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ServletException(e);
        } 
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        int id;
        
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

        try {
            id = membreService.create(nom, prenom, adresse, email, telephone);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(("/LibraryManager/membre_details?id="+id));
    }
}
