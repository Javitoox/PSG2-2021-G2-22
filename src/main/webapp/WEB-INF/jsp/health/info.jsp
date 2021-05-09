<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<petclinic:layout pageName="health">

    <jstl:if test="${health == 'UP'}">
    	<button type="button" class="btn btn-success">
			${health}
		</button>
    </jstl:if>
    <jstl:if test="${health != 'UP'}">
    	<button type="button" class="btn btn-danger">
			${health}
		</button>
    </jstl:if>
    
    <br>
    <p>
    	<a href="/manage/health">
    		<fmt:message key="label.health.message"/>
    	</a>
    </p>

</petclinic:layout>
