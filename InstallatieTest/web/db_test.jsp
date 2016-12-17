<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test database installatie</title>
</head>
<body>
<%@ page import="model.domein.Rij,java.util.ArrayList" %>
  <h1>Test voor database installatie</h1>
  <table cellspacing="5" cellpadding="5" border="1"> 
<%
   ArrayList<Rij> data = (ArrayList<Rij>)request.getAttribute("data");
   for (Rij d: data) {
 %>
    <tr>
      <td><%= d.getSleutel() %></td>
      <td><%= d.getWaarde() %></td>
    </tr> 
<%
  }
%>
  </table>
</body>
</html>