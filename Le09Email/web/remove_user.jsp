<%@ include file="includes/koptekst.jsp" %>
<%
  if (session.getAttribute("ingelogd") == null 
      || !(Boolean)session.getAttribute("ingelogd")) {  
%>
    <h1>You are not allowed to directly request this page</h1>
<%
    return;
  }
%>
<h1>Remove a user from the list</h1>
<p>Enter first and last name of the person to be removed
<br>
Then, click on the Submit button.</p>
<% 
  String errorMessage = (String)request.getAttribute("errorMessage");
  if (errorMessage != null && !errorMessage.equals("")) {
%>
    <p id="errorMessage">
      <%= errorMessage %>
    </p>
<%
  }
%>
<form action="<%=response.encodeURL("ControllerServlet?bron=remove")%>" method="post">
<table cellspacing="5" border="0">
  <tr>
    <td align="right"><label for="firstName">First name:</label></td>
    <td><input type="text" name="firstName"></td>
  </tr>
  <tr>
    <td align="right"><label for="lastname">Last name:</label></td>
    <td><input type="text" name="lastName"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" value="Submit"></td>
  </tr>
</table>
</form>
<%@ include file="includes/voettekst.html" %>