<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<petclinic:layout pageName="causes">
	<div class="alert alert-warning alert-dismissable">
	  <button type="button" class="close" data-dismiss="alert">&times;</button>
	  ${result}
	</div>
    <h2><fmt:message key="label.causes"/></h2>

    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>

            <th><fmt:message key="label.causes.name"/></th>
            <th><fmt:message key="label.causes.goal"/></th>
            <th><fmt:message key="label.causes.donations"/></th>
            <th><fmt:message key="label.causes.state"/></th>

        </tr>
        </thead>

        <tbody>	
        <c:forEach items="${causes}" var="cause">
            <tr>
                <td>
                    <spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causeUrl)}"><c:out value="${cause.name}"/></a>
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
							<fmt:message key="label.causes.donate-act"/>
						</button>
						<form:form action="/causes/donate/${cause.id}" modelAttribute="cause" class="form-amount" id="form-${cause.id}">
							<div class="form-group has-feedback">
					       		<petclinic:simpleInput name="donations" />
					       	</div>
					        <div class="form-group">
					            <div class="col-sm-offset-2 col-sm-2">
					            	<button class="btn btn-default" type="submit">
											<fmt:message key="label.causes.send"/>
									</button>
					            </div>
					        </div>
						</form:form>
						<a class="glyphicon glyphicon-remove-circle close-amount col-sm-offset-11" id="close-${cause.id}" onclick="close_amount(this)"></a>
					</jstl:if>
					<jstl:if test="${cause.donations >= cause.goal}">
						<button type="button" class="btn btn-danger">
							<fmt:message key="label.causes.closed"/>
						</button>
					</jstl:if>
                </td>
            </tr>
		</c:forEach>
		</tbody>
    </table>

		<a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'><fmt:message key="label.causes.add"/></a>
		
	<script src="/resources/js/donation.js"></script>
</petclinic:layout>