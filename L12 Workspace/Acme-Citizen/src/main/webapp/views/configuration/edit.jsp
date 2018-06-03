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

<form:form action="configuration/governmentAgent/edit.do"
	modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="configuration.countryFlag" path="countryFlag" />
	<br />

	<acme:textbox code="configuration.defaultCountryCode"
		path="defaultCountryCode" />
	<br />

	<acme:textbox code="configuration.numberParliamentSeats"
		path="numberParliamentSeats" />
	<br />

	<acme:submit name="save" code="configuration.save" />
	<acme:cancel url="welcome/index.do" code="configuration.cancel" />

</form:form>

<h4>
	<spring:message code="configuration.editWelcomeMessages"
		var="welcomeHead" />
	<jstl:out value="${welcomeHead}"></jstl:out>
</h4>
<table class="displaytag" name="editWelcomeMessages">
	<tr>
		<th><spring:message code="configuration.langLocale" /></th>
		<th><spring:message code="configuration.welcomeContent" /></th>
		<th><spring:message code="configuration.editMessage" /></th>
	</tr>
	<jstl:forEach var="welcomeMessage" items="${welcomeMessages}">
		<tr>
			<td><jstl:out value="${ welcomeMessage.languageCode }"></jstl:out></td>
			<td><jstl:out value="${ welcomeMessage.content }"></jstl:out></td>
			<td><a
				href="welcomemessage/governmentAgent/edit.do?welcomeMessageId=${ welcomeMessage.id }"><spring:message
						code="configuration.editMessage" /></a> <a
				href="welcomemessage/governmentAgent/delete.do?welcomeMessageId=${ welcomeMessage.id }"><spring:message
						code="configuration.deleteMessage" /></a></td>
		</tr>
	</jstl:forEach>
	<tr>
		<td />
		<td />
		<td><a href="welcomemessage/governmentAgent/create.do"><spring:message
					code="configuration.createMessage" /></a></td>
	</tr>
</table>
