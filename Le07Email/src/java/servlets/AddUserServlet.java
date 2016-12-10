package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.domein.*;

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
            url = "/display_email_entry.jsp";
            // haal de userList op uit de sessie en voeg de nieuwe gebruiker toe
            HttpSession session = request.getSession();  // maak sesion object
            UserList userList =  (UserList) session.getAttribute("userList");
            userList.addUser(firstName, lastName, emailAddress);
        }

        // stuur request and response door naar het antwoord
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
