<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<petclinic:layout pageName="vets">

    <h2><fmt:message key="label.vetList.vets"/></h2>


    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>

            <th><fmt:message key="label.vetList.name"/></th>
            <th><fmt:message key="label.vetList.specialties"/></th>
            <th><fmt:message key="label.ownersDetails.action"/></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
             	<td>
                    <spring:url value="/vets/{vetId}/edit" var="vetUrl">
                        <spring:param name="vetId" value="${vet.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vetUrl)}"><c:out value="${vet.firstName} ${vet.lastName}"/></a>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
                <td>
                <spring:url value="/vets/{vetId}/delete" var="deleteVetUrl">
                	<spring:param name="vetId" value="${vet.id}"/>
                </spring:url>
                <sec:authorize access="hasAuthority('admin')">
                <a href="${fn:escapeXml(deleteVetUrl)}" class="glyphicon glyphicon-remove-circle"></a>
                </sec:authorize>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
	
	
    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />"><fmt:message key="label.vetList.xml"/></a>
            </td>            
        </tr>
    </table>
     
    
    <br/>
    <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/vets/new" htmlEscape="true"/>'><fmt:message key="label.vetList.addVet"/></a>
	</sec:authorize>
	
</petclinic:layout>
