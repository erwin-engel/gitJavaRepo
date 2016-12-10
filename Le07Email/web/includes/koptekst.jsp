<%-- 
    Document   : koptekst
    Created on : 5-dec-2016, 17:10:38
    Author     : erwin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/stijl.css" rel="stylesheet" type="text/css">
<title>Le06Email</title>
</head>
<body>

<div id="kop">
<img src="images/logo.PNG" width="200" height="100">
<h1>Webapplicaties: de serverkant</h1>

<div id="menu">
<ul>
<li><a href="<%= response.encodeURL("ControllerServlet?bron=join")%>">Join list</a></li>
<li><a href="<%= response.encodeURL("ControllerServlet?bron=show")%>">Show users</a></li>
<li><a href="<%= response.encodeURL("ControllerServlet?bron=remove")%>">Remove user</a></li>
</ul>
</div>

</div>
