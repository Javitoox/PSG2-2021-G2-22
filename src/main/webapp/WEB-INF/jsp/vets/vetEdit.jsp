<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">
    <h2><fmt:message key="label.editVet"/></h2>
    <form:form modelAttribute="vet" class="form-horizontal" id="add-vet-form2" action="/vets/${vet.id}/edit">
        <div class="form-group has-feedback">
            <petclinic:inputField label="First Name" name="firstName"/>
            <petclinic:inputField label="Last Name" name="lastName"/>
           	<petclinic:selectMultipleField name="specialty2" label="Specialties " names="${specialties}" size="5"/>
        </div>
        <div class="form-group">
          <button class="btn btn-default" type="submit"><fmt:message key="label.editVet"/></button>    
        </div>
    </form:form>
</petclinic:layout>