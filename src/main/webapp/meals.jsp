<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table>
    <tr>
        <th width="120">Дата</th>
        <th width="120">Время</th>
        <th width="120">Вид Трапезы</th>
        <th width="120">Калории</th>
    </tr>

    <c:forEach items="${meals}" var="meal">
        <tr>
            <td>${meal.getDate()}</td>
            <td>${meal.getTime()}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
</table

</body>
</html>
