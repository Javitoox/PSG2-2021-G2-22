<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reservations">
    <h2><fmt:message key="label.reservation"/></h2>

    <table id="reservationsTable" class="table table-striped">
        <thead>
        <tr style = "background-color: #f1f1f1">
            <th><fmt:message key="label.reservationList.startDate"/></th>
            <th><fmt:message key="label.reservationList.endDate"/></th>
            <th><fmt:message key="label.reservationList.specialCares"/></th>
            <th><fmt:message key="label.reservationList.level"/></th>
            <th><fmt:message key="label.reservationList.pet"/></th>
            <th><fmt:message key="label.reservationList.owner"/></th>
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

                    <c:out value="${reservation.pet.name} con identificador:${reservation.pet.id}"/>

                </td>
                <td>
                    <c:out value="${reservation.pet.owner.user.username}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
