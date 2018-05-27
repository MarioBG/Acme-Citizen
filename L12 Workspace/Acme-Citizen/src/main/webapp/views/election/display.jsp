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

<jsp:useBean id="date" class="java.util.Date" />

<b><spring:message code="election.governmentAgent" />:&nbsp;</b>
<a
	href="governmentAgent/display.do?governmentAgentId=${election.governmentAgent.id}"><jstl:out
		value="${election.governmentAgent.name}" /></a>
<br />

<b><spring:message code="election.description" />:&nbsp;</b>
<jstl:out value="${election.description}" />
<br />

<spring:message var="patternDate" code="election.pattern.date" />
<b><spring:message code="election.candidatureDate" />:&nbsp;</b>
<fmt:formatDate value="${election.candidatureDate}"
	pattern="${patternDate}" />
<br />

<b><spring:message code="election.celebrationDate" />:&nbsp;</b>
<fmt:formatDate value="${election.celebrationDate}"
	pattern="${patternDate}" />
<br />

<jstl:if test="${election.candidatures not empty}">
	<a href="candidature/list.do?candidatureId=${election.candidature.id}"><spring:message
			code="election.listCandidatures" /></a>
	<br />
</jstl:if>

<jstl:if test="${election.comments not empty}">
	<a href="comment/list.do?commentableId=${election.id}"><spring:message
			code="election.listComments" /></a>
	<br />
</jstl:if>

<security:authorize access="hasRole('CITIZEN','GOVERNMENTAGENT')">
	<jstl:if test="${election.celebrationDate.before(date)}">
		<a href="comment/actor/create.do?commentableId=${election.id}"><spring:message
				code="election.createComment" /></a>
		<br />
	</jstl:if>
</security:authorize>

<spring:message var="backValue" code="election.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('election/list.do');" />