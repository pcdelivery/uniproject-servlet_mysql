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
            <td>Страница с ошибкой ("NULL_ERROR")</td>
            <td>null (пустое поле аргументов)</td>
        </tr>
        <tr>
            <td>Список специальностей в БД</td>
            <td>want=specs</td>
        </tr>
        <tr>
            <td>Список стран в БД</td>
            <td>want=countries</td>
        </tr>
        <tr>
            <td>Список городов страны</td>
            <td>want=towns&country=[название_страны]</td>
        </tr>
        <tr>
            <td>Список всех мест</td>
            <td>want=places&country=[название_страны]&town=[название_города]</td>
        </tr>
        <tr>
            <td>Информация об аккаунте</td>
            <td>want=account&email=[элпочта]</td>
        </tr>
        <tr>
            <td>Создание нового аккаунта</td>
            <td>want=put_acc&auth_type=[def/google/facebook]&email=[элпочта]&login=[логин]</td>
        </tr>
        <tr>
            <td>Викторина</td>
            <td>want=quiz&placeid=[номер_места]</td>
        </tr>
        <tr>
            <td>Обновление одного поля существующего аккаунта</td>
            <td>want=change&email=[эл.почта]&field=[поле]&value=[новое_значение]</td>
        </tr>
        <tr>
            <td>Обновление очков и истории аккаунта (завершение теста)</td>
            <td>want=update_quiz&email=[эл.почта]&placeid=[номер_места]&points=[число_полученных _очков]</td>
        </tr>

        <tr>
            <td>Отправка картинки</td>
            <td>sendpics (В разработке...)</td>
        </tr>
    </table>
  </body>
</html>
