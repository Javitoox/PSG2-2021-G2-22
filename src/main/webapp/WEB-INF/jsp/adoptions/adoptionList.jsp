<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <h2><fmt:message key="label.adoption"/></h2>

    <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr style = "background-color: #f1f1f1">

            <th><fmt:message key="label.adoptionList.owner"/></th>
            <th><fmt:message key="label.adoptionList.description"/></th>
            <th><fmt:message key="label.adoptionList.pet"/></th>

          <%--  <th>Actions</th> --%>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adopciones}" var="adopcion">
            <tr>
                <td>
                    <c:out value="${adopcion.owner}"/>
                </td>
                <td>
                    <c:out value="${adopcion.description}"/>
                </td>
                <td>

                    <c:out value="${adopcion.pet.name} con identificador:${adopcion.pet.id}"/>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>