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
	<jstl:out value="${agent.name}" />
</h3>
<jstl:if test="${ agent.surname!=null }">
	<h3>
		<b><spring:message code="user.surname" />:&nbsp;</b>
		<jstl:out value="${agent.surname}" />
	</h3>
</jstl:if>

<h3>
	<b><spring:message code="user.nif" />:&nbsp;</b>
	<jstl:out value="${agent.nif}" />
</h3>

<jstl:if test="${ agent.email!=null }">
	<b><spring:message code="user.email" />:&nbsp;</b>
	<jstl:out value="${agent.email}" />
	<br />
</jstl:if>

<jstl:if test="${ agent.nickname!=null }">
	<b><spring:message code="user.email" />:&nbsp;</b>
	<jstl:out value="${agent.nickname}" />
	<br />
</jstl:if>

<jstl:if test="${ agent.phone!=null }">
	<b><spring:message code="user.phone" />:&nbsp;</b>
	<jstl:out value="${agent.phone}" />
	<br />
</jstl:if>

<b><spring:message code="user.address" />:&nbsp;</b>
<jstl:out value="${agent.address}" />
<br />

<b><spring:message code="agent.bankCode" />:&nbsp;</b>
<jstl:out value="${agent.bankCode}" />
<br />

<jstl:choose>
	<jstl:when test="${agent.canCreateMoney }">
		<p class="goodThing">
			<spring:message code="governmentAgent.canCreateMoney" />
		</p>
		<security:authorize access="hasRole('GOVERNMENTAGENT')">
			<input type="button"
				value="<spring:message code="governmentAgent.denied" />"
				onclick="javascript: window.location.assign('bankAgent/governmentAgent/denied.do?agentId=${agent.id}')" />
		</security:authorize>

	</jstl:when>
	<jstl:otherwise>
		<p class="badThing">
			<spring:message code="governmentAgent.canNotCreateMoney" />
		</p>
	</jstl:otherwise>
</jstl:choose>

<acme:cancel code="user.back" url="bankAgent/list.do" />

