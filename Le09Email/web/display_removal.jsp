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
<%
  String firstName = request.getParameter("firstName");
  String lastName = request.getParameter("lastName");
  boolean removed = (Boolean)request.getAttribute("removed");
  if (removed) {
%>
    <h1>User <%= firstName %> <%= lastName %> has been succesfully removed.</h1>
<%
  } else {
%>
    <h1>User <%= firstName %> <%= lastName %> was not found in the list </h1>
<%
  }
%>
 
<%@ include file="includes/voettekst.html" %>