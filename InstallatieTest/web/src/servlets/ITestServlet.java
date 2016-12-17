package servlets;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.data.DBIntermediair;
import model.domein.Rij;

/**
 * Servlet implementation class DBTestServlet
 */
public class ITestServlet extends HttpServlet {
  
  /**
   * Leest de database uit en plaatst de resultaten in een
   * attribuut van de request.
   * De request wordt doorgestuurd naar een pagina db_test 
   * of jstl_test, afhankelijk van wat getest moet worden.
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ArrayList<Rij> data = DBIntermediair.getInstance().getData();
    request.setAttribute("data", data);
    
    String url;
    String test = request.getParameter("test");
    if (test.equals("db")) {
      url = "/db_test.jsp";
    }
    else {
      url = "/jstl_test.jsp";
    }
    
    RequestDispatcher dispatcher = 
      getServletContext().getRequestDispatcher(url);
    dispatcher.forward(request, response);
  }
}
