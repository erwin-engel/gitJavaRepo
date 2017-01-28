<%-- 
    Document   : som
    Created on : Jan 23, 2017, 12:35:01 PM
    Author     : erwin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Som</title>
    </head>
    <body>
        <s:form beanclass="reken.action.RekenActionBean">
            <p> ${actionBean.reken.getal1} + ${actionBean.reken.getal2} = ${actionBean.reken.som}</p>
            <s:submit name="toonRekenForm" value="Nieuwe Som"/>
        </s:form>    
        </body>
        <br>
        <s:link beanclass="reken.action.RekenActionBean">terug</s:link>
        
</html>
