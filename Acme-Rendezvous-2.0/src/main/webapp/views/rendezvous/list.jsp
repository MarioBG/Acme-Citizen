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

<display:table name="rendezvouses" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${requestURI == 'rendezvous/admin/list.do'}">
			<display:column>
				<a href="rendezvous/admin/delete.do?rendezvousId=${row.id}"><spring:message code="rendezvous.delete"/></a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	
	<jstl:choose>
		<jstl:when test="${requestURI == 'rendezvous/user/list-organised.do' and row.finalVersion == false and row.deleted == false}">
			<display:column>
				<a href="rendezvous/user/edit.do?rendezvousId=${row.id}"><spring:message code="rendezvous.edit"/></a>
			</display:column>
		</jstl:when>
		<jstl:when test="${requestURI == 'rendezvous/user/list-organised.do' and row.finalVersion == true and row.deleted == false}">
			<display:column>
				<a href="rendezvous/user/list-link.do?rendezvousId=${row.id}"><spring:message code="rendezvous.assignRendezvouses"/></a>
			</display:column>
		</jstl:when>
		<jstl:when test="${requestURI == 'rendezvous/user/list-rsvp.do'}">
			<display:column>
				<a href="comment/user/create.do?rendezvousId=${row.id}"><spring:message code="rendezvous.comment"/></a>
			</display:column>
		</jstl:when>
		<jstl:when test="${requestURI == 'rendezvous/user/list-link.do'}">
			<jstl:if test="${!rendezvousSource.linkedRendezvouses.contains(row)}">
				<display:column>
					<a href="rendezvous/user/assign.do?rendezvousSourceId=${rendezvousSource.id}&rendezvousTargetId=${row.id}"><spring:message code="rendezvous.assign"/></a>
				</display:column>
			</jstl:if>
			<jstl:if test="${rendezvousSource.linkedRendezvouses.contains(row)}">
				<display:column>
					<a href="rendezvous/user/unassign.do?rendezvousSourceId=${rendezvousSource.id}&rendezvousTargetId=${row.id}"><spring:message code="rendezvous.unassign"/></a>
				</display:column>
			</jstl:if>
		</jstl:when>
		<jstl:when test="${requestURI == 'rendezvous/admin/list.do'}">
		
		</jstl:when>
		<jstl:otherwise>
			<display:column/>
		</jstl:otherwise>
	</jstl:choose>
	
	<jstl:if test="${requestURI != 'rendezvous/user/list-link.do'}">
		<display:column>
			<a href="rendezvous/display.do?rendezvousId=${row.id}"><spring:message code="rendezvous.display"/></a>
		</display:column>
	</jstl:if>
		
	<spring:message var="nameHeader" code="rendezvous.name"/>
	<display:column property="name" title="${nameHeader}"/>
	
	<spring:message var="momentHeader" code="rendezvous.moment"/>
	<spring:message var="formatDate" code="rendezvous.format.date"/>
	<display:column property="moment" title="${momentHeader}" format="${formatDate}" sortable="true"/>
	
	<spring:message var="adultHeader" code="rendezvous.adult"/>
	<display:column title="${adultHeader}" sortable="true">
		<jstl:choose>
			<jstl:when test="${row.adult == true}">
				<spring:message code="rendezvous.yes"/>
			</jstl:when>
			<jstl:when test="${row.adult == false}">
				<spring:message code="rendezvous.no"/>
			</jstl:when>
		</jstl:choose>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('USER')">
	<a href="rendezvous/user/create.do"><spring:message code="rendezvous.create"/></a>
	<br/>
</security:authorize>

<spring:message var="cancelValue" code="rendezvous.cancel" />
<jstl:choose>
	<jstl:when test="${requestURI == 'rendezvous/user/list-link.do'}">
		<input type="button" name="cancel" value="${cancelValue}" onclick="javascript: relativeRedir('rendezvous/user/edit.do?rendezvousId=${rendezvousSource.id}');" />
	</jstl:when>
	<jstl:otherwise>
		<input type="button" name="cancel" value="${cancelValue}" onclick="javascript: relativeRedir('welcome/index.do');" />
	</jstl:otherwise>
</jstl:choose>


