<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reservations">
    <h2>Reservations</h2>

    <table id="reservationsTable" class="table table-striped">
        <thead>
        <tr style = "background-color: #f1f1f1">
            <th>Start date</th>
            <th>End date</th>
            <th>Special cares</th>
            <th>Level</th>
            <th>Pet</th>
            <th>Owner</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reservations}" var="reservation">
            <tr>
                <td>
                    <c:out value="${reservation.start}"/>
                </td>
                <td>
                    <c:out value="${reservation.end}"/>
                </td>
                <td>
                    <c:out value="${reservation.specialCares}"/>
                </td>
                <td>
                    <c:out value="${reservation.level}"/>
                </td>
                <td>
                    <c:out value="${reservation.pet.name} with identifier: ${reservation.pet.id}"/>
                </td>
                <td>
                    <c:out value="${reservation.pet.owner.user.username}"/>
                </td>
                <%-- <td>
                	<spring:url value="/hotel/{reservationId}/delete" var="deleteReservationUrl">
                        <spring:param name="reservationId" value="${reservation.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteReservationUrl)}" class="glyphicon glyphicon-remove-circle"></a>
                </td>
                --%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>