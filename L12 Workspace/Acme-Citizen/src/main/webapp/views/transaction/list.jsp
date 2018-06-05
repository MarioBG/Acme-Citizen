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



<h2>
	<spring:message code="economicTransaction.To"></spring:message>
</h2>
<display:table name="debtorTransactions" id="row"
	requestURI="transaction/list.do" pagesize="5" class="displaytag">


	<spring:message var="formatPrice" code="lottery.format.price" />
	<spring:message var="quantityHeader"
		code="economicTransaction.quantity" />
	<display:column property="quantity" title="${quantityHeader}"
		format="${formatPrice}" />

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


</display:table>

<h2>
	<spring:message code="economicTransaction.From"></spring:message>
</h2>
<display:table name="creditorTransactions" id="row"
	requestURI="transaction/list.do" pagesize="5" class="displaytag">

	<spring:message var="formatPrice" code="lottery.format.price" />
	<spring:message var="quantityHeader"
		code="economicTransaction.quantity" />
	<display:column property="quantity" title="${quantityHeader}"
		format="${formatPrice}" />

	<spring:message var="transactionMomentHeader"
		code="economicTransaction.transactionMoment" />
	<display:column property="transactionMoment"
		title="${transactionMomentHeader}" sortable="true" />

	<spring:message var="creditorHeader"
		code="economicTransaction.creditorBankFrom" />
	<display:column property="creditor.accountNumber"
		title="${creditorHeader}" />

	<spring:message var="conceptHeader" code="economicTransaction.concept" />
	<display:column property="concept" title="${conceptHeader}" />


</display:table>



<a href="transaction/create.do"><spring:message
		code="economicTransaction.create" /></a>
<br />

<spring:message var="backValue" code="economicTransaction.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />