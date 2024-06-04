<!DOCTYPE html>
<html>
<head>
    <title>Запись нового пациента</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="mt-5">Запись нового пациента</h1>
    <form method="post" action="/register">
        <div class="form-group">
            <label for="name">Имя:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="dob">Дата рождения:</label>
            <input type="date" class="form-control" id="dob" name="dob" required>
        </div>
        <div class="form-group">
            <label for="type">Тип пациента:</label>
            <select class="form-control" id="type" name="type" required>
                <option value="Первичный">Первичный</option>
                <option value="Вторичный">Вторичный</option>
            </select>
        </div>
        <div class="form-group">
            <label for="symptoms">Симптомы:</label>
            <textarea class="form-control" id="symptoms" name="symptoms" rows="3" required></textarea>
        </div>
        <div class="form-group">
            <label for="time">Время записи:</label>
            <input type="time" class="form-control" id="time" name="time" required>
        </div>
        <div class="form-group">
            <label for="selectedDate">Дата записи:</label>
            <input type="date" class="form-control" id="selectedDate" name="selectedDate" required>
        </div>
        <button type="submit" class="btn btn-primary">Записать</button>
        <a href="/" class="btn btn-primary">На главную</a>

    </form>
</div>
</body>
</html>
