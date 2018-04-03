<%-- list.jsp de Question --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="questions" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		
	<spring:message var="nameHeader" code="question.text"/>
	<display:column property="content" title="${nameHeader}"/>
	
	<jstl:if test="${isPrincipal}">
		<display:column>
			<a href="question/user/edit.do?questionId=${row.id}"><spring:message code="question.edit"/></a>
		</display:column>
		
		<display:column>
			<a href="question/user/delete.do?questionId=${row.id}"><spring:message code="question.delete"/></a>
		</display:column>
	</jstl:if>
	
	<display:column>
		<a href="answer/user/list.do?questionId=${row.id}"><spring:message code="question.seeAnswers"/></a>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('USER')">
	<a href="question/user/create.do"><spring:message code="question.create"/></a>
	<br/>
</security:authorize>

<spring:message var="cancelValue" code="question.cancel" />
<input type="button" name="cancel" value="${cancelValue}" onclick="javascript: relativeRedir('welcome/index.do');" />