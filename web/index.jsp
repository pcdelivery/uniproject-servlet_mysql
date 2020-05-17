<%--
  Created by IntelliJ IDEA.
  User: dan
  Date: 14.04.2020
  Time: 2:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Hello there!</title>
  </head>
  <body>
    <h1>Интерфейс для сообщения с сервером</h1>
    <table border="1">
        <tr>
            <td><b>Назначение</b></td>
            <td><b>Команда</b></td>
        </tr>
        <tr>
            <td>Страница с ошибкой</td>
            <td>null (пустое поле аргументов)</td>
        </tr>
        <tr>
            <td>Список городов страны</td>
            <td>want=towns&country=[название_страны]</td>
        </tr>
        <tr>
            <td>Подтверждение аутентификации</td>
            <td>want=auth&login=[логин]&email=[элпочта]&password=[пароль]</td>
        </tr>
        <tr>
            <td>Список стран в базе данных</td>
            <td>want=countries</td>
        </tr>
        <tr>
            <td>Список мест в текущем городе</td>
            <td>want=places&town=[город]</td>
        </tr>
    </table>
  </body>
</html>
