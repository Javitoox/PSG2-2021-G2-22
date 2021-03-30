<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="owners">
    <h2><fmt:message key="label.ownersList.owner"/></h2>

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr style = "background-color: #f1f1f1">

            <th style="width: 150px;"><fmt:message key="label.ownersList.name"/></th>
            <th style="width: 200px;"><fmt:message key="label.ownersList.address"/></th>
            <th><fmt:message key="label.ownersList.city"/></th>
            <th style="width: 120px"><fmt:message key="label.ownersList.telephone"/></th>
            <th><fmt:message key="label.ownersList.pets"/></th>
             <th><fmt:message key="label.ownersDetails.action"/></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="owner">
            <tr>
                <td>
                    <spring:url value="/owners/{ownerId}" var="ownerUrl">
                        <spring:param name="ownerId" value="${owner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(ownerUrl)}"><c:out value="${owner.firstName} ${owner.lastName}"/></a>
                </td>
                <td>
                    <c:out value="${owner.address}"/>
                </td>
                <td>
                    <c:out value="${owner.city}"/>
                </td>
                <td>
                    <c:out value="${owner.telephone}"/>
                </td>
                <td>
                    <c:forEach var="pet" items="${owner.pets}">
                        <c:out value="${pet.name} "/>
                    </c:forEach>
                </td>
                <td>
                	<spring:url value="/owners/{ownerId}/deleteOwner" var="deleteOwnerUrl">
                        <spring:param name="ownerId" value="${owner.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteOwnerUrl)}" class="glyphicon glyphicon-remove-circle"></a>
                </td>
                
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
