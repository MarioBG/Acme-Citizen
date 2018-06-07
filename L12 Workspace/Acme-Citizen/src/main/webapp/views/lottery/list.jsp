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


<display:table name="lotterys" id="row" requestURI="lottery/list.do"
	pagesize="5" class="displaytag">



	<spring:message var="lotteryNameHeader" code="lottery.lotteryName" />
	<display:column property="lotteryName" title="${lotteryNameHeader}" />

	<spring:message var="celebrationDateHeader"
		code="lottery.celebrationDate" />
	<spring:message var="formatDate" code="lottery.format.date" />
	<display:column property="celebrationDate"
		title="${celebrationDateHeader}" format="${formatDate}"
		sortable="true" />

	<spring:message var="formatPrice" code="lottery.format.price" />
	<spring:message var="quantityHeader" code="lottery.quantity" />
	<display:column title="${quantityHeader}" property="quantity"
		format="${formatPrice}" />

	<spring:message var="percentageForPrizesHeader"
		code="lottery.percentageForPrizes" />
	<display:column title="${percentageForPrizesHeader}"
		property="percentageForPrizes" />

	<spring:message var="formatPrice" code="lottery.format.price" />

	<spring:message var="ticketCostHeader" code="lottery.ticketCost" />
	<display:column title="${ticketCostHeader}" property="ticketCost"
		format="${formatPrice}" />
	<spring:message var="patternPrice" code="lottery.pattern.price" />
	<fmt:formatNumber value="${ticketCost}" pattern="${patternPrice}" />


	<spring:message var="winnerTicketHeader" code="lottery.winner" />
	<display:column>

		<jstl:choose>
			<jstl:when test="${row.celebrationDate.before(date)}">
				<jstl:choose>
					<jstl:when test="${row.lotteryTickets.size() > 0}">
						<jstl:out
							value="${row.winnerTicket.citizen.name} ${row.winnerTicket.citizen.surname}" />
					</jstl:when>
					<jstl:otherwise>
						<spring:message code="lottery.noWinnerLottery"></spring:message>
					</jstl:otherwise>
				</jstl:choose>
			</jstl:when>
			<jstl:otherwise>
				<security:authorize access="hasRole('CITIZEN')">
					<jstl:if test="${principal.bankAccount == null}">
						<a href="bankAgent/list.do"><spring:message
								code="lottery.bankAccount" /></a>
					</jstl:if>
					<jstl:if test="${principal.bankAccount != null}">
						<jstl:if test="${principal.bankAccount.money >= row.ticketCost }">
							<input type="button"
								value="<spring:message code="lottery.buyTicket" />"
								onclick="javascript: window.location.assign('lottery/buyTicket.do?lotteryId=${row.id}')" />
						</jstl:if>
						<jstl:if test="${principal.bankAccount.money < row.ticketCost }">
							<spring:message code="lottery.NoMoney" />
						</jstl:if>
					</jstl:if>
				</security:authorize>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>


</display:table>

<security:authorize access="hasRole('GOVERNMENTAGENT')">
	<a href="lottery/governmentAgent/create.do"><spring:message
			code="lottery.create" /></a>
	<br />
</security:authorize>

<spring:message var="backValue" code="lottery.back" />
<jstl:choose>
	<jstl:when test="${requestURI == 'lotteryTicket/list.do' }">
		<input type="button" name="back" value="${backValue}"
			onclick="javascript: relativeRedir('lottery/list.do');" />
	</jstl:when>
	<jstl:otherwise>
		<input type="button" name="back" value="${backValue}"
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</jstl:otherwise>
</jstl:choose>