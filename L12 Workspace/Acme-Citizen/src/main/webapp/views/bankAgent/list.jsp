<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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

<!-- displaying grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="agents" requestURI="bankAgent/list.do" id="row">

	<!-- Attributes -->

	<display:column title="${articlesHeader}">
		<a href="bankAgent/display.do?agentId=${row.id}"> <spring:message
				code="agent.display" />
		</a>
	</display:column>

	<spring:message code="agent.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="agent.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />
	<spring:message code="agent.bankCode" var="codeHeader" />
	<display:column property="bankCode" title="${codeHeader}"
		sortable="true" />

	<display:column>
		<jstl:if test="${principal != null}">
			<jstl:if test="${principal.bankAccount == null}">
				<input type="button"
					value="<spring:message code="agent.createBankAccount" />"
					onclick="javascript: window.location.assign('message/create.do')" />
			</jstl:if>
		</jstl:if>
	</display:column>

</display:table>

<spring:message var="backValue" code="agent.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />





