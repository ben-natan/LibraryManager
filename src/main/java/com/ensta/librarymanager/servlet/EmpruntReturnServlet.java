package com.ensta.librarymanager.servlet;

import java.util.List;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/emprunt_return.jsp");

        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            List<Emprunt> emprunts = empruntService.getListCurrent();

            request.setAttribute("emprunts", emprunts);

            dispatcher.forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        } 

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEmprunt = Integer.parseInt(request.getParameter("id"));

        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            empruntService.returnBook(idEmprunt);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("/LibraryManager/dashboard");
    }
}
