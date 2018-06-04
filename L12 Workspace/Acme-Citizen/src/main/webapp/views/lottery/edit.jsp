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

<form:form action="lottery/governmentAgent/edit.do"
	modelAttribute="lottery">

	<form:hidden path="id" />
	<form:hidden path="governmentAgent" />
	<form:hidden path="lotteryTickets" />
	<form:hidden path="winnerTicket" />
	<form:hidden path="quantity" />

	<acme:textbox code="lottery.lotteryName" path="lotteryName" />
	<br />

	<acme:textbox code="lottery.percentageForPrizes"
		path="percentageForPrizes" />
	<br />

	<acme:textbox code="lottery.celebrationDate" path="celebrationDate"
		placeholder="dd/MM/yyyy" />
	<br />

	<acme:textbox code="lottery.ticketCost" path="ticketCost" />
	<br />



	<acme:submit name="save" code="lottery.save" />
	&nbsp;
	<acme:cancel url="lottery/governmentAgent/MyLotterys.do"
		code="lottery.back" />

</form:form>