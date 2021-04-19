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
            <th><fmt:message key="label.adoptionList.petType"/></th>
            <th><fmt:message key="label.adoptionList.pet"/></th>

          <%--  <th>Actions</th> --%>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pet">
            <tr>
                <td>
                    <c:out value="${pet.owner.firstName}"/>
                </td>
                 <td>
                    <c:out value="${pet.type}"/>
                </td>
                <td>
                    <c:out value="${pet.name} con identificador:${pet.id}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>