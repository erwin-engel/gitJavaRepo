<%@page import="java.util.ArrayList"%>
<%@ include file="includes/koptekst.jsp" %>
<%@ page import="model.domein.*" %>

<%
  if (session.getAttribute("ingelogd") == null 
      || !(Boolean)session.getAttribute("ingelogd")) {  
%>
    <h1>You are not allowed to directly request this page</h1>
<%
    return;
  }
%>
<%
  ArrayList<User> userList = (ArrayList)session.getAttribute("userList");
  if (userList.size() == 0) {
%>
    <h1>The mailing list is empty</h1>
<%
  } else {
%>
    <h1>Members of the mailing list</h1>
    <table cellspacing="5" cellpadding="5" border="1">
      <tr>
        <td><i>First name</i></td>
        <td><i>Last name</i></td>
        <td><i>email address</i></td>
      </tr>
<%
      for (User user: userList) {
%>
        <tr>
          <td><%=user.getFirstName() %></td>
          <td><%=user.getLastName() %></td>
          <td><%=user.getEmailAddress() %></td>
        </tr>
<%
      }
%>
    </table>
<%
  }
%>

<%@ include file="includes/voettekst.html" %>