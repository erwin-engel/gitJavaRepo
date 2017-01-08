package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Verantwoordelijkheden van ControllerServlet: - alle HTTP requests ontvangen -
 * op basis van bron uit request de bestemming bepalen - request doorgeven aan
 * deze bestemming
 */
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * DoGet wordt gebruikt voor het ophalen van een enquete die kan worden
     * ingevuld door een respondent.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forward = "";
        String bron = request.getParameter("bron");
        switch (bron) {
            case "invullenenquete":
                forward = "/EnqueteVerzendenServlet";
                break;
            case "ingevuldeenquete":
                forward = "/ToonInvulresultaat.jsp";
                break;
            default:
                forward = "/unknown_bron.jsp";
                break;
        }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }

    /**
     * doPost wordt gebruikt voor het definieren van een enquete door een
     * gebruiker
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forward = "";
        String bron = request.getParameter("bron");
        if (bron.equals("nieuweenquete") || bron.equals("verzendvraag")) {
            forward = "/EnqueteDefinierenServlet";
        } else {
            forward = "/unknown_bron.jsp";
        }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }
}
