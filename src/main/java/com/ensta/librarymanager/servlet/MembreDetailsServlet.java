package com.ensta.librarymanager.servlet;

import java.util.List;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;


@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_details.jsp");

        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            int idMembre = Integer.parseInt(request.getParameter("id"));
            Membre membre = membreService.getById(idMembre);
            request.setAttribute("membre", membre);


            List<Emprunt> emprunts = empruntService.getListCurrentByMembre(idMembre);
            request.setAttribute("emprunts", emprunts);

            dispatcher.forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String abonnement = request.getParameter("abonnement");
        
        int id = Integer.parseInt(request.getParameter("id"));
        Membre membre = new Membre();

        membre.setId(id);
        membre.setNom(nom);
        membre.setPrenom(prenom);
        membre.setAdresse(adresse);
        membre.setEmail(email);
        membre.setTelephone(telephone);
        membre.setAbonnement(Abonnement.valueOf(abonnement));

        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        try {
            membreService.update(membre);
        } catch(ServiceException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("/LibraryManager/membre_details?id="+id);
    }
}
