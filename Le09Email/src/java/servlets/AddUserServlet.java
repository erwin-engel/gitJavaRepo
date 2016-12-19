package servlets;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import model.domein.*;
import model.data.*;

/**
 * Deze servlet verwerkt het formulier om een gebruiker aan de email lijst toe
 * te voegen.
 *
 */
public class AddUserServlet extends HttpServlet {

    /**
     * Als alle velden zijn ingevuld, wordt aan userList gevraagd om de nieuwe
     * gebruiker toe te voegen en wordt daarvan een bevstiging getoond; zo niet,
     * dan wordt het oorspronkelijke formulier opnieuw getoond (met een
     * foutmelding).
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        // haal de parameters op uit de request
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailAddress = request.getParameter("emailAddress");

        // controleer invoer
        String url;
        if (firstName.equals("") || lastName.equals("") || emailAddress.equals("")) {
            String errorMessage = "Please fill out all three boxes";
            url = "/join_email_list.jsp";
            // geef de foutboodschap door
            request.setAttribute("errorMessage", errorMessage);
        } else {
            try {
                addUser(firstName, lastName, emailAddress);
                url = "/display_email_entry.jsp";
            } catch (EmailException ex) {
                url = "/email_error.jsp";
                Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // stuur request and response door naar het antwoord
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private void addUser(String firstName, String lastName, String emailAddress) throws EmailException {
        User user = new User(firstName, lastName, emailAddress);
        UserIO userIo = UserIO.getInstance();
        userIo.addUser(user);
    }
}
