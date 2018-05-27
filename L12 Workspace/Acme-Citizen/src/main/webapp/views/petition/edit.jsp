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

<form:form action="petition/citizen/edit.do" modelAttribute="petitionForm">

	<form:hidden path="id"/>
	<form:hidden path="citizenId"/>
	<jstl:if test="${petitionForm.id == 0}">
		<form:hidden path="creationMoment"/>
	</jstl:if>
	
	<acme:textbox code="petition.name" path="title"/>
	<br/>
	
	<acme:textarea code="petition.description" path="description"/>
	<br/>
	
	<acme:textbox code="petition.picture" path="picture"/>
	<br/>
	
	<jstl:if test="${petitionForm.id != 0}">
		<acme:textbox code="petition.creationMoment" path="creationMoment" readonly="readonly"/>
	<br/>
	</jstl:if>
	
	<acme:checkbox code="petition.isFinal" path="finalVersion"/>
	<br/>
	
	<acme:submit name="save" code="petition.save"/>
	&nbsp;
	<acme:cancel url="petition/list.do" code="petition.back"/>
	
</form:form>