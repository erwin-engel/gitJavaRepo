<%@ include file="includes/begintags.jsp"%>
<html>
    <%@ include file="includes/head.jsp"%>
    <body>
        <%@ include file="includes/kop.jsp"%>
        <c:if test="${bron.equals('verzendvraag')}">
            <p id="melding">
                Uw enquete is opgeslagen onder nummer ${enquete.getEnquetenr() }.<br>
                Onthoud dit nummer goed; u heeft het nodig om uw enquete naar de
                doelgroep te versturen en om later de resultaten te bekijken.
            </p>
        </c:if> 
        <h1 id="titel">${enquete.getTitel()}</h1>
        <p id="koptekstEnquete">${enquete.getKoptekst()}</p>
        <p></p><p></p>
        <form action="<c:url value='/ControllerServlet' />" method="get">
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
        <div id="voettekst">
            <p>Open Universiteit 2009</p>
        </div>
    </body>
</html>
