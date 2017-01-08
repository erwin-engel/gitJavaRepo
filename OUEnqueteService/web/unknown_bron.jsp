<%@ include file="includes/begintags.jsp"%>
<html>
<%@ include file="includes/head.jsp"%>
<body>
<%@ include file="includes/kop.jsp"%>  
<p>
Er is een systeemfout opgetreden waardoor het verzoek niet kon worden
uitgevoerd. Ten behoeve van het systeembeheer volgen hier enkele details.
</p>
<p>
Bron = <%= request.getParameter("bron") %><br>
Method = <%= request.getMethod() %><br>
</p>
<p><%@ include file="includes/voettekst.jsp"%></p>
</body>
</html>