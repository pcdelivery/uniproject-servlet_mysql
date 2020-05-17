<%@ page import="java.util.Date" %>
<%@ page import="java.net.URLDecoder" %><%-- Обработка исключения --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
        pageEncoding="UTF-8" isErrorPage="true" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Информация о пользователе</title>
    </head>
    <body>
    <h1>Информация о пользователе</h1>
    <%
        request.setCharacterEncoding("UTF-8");
        String userName = null;     // request.getAttribute("user").toString();
        String colorName = null;    // request.getAttribute("color").toString();

        Cookie [] cookies = request.getCookies();

        for (int i = 0; i < cookies.length; i++) {
            if ("user".equals(cookies[i].getName()))
                userName = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
            if ("color".equals(cookies[i].getName()))
                colorName = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
        }

        Date dt = (Date) request.getSession().getAttribute("lastDate");
        String sessionCount = request.getSession().getAttribute("requestNum").toString();
    %>
    <font color="<%=colorName%>">
        <p><b>Имя пользователя:</b> <%=userName%></p>
        <p><b>Число запросов за сессию:</b> <%=sessionCount%></p>
        <p><b>Время последнего запроса:</b> <%=dt%></p>
        <p><b>Текущий цвет:</b> <%=colorName%></p>
    </font>
    </body>
</html>
