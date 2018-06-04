<%-- list.jsp de Application --%>

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



<display:table name="moneyMade" id="row"
	requestURI="transaction/listMoneyCreate.do" pagesize="5"
	class="displaytag">


	<spring:message var="quantityHeader"
		code="economicTransaction.quantity" />
	<display:column property="quantity" title="${quantityHeader}" />

	<spring:message var="transactionMomentHeader"
		code="economicTransaction.transactionMoment" />
	<display:column property="transactionMoment"
		title="${transactionMomentHeader}" sortable="true" />

	<spring:message var="creditorHeader"
		code="economicTransaction.creditorBankTo" />
	<display:column property="creditor.accountNumber"
		title="${creditorHeader}" />

	<spring:message var="conceptHeader" code="economicTransaction.concept" />
	<display:column property="concept" title="${conceptHeader}" />

	<spring:message var="agentHeader" code="economicTransaction.agent" />
	<display:column>
		<jstl:out value="${row.debtor.actor.name} ${row.debtor.actor.surname}" />
	</display:column>

</display:table>

<security:authorize access="hasAnyRole('BANKAGENT', 'GOVERNMENTAGENT')">
	<a href="transaction/create.do"><spring:message
			code="economicTransaction.createMoney" /></a>
	<br />
</security:authorize>

<spring:message var="backValue" code="economicTransaction.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />