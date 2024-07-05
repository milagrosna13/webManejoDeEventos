<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

</head>
<body>
	<div class="row">
		<form:form action="/editEvent" method="post" modelAttribute="event">
				<div>
					<form:label path="eventName">Event name</form:label>
					<form:input path="eventName" class="form-control"></form:input>
					<form:errors path="eventName" class="text-danger"></form:errors>
				</div>	
				<div>
					<form:label path="eventDate">Event date</form:label>
					<form:input path="eventDate" class="form-control" type="date"></form:input>
					<form:errors path="eventDate" class="text-danger"></form:errors>
				</div>	
				<div>
					<form:label path="eventLocation">Location</form:label>
					<form:input path="eventLocation" class="form-control"></form:input>
					<form:errors path="eventLocation" class="text-danger"></form:errors>
				</div>						
				
				<div>
						<form:label path="eventProvince">Provincia</form:label>
						<form:select path="eventProvince">
						
							<c:forEach items="${provinces}" var="province">
							
								<form:option value="${province}"> ${province}</form:option>
							</c:forEach>
						</form:select>
						
				</div>
				
				<form:hidden value="${userInSession.id}" path="host"></form:hidden>
				<form:hidden path="id" value="${event.id}"/>
				<input type="hidden" value="put" name="_method">
				
				<input type="submit" value="save" class="btn btn-success">	
		</form:form>
	</div>

</body>
</html>