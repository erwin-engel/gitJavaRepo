<%@ include file="includes/begintags.jsp"%>
<html>
    <%@ include file="includes/head.jsp"%>
    <body>
        <%@ include file="includes/kop.jsp"%>
        <c:if test="${bron.equals('verzendvraag')}">
            <p id="melding">
                De evaluatie is opgeslagen onder nummer ${enquete.getEnquetenr() }.<br>
                
                De nu volgende link kan naar de cursisten gestuurd worden zodat zij
                de evaluatie kunnen invullen:<br>
                <br>
                    "http://localhost:8080/OUEnqueteservice/ControllerServlet?bron=invullenenquete&enquetenr=${enquete.getEnquetenr()}"<br>
                <br>
            </p>
        </c:if> 
        <h1 id="titel">${enquete.getTitel()}</h1>
        <p id="koptekstEnquete">${enquete.getKoptekst()}</p>
        <form action="<c:url value='/ControllerServlet'/>" 
              <c:choose>
                  <c:when test="${bron.equals('verzendvraag')}">  
                     method="post"
                  </c:when>
                  <c:when test="${bron.equals('invullenenquete')}">  
                     method="get"
                  </c:when>  
              </c:choose> >    
            <c:forEach var="vraag" items="${enquete.getVragenlijst()}"> 
                <p>${vraag.getVraagnummer()} ${vraag.getTekst()}</p>
                <div class="antwoorden">       
                    <p> 
                    <c:if test="${vraag.getVraagtype() == 0}"> 
                        <textarea name="vraag${vraag.getVraagnummer()}" rows="5" cols="40">
                        </textarea>
                    </c:if>
                    <c:if test="${vraag.getVraagtype() == 1}">
                        <c:forEach var="alternatief" items="${vraag.getAlternatieven()}" varStatus="status">
                            <input type="radio" name="vraag${vraag.getVraagnummer()}" value="${status.index}">${alternatief}         
                        </c:forEach>
                    </c:if>
                    <c:if test="${vraag.getVraagtype() == 2}">
                        <select name="vraag${vraag.getVraagnummer()}">
                            <c:forEach var="alternatief" items="${vraag.getAlternatieven()}" varStatus="status">
                                <option value="${status.index}">${alternatief}</option>  
                            </c:forEach>
                        </select>
                    </c:if> 
                    </p>       
                </div>
            </c:forEach>
            <c:if test="${bron.equals('invullenenquete')}">
                <input type="hidden" name="bron" value="ingevuldeenquete">
                <input type="submit" value="versturen">
            </c:if>
        </form>
        <c:if test="${bron.equals('verzendvraag')}">
            <form action="ToonNieuweEnquete.jsp" method="get">
                <input type="submit" value="maak volgende nieuwe evaluatie">
            </form>
        </c:if>
        <div id="voettekst">
            <p><%@ include file="includes/voettekst.jsp"%></p>
        </div>
    </body>
</html>
