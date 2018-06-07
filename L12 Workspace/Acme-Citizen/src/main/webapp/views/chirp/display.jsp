<%--
 * display.jsp
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
	<b><spring:message code="chirp.title" />:&nbsp;</b>
	<jstl:out value="${chirp.title}" />
	<br />
</h3>

<jstl:choose>
	<jstl:when test="${chirp.image != null}">
		<img src="<jstl:out value="${chirp.image}"/>" width="450" height="174">
		<br />
	</jstl:when>
</jstl:choose>
<jstl:choose>
	<jstl:when test="${chirp.link != null}">
		<a href="<jstl:out value="${chirp.link}"/>"><spring:message
				code="chirp.link" /></a>
		<br />
	</jstl:when>
</jstl:choose>

<b><spring:message code="chirp.content" />:&nbsp;</b>
<jstl:out value="${chirp.content}" />
<br />

<b><spring:message code="chirp.governmentAgent" />:&nbsp;</b>
<a
	href="governmentAgent/display.do?governmentAgentId=${chirp.governmentAgent.id}"><jstl:out
		value="${chirp.governmentAgent.name}" /></a>
<br />

<spring:message var="patternDate" code="chirp.pattern.date" />
<b><spring:message code="chirp.publicationMoment" />:&nbsp;</b>
<fmt:formatDate value="${chirp.publicationMoment}"
	pattern="${patternDate}" />
<br />

<spring:message var="backValue" code="chirp.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('chirp/list.do');" />