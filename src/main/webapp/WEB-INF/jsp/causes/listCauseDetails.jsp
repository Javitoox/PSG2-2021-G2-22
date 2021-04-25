<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<petclinic:layout pageName="causes">
    <h2>Causa '${cause.name}'</h2>

    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>

            <th><fmt:message key="label.causes.description"/></th>
            <th><fmt:message key="label.causes.organization"/></th>
            <th><fmt:message key="label.causes.goal"/></th>
            <th><fmt:message key="label.causes.donations"/></th>
            <th><fmt:message key="label.causes.owner"/></th>
            <th><fmt:message key="label.causes.state"/></th>

        </tr>
        </thead>

        <tbody>
        <tr>
            <td>
                <c:out value="${cause.description}"/>
            </td>
            <td>
                <c:out value="${cause.organization}"/>
            </td>
            <td>
                <c:out value="${cause.goal}"/>
            </td>
            <td>
                <c:out value="${cause.donations}"/>
            </td>
            <td>
                <c:out value="${cause.owner.user.username}"/>
            </td>
            <td>
	            <jstl:if test="${cause.donations < cause.goal}">
					<button type="button" class="btn btn-success">
						<fmt:message key="label.donations.open"/>
					</button>
				</jstl:if>
				<jstl:if test="${cause.donations >= cause.goal}">
					<button type="button" class="btn btn-danger">
						<fmt:message key="label.donations.closed"/>
					</button>
				</jstl:if>
            </td> 
        </tr>
		</tbody>
    </table>
    
    <h2><fmt:message key="label.donations"/></h2>
    
    <table id="donationsTable" class="table table-striped">
        <thead>
        <tr>

            <th><fmt:message key="label.donations.date"/></th>
            <th><fmt:message key="label.donations.client"/></th>
            <th><fmt:message key="label.donations.amount"/></th>

        </tr>
        </thead>

        <tbody>	
        <c:forEach items="${cause.totalDonations}" var="donation">
            <tr>
                <td>
	                <c:out value="${donation.date}"/>
	            </td>
	            <td>
	                <c:out value="${donation.owner.user.username}"/>
	            </td>
	            <td>
	                <c:out value="${donation.amount}"/>
	            </td>
            </tr>
		</c:forEach>
		</tbody>
    </table>

</petclinic:layout>