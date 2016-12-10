<%@ page import="model.*" %>
<%
    BoterKaasEierenSpel boKaEiSpel = (BoterKaasEierenSpel) session.getAttribute("boKaEiSpel");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="css/stijl.css" rel="stylesheet" type="text/css">
        <title>Boter, kaas en eieren</title>
    </head>
    <body>
        <div id="kop">
            <img src="images/logo.PNG" width="200" height="100">
            <h1>Webapplicaties: de server-kant</h1>
            <h1>Boter, Kaas en Eieren</h1>
        </div>
        
<% if (boKaEiSpel != null) { %>
        <div id="speelbord">
            <table cellspacing="5" cellpadding="5" border="1">
                    <% for (int i = 0; i < 3; i++) { %>
                    <tr>
                        <%  int indexUp = 0;
                            if (i == 1) {
                               indexUp = 2; }
                            if (i == 2) {
                               indexUp = 4; }
                            for (int j = 0; j < 3 ; j++) { %>
                            <% if (boKaEiSpel.getMarkering( i + j + indexUp ) == Markering.KRUIS) { %>
                                <td><label>X</label></td>
                            <% } else if (boKaEiSpel.getMarkering( i +  j + indexUp ) == Markering.NUL) {  %>
                                <td><label>O</label></td>
                            <% } else {  %>
                                <td>
                                    <form action="<%= response.encodeURL("ControllerServlet")%>" method="post">
                                        <input type="hidden" name="bron" value="bo_ka_ei">
                                        <input type="hidden" name="spelerZetIndex" value="<%= i +  j + indexUp %>">                     
                                        <input type="submit" value="kies">
                                    </form>
                                </td>
                                <% } %>
                        <% } %>
                    </tr>
                   <% } %>
            </table>
        </div>
<% }%>
        <div id="controls">
            <form action="<%= response.encodeURL("ControllerServlet")%>" method="get"> 
                Selecteer een speelwijze:<br>
                <select name="slim">
                    <option value="true" selected>Computer speelt slim
                    <option value="false" >Computer speelt dom
                </select><br>
                <input type="hidden" name="bron" value="bo_ka_ei"><br>
                <p><input type="submit" value="Nieuw spel"></p>
            </form>
        </div>

        <div id="status">
            <% if ((Status)session.getAttribute("spelStatus") == Status.BEZIG){ %>
                <p>zet een kruis!</p>
            <% }%>
            <% if ((Status)session.getAttribute("spelStatus") == Status.KRUISWINT){ %>
                <p>je hebt gewonnen!</p>
            <% }%>
            <% if ((Status)session.getAttribute("spelStatus") == Status.NULWINT){ %>
                <p>Kaas wint :-(</p>
            <% }%>
            <% if ((Status)session.getAttribute("spelStatus") == Status.GELIJK){ %>
                <p>Gelijk spel...</p>
            <% }%>
        </div>

        <div id="voettekst">
            <p>&copy; Copyright Open Universiteit 2009</p>
        </div>
    </body>

</html>