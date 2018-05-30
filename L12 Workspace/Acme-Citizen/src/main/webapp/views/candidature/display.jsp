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

<img src="<jstl:out value="${candidature.partyLogo}"/>" style="display: block;
  max-width:230px;
  max-height:95px;
  width: auto;
  height: auto;"/>
<br />

<b><spring:message code="candidature.electoralProgram" />:&nbsp;</b>
<a href="<jstl:out value="${candidature.electoralProgram}"/>"><spring:message
		code="candidature.link" /></a>
<br />

<b><spring:message code="candidature.description" />:&nbsp;</b>
<jstl:out value="${candidature.description}" />
<br />

<jstl:if test="${not empty candidature.comments}">
	<a href="comment/list.do?commentableId=${candidature.id}"><spring:message
			code="candidature.listComments" /></a>
	<br />
</jstl:if>

<security:authorize access="hasAnyRole('CITIZEN','GOVERNMENTAGENT')">
	<a href="comment/actor/create.do?commentableId=${candidature.id}"><spring:message
			code="candidature.createComment" /></a>
	<br />
</security:authorize>

<jstl:if test="${not empty candidature.candidates}">

	<h3>
		<spring:message code="candidature.candidates" />
	</h3>

	<display:table name="candidature.candidates" id="row"
		requestURI="candidature/display.do" pagesize="5" class="displaytag">

		<spring:message code="candidature.listOrder" var="listOrderHeader" />
		<display:column title="${listOrderHeader}" sortable="true">
			<jstl:out value="${row.listOrder}" />
		</display:column>

		<spring:message code="candidature.citizen" var="citizenHeader" />
		<display:column title="${citizenHeader}">
			<a href="citizen/display.do?citizenId=${row.citizen.id}"><jstl:out
					value="${row.citizen.name}" /></a>
		</display:column>

	</display:table>
</jstl:if>

<security:authorize access="hasRole('CITIZEN')">
	<jstl:if test="${isCandidate == false and dateOneDayBeforeCelebrationDate.after(date)}">
		<a href="candidate/citizen/register.do?candidatureId=${candidature.id}"><spring:message code="candidature.registerAsCandidate"/></a>
	</jstl:if>
</security:authorize>

<acme:cancel
	url="candidature/list.do?electionId=${candidature.election.id}"
	code="candidature.back" />