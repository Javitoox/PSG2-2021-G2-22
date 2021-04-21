<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<petclinic:layout pageName="causes">
    <h2><fmt:message key="label.causes"/></h2>

    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>

            <th><fmt:message key="label.causes.name"/></th>
            <th><fmt:message key="label.causes.goal"/></th>
            <th><fmt:message key="label.causes.donations"/></th>
            <th>
                <div id="donation-state">
            		<fmt:message key="label.causes.state"/>
            	</div>
            	<div id="donation-donate">
            		<fmt:message key="label.causes.donate"/>
            	</div>
            </th>

        </tr>
        </thead>

        <tbody>
        <c:forEach items="${causes}" var="cause">
            <tr>
                <td>
                    <c:out value="${cause.name}"/>
                </td>
                <td>
                    <c:out value="${cause.goal}"/>
                </td>
                <td>
                    <c:out value="${cause.donations}"/>
                </td>
                <td>
                    <jstl:if test="${cause.donations < cause.goal}">
						<button id="button-${cause.id}" type="button" class="btn btn-success" onclick="display_amount(this)">
						<fmt:message key="label.causes.donate-act"/></button>
						<form class="form-amount" id="form-${cause.id}" action="/" method="get">
						    <input type="text" id="amount" name="amount"/>
						    <button type="submit" class="btn btn-default"><fmt:message key="label.causes.send"/></button>
					    </form>
					    <div>
					    	<a class="glyphicon glyphicon-remove-circle close-amount" id="close-${cause.id}" onclick="close_amount()"></a>
					    </div>
					</jstl:if>
					<jstl:if test="${cause.donations >= cause.goal}">
						<button type="button" class="btn btn-danger"><fmt:message key="label.causes.closed"/></button>
					</jstl:if>
                </td>
            </tr>
		</c:forEach>
		</tbody>
    </table>

		<a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'><fmt:message key="label.causes.add"/></a>
		
	<script src="/resources/js/donation.js"></script>
</petclinic:layout>