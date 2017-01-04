<%@ include file="includes/begintags.jsp"%>
<html>
<%@ include file="includes/head.jsp"%>
<body>
<%@ include file="includes/kop.jsp"%>
<form action="<c:url value='/ControllerServlet?bron=nieuweenquete' />" method="post">
<p id="melding">${errorMessage}</p>
<table border="0" cellpadding="5">
  <tr>
    <td>Titel:</td>
    <td><input type="text" name="titel" size="50" value=""></td>
  </tr>
  <tr>
    <td valign="top">Koptekst:</td>
    <td><textarea name="koptekst" rows="6" cols="50"></textarea></td>
  </tr>
  <tr>
    <td><input type="submit" value="Maak enquete"></td>
  </tr>
</table>
</form>

<div id="voettekst">
<p>Open Universiteit 2009</p>
</div>
</body>
</html>