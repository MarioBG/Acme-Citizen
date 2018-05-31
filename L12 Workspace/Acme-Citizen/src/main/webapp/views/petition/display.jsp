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

<h3>
	<b><spring:message code="petition.name" />:&nbsp;</b>
	<jstl:out value="${petition.name}" />
</h3>

<jstl:if test="${petition.picture != null}">
	<img src="<jstl:out value="${petition.picture}"/>" width="450"
		height="174">
	<br />
</jstl:if>

<b><spring:message code="petition.description" />:&nbsp;</b>
<jstl:out value="${petition.description}" />
<br />

<b><spring:message code="petition.citizen" />:&nbsp;</b>
<a href="citizen/display.do?citizenId=${petition.citizen.id}"><jstl:out
		value="${petition.citizen.name}" /></a>
<br />

<spring:message var="patternDate" code="petition.pattern.date" />
<b><spring:message code="petition.creationMoment" />:&nbsp;</b>
<fmt:formatDate value="${petition.creationMoment}"
	pattern="${patternDate}" />
<br />

<jstl:if test="${petition.finalVersion == true}">
	<h3 style="text-transform: uppercase; color: red;">
		<b><spring:message code="petition.finalVersion" /></b>
	</h3>
</jstl:if>

<jstl:if test="${petition.resolved == true}">
	<h3 style="text-transform: uppercase; color: green;">
		<b><spring:message code="petition.resolved" /></b>
	</h3>
</jstl:if>

<jstl:if test="${not empty petition.governmentAgents}">

	<h3>
		<spring:message code="petition.governmentAgents" />
	</h3>

	<display:table name="petition.governmentAgents" id="row"
		requestURI="petition/display.do" pagesize="5" class="displaytag">

		<spring:message code="petition.governmentAgent.name" var="nameHeader" />
		<display:column title="${nameHeader}">
			<a href="governmentAgent/display.do?governmentAgentId=${row.id}"><jstl:out value="${row.name}" /></a>
		</display:column>

		<spring:message code="petition.governmentAgent.registerCode" var="registerCodeHeader" />
		<display:column title="${registerCodeHeader}" property="registerCode"/>

	</display:table>
</jstl:if>

<security:authorize access="hasRole('GOVERNMENTAGENT')">
	<jstl:if test="${petition.resolved == false and petition.governmentAgents.contains(governmentAgent)}">
		<a href="petition/governmentAgent/resolve.do?petitionId=${petition.id}"><spring:message
				code="petition.resolve" /></a>
		<br />
	</jstl:if>
</security:authorize>

<jstl:if test="${not empty petition.comments}">
	<a href="comment/list.do?commentableId=${petition.id}"><spring:message
			code="petition.listComments" /></a>
	<br />
</jstl:if>

<security:authorize access="hasAnyRole('CITIZEN','GOVERNMENTAGENT')">
	<jstl:if test="${petition.resolved == false}">
		<a href="comment/actor/create.do?commentableId=${petition.id}"><spring:message
				code="petition.createComment" /></a>
		<br />
	</jstl:if>
</security:authorize>

<acme:cancel url="petition/list.do" code="petition.back" />