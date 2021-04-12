<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causas">
    <h2><fmt:message key="label.causas" /></h2>

    <table id="causasTable" class="table table-striped">
        <thead>
        <tr>

            <th>Nombre</th>
            <th>Descripción</th>
            <th>Oranización</th>
            <th>Objetivo</th>

        </tr>
        </thead>

        <tbody>
        <c:forEach items="${causas}" var="causa">
            <tr>
                <td>
                    <c:out value="${causa.name}"/>
                </td>
                <td>
                    <c:out value="${causa.description}"/>
                </td>
                <td>
                    <c:out value="${causa.organization}"/>
                </td>
                <td>
                    <c:out value="${causa.goal}"/>
                </td> 
            </tr>
		</c:forEach>
		</tbody>
    </table>


		<a class="btn btn-default" href='<spring:url value="/causas/new" htmlEscape="true"/>'>Añadir Causa</a>
</petclinic:layout>