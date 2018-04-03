<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="announcement" requestURI="${requestURI }" id="row" defaultsort="3" defaultorder="descending">	
	
	
	<display:column>
		<a href="announcement/display.do?announcementId=${row.id}"><spring:message code="announcement.display"/></a>
	</display:column>
	
	<spring:message code="announcement.title" var="titleHeader"/>
	<display:column property="title" title="${titleHeader}" sortable="false"/>
	
	<spring:message code="announcement.format.date" var="formatDate"/>
	<spring:message code="announcement.moment" var="momentHeader"/>
	<display:column property="moment" title="${momentHeader}" sortable="false" format="${formatDate}"/>
	
	<spring:message code="announcement.rendezvous" var="rendezvousHeader"/>
	<display:column title="${rendezvousHeader}">
		<a href="rendezvous/display.do?rendezvousId=${row.rendezvous.id}"><jstl:out value="${row.rendezvous.name}"/></a>
	</display:column>
	

</display:table>