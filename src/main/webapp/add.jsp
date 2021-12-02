<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add</title>
</head>
<body>
<h2><a href="">Home</a> </h2>
<h3>Add meal</h3>

<form name="addform" method="post" action="meals" >
    <p>Выберите дату</p>
    <input type="datetime-local" name="datetime" size="40">
    <p>Введите описание</p>
    <input type="text" name="description" size="40">
    <p>Введите количество калорий</p>
    <input type="number" name="calories" size="20">
    <input type="submit" value="Добавить">
</form>

</body>
</html>
