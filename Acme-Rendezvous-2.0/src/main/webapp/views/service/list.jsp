<%-- list.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="services" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<jstl:if test="${row.cancelled == false && empty row.requests}">
				<a href="service/admin/cancel.do?serviceId=${row.id}"><spring:message code="service.cancel"/></a>
			</jstl:if>
		</display:column>	
	</security:authorize>
	
	<jstl:if test="${requestURI == 'service/manager/list.do'}">
		<display:column>
			<a href="service/manager/edit.do?serviceId=${row.id}"><spring:message code="service.edit"/></a>
		</display:column>
	</jstl:if>
	
	<display:column>
		<jstl:if test="${row.cancelled == false}">
			<a href="service/display.do?serviceId=${row.id}"><spring:message code="service.display"/></a>
		</jstl:if>
	</display:column>
		
	<spring:message var="nameHeader" code="service.name"/>
	<display:column property="name" title="${nameHeader}"/>
	
	<jstl:if test="${requestURI == 'service/list.do'}">
		<spring:message var="managerHeader" code="service.manager"/>
		<display:column title="${nameHeader}">
			<jstl:out value="${row.manager.name} ${row.manager.surname}"/>
		</display:column>
	</jstl:if>
	
	<spring:message var="cancelledHeader" code="service.cancelled"/>
	<display:column title="${cancelledHeader}" sortable="true">
		<jstl:choose>
			<jstl:when test="${row.cancelled == true}">
				<spring:message code="service.yes"/>
			</jstl:when>
			<jstl:when test="${row.cancelled == false}">
				<spring:message code="service.no"/>
			</jstl:when>
		</jstl:choose>
	</display:column>
	
</display:table>

<%-- <jstl:if test="${requestURI == 'service/manager/list.do'}"> --%>
<security:authorize access="hasRole('MANAGER')">
	<a href="service/manager/create.do"><spring:message code="service.create"/></a>
	<br/>
</security:authorize>
<%-- </jstl:if> --%>

<acme:cancel code="service.cancel" url="welcome/index.do"/>