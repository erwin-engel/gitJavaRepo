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
 * Verantwoordelijkheden van ControllerServlet:
 * - nieuw Spel object aanmaken bij eerste eerste aanroep 
 * - keuzes van gebruiker na starten spel doorgeven aan Spel
 * - resultaten spel doorgeven aan view (via sessie object)
 * - inlezen en verwerken scores uit persistant cookies
 * - reset scores in persistant cookies afhandelen
 * 
 * @author erwin
 */
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Deze methode verwerkt HTTP doGet request.
     * 
     * De web.xml welcome-file-list zorgt ervoor dat de eerste aanroep door
     * de doGet methode wordt afgehandelt
     * Het starten van een nieuw spel  wordt ook door de doGetMethode afgehandelt
     * @param request vanaf browser
     * @param response naar browser
     * @throws ServletException
     * @throws IOException 
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
        // door gebruiker)
        if (bron == null) {
            bron = "webXmlBo-Ka-Ei";
        }
        switch (bron) {
            case "webXmlBo-Ka-Ei":
                boKaEiSpel.setBedenkSlim(true);
                session.setAttribute("boKaEiSpel", boKaEiSpel);
                processScoreCookiesDoGet(scoreCookies, session, response);
                break;
            case "bo_ka_ei":
                if (request.getParameter("slim").equals("true")) {
                    boKaEiSpel.setBedenkSlim(true);
                } else {
                    boKaEiSpel.setBedenkSlim(false);
                }
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
     * Deze methode verwerkt de score cookies die door doGet methode ontvangen
     * zijn. Score cookies worden in response gezet en inhoud score cookies 
     * wordt in sessie opgenomen
     * 
     * @param scoreCookies (user- en auto score cookies)
     * @param session
     * @param response 
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
     * Deze methode verwerkt HTTP doPost request
     *
     * taken:
     * - zet van gebruiker doorgeven aan Spel en resultaten in sessie opnemen
     * - score cookies reset n.a.v. keuze gebruiker
     * - score cookies bijwerken m.b.v. status Spel
     * - scores uit cookies in sessie opnemen
     * - score cookies in response opnemen
     * 
     * @param request vanaf browser
     * @param response naar browser
     * @throws ServletException
     * @throws IOException 
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        System.out.println("do post");
        HttpSession session = request.getSession();
        BoterKaasEierenSpel boKaEiSpel = (BoterKaasEierenSpel) 
            session.getAttribute("boKaEiSpel");
        Cookie[] scoreCookies = getScoreCookies(request);

        String url = "/Display_BoKaEiSpel.jsp";
        String bron = request.getParameter("bron");
        switch (bron) {
            case "bo_ka_ei":
                int spelerZetIndex = Integer.parseInt(request.getParameter("spelerZetIndex"));  
                boKaEiSpel.verwerkZet(Markering.KRUIS, spelerZetIndex);
                boKaEiSpel.verwerkZet(Markering.NUL, 
                        boKaEiSpel.bedenkZet(Markering.NUL));
                processScoreCookiesDoPost(scoreCookies, session, response, 
                        boKaEiSpel);
                session.setAttribute("boKaEiSpel", boKaEiSpel);
                break;
            case "bo_ka_ei_reset_score":
                resetScoreCookies(scoreCookies, session, response);    
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
     * Deze methode verwerkt de score cookies die door doPost methode ontvangen
     * zijn. 
     * - Score cookies worden bijgewerkt met spel resultaten. 
     * - Nieuwe inhoud score cookies wordt in sessie opgenomen.
     * @param scoreCookies (user- en auto score cookies)
     * @param session
     * @param response
     * @param boKaEiSpel 
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
     * Deze methode doet een reset de scores.
     * 
     * @param scoreCookies (user- en auto score cookies)
     * @param session
     * @param response 
     */
    private void resetScoreCookies (Cookie[] scoreCookies, 
            HttpSession session, HttpServletResponse response) {
        for (int i = 0; i < scoreCookies.length; i++) {
            scoreCookies[i].setMaxAge(0);
            scoreCookies[i].setPath("/");
            response.addCookie(scoreCookies[i]);
            if (scoreCookies[i].getName().equals("userScoreCookie")){
                session.setAttribute("userScore", 0);
            }
            if (scoreCookies[i].getName().equals("autoScoreCookie")){
                session.setAttribute("autoScore", 0);
            }
        }        
    }
    /**
     * Deze methode haalt de score cookies op uit het request. 
     * Als deze er niet zijn maak dan worden er nieuwe score cookies gemaakt
     * 
     * @param request (van zowel doGet als doPost)
     * @return 
     */
    private Cookie[] getScoreCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userScoreCookieName = "userScoreCookie";
        String userScoreCookieValue = "";
        String autoScoreCookieName = "autoScoreCookie";
        String autoScoreCookieValue = "";
        if (cookies != null) {
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
