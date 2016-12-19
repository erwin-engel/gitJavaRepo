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
import model.data.EmailException;
import model.data.UserIO;
import model.domein.Administrator;
import model.domein.User;

/**
 * Verantwoordelijkheden van ControllerServlet:
 * � userList toevoegen aan elke HTTP-request
 * � HTTP-requests doorgeven.
 */
public class ControllerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final String FILENAME = "users.txt";
  
 // private ArrayList userList = null;
//  private Administrator admin = new Administrator("admin", "admin");
  
  /**
   * Bij het starten van de servlet wordt de userList
   * ingelezen. 
   */
  public void init() throws ServletException {
 /*   try {
      userList = new UserList(FILENAME);
    } catch (IOException e) { 
      e.printStackTrace();
    } */
  } 
  
  /**
   * Bij het afsluiten van de servlet (als de server
   * wordt uitgezet) wordt de userList naar een bestand
   * geschreven.
   */
  public void destroy() {
 /*   try {
      userList.close();
    } catch (IOException e) {
      e.printStackTrace();
    } */
  }

  /**
   * Alle menu-opties komen terecht bij de ControllerServlet via een
   * HTTP get-request.  
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String forward = "";
    String bron = request.getParameter("bron");
    HttpSession session = getSession(request);
        
    if (bron.equals("join")) { 
      forward = "/join_email_list.jsp"; 
    }
    else if (!(Boolean)session.getAttribute("ingelogd")) {
      // gebruiker moet inloggen; onthoud in de sessie waar
      // de request vandaan kwam
      forward = "/login.jsp"; 
      session.setAttribute("gebruiksmogelijkheid", bron);
    } 
    else if (bron.equals("show")) {
        // indien ingelogd dan eerst verse userList in sessie zetten voordat
        // users getoond worden
        try {
            ArrayList<User> userList = UserIO.getInstance().getUsers();
            session.setAttribute("userList", userList);
            forward = "/show_users.jsp" ;
        } catch (EmailException ex) {
            forward = "/email_error.jsp";
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    else if (bron.equals("remove")) {
      forward = "/remove_user.jsp";       
    }
    else {
      forward = "/unknown_bron.jsp";
    } 
    
    RequestDispatcher dispatcher = 
     getServletContext().getRequestDispatcher(forward);
    dispatcher.forward(request, response);
  }

  /**
   * doPost wordt verwerkt als de gebruiker een formulier
   * heeft aangeboden. 
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String forward = "";
    String bron = request.getParameter("bron");
    HttpSession session = getSession(request);
    
    if (bron.equals("join")) { // join-email-list formulier ingevuld
      forward = "/AddUserServlet";
    }
    else if (bron.equals("login")) {
      forward = "/LoginServlet";
    }
    // Controleer bij remove of de gebruiker inderdaad is ingelogd
    else if (bron.equals("remove") && (Boolean)session.getAttribute("ingelogd")) {
      forward = "/RemoveUserServlet";   
    }
    else {
      forward = "/unknown_bron.jsp";
    }
    
    RequestDispatcher dispatcher = 
      getServletContext().getRequestDispatcher(forward);
    dispatcher.forward(request, response);
  }
  
  private HttpSession getSession(HttpServletRequest request) {
    HttpSession session = request.getSession();
          
    if (session.getAttribute("userList") == null) {
 //     session.setAttribute("userList", userList);
 //     session.setAttribute("admin", admin);
      session.setAttribute("ingelogd", false);
    } 
    return session;
  }

}
