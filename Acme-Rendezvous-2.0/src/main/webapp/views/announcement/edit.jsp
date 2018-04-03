<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action ="${requestURI }" modelAttribute="announcementForm">


	<acme:textbox code="announcement.title" path="title"/>
	<br/>
	<acme:textarea code="announcement.description" path="description"/>
	<br/>
	<acme:select items="${rendezvous }" itemLabel="name" code="announcement.rendezvous" path="rendezvous"/>
	<br/>
	
	<input type="button" name="cancel"
		value="<spring:message code="announcement.cancel" />"
		onclick="javascript: relativeRedir('/');" />
	
	<security:authorize access="hasRole('USER')">
		<input type="submit" name="save"
			value="<spring:message code="announcement.save" />" />&nbsp; 

	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${announcement.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="announcement.delete" />"
				onclick="return confirm('<spring:message code="announcement.confirm.delete" />')" />&nbsp;
	</jstl:if>
	</security:authorize>

</form:form>	