<!DOCTYPE html>
<html>
<head>
    <title>Расписание врача</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .primary-patient {
            font-weight: bold;
            color: green;
        }

        .secondary-patient {
            font-weight: bold;
            color: cadetblue;
        }

        .today {
            background-color: black;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="mt-5">Расписание врача на текущий месяц <a href="/" class="btn btn-primary">На главную</a>
    </h1>
    <p>Текущий день: ${currentDate}</p>
    <div class="table-responsive">
        <table class="table table-bordered table-striped">
            <thead class="thead-dark">
            <tr>
                <th>Дата</th>
                <th>Время</th>
                <th>Пациенты</th>
            </tr>
            </thead>
            <tbody>
            <#list scheduleEntries as entry>
                <tr <#if entry.date == currentDate>class="today"</#if>>
                    <td>
                        <a href="/schedule/thisday?date=${entry.date}">${entry.date}</a>
                    </td>
                    <td>
                        <#list entry.patients as patient>
                            ${patient.time}<br>
                        </#list>
                    </td>
                    <td>
                        <ul>
                            <#list entry.patients as patient>
                                <#if patient.type == "Первичный">
                                    <li><span class="primary-patient">${patient.name} (${patient.type})</span></li>
                                <#elseif patient.type == "Вторичный">
                                    <li><span class="secondary-patient">${patient.name} (${patient.type})</span></li>
                                </#if>
                            </#list>
                        </ul>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
