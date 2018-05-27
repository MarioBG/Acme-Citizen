<%--
 * action-1.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('GOVERNMENTAGENT')">

	<h4>
		<spring:message code="administrator.numberRegisteredActors"
			var="numberRegisteredActorsHead" />
		<jstl:out value="${numberRegisteredActorsHead}" />
		=
		<fmt:formatNumber value="${numberRegisteredActors}"
			maxFractionDigits="0" />
	</h4>
	<h4>
		<spring:message code="administrator.avgMinMaxStdvPerCitizen"
			var="avgMinMaxStdvPerCitizenHead" />
		<jstl:out value="${avgMinMaxStdvPerCitizenHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStdvPerCitizen">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStdvPerCitizen}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>
	<h4>
		<spring:message code="administrator.avgMinMaxStdvPerGovAgent"
			var="avgMinMaxStdvPerGovAgentHead" />
		<jstl:out value="${avgMinMaxStdvPerGovAgentHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStdvPerGovAgent">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStdvPerGovAgent}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>

	<table class="displaytag" name="petitionsByComments">
		<tr>
			<th><spring:message code="administrator.petitionsByComments"
					var="bestHeader" /> <jstl:out value="${bestHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${petitionsByComments}">
				<td><a
					href="petitions/display.do?petitionId=<jstl:out value="${datos.id}"/>"><jstl:out
							value="${datos.name}" /></a></td>
			</jstl:forEach>
		</tr>

	</table>

	<table class="displaytag" name="electionsByComments">
		<tr>
			<th><spring:message code="administrator.electionsByComments"
					var="bestHeader2" /> <jstl:out value="${bestHeader2}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${electionsByComments}">
				<td><a
					href="elections/display.do?electionId=<jstl:out value="${datos.id}"/>"><spring:message
							code="election.pattern.date" var="patternDate" /> <fmt:formatDate
							value="${datos.celebrationDate}" pattern="${patternDate}" /></a></td>
			</jstl:forEach>
		</tr>

	</table>

	<h4>
		<spring:message code="administrator.percentageElectionCandidates"
			var="percentageElectionCandidatesHead" />
		<jstl:out value="${percentageElectionCandidatesHead}" />
		=
		<fmt:formatNumber value="${percentageElectionCandidates}"
			maxFractionDigits="0" />
		%
	</h4>

	<table class="displaytag" name="citizensLessLotteryTicketsAverage">
		<tr>
			<th><spring:message
					code="administrator.citizensLessLotteryTicketsAverage"
					var="bestHeader2" /> <jstl:out value="${bestHeader2}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos"
				items="${citizensLessLotteryTicketsAverage}">
				<td><a
					href="citizens/display.do?citizenId=<jstl:out value="${datos.id}"/>"><jstl:out
							value="${datos.name}" /> (<jstl:out value="${datos.nif}" />)</a></td>
			</jstl:forEach>
		</tr>

	</table>
	<table class="displaytag" name="candidaturesMoreVotesAverage">
		<tr>
			<th><spring:message
					code="administrator.candidaturesMoreVotesAverage"
					var="bestHeader3" /> <jstl:out value="${bestHeader3}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos"
				items="${candidaturesMoreVotesAverage}">
				<td><a
					href="candidatures/display.do?candidatureId=<jstl:out value="${datos.id}"/>"><jstl:out
							value="${datos.description}" /></a></td>
			</jstl:forEach>
		</tr>

	</table>
	
	<h4>
		<spring:message code="administrator.avgMinMaxStdvVotesPerElection"
			var="avgMinMaxStdvVotesPerElectionHead" />
		<jstl:out value="${avgMinMaxStdvVotesPerElectionHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStdvVotesPerElection">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStdvVotesPerElection}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>
	
	<h4>
		<spring:message code="administrator.avgMinMaxStdvCandidaturesPerElection"
			var="avgMinMaxStdvCandidaturesPerElectionHead" />
		<jstl:out value="${avgMinMaxStdvCandidaturesPerElectionHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStdvCandidaturesPerElection">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStdvCandidaturesPerElection}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>
	
	<h4>
		<spring:message code="administrator.allMoneyInSystem"
			var="allMoneyInSystemHead" />
		<jstl:out value="${allMoneyInSystemHead}" />
		= <fmt:formatNumber maxFractionDigits="2" value="${allMoneyInSystem}" pattern="#.00'Rb'"/>
	</h4>
	
	<h4>
		<spring:message code="administrator.avgMinMaxStdvMoneyPerActor"
			var="avgMinMaxStdvMoneyPerActorHead" />
		<jstl:out value="${avgMinMaxStdvMoneyPerActorHead}"></jstl:out>
	</h4>
	<table class="displaytag" name="avgMinMaxStdvMoneyPerActor">
		<tr>
			<th><spring:message code="administrator.minimum"
					var="minimumHeader" /> <jstl:out value="${minimumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.maximum"
					var="maximumHeader" /> <jstl:out value="${maximumHeader}"></jstl:out>
			</th>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgMinMaxStdvMoneyPerActor}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>

</security:authorize>
