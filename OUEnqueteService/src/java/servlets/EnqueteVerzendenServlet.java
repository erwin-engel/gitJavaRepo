package servlets;

import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import model.data.DatabaseException;
import model.data.DAO;
import model.domein.*;

/**
 * Deze servlet verwerkt het formulier om een gebruiker aan de email lijst toe
 * te voegen.
 *
 */
public class EnqueteVerzendenServlet extends HttpServlet {

    /**
     * nog invullen
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String bron = request.getParameter("bron");
        String url = "";
        if (bron.equals("invullenenquete")) {
            // haal enquetenr op uit het request object
            String enquetenr = request.getParameter("enquetenr");
            // foutmelding indien enquetenr niet aanwezig
            if (enquetenr.equals("")) {
                String errorMessage = "de gebruikte URL is niet correct";
                url = "/EnqueteError.jsp";
                session.setAttribute("errorMessage", errorMessage);
            // voeg de nieuwe enquete toe aan de database
            } else {
                try {
                    DAO dao = DAO.getInstance();
                    Enquete enquete = dao.leesEnquete(Integer.parseInt(enquetenr));
                    session.setAttribute("enquete", enquete);
                    session.setAttribute("bron", bron);
                    url = "/ToonOpgeslagenEnquete.jsp";
                } catch (DatabaseException ex) {
                        url = "/EnqueteError.jsp";
                        session.setAttribute("errorMessage", ex.toString());
                        Logger.getLogger(EnqueteDefinierenServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }  
        // stuur request and response door naar het antwoord
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
