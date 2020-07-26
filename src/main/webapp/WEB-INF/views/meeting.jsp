<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Create new meeting</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/main.css" />" />
    <script src="<c:url value="/resources/script.js"/>"></script>
</head>
<body>
<div class="container-xl">
    <form name="newMeetingForm" method="POST" onsubmit="createJSON()">
        <label style="font-size: 20px;">New Meeting</label>
        <div class="form-group">
            <label for="summary">Event title</label>
            <input name="summary" type="text" class="form-control" id="summary" required>
            <br>
            <label for="location">Location</label>
            <input name="location" type="text" class="form-control" id="location" required>
            <br>
            <label for="description">Description</label>
            <textarea name="description" class="form-control" id="description" rows="5"></textarea>
            <br>
            <label for="selectTimezone">Select your timezone</label>
            <select name="timezone" id="selectTimezone" class="form-control">
                <option selected>+06:00 Asia/Omsk</option>
                <option>+00:00 UTC</option>
            </select>
            <br>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="startDate">Start date</label>
                    <small id="startDateHelp" class="form-text text-muted">Enter data as DD.MM.YYYY</small>
                    <input name="startDate" type="date" class="form-control" id="startDate" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="startTime">Start time</label>
                    <small id="startTimeHelp" class="form-text text-muted">Enter data as HH:MM</small>
                    <input name="startTime" type="time" class="form-control" id="startTime" pattern="(0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){2}" required>
                </div>
            </div>
            <br>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="endDate">End date</label>
                    <small id="endDateHelp" class="form-text text-muted">Enter data as DD.MM.YYYY</small>
                    <input name="endDate" type="date" class="form-control" id="endDate" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="endTime">End time</label>
                    <small id="endTimeHelp" class="form-text text-muted">Enter data as HH:MM</small>
                    <input name="endTime" type="time" class="form-control" id="endTime" pattern="(0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){2}" required>
                </div>
            </div>
            <br>
            <label for="selectReminder">Reminder</label>
            <select name="reminder" id="selectReminder" class="form-control">
                <option>None</option>
                <option selected>At time of event</option>
                <option>5 minutes before event</option>
                <option>15 minutes before event</option>
                <option>30 minutes before event</option>
                <option>1 hour before event</option>
                <option>1 day before event</option>
                <option>1 week before event</option>
                <!--<option>Custom</option>-->
            </select>
        </div>
        <button id="submit" type="submit" class="btn btn-primary">Create ics file</button>
    </form>
</div>
</body>
</html>