<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="error">

    <spring:url value="/resources/images/error_pet_image.jpg" var="petsImage"/>
    <img src="${petsImage}"/>

    <h2><fmt:message key="label.exception.error"/></h2>

    <p>${exception.message}</p>

</petclinic:layout>
