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
public class EnqueteDefinierenServlet extends HttpServlet {

    /**
     * Als alle velden zijn ingevuld, wordt aan userList gevraagd om de nieuwe
     * gebruiker toe te voegen en wordt daarvan een bevstiging getoond; zo niet,
     * dan wordt het oorspronkelijke formulier opnieuw getoond (met een
     * foutmelding).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String bron = request.getParameter("bron");
        String laatsteVraag = request.getParameter("laatstevraag");
        String url = "";

        // controleer invoer
        if (bron.equals("nieuweenquete")) {
            // haal de parameters op uit het request object
            String titel = request.getParameter("titel");
            String koptekst = request.getParameter("koptekst");
            if (titel.equals("") || koptekst.equals("")) {
                String errorMessage = "vul alle velden in";
                url = "/ToonNieuweEnquete.jsp";
                session.setAttribute("errorMessage", errorMessage);
            // voeg de nieuwe enquete toe aan de database
            } else {
                try {
                    Enquete enquete = new Enquete(titel, koptekst);
                    session.setAttribute("enquete", enquete);
                    url = "/ToonNieuweVraag.jsp";
                } catch (EnqueteException ex) {
                    url = "/EnqueteError.jsp";
                    session.setAttribute("errorMessage", ex.toString());
                    Logger.getLogger(EnqueteDefinierenServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (bron.equals("verzendvraag")) {
            Enquete enquete = (Enquete) session.getAttribute("enquete");
            // enquete in sessie word op null gezet na verzenden van laatste vraag
            if (enquete != null) {
                String tekst = request.getParameter("tekst");
                String vraagtype = request.getParameter("vraagtype");
                String[] alternatieven = request.getParameterValues("alt");
                if (tekst.equals("")) {
                    String errorMessage = "geef tekst op";
                    url = "/ToonNieuweVraag.jsp";
                    session.setAttribute("errorMessage", errorMessage);
                } else {
                    // voeg de nieuwe vraag toe aan enquete
                    try {

                        Vraag vraag = new Vraag(enquete.getAantalVragen() + 1, Integer.parseInt(vraagtype), tekst);
                        enquete.addVraag(vraag);
                        if (!vraagtype.equals("0")) {
                            for (String alternatief : alternatieven) {
                                if (!alternatief.equals("")) {
                                    vraag.voegAlternatiefToe(alternatief);
                                }
                            }
                        }
                        url = "/ToonNieuweVraag.jsp";
                    } catch (EnqueteException ex) {
                        url = "/EnqueteError.jsp";
                        session.setAttribute("errorMessage", ex.toString());
                        Logger.getLogger(EnqueteDefinierenServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (laatsteVraag != null) {
                    try {
                        DAO dao = DAO.getInstance();
                        enquete.setEnquetenr(dao.schrijfEnquete((Enquete) session.getAttribute("enquete")));
                        // bij laatste vraag word enquete in sessie op null gezet en wordt tbv het tonen van de enquete
                        // de enquete in een request attribute opgenomen
                        request.setAttribute("enquete", enquete);
                        session.setAttribute("enquete", null);
                        // bron in sessie opnemen omdat ToonOpgeslagenEnquete.jsp tbv meerdere bronnen gebruikt wordt
                        session.setAttribute("bron", bron);
                        url = "/ToonOpgeslagenEnquete.jsp";
                    } catch (SQLException ex) {
                        url = "/EnqueteError.jsp";
                        session.setAttribute("errorMessage", ex.toString());
                        Logger.getLogger(EnqueteDefinierenServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DatabaseException ex) {
                        url = "/EnqueteError.jsp";
                        session.setAttribute("errorMessage", ex.toString());
                        Logger.getLogger(EnqueteDefinierenServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                request.setAttribute("errorMessage", "al verzonden vragen kunnen niet nogmaals verzonden worden,"
                        + " maak een nieuwe enquete indien gewenst");
                url = "/ToonNieuweEnquete.jsp";
            }
        }

        // stuur request and response door naar het antwoord
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
