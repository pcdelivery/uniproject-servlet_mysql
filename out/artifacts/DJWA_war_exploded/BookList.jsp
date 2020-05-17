<%@ page import="java.util.Date" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.io.PrintWriter" %>
<%--
  Created by IntelliJ IDEA.
  User: dan
  Date: 14.04.2020
  Time: 0:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" errorPage="/ErrorManager.jsp" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Список книг</title>
</head>
<body>
    <%
        Cookie [] cookies = request.getCookies();
        String userName = null;
        String colorName = null;

        if(cookies != null)
            for(int i = 0; i < cookies.length; i++) {
                if ("user".equals(cookies[i].getName())) {
                    userName = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
                }
                if ("color".equals(cookies[i].getName())) {
                    colorName = cookies[i].getValue();
                }
                if (userName != null &&  colorName != null)
                    break;
            }

        if (userName == null) {
            System.out.println("[username-cookies-not-found]");
            userName = "";
        }
        else
            System.out.println("[username-cookies-detected]");
        if (colorName == null) {
            System.out.println("[color-cookies-not-found]");
            colorName = "";
        }
        else
            System.out.println("[color-cookies-detected]");
    %>
    <form METHOD=GET action="MyServlet">
        Введите имя пользователя <br>
        <INPUT type=TEXT name="user" value="<%=userName%>"> <br>
        Введите цвет для отображения<br>
        <INPUT TYPE=TEXT name="color" value="<%=colorName%>"> <br>
        <INPUT TYPE=SUBMIT value="Ввод">
    </form>
<%--    <%--%>
<%--        request.setCharacterEncoding("UTF-8");--%>
<%--        Date date = new Date(request.getSession().getCreationTime());--%>
<%--//        String pageToDisplay = "null";--%>

<%--        String name = request.getParameter("name");--%>
<%--        if (name == null)--%>
<%--            throw new IllegalArgumentException("Name expected");--%>

<%--        session.setAttribute("userName", name);--%>
<%--    %>--%>
<%--    <h2>Список книг читателя <%=name%> на момент - <%=date.toString()%> - </h2>--%>
<%--    <p><%@include file="DisplayPersonalInfo.jsp"%></p>--%>
</body>
</html>
