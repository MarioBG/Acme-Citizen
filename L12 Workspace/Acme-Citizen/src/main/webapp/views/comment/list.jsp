<%-- list.jsp de Application --%>

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
	<jstl:choose>
		<jstl:when
			test="${requestURI == 'comment/list.do' and parentComment == null}">
			<spring:message code="comment.commentsFromCommentable" />
		</jstl:when>
		<jstl:when
			test="${requestURI == 'comment/list.do' and parentComment != null}">
			<spring:message code="comment.repliesFromComment" />
		</jstl:when>
		<jstl:when test="${requestURI == 'comment/actor/list.do'}">
			<spring:message code="comment.yourComments" />
		</jstl:when>
	</jstl:choose>
</h3>

<display:table name="comments" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<security:authorize access="hasRole('GOVERNMENTAGENT')">
		<display:column>
				<a href="comment/governmentAgent/delete.do?commentId=${row.id}"><spring:message
						code="comment.delete" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasAnyRole('CITIZEN', 'GOVERNMENTAGENT')">
		<display:column>
			<security:authentication property="principal" var="loggedactor" />
			<jstl:if test="${row.actor.userAccount.id eq loggedactor.id}">
				<a href="comment/actor/edit.do?commentId=${row.id}"><spring:message
						code="comment.edit" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column>
		<a href="comment/display.do?commentId=${row.id}"><spring:message
				code="comment.display" /></a>
	</display:column>

	<display:column>
		<jstl:if test="${not empty row.replies}">
			<a href="comment/list.do?commentId=${row.id}"><spring:message
					code="comment.replies" /></a>
		</jstl:if>
	</display:column>

	<spring:message var="textHeader" code="comment.text" />
	<display:column title="${textHeader}" property="text" />

	<spring:message var="momentHeader" code="comment.moment" />
	<spring:message var="formatDate" code="comment.format.date" />
	<display:column property="moment" title="${momentHeader}"
		format="${formatDate}" sortable="true" />

	<jstl:if test="${requestURI != 'comment/actor/list.do' }">
		<spring:message var="actorHeader" code="comment.actor" />
		<display:column title="${actorHeader}">
			<jstl:out value="${row.actor.name}" />
		</display:column>
	</jstl:if>

</display:table>

<security:authorize access="hasAnyRole('CITIZEN', 'GOVERNMENTAGENT')">
	<jstl:if test="${requestURI == 'comment/list.do' }">
		<jstl:choose>
			<jstl:when test="${parentComment != null}">
				<a href="comment/actor/create.do?commentId=${parentComment.id}"><spring:message
						code="comment.create" /></a>
				<br />
			</jstl:when>
			<jstl:otherwise>
				<a href="comment/actor/create.do?commentableId=${commentable.id}"><spring:message
						code="comment.create" /></a>
				<br />
			</jstl:otherwise>
		</jstl:choose>
	</jstl:if>
</security:authorize>

<spring:message var="backValue" code="comment.back" />
<jstl:choose>

	<jstl:when test="${parentComment != null}">
		<input type="button" name="back" value="${backValue}"
			onclick="javascript: relativeRedir('comment/list.do?commentableId=${commentable.id}');" />
	</jstl:when>

	<jstl:otherwise>
		<input type="button" name="back" value="${backValue}"
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</jstl:otherwise>

</jstl:choose>