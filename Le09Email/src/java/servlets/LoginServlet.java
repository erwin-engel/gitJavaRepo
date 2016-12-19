package servlets;

import java.io.IOException;
import java.util.ArrayList;
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
 * Deze servlet verwerkt het login-formulier.
 */
public class LoginServlet extends HttpServlet {
  
  /**
   * Bij het admin-attribuut van de sessie wordt
   * nagevraagd of de gebruiker ingelogd mag worden.
   * Is dat het geval, dan wordt het sessie-attribuut
   * ingelogd gelijk aan true en wordt alsnog het
   * oorspronkelijke gevraagde formulier getoond.
   * Is de login incorrect, dan wordt het login-
   * formulier opnieuw getoond.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
    // haal de parameters op uit de request
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");
    HttpSession session = request.getSession();
 // waarde gebruiksmogelijkheid komt uit menu  
    String gebruiksmogelijkheid = 
        (String)session.getAttribute("gebruiksmogelijkheid");
    String url;
     if (userName.equals("") || password.equals("")) {
        String errorMessage = "Please fill out all boxes";
        url = "/login.jsp";
        // geef de foutboodschap door
        request.setAttribute("errorMessage", errorMessage);
     } 
     else {
        try {
            UserIO userIO = UserIO.getInstance();
            Administrator admin = userIO.getAdmin(userName);
            boolean ingelogd = admin.controleerLogin(userName, password);
            if (ingelogd) {
              session.setAttribute("ingelogd", true);
                switch (gebruiksmogelijkheid) {
                    case "show":
                        ArrayList<User> userList = UserIO.getInstance().getUsers();
                        session.setAttribute("userList", userList);
                        url = "/show_users.jsp";
                        break;
                    case "remove":
                        url = "/remove_user.jsp";
                        break;
                    default:
                        url = "/unknown_bron.jsp";
                        break;
                }
            } else {
              String errorMessage = "Username or password is incorrect";
              url = "/login.jsp";
              // geef de foutboodschap door
              request.setAttribute("errorMessage", errorMessage);
            } 
        } catch (EmailException ex) {
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
