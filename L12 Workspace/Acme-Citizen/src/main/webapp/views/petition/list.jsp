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
			test="${citizen == null and requestURI == 'petition/list.do'}">
			<spring:message code="petition.availablePetitions" />
		</jstl:when>
		<jstl:when
			test="${citizen != null and requestURI == 'petition/list.do'}">
			<spring:message code="petition.petitionsOf" />
			<jstl:out value="${citizen.name}" />
		</jstl:when>
		<jstl:when test="${requestURI == 'petition/citizen/list.do'}">
			<spring:message code="petition.yourPetitions" />
		</jstl:when>
	</jstl:choose>
</h3>

<display:table name="petitions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('GOVERNMENTAGENT')">
		<display:column>
			<a href="petition/governmentAgent/delete.do?petitionId=${row.id}"><spring:message
					code="petition.delete" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('CITIZEN')">
		<display:column>
			<security:authentication property="principal" var="loggedactor" />
			<jstl:if test="${row.citizen.userAccount.id eq loggedactor.id}">
				<jstl:if test="${row.finalVersion == false}">
					<a href="petition/citizen/edit.do?petitionId=${row.id}"><spring:message
							code="petition.edit" /></a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column>
		<a href="petition/display.do?petitionId=${row.id}"><spring:message
				code="petition.display" /></a>
	</display:column>

	<spring:message var="nameHeader" code="petition.name" />
	<display:column property="name" title="${nameHeader}" />

	<spring:message var="creationMomentHeader"
		code="petition.creationMoment" />
	<spring:message var="formatDate" code="petition.format.date" />
	<display:column property="creationMoment"
		title="${creationMomentHeader}" format="${formatDate}" sortable="true" />

	<jstl:if test="${requestURI != 'petition/citizen/list.do' }">
		<spring:message var="citizenHeader" code="petition.citizen" />
		<display:column title="${citizenHeader}">
			<a href="citizen/display.do?citizenId=${row.citizen.id}"><jstl:out
					value="${row.citizen.name}" /></a>
		</display:column>
	</jstl:if>

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
	<display:column title="${isResolvedHeader}" sortable="true">
		<jstl:choose>
			<jstl:when test="${row.resolved == true}">
				<spring:message code="petition.yes" />
			</jstl:when>
			<jstl:when test="${row.resolved == false}">
				<spring:message code="petition.no" />
			</jstl:when>
		</jstl:choose>
	</display:column>

	<security:authorize access="hasRole('GOVERNMENTAGENT')">
		<spring:message var="isDeletedHeader" code="petition.isDeleted" />
		<display:column title="${isDeletedHeader}" sortable="true">
			<jstl:choose>
				<jstl:when test="${row.deleted == true}">
					<spring:message code="petition.yes" />
				</jstl:when>
				<jstl:when test="${row.deleted == false}">
					<spring:message code="petition.no" />
				</jstl:when>
			</jstl:choose>
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('CITIZEN')">
	<a href="petition/citizen/create.do"><spring:message
			code="petition.create" /></a>
	<br />
</security:authorize>

<spring:message var="backValue" code="petition.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />
