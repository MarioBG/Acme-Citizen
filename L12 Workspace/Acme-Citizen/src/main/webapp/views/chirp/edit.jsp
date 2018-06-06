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

<form:form action="chirp/governmentAgent/edit.do"
	modelAttribute="chirpForm">

	<form:hidden path="id" />
	<form:hidden path="governmentAgentId" />
	<form:hidden path="publicationMoment" />

	<acme:textbox code="chirp.title" path="title" />
	<br />

	<acme:textarea code="chirp.content" path="content" />
	<br />

	<jstl:if test="${chirpForm.id != 0}">
		<spring:message var="patternDate" code="chirp.pattern.date" />
		<b><spring:message code="chirp.publicationMoment" />:&nbsp;</b>
		<fmt:formatDate value="${chirpForm.publicationMoment}"
			pattern="${patternDate}" />
		<br />
	</jstl:if>

	<acme:textbox code="chirp.image" path="image" />
	<br />

	<acme:textbox code="chirp.link" path="link" />
	<br />

	<acme:submit name="save" code="chirp.save" />
	&nbsp;
	<jstl:if test="${chirpForm.id != 0 }">
		<acme:submit name="delete" code="chirp.delete" />
		&nbsp;
	</jstl:if>
	<acme:cancel
		url="chirp/list.do?governmentAgentId=${chirpForm.governmentAgentId}"
		code="chirp.back" />

</form:form>
