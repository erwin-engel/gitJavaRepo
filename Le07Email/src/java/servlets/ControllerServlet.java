package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.domein.Administrator;
import model.domein.UserList;

/**
 * Verantwoordelijkheden van ControllerServlet: � Inlezen userList bij starten
 * server � Wegschrijven userLst bij aflsuiten server � userList toevoegen aan
 * elke HTTP-request � HTTP-requests doorgeven.
 */
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String FILENAME = "users.txt";

    private UserList userList = null;
    private Administrator admin = new Administrator("admin", "Amsterdam");

    /**
     * Bij het starten van de servlet wordt de userList ingelezen.
     */
    public void init() throws ServletException {
        // maken userlist
        try {
            userList = new UserList(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bij het afsluiten van de servlet (als de server wordt uitgezet) wordt de
     * userList naar een bestand geschreven.
     */
    public void destroy() {
        try {
            userList.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Alle menu-opties komen terecht bij de ControllerServlet via een HTTP
     * get-request.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forward = "";
        String bron = request.getParameter("bron");
        HttpSession session = getSession(request);
        switch (bron) {
            case "join":
                forward = "/join_email_list.jsp";
                break;
            case "show":
                if ((boolean) session.getAttribute("ingelogd")) {
                    forward = "/show_users.jsp";
                } else {
                    forward = "/login.jsp";
                    session.setAttribute("gebruiksmogelijkheid", bron);
                }
                break;
            case "remove":
                if ((boolean) session.getAttribute("ingelogd")) {
                    forward = "/remove_user.jsp";
                    session.setAttribute("gebruiksmogelijkheid", bron);
                } else {
                    forward = "/login.jsp";
                }
                break;
            default:
                forward = "/unknownBron.jsp";
                break;
        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }

    /**
     * doPost wordt verwerkt als de gebruiker een formulier heeft aangeboden.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String bron = request.getParameter("bron");
        HttpSession session = getSession(request);

        switch (bron) {
            case "join":
                // join-email-list formulier ingevuld
                forward = "/AddUserServlet";
                break;
            case "remove":
                if ((boolean) session.getAttribute("ingelogd")) {
                    // remove-formulier ingevuld
                    forward = "/RemoveUserServlet";
                    break; 
                } else {
                    forward = "/login.jsp";
                    session.setAttribute("gebruiksmogelijkheid", bron);
                }
            case "login":
                // login-formulier ingevuld
                forward = "/LoginServlet";
                break;
            default:
                forward = "/unknownBron.jsp";
                break;
        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }

    private HttpSession getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();  // maak sesion object
        if (session.getAttribute("userList") == null) {
            session.setAttribute("userList", userList);
            session.setAttribute("admin", admin);
            session.setAttribute("ingelogd", false);
        }
        return session;
    }
}

