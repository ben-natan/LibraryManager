package com.ensta.librarymanager.servlet;

import java.util.List;
// import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/dashboard.jsp");

        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        
        try {
            int nbLivres = livreService.count();
            int nbMembres = membreService.count();
            List<Emprunt> currentEmprunts = empruntService.getListCurrent();
            int nbEmprunts = currentEmprunts.size();
            // int nbEmprunts = empruntService.count() si on veut le compte total

            request.setAttribute("nbLivres", nbLivres);
            request.setAttribute("nbMembres", nbMembres);
            request.setAttribute("nbEmprunts", nbEmprunts);
            request.setAttribute("currentEmprunts", currentEmprunts);

            dispatcher.forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        } 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
