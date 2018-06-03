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
	<jstl:out value="${governmentAgent.name}" />
</h3>
<jstl:if test="${ governmentAgent.surname!=null }">
	<h3>
		<b><spring:message code="user.surname" />:&nbsp;</b>
		<jstl:out value="${governmentAgent.surname}" />
	</h3>
</jstl:if>

<h3>
	<b><spring:message code="user.nif" />:&nbsp;</b>
	<jstl:out value="${governmentAgent.nif}" />
</h3>

<jstl:if test="${ governmentAgent.email!=null }">
	<b><spring:message code="user.email" />:&nbsp;</b>
		<jstl:out value="${governmentAgent.email}" />
	<br />
</jstl:if>

<jstl:if test="${ governmentAgent.nickname!=null }">
	<b><spring:message code="user.email" />:&nbsp;</b>
		<jstl:out value="${governmentAgent.nickname}" />
	<br />
</jstl:if>

<jstl:if test="${ governmentAgent.phone!=null }">
	<b><spring:message code="user.phone" />:&nbsp;</b>
		<jstl:out value="${governmentAgent.phone}" />
	<br />
</jstl:if>

<b><spring:message code="user.address" />:&nbsp;</b>
<jstl:out value="${governmentAgent.address}" />
<br />

<b><spring:message code="governmentAgent.registerCode" />:&nbsp;</b>
<jstl:out value="${governmentAgent.registerCode}" />
<br />

<jstl:choose>
	<jstl:when test="${ governmentAgent.canOrganiseElection }">
		<p class="goodThing"><spring:message code="governmentAgent.canOrganiseElections" /></p>
	</jstl:when>
	<jstl:otherwise>
		<p class="badThing"><spring:message code="governmentAgent.canNotOrganiseElections" /></p>	
	</jstl:otherwise>
</jstl:choose>

<jstl:choose>
	<jstl:when test="${ governmentAgent.canCreateMoney }">
		<p class="goodThing"><spring:message code="governmentAgent.canCreateMoney" /></p>
	</jstl:when>
	<jstl:otherwise>
		<p class="badThing"><spring:message code="governmentAgent.canNotCreateMoney" /></p>	
	</jstl:otherwise>
</jstl:choose>

<h3>
	<spring:message code="governmentAgent.chirps" />
</h3>

<display:table name="${chirps}" id="row"
	requestURI="governmentAgent/display.do" pagesize="5" class="displaytag">

	<spring:message var="titleHeader" code="user.title" />
	<display:column title="${titleHeader}">
		<a href="chirp/display.do?chirpId=${row.id}"><jstl:out
				value="${row.title}" /></a>
	</display:column>

	<spring:message var="titleHeader" code="governmentAgent.content" />
	<display:column title="Contenido">
		<jstl:out
				value="${row.content}" />
	</display:column>
	
	<spring:message var="titleHeader" code="governmentAgent.image" />
	<display:column title="Imagen">
		<img src="${ row.image }" style="display: block;
  max-width:230px;
  max-height:95px;
  width: auto;
  height: auto;"/>
	</display:column>

</display:table>

<acme:cancel code="user.back" url="governmentAgent/list.do" />

