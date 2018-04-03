<%-- edit.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:choose>
	<jstl:when test="${rendezvous.deleted == false}">
		<h3><b><spring:message code="rendezvous.name"/>:&nbsp;</b><jstl:out value="${rendezvous.name}"/></h3>
		
		<img src="<jstl:out value="${rendezvous.picture}"/>" width="450" height="174">
		<br/>
		
		<b><spring:message code="rendezvous.description"/>:&nbsp;</b><jstl:out value="${rendezvous.description}"/>
		<br/>
		
		<spring:message var="patternDate" code="rendezvous.pattern.date"/>
		<b><spring:message code="rendezvous.moment"/>:&nbsp;</b><fmt:formatDate value="${rendezvous.moment}" pattern="${patternDate}"/>
		<br/>
		
		<b><spring:message code="rendezvous.organiser"/>:&nbsp;</b><a href="user/display.do?userId=${rendezvous.organiser.id}"><jstl:out value="${rendezvous.organiser.name} ${rendezvous.organiser.surname}"/></a>
		<br/>
		
		<jstl:if test="${rendezvous.gpsCoordinate != null}">
			<fieldset>
				<legend><spring:message code="rendezvous.gpsCoordinate"/></legend>
				
				<b><spring:message code="rendezvous.gpsCoordinate.latitude"/>:&nbsp;</b><jstl:out value="${rendezvous.gpsCoordinate.latitude}º"/>
				<br/>
				<b><spring:message code="rendezvous.gpsCoordinate.longitude"/>:&nbsp;</b><jstl:out value="${rendezvous.gpsCoordinate.longitude}º"/>
				<br/>
			</fieldset>
		</jstl:if>
		
		<jstl:if test="${rendezvous.adult == true}">
			<h3 style="text-transform: uppercase; color: red;"><b><spring:message code="rendezvous.onlyAdults"/></b></h3>
		</jstl:if>
		
		<jstl:if test="${not empty rendezvous.attendants}">
			<a href="user/listRendezvousAttends.do?rendezvousId=${rendezvous.id}"><spring:message code="rendezvous.listAttendants"/></a>
			<br/>
		</jstl:if>
		
		<jstl:if test="${not empty rendezvous.linkedRendezvouses}">
			<a href="rendezvous/list.do?rendezvousId=${rendezvous.id}"><spring:message code="rendezvous.listLinkedRendezvouses"/></a>
			<br/>
		</jstl:if>
		
		<jstl:if test="${not empty rendezvous.announcements}">
			<a href="announcement/list.do?rendezvousId=${rendezvous.id}"><spring:message code="rendezvous.listAnnouncements"/></a>
			<br/>
		</jstl:if>
		
		<jstl:if test="${not empty rendezvous.comments}">
			<a href="comment/list.do?rendezvousId=${rendezvous.id}"><spring:message code="rendezvous.listComments"/></a>
			<br/>
		</jstl:if>
		
		<security:authorize access="isAuthenticated()">
			<jstl:if test="${not empty rendezvous.requests}">
				<a href="service/list.do?rendezvousId=${rendezvous.id}"><spring:message code="rendezvous.listServices"/></a>
				<br/>
			</jstl:if>
		</security:authorize>
		
		<jstl:choose>
			<jstl:when test="${areRSPVd == false}">
				<button type="button" onclick="javascript: relativeRedir('rendezvous/user/rspv.do?rendezvousId=${rendezvous.id}')" ><spring:message code="rendezvous.makeRSPV" /></button>
				<br/>
			</jstl:when>
			<%-- <jstl:when test="${areRSPVd == false and not empty rendezvous.questions}">
				<button type="button" onclick="javascript: relativeRedir('rendezvous/user/rspv.do?rendezvousId=${rendezvous.id}')" ><spring:message code="rendezvous.makeRSPV" /></button>
				<br/>
			</jstl:when> --%>
			<jstl:when test="${areRSPVd}">
				<button type="button" onclick="javascript: relativeRedir('rendezvous/user/unrspv.do?rendezvousId=${rendezvous.id}')" ><spring:message code="rendezvous.cancelRSPV" /></button>
				<br/>
			</jstl:when>
			<jstl:when test="${areRSPVd == null}">
				
			</jstl:when>
		</jstl:choose>
		
	</jstl:when>
	
	<jstl:when test="${rendezvous.deleted == true}">
		<h3 style="text-transform: uppercase; color: red;"><b><spring:message code="rendezvous.isDeleted"/></b></h3>
	</jstl:when>
	
</jstl:choose>

<acme:cancel url="rendezvous/list.do" code="rendezvous.back"/>