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
 * object - benodigde informatie voor view in HTTP-request zetten
 */
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("do get");
        BoterKaasEierenSpel boKaEiSpel = new BoterKaasEierenSpel(Markering.KRUIS);
        HttpSession session = request.getSession();
        Cookie[] scoreCookies = getScoreCookies(request);
        String bron = request.getParameter("bron");
        String url = "/Display_BoKaEiSpel.jsp";
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
                // zet score cookies in request object en values van score cookies 
                // in sessie object
                processScoreCookiesDoGet(scoreCookies, session, response);
                break;
            case "bo_ka_ei":
                if (request.getParameter("slim").equals("true")) {
                    boKaEiSpel.setBedenkSlim(true);
                } else {
                    boKaEiSpel.setBedenkSlim(false);
                }
                // zet score cookies in request object en values van score cookies 
                // in sessie object
                 processScoreCookiesDoGet(scoreCookies, session, response);
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
     * zet cookies die via request object ontvangen zijn (via doGet methode) in
     * response object en in sessie object
     */
    private void processScoreCookiesDoGet (Cookie[] scoreCookies, 
            HttpSession session, HttpServletResponse response) {
        for (int i = 0; i < scoreCookies.length; i++) {
            scoreCookies[i].setMaxAge(60 * 60 * 24 * 365 * 2);
            scoreCookies[i].setPath("/");
            response.addCookie(scoreCookies[i]);
            if (scoreCookies[i].getName().equals("userScoreCookie")){
                session.setAttribute("userScore",
                        Integer.parseInt(scoreCookies[i].getValue()));
            }
            if (scoreCookies[i].getName().equals("autoScoreCookie")){
                session.setAttribute("autoScore",
                        Integer.parseInt(scoreCookies[i].getValue()));
            }
        }        
    }
    
    /**
     * doPost wordt verwerkt als de gebruiker een zet heeft gedaan (er wordt dan
     * een formulier aangeboden)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        System.out.println("do post");
        HttpSession session = request.getSession();
        BoterKaasEierenSpel boKaEiSpel = (BoterKaasEierenSpel) 
            session.getAttribute("boKaEiSpel");
        Cookie[] scoreCookies = getScoreCookies(request);
        int spelerZetIndex = Integer.parseInt(request.getParameter("spelerZetIndex"));
        String url = "/Display_BoKaEiSpel.jsp";
        String bron = request.getParameter("bron");
        switch (bron) {
            case "bo_ka_ei":
                boKaEiSpel.verwerkZet(Markering.KRUIS, spelerZetIndex);
                boKaEiSpel.verwerkZet(Markering.NUL, 
                        boKaEiSpel.bedenkZet(Markering.NUL));
                processScoreCookiesDoPost(scoreCookies, session, response, 
                        boKaEiSpel);
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
     * Zet de spelresultaten in de cookies die via request object ontvangen zijn
     * (via doPost methode) en zet deze cookies daarna in response object en in
     * sessie object. 
     */
    private void processScoreCookiesDoPost (Cookie[] scoreCookies, 
            HttpSession session, HttpServletResponse response, 
            BoterKaasEierenSpel boKaEiSpel) {
        for (int i = 0; i < scoreCookies.length; i++) {
            if (boKaEiSpel.getStatus() == Status.KRUISWINT) {
                if (scoreCookies[i].getName().equals("userScoreCookie")) {
                    int userScore = 
                        Integer.parseInt(scoreCookies[i].getValue());
                    userScore = userScore + 1;
                    scoreCookies[i].setValue("" + userScore);
                    session.setAttribute("userScore", userScore);
                }
            }
            if (boKaEiSpel.getStatus() == Status.NULWINT) {
                if (scoreCookies[i].getName().equals("autoScoreCookie")) {
                    int autoScore = 
                        Integer.parseInt(scoreCookies[i].getValue());
                    autoScore = autoScore + 1;
                    scoreCookies[i].setValue("" + autoScore);
                    session.setAttribute("autoScore", autoScore);
                } 
            }
            scoreCookies[i].setMaxAge(60 * 60 * 24 * 365 * 2);
            scoreCookies[i].setPath("/");
            response.addCookie(scoreCookies[i]);
        }  
    }
    /**
     * Haal de score cookies op uit het request. Als deze er niet zijn maak dan 
     * worden er nieuwe cookies gemaakt
     */
    private Cookie[] getScoreCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userScoreCookieName = "userScoreCookie";
        String userScoreCookieValue = "";
        String autoScoreCookieName = "autoScoreCookie";
        String autoScoreCookieValue = "";
        if (cookies != null) {
            // cookies aanwezig
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (userScoreCookieName.equals(cookie.getName()))  {
                    userScoreCookieValue = cookie.getValue();
                } else if (autoScoreCookieName.equals(cookie.getName())) {
                    autoScoreCookieValue = cookie.getValue();
                } else {
                // de volgende situatie doet zich allen voor als 
                // het spel voor de eerste keer is getoond en de speler
                // gebruik de refresh button:
                    userScoreCookieValue = "0";
                    autoScoreCookieValue = "0";     
                }
            }
        } else {
            userScoreCookieValue = "0";
            autoScoreCookieValue = "0";
        }
        Cookie[] cookiesNew = new Cookie[2];
        cookiesNew[0] = new Cookie(userScoreCookieName, userScoreCookieValue);
        cookiesNew[1] = new Cookie(autoScoreCookieName, autoScoreCookieValue);
        return cookiesNew;
    }
}
