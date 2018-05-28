<%--
 * display.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3>
	<b><spring:message code="user.name" />:&nbsp;</b>
	<jstl:out value="${citizen.name}" />
</h3>

<h3>
	<b><spring:message code="user.surname" />:&nbsp;</b>
	<jstl:out value="${citizen.surname}" />
</h3>

<b><spring:message code="user.email" />:&nbsp;</b>
<jstl:out value="${citizen.email}" />
<br />

<b><spring:message code="user.phone" />:&nbsp;</b>
<jstl:out value="${citizen.phone}" />
<br />

<b><spring:message code="user.address" />:&nbsp;</b>
<jstl:out value="${citizen.address}" />
<br />

<h3>
	<spring:message code="citizen.candidatures" />
</h3>

<display:table name="${candidatures}" id="row"
	requestURI="user/display.do" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="article/admin/delete.do?articleId=${row.id}"><spring:message
					code="user.delete" /></a>
		</display:column>
	</security:authorize>

	<spring:message var="titleHeader" code="user.title" />
	<display:column title="${titleHeader}">
		<a href="candidature/display.do?articleId=${row.id}"><jstl:out
				value="${row.description}" /></a>
	</display:column>
	
	<spring:message var="titleHeader" code="user.title" />
	<display:column title="${titleHeader}">
		<img src="${ row.partyLogo }" style="display: block;
  max-width:230px;
  max-height:95px;
  width: auto;
  height: auto;"/>
	</display:column>

</display:table>

<acme:cancel code="user.back" url="citizen/list.do" />

