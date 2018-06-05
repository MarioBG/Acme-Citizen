
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


<display:table name="lotterys" id="row"
	requestURI="lottery/myTickets.do" pagesize="5"
	class="displaytag">

	<spring:message var="lotteryNameHeader" code="lottery.lotteryName" />
	<display:column property="lottery.lotteryName" title="${lotteryNameHeader}" />

	<spring:message var="celebrationDateHeader"
		code="lottery.celebrationDate" />
	<spring:message var="formatDate" code="lottery.format.date" />
	<display:column property="lottery.celebrationDate"
		title="${celebrationDateHeader}" format="${formatDate}"
		sortable="true" />

<spring:message var="formatPrice" code="lottery.format.price" />
	<spring:message var="ticketCostHeader" code="lottery.ticketCost" />
	<display:column title="${ticketCostHeader}" property="lottery.ticketCost" format="${formatPrice}"/>
	
	<spring:message var="numberHeader" code="lotteryTicket.number" />
	<display:column title="${numberHeader}" property="number" />

</display:table>

<acme:cancel url="welcome/index.do" code="lottery.back" />


