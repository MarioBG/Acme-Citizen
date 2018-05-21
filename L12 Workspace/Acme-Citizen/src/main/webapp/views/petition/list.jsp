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
		<jstl:when test="${requestURI == 'petition/list.do'  }">
			<spring:message code="petition.systemPetitions" />
		</jstl:when>
	</jstl:choose>
</h3>

<display:table name="petitions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<%-- 	<security:authorize access="hasRole('ADMIN')"> --%>
	<%-- 		<display:column> --%>
	<%-- 			<a href="newspaper/admin/delete.do?newspaperId=${row.id}"><spring:message --%>
	<%-- 					code="newspaper.delete" /></a> --%>
	<%-- 		</display:column> --%>
	<%-- 	</security:authorize> --%>

	<%-- 	<security:authorize access="hasRole('USER')"> --%>
	<%-- 		<jstl:if test="${requestURI == 'newspaper/user/listAddNewspapers.do'}"> --%>
	<%-- 			<display:column> --%>
	<%-- 				<jstl:choose> --%>
	<%-- 					<jstl:when test="${!volume.newspapers.contains(row)}"> --%>
	<!-- 						<a -->
	<%-- 							href="volume/user/addNewspaper.do?newspaperId=${row.id}&volumeId=${volume.id}"><spring:message --%>
	<%-- 								code="newspaper.addNewspaper" /></a> --%>
	<%-- 					</jstl:when> --%>
	<%-- 					<jstl:otherwise> --%>
	<!-- 						<a -->
	<%-- 							href="volume/user/removeNewspaper.do?newspaperId=${row.id}&volumeId=${volume.id}"><spring:message --%>
	<%-- 								code="newspaper.removeNewspaper" /></a> --%>
	<%-- 					</jstl:otherwise> --%>
	<%-- 				</jstl:choose> --%>
	<%-- 			</display:column> --%>
	<%-- 		</jstl:if> --%>
	<%-- 	</security:authorize> --%>
	<%-- 	<security:authorize access="hasRole('AGENT')"> --%>
	<%-- 			<display:column> --%>
	<!-- 				<a -->
	<%-- 					href="advertisement/agent/create.do?newspaperId=${row.id}"><spring:message --%>
	<%-- 						code="newspaper.createAdvertisement" /></a> --%>
	<%-- 			</display:column> --%>
	<%-- 	</security:authorize> --%>

	<display:column>
		<a href="petition/display.do?petitionId=${row.id}"><spring:message
				code="petition.display" /></a>
	</display:column>

	<display:column>
		<jstl:if test="${row.governmentAgents not empty}">
			<a href="governmentAgent/list.do?petitionId=${row.id}"><spring:message
					code="petition.listGovernmentAgents" /></a>
		</jstl:if>
	</display:column>

	<spring:message var="nameHeader" code="petition.name" />
	<display:column property="name" title="${nameHeader}" />

	<spring:message var="creationMomentHeader"
		code="petition.creationMoment" />
	<spring:message var="formatDate" code="petition.format.date" />
	<display:column property="creationMoment"
		title="${creationMomentHeader}" format="${formatDate}" sortable="true" />

	<spring:message var="citizenHeader" code="petition.citizen" />
	<display:column title="${nameHeader}">
		<a href="citizen/display.do?citizenId=${row.citizen.id}"><jstl:out
				value="${row.citizen.name}" /></a>
	</display:column>

	<spring:message var="isFinalHeader" code="petition.isFinal" />
	<display:column title="${isFinalHeader}" sortable="true">
		<jstl:choose>
			<jstl:when test="${row.finalVersion == true}">
				<spring:message code="petition.yes" />
			</jstl:when>
			<jstl:when test="${row.finalVersion == false}">
				<spring:message code="petition.no" />
			</jstl:when>
		</jstl:choose>
	</display:column>

	<spring:message var="isResolvedHeader" code="petition.isResolved" />
	<display:column title="${isFinalHeader}" sortable="true">
		<jstl:choose>
			<jstl:when test="${row.resolved == true}">
				<spring:message code="petition.yes" />
			</jstl:when>
			<jstl:when test="${row.resolved == false}">
				<spring:message code="petition.no" />
			</jstl:when>
		</jstl:choose>
	</display:column>

</display:table>

<security:authorize access="hasRole('CITIZEN')">
	<a href="petition/citizen/create.do"><spring:message
			code="petition.create" /></a>
	<br />
</security:authorize>

<spring:message var="backValue" code="petition.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />