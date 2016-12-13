<%@ page import="model.*" %>
<%
    BoterKaasEierenSpel boKaEiSpel = (BoterKaasEierenSpel) session.getAttribute("boKaEiSpel");
    int userScore = (Integer) session.getAttribute("userScore");
    int autoScore = (Integer) session.getAttribute("autoScore");
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
        <div id="speelbord">
            <table cellspacing="5" cellpadding="5" border="1">
                <% for (int i = 0; i < 3; i++) { %>
                <tr>
                    <%  int addToRowIndex = 0;
                        if (i == 1) {
                            addToRowIndex = 2;
                        }
                        if (i == 2) {
                            addToRowIndex = 4;
                        }
                        for (int j = 0; j < 3; j++) {
                            if (boKaEiSpel.getMarkering(i + j + 
                               addToRowIndex) == Markering.KRUIS) { %>
                    <td><label>X</label></td>
                    <% } else if (boKaEiSpel.getMarkering(i + j + 
                            addToRowIndex) == Markering.NUL) {  %>
                    <td><label>O</label></td>
                    <% } else {%>
                    <td>
                        <form action="<%= response.encodeURL("ControllerServlet")%>" method="post">
                            <input type="hidden" name="bron" value="bo_ka_ei">
                            <input type="hidden" name="spelerZetIndex" value="<%= i + j + addToRowIndex%>">                     
                            <input type="submit" 
                                   <% if (boKaEiSpel.getStatus() == Status.BEZIG) { %>
                                   value="kies">
                            <% } else { %>
                            value="">
                            <% } %>
                        </form>
                    </td>
                    <% } %>
                    <% } %>
                </tr>
                <% } %>
            </table>
        </div>
        <div id="controls">
            <p>speler score = <%= userScore%> </p> 
            <p>computer score = <%= autoScore%> </p>
        </div>
        <div id="controls">
            <form action="<%= response.encodeURL("ControllerServlet")%>" method="get"> 
                Selecteer een speelwijze:<br>
                <select name="slim">
                    <option value="true" 
                            <% if (boKaEiSpel.isBedenkSlim()) { %> 
                            selected="selected" 
                            <% } %>
                            >Computer speelt slim
                    <option value="false"
                            <% if (!boKaEiSpel.isBedenkSlim()) { %>
                            selected="selected"
                            <% } %>
                            >Computer speelt dom
                </select><br>
                <input type="hidden" name="bron" value="bo_ka_ei"><br>
                <p><input type="submit" value="Nieuw spel"></p>
            </form>
        </div>
        <div id="status">
            <% if (boKaEiSpel.getStatus() == Status.BEZIG) { %>
            <p>doe een zet </p>
            <% }%>
            <% if (boKaEiSpel.getStatus() == Status.KRUISWINT) { %>
            <p>je hebt gewonnen!</p>
            <% }%>
            <% if (boKaEiSpel.getStatus() == Status.NULWINT) { %>
            <p>Kaas wint :-(</p>
            <% }%>
            <% if (boKaEiSpel.getStatus() == Status.GELIJK) { %>
            <p>Gelijk spel...</p>
            <% }%>
        </div>
        <div id="voettekst">
            <p>&copy; Copyright Open Universiteit 2009</p>
        </div>
    </body>
</html>