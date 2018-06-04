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

<form:form action="petition/citizen/edit.do"
	modelAttribute="petitionForm">

	<form:hidden path="id" />
	<form:hidden path="citizenId" />
	<form:hidden path="creationMoment" />

	<acme:textbox code="petition.name" path="name" />
	<br />

	<acme:textarea code="petition.description" path="description" />
	<br />

	<acme:textbox code="petition.picture" path="picture" />
	<br />

	<jstl:if test="${petitionForm.id != 0}">
		<spring:message var="patternDate" code="petition.pattern.date" />
		<b><spring:message code="petition.creationMoment" />:&nbsp;</b>
		<fmt:formatDate value="${petitionForm.creationMoment}"
			pattern="${patternDate}" />
		<br />
	</jstl:if>

	<acme:checkbox code="petition.isFinal" path="finalVersion" />
	<br />
	
	<jstl:if test="${petitionForm.id != 0}">
		<a
			href="governmentAgent/citizen/addGovernmentAgents.do?petitionId=${petitionForm.id}"><spring:message
				code="petition.addGovernmentAgents" /></a>
		<br />
	</jstl:if>

	<acme:submit name="save" code="petition.save" />
	&nbsp;
	<jstl:if test="${petitionForm.id != 0}">
		<acme:submit name="delete" code="petition.delete" />
	&nbsp;
	</jstl:if>
	<acme:cancel url="petition/list.do" code="petition.back" />

</form:form>