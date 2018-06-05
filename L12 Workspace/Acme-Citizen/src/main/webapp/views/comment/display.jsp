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

<jstl:if test="${comment.picture != null}">
	<img src="<jstl:out value="${comment.picture}"/>" />
	<br />
</jstl:if>

<b><spring:message code="comment.actor" />:&nbsp;</b>
<jstl:choose>
	<jstl:when test="${role == 'citizen' }">
		<a href="citizen/display.do?citizenId=${comment.actor.id}"><jstl:out
				value="${comment.actor.name}" /></a>
	</jstl:when>
	<jstl:when test="${role == 'governmentAgent' }">
		<a href="governmentAgent/display.do?governmentAgentId=${comment.actor.id}"><jstl:out
				value="${comment.actor.name}" /></a>
	</jstl:when>
</jstl:choose>
<br />

<spring:message var="patternDate" code="comment.pattern.date" />
<b><spring:message code="comment.moment" />:&nbsp;</b>
<fmt:formatDate value="${comment.moment}" pattern="${patternDate}" />
<br />

<b><spring:message code="comment.text" />:&nbsp;</b>
<jstl:out value="${comment.text}" />
<br />

<jstl:if test="${not empty comment.replies}">
	<a href="comment/list.do?commentId=${comment.id}"><spring:message code="comment.replies"/></a>
	<br/>
</jstl:if>

<security:authorize access="hasAnyRole('CITIZEN', 'GOVERNMENTAGENT')">
	<a href="comment/actor/create.do?commentId=${comment.id}"><spring:message code="comment.reply"/></a>
	<br/>
</security:authorize>

<jstl:choose>
	<jstl:when test="${comment.parentComment != null}">
		<acme:cancel
			url="comment/list.do?commentId=${comment.parentComment.id}"
			code="comment.back" />
	</jstl:when>
	<jstl:otherwise>
		<acme:cancel
			url="comment/list.do?commentableId=${comment.commentable.id}"
			code="comment.back" />
	</jstl:otherwise>
</jstl:choose>
