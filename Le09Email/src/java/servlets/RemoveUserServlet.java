package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.domein.*;
import model.data.*;

/**
 * Deze servlet verwerkt het formulier om een gebruiker
 * uit de email lijst te verwijderen.
 */
public class RemoveUserServlet extends HttpServlet {
  
  /**
   * Als beide velden zijn ingevuld, wordt aan userList 
   * gevraagd om de gebruiker te verwijderen en wordt
   * gemeld of dat is gelukt; zo niet, dan wordt het
   * oorspronkelijke formulier opnieuw getoond (met 
   * een foutmelding).
   */
  protected void doPost(HttpServletRequest request, 
                        HttpServletResponse response) 
                        throws ServletException, IOException {
    // haal de parameters op uit de request
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");

    // controleer invoer
    String url;
    if (firstName.equals("") || lastName.equals("")) {
      String errorMessage = "Please fill out both boxes";
      url = "/remove_user.jsp";
      // geef de foutboodschap door
      request.setAttribute("errorMessage", errorMessage);
    } else {
        try {
            boolean removed = UserIO.getInstance().removeUser(firstName, lastName);
            url = "/display_removal.jsp";
            request.setAttribute("removed", removed); 
        } 
        catch (EmailException ex) {
            url = "/email_error.jsp";
            Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);  
        }
    }
    // stuur request and response door naar het antwoord
    RequestDispatcher dispatcher =
      getServletContext().getRequestDispatcher(url);
    dispatcher.forward(request, response);              
  }  
}
