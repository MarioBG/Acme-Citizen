<%-- list.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h3><spring:message code="answer.showing"/> ${question.content}</h3>

<display:table name="answers" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		
	<spring:message var="nameHeader" code="answer.text"/>
	<display:column property="content" title="${nameHeader}"/>
	
	<spring:message var="userHeader" code="answer.user"/>
	<display:column property="user.name" title="${userHeader}"/>
	
</display:table>

<spring:message var="cancelValue" code="question.cancel" />
<input type="button" name="cancel" value="${cancelValue}" onclick="javascript: relativeRedir('welcome/index.do');" />
