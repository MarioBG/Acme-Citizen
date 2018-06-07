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

<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script src="https://www.amcharts.com/lib/3/pie.js"></script>
<script src="scripts/chart.js"></script>
<!-- <script type="text/javascript">
	$(document).ready(function() {
		var chartData=[];
		for (var i=0; i<election.candidatures.length; i++){
			chartData.push({"party":election.candidatures[i].description, "votes":election.candidatures[i].votes});
		}
		makeVoteDiagram(chartData);
	});
</script> -->



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

<jstl:if test="${not empty election.comments and daysCelebration > 0}">
	<a href="comment/list.do?commentableId=${election.id}"><spring:message
			code="election.listComments" /></a>
	<br />
</jstl:if>

<security:authorize access="hasAnyRole('CITIZEN','GOVERNMENTAGENT')">
	<jstl:if test="${daysCelebration > 0}">
		<a href="comment/actor/create.do?commentableId=${election.id}"><spring:message
				code="election.createComment" /></a>
		<br />
	</jstl:if>
</security:authorize>

<h3>
	<spring:message code="election.candidatures"/>
</h3>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="election.candidatures" requestURI="election/display.do" id="row">

	<security:authorize access="hasRole('GOVERNMENTAGENT')">
		<jstl:if test="${daysCelebration < 0}">
			<display:column>
				<a
					href="candidature/governmentAgent/delete.do?candidatureId=${row.id}"><spring:message
						code="election.delete" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>

	<security:authorize access="hasRole('CITIZEN')">
		<jstl:if test="${daysCelebration == 0 and hasVoted == false}">
			<display:column>
				<a href="candidature/citizen/vote.do?candidatureId=${row.id}"><spring:message
						code="candidature.vote" /></a>
			</display:column>
		</jstl:if>
		<jstl:if test="${daysCelebration<-1}">
			<display:column>
				<jstl:if test="${participatingCandidatures.contains(row)}">
					<a href="candidature/citizen/edit.do?candidatureId=${row.id}"><spring:message
							code="candidature.edit" /></a>
				</jstl:if>
			</display:column>
		</jstl:if>
		<jstl:if test="${daysCelebration<0}">
			<display:column>
				<jstl:if test="${participatingCandidatures.contains(row)}">
					<a href="candidature/citizen/delete.do?candidatureId=${row.id}"><spring:message
							code="candidature.delete" /></a>
				</jstl:if>
			</display:column>
		</jstl:if>
	</security:authorize>
	<spring:message code="candidature.description" var="candidatureDesc" />
	<display:column title="${candidatureDesc}">
		<a href="candidature/display.do?candidatureId=${row.id}"> <jstl:out
				value="${row.description}" />
		</a>
	</display:column>
	<spring:message var="partyLogoHeader" code="candidature.partyLogo" />
	<display:column title="${partyLogoHeader}">
		<img src="${row.partyLogo}"
			style="display: block; max-width: 230px; max-height: 95px; width: auto; height: auto;" />
	</display:column>

	<spring:message var="electoralProgramHeader"
		code="candidature.electoralProgram" />
	<display:column title="${electoralProgramHeader}">
		<a href="${row.electoralProgram}"><spring:message
				code="candidature.link" /></a>
	</display:column>

	<jstl:if test="${ daysCelebration>0 }">
		<spring:message var="voteNumberHeader" code="candidature.voteNumber" />
		<display:column property="voteNumber" title="${voteNumberHeader}"
			sortable="true" />
	</jstl:if>
</display:table>

<security:authorize access="hasRole('CITIZEN')">
	<jstl:if test="${daysCandidature >= 0 and daysCelebration < 0}">
		<a href="candidature/citizen/create.do?electionId=${election.id}"><spring:message
				code="election.createCandidature" /></a>
	</jstl:if>
</security:authorize>

<!-- <div id="chartdiv"></div>
 -->
<div class="middle">
	<img src="${countryFlag}"
		style="display: block; max-width: 500px; max-height: 200px; width: auto; height: auto;" />
</div>
<spring:message var="backValue" code="election.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('election/list.do');" />