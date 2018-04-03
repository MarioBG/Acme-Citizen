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

<form:form action="rendezvous/user/edit.do" modelAttribute="rendezvousForm">

	<form:hidden path="id"/>
	<jstl:if test="${rendezvousForm.finalVersion == true}">
		<form:hidden path="finalVersion"/>
	</jstl:if>
	
	<acme:textbox code="rendezvous.name" path="name" readonly="${rendezvousForm.finalVersion}"/>
	<br/>
	
	<acme:textarea code="rendezvous.description" path="description" readonly="${rendezvousForm.finalVersion}"/>
	<br/>
	
	<acme:textbox code="rendezvous.picture" path="picture" readonly="${rendezvousForm.finalVersion}"/>
	<br/>
	
	<acme:textbox code="rendezvous.moment" path="moment" placeholder="dd/MM/yyyy HH:mm" readonly="${rendezvousForm.finalVersion}"/>
	<br/>
	
	<fieldset>
		<legend><spring:message code="rendezvous.gpsCoordinate"/>:</legend>
		
		<acme:textbox code="rendezvous.gpsCoordinate.latitude" path="latitude" placeholder="(-90.0, 90.0)" readonly="${rendezvousForm.finalVersion}"/>
		<br/>
		<acme:textbox code="rendezvous.gpsCoordinate.longitude" path="longitude" placeholder="(-180.0, 180.0)" readonly="${rendezvousForm.finalVersion}"/>
		<br/>
	</fieldset>
	
	<jstl:if test="${adultUser==true }">
		<acme:checkbox code="rendezvous.adult" path="adult" disabled="${rendezvousForm.finalVersion}"/>
		<br/>
	</jstl:if>
	
	<jstl:if test="${rendezvousForm.finalVersion == false}">
		<acme:checkbox code="rendezvous.finalVersion" path="finalVersion"/>
		<br/>
	</jstl:if>
	
	<jstl:if test="${rendezvousForm.id != 0}">
		<a href="rendezvous/user/list-link.do?rendezvousId=${rendezvousForm.id}"><spring:message code="rendezvous.assignRendezvouses"/></a>
		<br/>
		
		<a href="announcement/user/create.do?rendezvousId=${rendezvousForm.id}"><spring:message code="rendezvous.addAnnouncement"/></a>
		<br/>
		
		<%-- <a href="question/user/list.do?rendezvousId=${rendezvousForm.id}"><spring:message code="rendezvous.listQuestions"/></a>
		<br/> --%>
		
		<a href="request/user/create.do?rendezvousId=${rendezvousForm.id}"><spring:message code="rendezvous.makeRequest"/></a>
		<br/>
	</jstl:if>
	
	<jstl:if test="${rendezvousForm.finalVersion == false or rendezvousForm.deleted == false}">
		<acme:submit name="save" code="rendezvous.save"/>
		&nbsp;
		<jstl:if test="${rendezvousForm.id != 0}">
			<acme:submit name="delete" code="rendezvous.delete"/>
			&nbsp;
		</jstl:if>
	</jstl:if>
	
	<acme:cancel url="rendezvous/user/list-organised.do" code="rendezvous.cancel"/>
	
</form:form>