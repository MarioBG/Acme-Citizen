<%-- edit.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="service/manager/edit.do" modelAttribute="serviceForm">

	<form:hidden path="id" />
	<form:hidden path="cancelled" />
	<acme:textbox code="service.name" path="name"
		readonly="${serviceForm.cancelled}" />
	<br />

	<acme:textarea code="service.description" path="description"
		readonly="${service.cancelled}" />
	<br />

	<acme:textbox code="service.picture" path="picture"
		readonly="${serviceForm.cancelled}" />
	<br />

	<acme:selectObligatory items="${categories}" itemLabel="name"
		code="service.category" path="category" />
	<br />

	<jstl:if test="${serviceForm.cancelled == false}">
		<acme:submit name="save" code="service.save" />
		&nbsp;
		<jstl:if test="${serviceForm.id != 0}">
			<acme:submit name="delete" code="service.delete" />
			&nbsp;
		</jstl:if>
	</jstl:if>

	<acme:cancel url="service/manager/list.do" code="service.cancel" />

</form:form>