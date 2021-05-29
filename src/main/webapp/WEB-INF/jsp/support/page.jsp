<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="support">
	<h2>Información de contacto de nuestros proveedores</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Email</th>
				<th>Teléfono</th>
			</tr>
		</thead>
		<tbody id="rows">
		</tbody>
	</table>

	<script src="/resources/js/support.js"></script>
</petclinic:layout>