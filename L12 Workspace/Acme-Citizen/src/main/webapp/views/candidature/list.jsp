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
		<jstl:when test="${requestURI == 'candidature/list.do'  }">
			<spring:message code="candidature.candidaturesFromElection" />
		</jstl:when>
	</jstl:choose>
</h3>

<display:table name="candidatures" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('CITIZEN')">
		<jstl:if
			test="${election.celebrationDate.before(date) and hasVoted == false}">
			<display:column>
				<a href="candidature/citizen/vote.do?candidatureId=${row.id}"><spring:message
						code="candidature.vote" /></a>
			</display:column>
		</jstl:if>

		<jstl:if test="${dateOneDayBeforeCelebrationDate.after(date)}">
			<display:column>
				<jstl:if test="${participatingCandidatures.contains(row)}">
					<a href="candidature/citizen/edit.do?candidatureId=${row.id}"><spring:message
							code="candidature.edit" /></a>
				</jstl:if>
			</display:column>
		</jstl:if>

		<jstl:if test="${election.celebrationDate.after(date)}">
			<display:column>
				<jstl:if test="${participatingCandidatures.contains(row)}">
					<a href="candidature/citizen/delete.do?candidatureId=${row.id}"><spring:message
							code="candidature.delete" /></a>
				</jstl:if>
			</display:column>
		</jstl:if>

	</security:authorize>

	<display:column>
		<a href="candidature/display.do?candidatureId=${row.id}"><spring:message
				code="candidature.display" /></a>
	</display:column>

	<spring:message var="partyLogoHeader" code="candidature.partyLogo" />
	<display:column title="${partyLogoHeader}">
		<img src="${row.partyLogo}" style="display: block; max-width: 230px; max-height: 95px; width: auto; height: auto;" />
	</display:column>

	<spring:message var="electoralProgramHeader"
		code="candidature.electoralProgram" />
	<display:column title="${electoralProgramHeader}">
		<a href="${row.electoralProgram}"><spring:message
				code="candidature.link" /></a>
	</display:column>

	<jstl:if test="${election.celebrationDate.after(date)}">
		<spring:message var="voteNumberHeader" code="candidature.voteNumber" />
		<display:column property="voteNumber" title="${voteNumberHeader}" sortable="true"/>
	</jstl:if>

</display:table>

<security:authorize access="hasRole('CITIZEN')">
	<jstl:if
		test="${election.candidatureDate.before(date) and dateOneDayBeforeCelebrationDate.after(date)}">
		<a href="candidature/citizen/create.do?electionId=${election.id}"><spring:message
				code="candidature.create" /></a>
		<br />
	</jstl:if>
</security:authorize>

<spring:message var="backValue" code="candidature.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('election/display.do?electionId=${election.id}');" />