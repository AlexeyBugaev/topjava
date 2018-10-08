<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        table {
            text-align: center;
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        td{
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
        }

        .red {
            color: #FF0000;
        }

        .green {
            color: #008000;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table>
    <tr>
        <th width="120">Дата</th>
        <th width="120">Время</th>
        <th width="120">Описание</th>
        <th width="120">Калории</th>
    </tr>

    <c:forEach items="${meals}" var="meal">
        <tr>
            <td>${meal.getDate()}</td>
            <td>${meal.getTime()}</td>
            <td>${meal.getDescription()}</td>
            <c:if test="${meal.isExceed()}">
            <td class="red">${meal.getCalories()}</td>
            </c:if>
            <c:if test="${!meal.isExceed()}">
            <td class="green">${meal.getCalories()}</td>
            </c:if>
        </tr>
    </c:forEach>
</table

</body>
</html>
