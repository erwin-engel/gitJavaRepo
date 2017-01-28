<%-- 
    Document   : rekenform
    Created on : Jan 23, 2017, 12:34:22 PM
    Author     : erwin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>reken form</title>
    </head>
    <body>
        <h1>Voer twee gehele getallen in</h1>
        <s:form beanclass="reken.action.RekenActionBean">
            <table cellspacing="5" border="0">
                <tr>
                  <td align="right">Getal1:</td>
                  <td><s:text name="reken.getal1"/></td>
                </tr>
                <tr>
                  <td align="right">Getal2:</td>
                  <td><s:text name="reken.getal2"/></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><s:submit name="optellen" value="Bereken Som"/></td>
                </tr> 
            </table>        
        </s:form>
    </body>
</html>
