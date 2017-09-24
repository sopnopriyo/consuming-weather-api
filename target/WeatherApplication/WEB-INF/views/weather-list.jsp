<%--
  Created by IntelliJ IDEA.
  User: shahin
  Date: 9/22/17
  Time: 12:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Weather</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<div class="container">

    <div class="row">
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-success lead has-error">
                    ${errorMessage}
            </div>
        </c:if>
        <h2>Search weather information by City Name</h2>
        <div id="custom-search-input">
            <form:form method="POST" modelAttribute="city" action="/list" class="form-horizontal">
                <form:input type="hidden" path="cityId" id="id"/>
                <div class="input-group col-md-12">
                    <form:input type="text" path="cityName" class="search-query form-control" placeholder="Enter city name"/>
                    <span class="input-group-btn">
                    <input type="submit" value="Search" class="btn btn-primary"/> or <a href="<c:url value='/' />">Search again</a>
                </span>
                </div>
            </form:form>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <table class="table" id="table">
                <tbody>
                <c:forEach items="${weatherHistories}" var="weather">
                    <tr>
                        <td> <img src="http://icons.iconarchive.com/icons/icons8/ios7/256/Weather-Partly-Cloudy-Day-icon.png"
                                  height="50px" width="50px"></td>
                        <td><c:out value="${weather.city.cityName}" /> <br>
                            <jsp:useBean id="myDate" class="java.util.Date"/>
                            <c:set target="${myDate}" property="time" value="${weather.timestamp*1000}"/>
                            <c:out value="${myDate}" /></td>
                        <c:set var="billableTime"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                                    value="${weather.temperature-273.15}" /></c:set>
                        <td><kbd><c:out value="${billableTime}" /> &deg;C </kbd>  <c:out value="${weather.weatherCondition}" />
                            <br> <c:out value="${weather.windSpeed}" />  m/s. <c:out value="${weather.humidity}" /> % ,
                            <c:out value="${weather.pressure}" /> hpa</td>
                        <td><a href="<c:url value='/delete-weather-record-${weather.cityWeatherRecordId}' />" class="btn btn-danger custom-width">delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <hr>
        </div>
    </div>
</div>
<script type="text/javascript">
    var numShown = 5; // Initial rows shown & index
    var numMore = 5;  // Increment

    var $table = $('table').find('tbody');  // tbody containing all the rows
    var numRows = $table.find('tr').length; // Total # rows

    $(function () {
        // Hide rows and add clickable div
        $table.find('tr:gt(' + (numShown - 1) + ')').hide().end()
            .after('<tbody id="more"><tr><td colspan="' +
                $table.find('tr:first td').length + '"><div>Show More</div></tbody></td></tr>');

        $('#more').click(function() {
            numShown = numShown + numMore;
            // no more "show more" if done
            if (numShown >= numRows) {
                $('#more').remove();
            }
            // change rows remaining if less than increment
            if (numRows - numShown < numMore) {
                $('#more span').html(numRows - numShown);
            }
            $table.find('tr:lt(' + numShown + ')').show();
        });

    });
</script>
</body>

</html>
