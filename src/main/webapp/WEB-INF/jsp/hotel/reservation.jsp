<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

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
	    <h2>Reservation</h2>
	    
	    <form:form modelAttribute="reservation" class="form-horizontal" id="add-reservation-form">
	        <div class="form-group has-feedback">
	            <petclinic:inputField label="Start date" name="start"/>
	            <petclinic:inputField label="End date" name="end"/>
	            <petclinic:inputField label="Special Cares" name="specialCares"/>
	            <div class="control-group">
                    <petclinic:selectField name="pet" label="Pet" names="${pets}" size="1"/>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">Level</label>
	                <div class="col-sm-offset-2 col-sm-10">
			            <input class="form" type="radio" name="level" value="STANDARD"> STANDARD<br>
		                <input type="radio" name="level" value="VIP"> VIP
	                </div>
                </div>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	            	<button class="btn btn-default" type="submit">Send</button>
	            </div>
	        </div>
	    </form:form>
    </jsp:body>
	
</petclinic:layout>
