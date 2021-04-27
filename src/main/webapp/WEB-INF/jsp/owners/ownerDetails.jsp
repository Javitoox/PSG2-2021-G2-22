<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="owners">

    <h2><fmt:message key="label.ownersDetails.ownerInformation"/></h2>


    <table class="table table-striped">
        <tr>
            <th><fmt:message key="label.ownersDetails.name"/></th>
            <td><b><c:out value="${owner.firstName} ${owner.lastName}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="label.ownersDetails.address"/></th>
            <td><c:out value="${owner.address}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="label.ownersDetails.city"/></th>
            <td><c:out value="${owner.city}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="label.ownersDetails.telephone"/></th>
            <td><c:out value="${owner.telephone}"/></td>
        </tr>
    </table>
    
    <h2><fmt:message key="label.ownerDetails.petsInfo"/></h2>
    
    <table class="table table-striped">
        <c:forEach var="pet" items="${owner.pets}">
            <tr>

                <td><c:out value="${pet.name} con identificador:${pet.id}"/></td>

            </tr>
        </c:forEach>
    </table>

    <spring:url value="{ownerId}/edit" var="editUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default"><fmt:message key="label.ownersDetails.editOwner"/></a>

    <spring:url value="{ownerId}/pets/new" var="addUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default"><fmt:message key="label.ownersDetails.addPet"/></a>

    <br/>
    <br/>
    <br/>
    <h2><fmt:message key="label.ownersDetails.petsVisits"/></h2>

    <table class="table table-striped">
        <c:forEach var="pet" items="${owner.pets}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt><fmt:message key="label.ownersDetails.name"/></dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt><fmt:message key="label.ownersDetails.birthDay"/></dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt><fmt:message key="label.ownersDetails.type"/></dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>

                            <th><fmt:message key="label.ownersDetails.birthDay"/></th>
                            <th><fmt:message key="label.ownersDetails.description"/></th>
                            <th><fmt:message key="label.ownersDetails.action"/></th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${visit.description}"/></td>
                                <td><spring:url value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/delete" var="deleteVisitUrl">
                					<spring:param name="ownerId" value="${owner.id}"/>
                        			<spring:param name="petId" value="${pet.id}"/>
                        			<spring:param name="visitId" value="${visit.id}"/>
                        			
                    			</spring:url>
                    			<a href="${fn:escapeXml(deleteVisitUrl)}" class="glyphicon glyphicon-remove-circle"></a></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}"><fmt:message key="label.ownersDetails.editPet"/></a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitUrl)}"><fmt:message key="label.ownersDetails.addVisit"/></a>
                            </td>
                            <td>
                				<spring:url value="/owners/{ownerId}/pets/{petId}/delete" var="deletePetUrl">
                					<spring:param name="ownerId" value="${owner.id}"/>
                        			<spring:param name="petId" value="${pet.id}"/>
                    			</spring:url>
                    			<a href="${fn:escapeXml(deletePetUrl)}" class="btn btn-default"><fmt:message key="label.ownersDetails.deletePet"/></a>
                			</td>
                			<td>
                    			<c:if test="${pet.inAdoption !=  true}">                				
                				<spring:url value="/owners/{ownerId}/pets/{petId}/inAdoption" var="inAdoptionPetUrl">
                					<spring:param name="ownerId" value="${owner.id}"/>
                        			<spring:param name="petId" value="${pet.id}"/>
                    			</spring:url>
                    			
                    			<a href="${fn:escapeXml(inAdoptionPetUrl)}" class="btn btn-default"><fmt:message key="label.ownersDetails.inAdoptionPet"/></a>
                			</c:if></td>
                        </tr>
                    </table>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
