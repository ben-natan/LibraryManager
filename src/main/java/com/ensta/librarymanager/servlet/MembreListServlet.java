package com.ensta.librarymanager.servlet;

import java.util.List;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/membre_list")
public class MembreListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_list.jsp");
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

        try {
            List<Membre> membres = membreService.getList();
            request.setAttribute("membres", membres);

            dispatcher.forward(request, response);
        } catch(ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        }
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
