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

<h3>
	<jstl:choose>
		<jstl:when test="${requestURI == 'citizen/list.do'  }">
			<spring:message code="citizen.generalList" />
		</jstl:when>
		<jstl:when test="${requestURI == 'user/user/list-followers.do'  }">
			<spring:message code="user.followers" />
		</jstl:when>
		<jstl:when test="${requestURI == 'user/user/list-followed.do'  }">
			<spring:message code="user.followed" />
		</jstl:when>
	</jstl:choose>
</h3>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="citizens" requestURI="${requestURI }" id="row">

	<!-- Attributes -->
	<spring:message code="citizen.show" var="showHeader" />
	<display:column title="${showHeader}">
		<a href="citizen/display.do?citizenId=${row.id}"> <spring:message
				code="user.display" />
		</a>
	</display:column>

	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="user.nickname" var="nicknameHeader" />
	<display:column property="nickname" title="${nicknameHeader}"
		sortable="true" />

	<spring:message code="user.petitions" var="jorlHeader" />
	<display:column title="${jorlHeader}">
		<a href="petition/list.do?citizenId=${row.id}"> <spring:message
				code="user.displayPetitions" />
		</a>
	</display:column>

</display:table>

<spring:message var="backValue" code="agent.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />
