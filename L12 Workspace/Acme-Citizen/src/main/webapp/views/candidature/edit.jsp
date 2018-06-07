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

<form:form action="candidature/citizen/edit.do" modelAttribute="candidatureForm">

	<form:hidden path="id"/>
	<form:hidden path="electionId"/>
	
	<acme:textbox code="candidature.partyLogo" path="partyLogo"/>
	<br/>
	
	<acme:textbox code="candidature.electoralProgram" path="electoralProgram"/>
	<br/>
	
	<acme:textarea code="candidature.description" path="description"/>
	<br/>
	
	<acme:submit name="save" code="petition.save"/>
	&nbsp;
	<acme:cancel url="election/display.do?electionId=${candidatureForm.electionId}" code="candidature.back"/>
	
</form:form>