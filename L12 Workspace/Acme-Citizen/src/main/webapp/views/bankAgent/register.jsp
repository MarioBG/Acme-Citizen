<%--
 * register.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<script type="text/javascript">

$(document).ready(function() {
	 $("#bankAgentForm").submit(function(){
	var m = document.getElementById("phone").value;
	var expreg = /^(\+[1-9][0-9]{2}|\+[1-9][0-9]|\+[1-9])(\s\([1-9][0-9]{2}\)|\ \([1-9][0-9]\)|\ \([1-9]\))?(\ \d{4,})|(\d{4,})|(\d+)$/;
	
	if(!expreg.test(m)){
		
		return confirm("Are you sure you want to save this phone?");
	}
});
});

</script>

<form:form action="bankAgent/governmentAgent/register.do" modelAttribute="bankAgentForm">

	<form:hidden path="nif"/>
	
	<acme:textbox code="agent.name" path="name"/>
	<br/>
	<acme:textbox code="agent.surname" path="surname"/>
	<br/>
	<acme:textbox code="agent.email" path="email"/>
	<br/>
	<acme:textbox code="agent.phone" path="phone"/>
	<br/>
	<acme:textbox code="agent.address" path="address"/>
	<br/>
	<acme:textbox code="agent.nickname" path="nickname"/>
	<br/>
	<spring:message code="agent.bankCodeTip" var="bankCodeTip"/>
	<acme:textbox code="agent.bankCode" path="bankCode" placeholder="${ bankCodeTip }"/>
	<br/>
	<acme:checkbox code="agent.canCreateMoney" disabled="${ !canCreateMoneyParent }" path="canCreateMoney"/>
	<br/>
	<acme:textbox code="agent.userName" path="username"/>
	<br/>
	<acme:password code="agent.password" path="password"/>
	<br/>
	<acme:password code="agent.repeatPassword" path="repeatPassword"/>
	<br/>
	
	<acme:checkbox code="agent.acceptTerms" path="termsAndConditions"/>
	
	<a href="terms/list.do"><spring:message code="agent.acceptTermsLink"/></a>
	<br />
	
	<acme:submit name="save" code="agent.save"/>
	<acme:cancel code="agent.cancel" url="welcome/index.do"/>
	
</form:form>
	
	
	
	
	
	
	
	
	
	
