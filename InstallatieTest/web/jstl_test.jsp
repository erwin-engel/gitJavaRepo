<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test JSTL-installatie</title>
</head>
<body>
  <h1>Test voor JSTL installatie</h1>
  <table cellspacing="5" cellpadding="5" border="1"> 
    <c:forEach var="d" items="${data}"> 
      <tr>
        <td>${d.sleutel}</td>
        <td>${d.waarde}</td>
      </tr> 
    </c:forEach>
  </table>
</body>
</html>