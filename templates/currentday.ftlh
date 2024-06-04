<!DOCTYPE html>
<html>
<head>
    <title>Список пациентов на ${selectedDate}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="mt-5">Список пациентов на ${selectedDate}</h1>
    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
        <tr>
            <th>Время записи</th>
            <th>Имя</th>
            <th>Дата рождения</th>
            <th>Тип пациента</th>
            <th>Симптомы</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list patients as patient>
            <tr>
                <td>${patient.time}</td>
                <td>${patient.name}</td>
                <td>${patient.dob}</td>
                <td>${patient.type}</td>
                <td>${patient.symptoms}</td>
                <td>
                    <form method="post" action="/delete">
                        <input type="hidden" name="name" value="${patient.name}">
                        <input type="hidden" name="dob" value="${patient.dob}">
                        <input type="hidden" name="selectedDate" value="${selectedDate}">
                        <button type="submit" class="btn btn-danger">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

    <a href="/register" class="btn btn-primary">Регистрация нового пациента</a>
    <a href="/" class="btn btn-primary">На главную</a>

</div>
</body>
</html>
