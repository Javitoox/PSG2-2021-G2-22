<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<petclinic:layout pageName="causes">
	<jsp:body>
	
	    <h2><fmt:message key="label.causes.new" /></h2>
	    
	    <form:form modelAttribute="cause" class="form-horizontal" id="add-cause-form">
	   
	        <div class="form-group has-feedback">
	       		<petclinic:inputField label="Name" name="name" />
	            <petclinic:inputField label="Description" name="description" />
	            <petclinic:inputField label="Goal" name="goal"/>
	            <petclinic:inputField label="Organization" name="organization" />
	            <input type=hidden name="owner" value="${owner}" /> 
            </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	            	<button class="btn btn-default" type="submit">
							<fmt:message key="label.causes.add"/>
					</button>
	            </div>
	        </div>
	    
		</form:form>
    </jsp:body>
</petclinic:layout>