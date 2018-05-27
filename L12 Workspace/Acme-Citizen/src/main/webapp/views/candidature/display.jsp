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

<img src="<jstl:out value="${candidature.partyLogo}"/>" />
<br />

<b><spring:message code="candidature.electoralProgram" />:&nbsp;</b>
<a href="<jstl:out value="${candidature.electoralProgram}"/>"><spring:message
		code="candidature.link" /></a>
<br />

<b><spring:message code="candidature.description" />:&nbsp;</b>
<jstl:out value="${candidature.description}" />
<br />

<jstl:if test="${candidature.candidates not empty}">

	<h3>
		<spring:message code="candidature.candidates" />
	</h3>

	<display:table name="candidature.candidates" id="row"
		requestURI="candidature/display.do" pagesize="5" class="displaytag">
		
		<spring:message code="candidature.listOrder" var="listOrderHeader" />
		<display:column title="${listOrderHeader}" sortable="true">
			<jstl:out value="${row.listOrder}"/>
		</display:column>

		<spring:message code="candidature.citizen" var="citizenHeader" />
		<display:column title="${citizenHeader}">
			<a href="citizen/display.do?cistizenId=${row.cistizen.id}"><jstl:out value="${row.citizen.name}"/></a>
		</display:column>

	</display:table>
</jstl:if>

<acme:cancel url="candidature/list.do?electionId=${candidature.election.id}" code="candidature.back" />