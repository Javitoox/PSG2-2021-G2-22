<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="false" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="required" required="false" rtexprvalue="true"
              description="Required appears if the input is required" %>


<spring:bind path="${name}">
    <div class="${cssGroup}">
        <label class="col-sm-2 control-label">
       		 <c:if test="${label=='First Name'}">
                <fmt:message key="label.inputField.firstName"/>
            </c:if>
            <c:if test="${label=='Last Name'}">
                <fmt:message key="label.inputField.lastName"/>
            </c:if>
            <c:if test="${label=='Address'}">
                <fmt:message key="label.inputField.address"/>
            </c:if>
            <c:if test="${label=='City'}">
                <fmt:message key="label.inputField.city"/>
            </c:if>
            <c:if test="${label=='Telephone'}">
                <fmt:message key="label.inputField.telephone"/>
            </c:if>
            <c:if test="${label=='Username'}">
                <fmt:message key="label.inputField.username"/>
            </c:if>
            <c:if test="${label=='Password'}">
                <fmt:message key="label.inputField.password"/>
            </c:if>
            <c:if test="${label=='Date'}">
                <fmt:message key="label.inputField.date"/>
            </c:if>
            <c:if test="${label=='Description'}">
                <fmt:message key="label.inputField.description"/>
            </c:if>
            <c:if test="${label=='Name'}">
                <fmt:message key="label.inputField.name"/>
            </c:if>
            <c:if test="${label=='Birth Date'}">
                <fmt:message key="label.inputField.birthdate"/>
            </c:if>
            <c:if test="${label=='Start date'}">
                <fmt:message key="label.inputField.startdate"/>
            </c:if>
            <c:if test="${label=='End date'}">
                <fmt:message key="label.inputField.enddate"/>
            </c:if>
            <c:if test="${label=='Special Cares'}">
                <fmt:message key="label.inputField.specialCares"/>
            </c:if>
            <c:if test="${label=='Pet'}">
                <fmt:message key="label.createOrUpdateVisitForm.pet"/>
            </c:if>
            <c:if test="${label=='Goal'}">
                <fmt:message key="label.goal"/>
            </c:if>
            <c:if test="${label=='Organization'}">
                <fmt:message key="label.organization"/>
            </c:if>
             
         </label>

        <div class="col-sm-10">
            <form:input class="form-control" path="${name}" required="${required}"/>
        </div>
    </div>
</spring:bind>