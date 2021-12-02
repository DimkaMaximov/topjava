<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<style>
    .red{color: red}
    .green{color: green}
</style>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr style="line-break: normal">
<h2>Meals</h2>

<h3><a href="${pageContext.request.contextPath}/add.jsp">Add meal</a></h3>


<h3>Через JSP + JSTL</h3>
<table border="1" style="border-collapse: collapse">
    <thead>
    <tr>
        <th><b>Date</b></th>
        <th><b>Description</b></th>
        <th><b>Calories</b></th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <% int count = 0; %>
    <c:forEach var="meal" items="${mealsTo}">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${meal.excess ? 'red' : 'green'}">
            <td>${meal.dateTime.toString().replace("T", " ")}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="${pageContext.request.contextPath}/update.jsp?action=update&id=<%=count%>&description=${meal.description}&datetime=${meal.dateTime}&calories=${meal.calories}">Update</a></td>
            <td><a href="${pageContext.request.contextPath}/meals?action=delete&id=<%=count++%>" >Delete</a></td>
        </tr>
    </c:forEach>
</table>


<h3>Через JSP + Java</h3>
    <table border="1" style="border-collapse: collapse">
        <thead>
        <tr>
            <th><b>Date</b></th>
            <th><b>Description</b></th>
            <th><b>Calories</b></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
    <% List<MealTo> mealsTo2 = (List<MealTo>)request.getAttribute("mealsTo");
        for(MealTo mealTo: mealsTo2){
            String color = null;
            if(mealTo.isExcess()) {
                color = "red";
            } else color = "green";
    %>
    <tr style="color:<%= color%>">
        <td><%= mealTo.getDateTime().toString().replace("T", " ") %></td>
        <td><%= mealTo.getDescription() %></td>
        <td><%= mealTo.getCalories() %></td>
        <td><a href="index.html">Update</a></td>
        <td><a href="index.html">Delete</a></td>
    </tr>
    <% }%>

</table>

</body>
</html>