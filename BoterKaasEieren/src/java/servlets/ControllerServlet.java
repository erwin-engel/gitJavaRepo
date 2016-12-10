package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;


/**
 * Verantwoordelijkheden van ControllerServlet: � Inlezen userList bij starten
 * server � Wegschrijven userLst bij aflsuiten server � userList toevoegen aan
 * elke HTTP-request � HTTP-requests doorgeven.
 */
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private BoterKaasEierenSpel boKaEiSpel = null;
    private Status status = null;

    /**
     * Uitvoeren bij het starten van de servlet (als server wordt gestart).
     */
    public void init() throws ServletException {
        // Niet nodig ?
    }

    /**
     * Uitvoeren bij het starten van de servlet (als server wordt gestart) 
     */
    public void destroy() {
        // Niet nodig ?
    }
    /**
     * Keuze 'nieuw spel' komt terecht bij de ControllerServlet via een HTTP
     * get-request.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("do get");
        String url = "";
        String bron = request.getParameter("bron");
        System.out.println(bron);
        HttpSession session = getSession(request);
        switch (bron) {
            case "bo_ka_ei":
                System.out.println("bo_ka_ei get");
                boKaEiSpel = new BoterKaasEierenSpel(Markering.KRUIS);
                session.setAttribute("spelStatus", boKaEiSpel.getStatus());
                if (request.getParameter("slim").equals("true")){
                    boKaEiSpel.setBedenkSlim(true);
                } else {
                    boKaEiSpel.setBedenkSlim(false);
                }
                session.setAttribute("boKaEiSpel", boKaEiSpel);
                session.setAttribute("spelStatus", boKaEiSpel.getStatus());
                url = "/Display_BoKaEiSpel.jsp";
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
     * doPost wordt verwerkt als de gebruiker een formulier heeft aangeboden.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("do post");
        String url = "";
        String bron = request.getParameter("bron");
        int spelerZetIndex = Integer.parseInt(request.getParameter("spelerZetIndex"));
        HttpSession session = getSession(request);

        switch (bron) {
            case "bo_ka_ei":
                System.out.println("bo_ka_ei post spelerZetIndex = " + spelerZetIndex);
                boKaEiSpel.verwerkZet(Markering.KRUIS, spelerZetIndex);        
                boKaEiSpel.verwerkZet(Markering.NUL, boKaEiSpel.bedenkZet(Markering.NUL));
                session.setAttribute("boKaEiSpel", boKaEiSpel);
                session.setAttribute("spelStatus", boKaEiSpel.getStatus());
                url = "/Display_BoKaEiSpel.jsp";
                break;
            default:
                url = "/unknown_bron.jsp";
                break;
        }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private HttpSession getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();  // maak sesion object
        if (session.getAttribute("boKaEiSpel") == null) {
            System.out.println("maak spel instance");
            session.setAttribute("boKaEiSpel", boKaEiSpel);
        }
        return session;
    }
}

