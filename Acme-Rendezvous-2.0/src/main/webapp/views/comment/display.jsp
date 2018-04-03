<%-- display.jsp de Comment --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="comment/display.do" modelAttribute="commentForm">
	

<img src="<jstl:out value="${comment.picture}"/>" width="450" height="174">
<br/>

<spring:message var="patternDate" code="comment.pattern.date"/>
<b><spring:message code="comment.moment"/>:&nbsp;</b><fmt:formatDate value="${comment.moment}" pattern="${patternDate}"/>
<br/>

<b><spring:message code="comment.text"/>:&nbsp;</b><jstl:out value="${comment.text}"/>
<br/>

<b><spring:message code="comment.rendezvous"/>:&nbsp;</b><jstl:out value="${comment.rendezvous.name}"/>
<br/>

<b><spring:message code="comment.user"/>:&nbsp;</b><jstl:out value="${comment.user.name}"/>
<br/>

</form:form>