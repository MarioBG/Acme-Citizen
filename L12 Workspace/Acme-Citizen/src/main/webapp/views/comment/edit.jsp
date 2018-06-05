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

<form:form action="comment/actor/edit.do" modelAttribute="commentForm">

	<form:hidden path="id" />
	<form:hidden path="commentableId" />
	<form:hidden path="parentCommentId" />
	<form:hidden path="moment" />

	<acme:textbox code="comment.picture" path="picture" />
	<br />

	<acme:textarea code="comment.text" path="text" />
	<br />

	<jstl:if test="${commentForm.id != 0}">
		<spring:message var="patternDate" code="comment.pattern.date" />
		<b><spring:message code="comment.moment" />:&nbsp;</b>
		<fmt:formatDate value="${commentForm.moment}"
			pattern="${patternDate}" />
		<br />
	</jstl:if>

	<acme:submit name="save" code="comment.save" />
	&nbsp;
	<jstl:if test="${commentForm.id != 0}">
		<acme:submit name="delete" code="comment.delete" />
		&nbsp;
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${commentForm.parentCommentId != null}">
			<acme:cancel
				url="comment/list.do?commentId=${commentForm.parentCommentId}"
				code="comment.back" />
		</jstl:when>
		<jstl:otherwise>
			<acme:cancel
				url="comment/list.do?commentableId=${commentForm.commentableId}"
				code="comment.back" />
		</jstl:otherwise>
	</jstl:choose>

</form:form>