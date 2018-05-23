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
		<jstl:when test="${requestURI == 'election/list.do'  }">
			<spring:message code="election.systemElections" />
		</jstl:when>
	</jstl:choose>
</h3>

<display:table name="elections" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('GOVERNMENTAGET)">
		<security:authentication property="principal" var="loggedactor" />
		<jsp:useBean id="date" class="java.util.Date" />
		<display:column>
			<jstl:if
				test="${(row.governmentAgent.userAccount.id eq loggedactor.id) and row.candidatureDate.after(date)}">
				<a href="election/governmentAgent/election.do?chirpId=${row.id}"><spring:message
						code="election.edit" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column>
		<a href="election/display.do?electionId=${row.id}"><spring:message
				code="election.display" /></a>
	</display:column>

	<spring:message var="governmentAgentHeader"
		code="election.governmentAgent" />
	<display:column title="${nameHeader}">
		<a
			href="governmentAgent/display.do?governmentAgentId=${row.governmentAgent.id}"><jstl:out
				value="${row.governmentAgent.name}" /></a>
	</display:column>

	<spring:message var="candidatureDateHeader"
		code="election.candidatureDate" />
	<spring:message var="formatDate" code="election.format.date" />
	<display:column property="candidatureDate"
		title="${candidatureDateHeader}" format="${formatDate}"
		sortable="true" />

	<spring:message var="celebrationDateHeader"
		code="election.celebrationDate" />
	<display:column property="celebrationDate"
		title="${celebrationDateHeader}" format="${formatDate}"
		sortable="true" />

</display:table>

<security:authorize access="hasRole('GOVERNMENTAGENT')">
	<jstl:if test="${governmentAgent.canOrganiseElection == true}">
		<a href="election/governmentAgent/create.do"><spring:message
			code="election.create" /></a>
		<br />
	</jstl:if>	
</security:authorize>

<spring:message var="backValue" code="election.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />