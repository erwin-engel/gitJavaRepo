<%@ include file="includes/koptekst.jsp" %>

<h1>Join our email list</h1>
<p>To join our email list, enter your name and email address below.
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
<form action="<%=response.encodeURL("ControllerServlet?bron=join")%>" method="post">
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
    <td align="right"><label for="emailAddress">Email
    address:</label></td>
    <td><input type="text" name="emailAddress"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" value="Submit"></td>
  </tr>
</table>
</form>

<%@ include file="includes/voettekst.html" %>