<%@ page import="java.io.PrintWriter" %><%-- Вывод тела таблицы --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
        pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" context="text/html; charset=UTF-8">
    <title>Список книг</title>
</head>
<body>
    <h1>
<%--        Код запроса <%=session.getAttribute("want")%>--%>
        <%
//            String link = (String) request.getSession().getAttribute("image");
            String link = "web/places/tryimageio";
            System.out.println("PATH: " + request.getContextPath() + " Image: " + link);

        %>
    </h1>

<%--    <img src="../bin/arcantown/places/tryimageio" height="255" width="255" alt="Error">--%>
    <img src="<%=link%>" height="255" width="255" alt="Error2">
<%--<%@include file="ErrorManager.jsp"%>--%>
</body>
</html>