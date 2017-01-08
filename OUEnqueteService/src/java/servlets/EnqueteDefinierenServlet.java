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
 * Deze servlet verwerkt de formulieren t.b.v.: - de definitie van een enquete -
 * het toevoegen van vragen aan een enquete - het opslaan van een enquete (en
 * vervolgens het tonen van het resultaat daarvan)
 */
public class EnqueteDefinierenServlet extends HttpServlet {

    /**
     * vererkt alle forumlieren die gebruikt worden om een enquete te definieren
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String bron = request.getParameter("bron");
        String url = "";
        // maak nieuwe equete
        if (bron.equals("nieuweenquete")) {
            url = maakNieuweEnquete(request, session);
        }
        // voeg vraag toe aan nieuwe enquete
        if (bron.equals("verzendvraag")) {
            Enquete enquete = (Enquete) session.getAttribute("enquete");
            // als de enquete in sessie object niet aanwezig is dan is de 
            // laatste vraag al verzonden en het resultaatgetoond waarna de 
            // gebruiker de browsers's "go back" button heeft gebruikt. In deze situatie
            // wordt de eerste pagina getoond.
            if (enquete == null) {
                request.setAttribute("errorMessage", "al verzonden vragen kunnen niet nogmaals verzonden worden,"
                        + " maak een nieuwe enquete indien gewenst");
                url = "/ToonNieuweEnquete.jsp";
            } else {
                url = maakNieuweVraag(request, session, enquete);
            }
        }
        // verzend request and response naar bestemming
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    // maak nieuwe equete, zet enquete in het sessie objecgt en 
    // bepaal URL voor de volgende view
    private String maakNieuweEnquete(HttpServletRequest request, HttpSession session) {
        // haal de parameters op uit het request object
        String titel = request.getParameter("titel");
        String koptekst = request.getParameter("koptekst");
        String url = "";
        session.setAttribute("errorMessage", "");
        if (titel.equals("") || koptekst.equals("")) {
            url = "/ToonNieuweEnquete.jsp";
            session.setAttribute("errorMessage", "vul alle velden in");
            // maak nieuwe enquete en zet deze in de sessie
        } else {
            try {
                session.setAttribute("enquete", new Enquete(titel, koptekst));
                url = "/ToonNieuweVraag.jsp";
            } catch (EnqueteException ex) {
                url = "/EnqueteError.jsp";
                session.setAttribute("errorMessage", ex.toString());
                Logger.getLogger(EnqueteDefinierenServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return url;
    }

    // Voeg een nieuwe vraag toe aan enquete. Als het de laatste vraag betreft
    // sla de vraag op in de data store 

    private String maakNieuweVraag(HttpServletRequest request, HttpSession session,
            Enquete enquete) {
        String tekst = request.getParameter("tekst");
        String vraagtype = request.getParameter("vraagtype");
        String[] alternatieven = request.getParameterValues("alt");
        String laatsteVraag = request.getParameter("laatstevraag");
        session.setAttribute("errorMessage", "");
        String url = "";
        if (tekst.equals("")) {
            url = "/ToonNieuweVraag.jsp";
            session.setAttribute("errorMessage", "vul een tekst in");
        } else {
            // voeg de nieuwe vraag toe aan enquete
            try {
                Vraag vraag = new Vraag(enquete.getAantalVragen() + 1,
                        Integer.parseInt(vraagtype), tekst);
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
        // voeg laatste vraag toe aan enquete en sla enquete op in datastore
        if (laatsteVraag != null && !tekst.equals("")) {
            
            try {
                DAO dao = DAO.getInstance();
                enquete.setEnquetenr(dao.schrijfEnquete((Enquete) session.getAttribute("enquete")));
                // bij laatste vraag word de enquete in het sessie object 
                // op null gezet en wordt de enquete in request object opgenomen 
                // tbv het tonen van de opgeslagen enquete
                session.setAttribute("enquete", null);
                request.setAttribute("enquete", enquete);
                // bron in sessie opnemen omdat ToonOpgeslagenEnquete.jsp 
                // t.b.v. meerdere bronnen gebruikt wordt
                session.setAttribute("bron", request.getParameter("bron"));
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
        return url;
    }
}
