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

<form:form action="welcomemessage/governmentAgent/edit.do"
	modelAttribute="welcomeMessageForm">

	<form:hidden path="id" />

	<acme:textbox code="welcomeMessage.languageCode" path="languageCode" />
	
	<acme:textbox code="welcomeMessage.content" path="content" />

	<acme:submit name="save" code="welcomeMessage.save" />
	&nbsp;
	<acme:cancel url="configuration/governmentAgent/edit.do" code="welcomeMessage.back" />

</form:form>