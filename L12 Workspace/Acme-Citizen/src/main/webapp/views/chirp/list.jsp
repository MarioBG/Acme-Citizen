<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h3>
	<jstl:choose>
		<jstl:when
			test="${requestURI == 'chirp/list.do' and governmentAgent == null}">
			<spring:message code="chirp.systemChirps" />
		</jstl:when>
		<jstl:when
			test="${requestURI == 'chirp/list.do' and governmentAgent != null}">
			<spring:message code="chirp.chirpsOf" />
			<jstl:out value="${governmentAgent.name}" />
		</jstl:when>
		<jstl:when test="${requestURI == 'chirp/governmentAgent/list.do'}">
			<spring:message code="chirp.yourChirps"/>
		</jstl:when>
	</jstl:choose>
</h3>

<!-- displaying grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="chirps" requestURI="${requestURI}" id="row">

	<security:authorize access="hasRole('GOVERNMENTAGENT')">
		<security:authentication property="principal" var="loggedactor" />
		<display:column>
			<jstl:if
				test="${row.governmentAgent.userAccount.id eq loggedactor.id}">
				<a href="chirp/governmentAgent/edit.do?chirpId=${row.id}"><spring:message
						code="chirp.edit" /></a>
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
				code="chirp.display" /></a>
	</display:column>

	<jstl:if test="${requestURI == 'chirp/list.do' and governmentAgent == null}">
		<spring:message code="chirp.governmentAgent"
			var="governmentAgentHeader" />
		<display:column title="${governmentAgentHeader}">
			<a
				href="governmentAgent/display.do?governmentAgentId=${row.governmentAgent.id}">
				<jstl:out value="${row.governmentAgent.name}" />
			</a>
		</display:column>
	</jstl:if>

	<spring:message code="chirp.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message var="publicationMomentHeader"
		code="chirp.publicationMoment" />
	<spring:message var="formatDate" code="chirp.format.date" />
	<display:column property="publicationMoment"
		title="${publicationMomentHeader}" format="${formatDate}"
		sortable="true" />

</display:table>

<security:authorize access="hasRole('GOVERNMENTAGENT')">
	<p>
		<a href="chirp/governmentAgent/create.do"><spring:message
				code="chirp.create" /></a>
	</p>
</security:authorize>

<spring:message var="backValue" code="chirp.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />