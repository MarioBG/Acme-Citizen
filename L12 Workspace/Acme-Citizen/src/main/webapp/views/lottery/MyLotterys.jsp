
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
	requestURI="lottery/governmentAgent/MyLotterys.do" pagesize="5"
	class="displaytag">

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


	<security:authorize access="hasRole('GOVERNMENTAGENT')">
		<display:column>
			<jstl:choose>
				<jstl:when
					test="${row.lotteryTickets.size() > 0 && row.celebrationDate.before(date)}">
					<jstl:if test="${ row.winnerTicket == null  }">
						<input type="button"
							value="<spring:message code="lottery.makeWinner" />"
							onclick="javascript: window.location.assign('lottery/governmentAgent/makeWinner.do?lotteryId=${row.id}')" />
					</jstl:if>
					<jstl:if test="${ row.winnerTicket != null  }">
						<jstl:out
							value="${row.winnerTicket.citizen.name} ${row.winnerTicket.citizen.surname}" />
					</jstl:if>
				</jstl:when>
				<jstl:otherwise>
					<jstl:if
						test="${row.lotteryTickets.size() == 0 && row.celebrationDate.before(date)}">
						<spring:message code="lottery.noWinnerLottery"></spring:message>
					</jstl:if>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>




</display:table>

<security:authorize access="hasRole('GOVERNMENTAGENT')">
	<a href="lottery/governmentAgent/create.do"><spring:message
			code="lottery.create" /></a>
	<br />
</security:authorize>


<acme:cancel url="welcome/index.do" code="lottery.back" />


