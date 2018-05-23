<%-- edit.jsp de Application --%>

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

<form:form action="chirp/governmentAgent/edit.do"
	modelAttribute="chirpForm">

	<form:hidden path="id" />
	<form:hidden path="governmentAgentId" />
	<jstl:if test="${chirpForm.id == 0}">
		<form:hidden path="publicationMoment" />
	</jstl:if>

	<acme:textbox code="chirp.title" path="title" />
	<br />
	
	<acme:textarea code="chirp.content" path="content" />
	<br />
	
	<jstl:if test="${chirpForm.id != 0}">
		<acme:textbox code="chirp.publicationMoment" path="publicationMoment" readonly="readonly"/>
		<br/>
	</jstl:if>
	
	<acme:textbox code="chirp.image" path="image" />
	<br />
	
	<acme:textbox code="chirp.link" path="link" />
	<br />
	
	<acme:submit name="save" code="chirp.save" />
	<acme:cancel url="chirp/list.do" code="chirp.back"/>

</form:form>
