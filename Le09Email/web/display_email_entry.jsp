<%@ include file="includes/koptekst.jsp" %>

    <h1>Thanks for joining our email list</h1>

    <p>Here is the information that you entered:</p>

    <table cellspacing="5" cellpadding="5" border="1">
        <tr>
            <td align="right">First name:</td>
            <td><%= request.getParameter("firstName") %></td>
        </tr>
        <tr>
            <td align="right">Last name:</td>
            <td><%= request.getParameter("lastName") %></td>
        </tr>
        <tr>
            <td align="right">Email address:</td>
            <td><%= request.getParameter("emailAddress") %></td>
        </tr>
    </table>

<%@ include file="includes/voettekst.html" %>