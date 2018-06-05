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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:choose>
	<jstl:when test="${principal.bankAccount == null}">

		<h3>
			<spring:message code="bankAccount.badthing"></spring:message>
		</h3>
		<br>
		<a href="bankAgent/list.do"><spring:message
				code="lottery.bankAccount" /></a>

	</jstl:when>
	<jstl:otherwise>


		<h2>
			<b><spring:message code="bankAccount.accountNumber" />:&nbsp;</b>
			<jstl:out value="${bankAccount.accountNumber}" />
		</h2>

		<h3>
			<b> <spring:message var="formatGeneralPrice"
					code="master.page.format.general.price" /> <spring:message
					code="bankAccount.money" />:&nbsp;
			</b> <font color="green"> <jstl:out value="${bankAccount.money}" />
				${formatGeneralPrice}
			</font>
		</h3>



		<b><spring:message code="bankAccount.bankAgent" />:&nbsp;</b>
		<a href="bankAgent/display.do?agentId=${bankAccount.bankAgent.id}">
			<jstl:out
				value="${bankAccount.bankAgent.name} ${bankAccount.bankAgent.surname}" />
		</a>
		<br />
	</jstl:otherwise>
</jstl:choose>





