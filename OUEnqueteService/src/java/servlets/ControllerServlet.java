package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Verantwoordelijkheden van ControllerServlet: � todo � HTTP-requests
 * doorgeven.
 */
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** 
     * ophalen opgeslagen enquete door respondent komt terecht bij controller
     * via HTTP get request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String bron = request.getParameter("bron");
        System.out.println("bron" + bron);
        if (bron.equals("invullenenquete")) {
            forward = "/EnqueteVerzendenServlet";
        } else {
            forward = "/unknown_bron.jsp";
        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }
    /**
     * doPost wordt verwerkt als de gebruiker een formulier heeft aangeboden.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forward = "";
        String bron = request.getParameter("bron");
        if (bron.equals("nieuweenquete")) { // titel en koptekst enquete ingevuld

            forward = "/EnqueteDefinierenServlet";
            System.out.println(forward);
        } else if (bron.equals("verzendvraag")) {
            forward = "/EnqueteDefinierenServlet";
        } else {
            forward = "/unknown_bron.jsp";
        }
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }
}
