package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

/**
 * Verantwoordelijkheden van ControllerServlet: - nieuw Spel object aanmaken bij
 * eerste aanroep - keuzes van gebruiker na starten spel doorgeven aan Spel
 * object - Spel object toevoegen aan elke HTTP-request - HTTP-request
 */
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BoterKaasEierenSpel boKaEiSpel = null;

    /**
     * Uitvoeren bij het starten van de servlet (als server wordt gestart).
     */
    public void init() throws ServletException {
        // deze methode wordt nog niet gebruikt
    }

    /**
     * Uitvoeren bij het starten van de servlet (als server wordt gestart)
     */
    public void destroy() {
        // deze methode wordt nog niet gebruikt
    }

    /**
     * De web.xml welcome-file-list zorgt ervoor dat de eerste aanroep door
     * gebruiker bij de ControllerServlet terecht komt via een HTTP get-request
     *
     * Keuze 'nieuw spel' na eerste aanroep komt terecht bij de
     * ControllerServlet via een HTTP get-request.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse 
            response) throws ServletException, IOException {
        System.out.println("do get");
        HttpSession session = getSession(request); 
        boKaEiSpel = new BoterKaasEierenSpel(Markering.KRUIS);
        String url = "/Display_BoKaEiSpel.jsp";
        String bron = request.getParameter("bron");
        // als de parameter "bron" niet aanwezig is dan is de bron van de 
        // aanroep de web.xml file. (welcome-file-list: aller eerste aanroep 
        // door user)
        if (bron == null) {
            bron = "webXmlBo-Ka-Ei";
        }
        switch (bron) {
            case "webXmlBo-Ka-Ei":
                boKaEiSpel.setBedenkSlim(true);
                session.setAttribute("boKaEiSpel", boKaEiSpel);
                // Haal de scoreCookies op en zet ze in het sessie object
                Cookie[] scoreCookies = getScoreCookies(request);
                for (int i = 0; i < scoreCookies.length; i++) {
                    session.setAttribute(scoreCookies[i].getName(), 
                            scoreCookies[i]);
                }
                break;
            case "bo_ka_ei":
                if (request.getParameter("slim").equals("true")) {
                    boKaEiSpel.setBedenkSlim(true);
                } else {
                    boKaEiSpel.setBedenkSlim(false);
                }
                session.setAttribute("boKaEiSpel", boKaEiSpel);
                break;
            default:
                url = "/unknown_bron.jsp";
                break;
        }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * doPost wordt verwerkt als de gebruiker een zet heeft gedaan (er wordt dan
     * een formulier aangeboden)
     */
    protected void doPost(HttpServletRequest request,
             HttpServletResponse response)throws ServletException, IOException {
        System.out.println("do post");
        HttpSession session = getSession(request);
        int spelerZetIndex = Integer.parseInt(request.getParameter("spelerZetIndex"));
        String url = "/Display_BoKaEiSpel.jsp";
        String bron = request.getParameter("bron");
        switch (bron) {
            case "bo_ka_ei":
                boKaEiSpel.verwerkZet(Markering.KRUIS, spelerZetIndex);
                boKaEiSpel.verwerkZet(Markering.NUL, boKaEiSpel.bedenkZet(Markering.NUL));
                session.setAttribute("boKaEiSpel", boKaEiSpel);
                break;
            default:
                url = "/unknown_bron.jsp";
                break;
        }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Deze methode wordt door doGet en doPost methode gebruikt om het sessie
     * object op te halen. Als de sessie nog geen spel object heeft (i.g.v. 
     * eerste aanroep) dan wordt spel aan sessie toegevoegd
     */
    private HttpSession getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("boKaEiSpel") == null) {
            session.setAttribute("boKaEiSpel", boKaEiSpel);
        }
        return session;
    }
    
    /**
     * Haal de score cookies op uit het request. Als deze er niet zijn maak dan 
     * nieuwe scoreCookies
     */
    private Cookie[] getScoreCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userScoreCookieName = "userScoreCookie";
        String userScoreCookieValue = "";
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                System.out.println("cookies not null, length > nul");
                Cookie cookie = cookies[i];
                if (userScoreCookieName.equals(cookie.getName())) {
                    System.out.println("aanwezig");
                    userScoreCookieValue = cookie.getValue();
                // deze volgende situatie doet zich allen voor als 
                // het spel voor de eerste keer is getoond en de speler
                //  gebruik de refresh button:
                } else {
                    userScoreCookieValue = "0";
                }
            }
        } else {
            userScoreCookieValue = "0";
        }
        Cookie[] cookiesNew = new Cookie[1];
        cookiesNew[0] = new Cookie(userScoreCookieName, userScoreCookieValue);
        return cookiesNew;
    }
}
