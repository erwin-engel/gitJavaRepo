/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.domein.Administrator;

/**
 *
 * @author erwin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {




    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = (String) request.getParameter("userName");
        String password = (String) request.getParameter("password");
        Administrator admin = (Administrator) session.getAttribute("admin");
        String bron = (String) session.getAttribute("gebruiksmogelijkheid");
        String errorMessage = "please provide correct userid / password";
        String url = null;
        System.out.println("admin name: " + userName);
 //       System.out.println("admin name: " + password);
        if (admin.controleerLogin(userName, password)){
            session.setAttribute("ingelogd", true);
            if (bron.equals("show")){
               url = "/show_users.jsp"; 
            } else {
                if (bron.equals("remove")){
                   url = "/remove_user.jsp"; 
                }
            }
        } else {
            request.setAttribute("errorMessage", errorMessage);
            url = "/login.jsp";
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
