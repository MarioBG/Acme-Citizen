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


<jstl:if test="${principal.canCreateMoney == false }">

	<spring:message code="economicTransaction.noCreteMoney"></spring:message>

</jstl:if>

<jstl:if test="${principal.canCreateMoney == true }">

	<form:form action="transaction/createMoney.do"
		modelAttribute="economicTransaction">

		<form:hidden path="id" />
		<form:hidden path="transactionMoment" />
		<form:hidden path="doMoney" />
		<form:hidden path="debtor" />

		<acme:textbox code="economicTransaction.quantity" path="quantity" />
		<br />
		<acme:textarea code="economicTransaction.concept" path="concept" />
		<br />
		<form:label path="creditor">
			<spring:message code="economicTransaction.creditor" />:
	</form:label>
		<form:select id="creditor" path="creditor">
			<form:option value="0" label="----" />
			<form:options items="${bankAccounts}" itemValue="id"
				itemLabel="actor.name" />
		</form:select>
		<form:errors cssClass="error" path="creditor" />
		<br />
		<br />

		<acme:submit name="save" code="economicTransaction.save" />
	&nbsp;
	<acme:cancel url="welcome/index.do" code="economicTransaction.back" />

	</form:form>

</jstl:if>