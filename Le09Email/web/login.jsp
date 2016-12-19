<%@ include file="includes/koptekst.jsp" %>
<h1>Login</h1>
<p>This option requires administrator login</p>
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
<form action="<%=response.encodeURL("ControllerServlet?bron=login")%>" method="post">
<table cellspacing="5" border="0">
  <tr>
    <td align="right"><label for="userName">User name:</label></td>
    <td><input type="text" name="userName"></td>
  </tr>
  <tr>
    <td align="right"><label for="password">Password:</label></td>
    <td><input type="password" name="password"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" value="Submit"></td>
  </tr>
</table>
</form>
<%@ include file="includes/voettekst.html" %>