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

<!-- displaying grid -->

<h3>
	<jstl:choose>
		<jstl:when test="${requestURI == 'governmentAgent/list.do'  }">
			<spring:message code="governmentAgent.generalList" />
		</jstl:when>
		<jstl:when
			test="${requestURI == 'governmentAgent/citizen/addGovernmentAgents.do'  }">
			<spring:message code="governmentAgent.addGovernmentAgentsFor" />
			<jstl:out value="${petition.name}" />
		</jstl:when>
	</jstl:choose>
</h3>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="governmentAgents" requestURI="${ requestURI }" id="row">

	<jstl:if
		test="${requestURI == 'governmentAgent/citizen/addGovernmentAgents.do' }">
		<display:column>
			<jstl:if test="${!petition.governmentAgents.contains(row) }">
				<a
					href="petition/citizen/addGovernmentAgent.do?petitionId=${petition.id}&governmentAgentId=${row.id}"><spring:message
						code="governmentAgent.add" /></a>
			</jstl:if>
			<jstl:if test="${petition.governmentAgents.contains(row) }">
				<a
					href="petition/citizen/removeGovernmentAgent.do?petitionId=${petition.id}&governmentAgentId=${row.id}"><spring:message
						code="governmentAgent.remove" /></a>
			</jstl:if>
		</display:column>
	</jstl:if>

	<jstl:if
		test="${requestURI != 'governmentAgent/citizen/addGovernmentAgents.do' }">
		<spring:message code="governmentAgent.show" var="showHeader" />
		<display:column title="${showHeader}">
			<a href="governmentAgent/display.do?governmentAgentId=${row.id}">
				<spring:message code="user.display" />
			</a>
		</display:column>
	</jstl:if>

	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="user.nickname" var="nicknameHeader" />
	<display:column property="nickname" title="${nicknameHeader}"
		sortable="true" />

	<%-- 	<spring:message code="user.petitions" var="jorlHeader" /> --%>
	<%-- 	<display:column title="${jorlHeader}"> --%>
	<%-- 		<a href="petitions/list.do?citizenId=${row.id}"> <spring:message --%>
	<%-- 				code="user.displayPetitions" /> --%>
	<!-- 		</a> -->
	<%-- 	</display:column> --%>

</display:table>

<spring:message var="backValue" code="agent.back" />
<jstl:choose>
	<jstl:when test="${requestURI == 'governmentAgent/citizen/addGovernmentAgents.do' }">
		<input type="button" name="back" value="${backValue}"
			onclick="javascript: relativeRedir('petition/citizen/edit.do?petitionId=${petition.id}');" />
	</jstl:when>
	<jstl:otherwise>
		<input type="button" name="back" value="${backValue}"
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</jstl:otherwise>
</jstl:choose>



