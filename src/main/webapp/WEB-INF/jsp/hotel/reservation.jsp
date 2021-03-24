<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="hotel">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
            	$("#start").datepicker({dateFormat: 'yy-mm-dd'});
                $("#end").datepicker({dateFormat: 'yy-mm-dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
	    <h2><fmt:message key="label.reservation"/></h2>
	    
	    <form:form modelAttribute="reservation" class="form-horizontal">
	        <div class="form-group has-feedback">
	            <petclinic:inputField label="Start date" name="start"/>
	            <petclinic:inputField label="End date" name="end"/>
	            <petclinic:inputField label="Special Cares" name="specialCares"/>
	            <petclinic:inputField label="Your pets identifier" name="pet"/>
	            <petclinic:radioField name="level" label="STANDARD"/>
                <petclinic:radioField name="level" label="VIP"/>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	            	<button class="btn btn-default" type="submit"><fmt:message key="label.reservation.send"/></button>
	            </div>
	        </div>
	    </form:form>
    </jsp:body>
	
</petclinic:layout>
