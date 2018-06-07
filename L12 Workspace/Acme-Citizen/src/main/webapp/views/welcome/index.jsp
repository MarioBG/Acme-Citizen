<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="welcome.greeting.prefix" />
	<jstl:choose>
		<jstl:when test="${name.equals('anonymous user')}">
			<spring:message code="welcome.greeting.anonymousUser" />
		</jstl:when>
		<jstl:otherwise>
			<jstl:out value="${name}" />
		</jstl:otherwise>
	</jstl:choose>
	<spring:message code="welcome.greeting.suffix" />
</p>

<spring:message code="welcome.pattern.date" var="patternDate" />
<p>
	<spring:message code="welcome.greeting.current.time" />
	<fmt:formatDate value="${moment}" pattern="${patternDate}" />
</p>

<p>
	<jstl:out value="${ welcomeMessage }"/>
</p>

<h3>
	<spring:message code="welcome.lastChirps" />
</h3>

<display:table pagesize="3" class="displaytag" keepStatus="true"
	name="chirps" requestURI="welcome/index.do" id="row">

	<security:authentication property="principal" var="loggedactor" />
	<security:authorize access="hasRole('GOVERNMENTAGENT')">
		<display:column>
			<jstl:if
				test="${row.governmentAgent.userAccount.id eq loggedactor.id}">
				<a href="chirp/governmentAgent/edit.do?chirpId=${row.id}"><spring:message
						code="welcome.chirp.edit" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<spring:message code="chirp.image" var="chirpImageHead" />
	<jstl:if test="${ row.image!=null }">
		<display:column title="${ chirpImageHead }">
			<img src="${row.image}" style="display: block; max-width: 100px; max-height: 100px; width: auto; height: auto;"/>
		</display:column>
	</jstl:if>

	<display:column>
		<a href="chirp/display.do?chirpId=${row.id}"><spring:message
				code="welcome.chirp.display" /></a>
	</display:column>

	<spring:message code="welcome.chirp.governmentAgent"
		var="governmentAgentHeader" />
	<display:column title="${governmentAgentHeader}">
		<a
			href="governmentAgent/display.do?governmentAgentId=${row.governmentAgent.id}">
			<jstl:out value="${row.governmentAgent.name}" />
		</a>
	</display:column>

	<spring:message code="welcome.chirp.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message var="publicationMomentHeader"
		code="welcome.chirp.publicationMoment" />
	<spring:message var="formatDate" code="welcome.chirp.format.date" />
	<display:column property="publicationMoment"
		title="${publicationMomentHeader}" format="${formatDate}"
		sortable="true" />

</display:table>
