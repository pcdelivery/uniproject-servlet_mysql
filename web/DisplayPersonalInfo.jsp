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
        <%=session.getAttribute("message")%>
    </h1>
<%--    <table border='1'>--%>
<%--        <tr>--%>
<%--            <td><b>Название</b></td>--%>
<%--            <td><b>Автор</b></td>--%>
<%--            <td><b>Год публикации</b></td>--%>
<%--            <td><b>Год покупки</b></td>--%>
<%--            <td><b>Прочитал</b></td>--%>
<%--            <td><b>Наличие</b></td>--%>
<%--        </tr>--%>

        <%
//            if (session.getAttribute("user").equals("Иван Алексеевич")) {
//            if (session.getAttribute("user").equals("Иван Алексеевич")) {
            if(false) {
        %>
        <tr>
            <td>Бесы</td>
            <td>Достоевский Ф.М.</td>
            <td>1872</td>
            <td>2011</td>
            <td>Да</td>
            <td>Нет</td>
        </tr>
        <tr>
            <td>Записки сумасшедшего</td>
            <td>Толстой Л.Н.</td>
            <td>1912</td>
            <td>1999</td>
            <td>Да</td>
            <td>Да</td>
        </tr>
        <tr>
            <td>Собачье Сердце</td>
            <td>Булгаков М.А.</td>
            <td>1987</td>
            <td>2019</td>
            <td>Нет</td>
            <td>Да</td>
        </tr>

        <% }
//            else if (session.getAttribute("user").equals("Алексей Иванов")) {
            else if (false) {
        %>

        <tr>
            <td>Тоска</td>
            <td>Чехов А.П.</td>
            <td>1886</td>
            <td>2014</td>
            <td>Нет</td>
            <td>Да</td>
        </tr>
        <tr>
            <td>Герой нашего времени</td>
            <td>Лермонтов М.Ю.</td>
            <td>1840</td>
            <td>2005</td>
            <td>Да</td>
            <td>Да</td>
        </tr>

        <% }
            else if (false) { %>
        <tr>
            <td>Error</td>
            <td>Error</td>
            <td>Error</td>
            <td>Error</td>
            <td>Error</td>
            <td>Error</td>
        </tr>
        <% } %>

    </table>
<%--<%@include file="ErrorManager.jsp"%>--%>
</body>
</html>