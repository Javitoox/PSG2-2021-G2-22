<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
    <h2><fmt:message key="label.causes"/></h2>

    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>

            <th><fmt:message key="label.causes.name"/></th>
            <th><fmt:message key="label.causes.description"/></th>
            <th><fmt:message key="label.causes.organization"/></th>
            <th><fmt:message key="label.causes.goal"/></th>

        </tr>
        </thead>

        <tbody>
        <c:forEach items="${causes}" var="cause">
            <tr>
                <td>
                    <c:out value="${cause.name}"/>
                </td>
                <td>
                    <c:out value="${cause.description}"/>
                </td>
                <td>
                    <c:out value="${cause.organization}"/>
                </td>
                <td>
                    <c:out value="${cause.goal}"/>
                </td> 
            </tr>
		</c:forEach>
		</tbody>
    </table>

		<a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'><fmt:message key="label.causes.add"/></a>
</petclinic:layout>