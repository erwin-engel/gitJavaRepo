<%@ include file="includes/begintags.jsp"%>
<html>
<%@ include file="includes/head.jsp"%>
<body>
<%@ include file="includes/kop.jsp"%>    

<!-- 
Met dit formulier wordt een vraag ingevoerd. Het formulier toont velden voor
de vraagtekst, het type van de vraag en voor tien alternatieven. Deze
applicatie wint veel bij gebruik van client-side technieken.
Met Ajax kunnen de velden voor de alternatieven getoond / verborgen 
worden bij een wijziging van het vraagtype, zodat er niet meer
alternatieven ingevoerd kunnen worden dan toegestaan.
Met JavaScript zou gecontroleerd kunnen worden of de ingevoerde tekst de
maximale lengte niet overschrijdt.
Als een vraag verkeerd is ingevoerd, wordt de vorige invoer getoond.
 -->

<h2>Vraag ${enquete.getAantalVragen() + 1 }</h2>
<c:if test="${errorMessage != null}">
    <p id="melding">
      ${errorMessage}
    </p>
</c:if> 
<form action="<c:url value='/ControllerServlet?bron=verzendvraag' />" method="post">
<table border="0" cellpadding="5">
  <tr>
    <td valign="top">Tekst:</td>
    <td><textarea name="tekst" rows="4" cols="30"></textarea></td>
  </tr>
  <tr>

    <td>Vraagtype:</td>
    <td><select name="vraagtype">
          <option value="0">Open vraag</option>
          <option value="1">Meerkeuze, radio buttons</option>
          <option value="2">Meerkeuze, afrollijst</option>
        </select>
    </td>

  </tr>
  <tr>
  <td valign="top">Alternatieven</td> 
  <td>

      1. <input type="text" name="alt" 
                           size="55" value=""><br><br>
      2. <input type="text" name="alt" 
                           size="55" value=""><br><br>
      3. <input type="text" name="alt" 
                           size="55" value=""><br><br>
      4. <input type="text" name="alt" 
                           size="55" value=""><br><br>
      5. <input type="text" name="alt" 
                           size="55" value=""><br><br>
   
  </td> 
  <td>
      6. <input type="text" name="alt" 
                           size="55" value=""><br><br>
      7. <input type="text" name="alt" 
                           size="55" value=""><br><br>
      8. <input type="text" name="alt" 
                           size="55" value=""><br><br>
      9. <input type="text" name="alt" 
                           size="55" value=""><br><br>
      10. <input type="text" name="alt" 
                           size="55" value=""><br><br>   
  </td> 
  </tr>
  <tr>
    <td><input type="checkbox" name="laatstevraag">Dit was de laatste vraag</td>
    <td><input type="submit" value="Verzend vraag"></td>
  </tr>
</table>
</form>

<div id="voettekst">
<p>Open Universiteit 2009</p>
</div>
</body>
</html>