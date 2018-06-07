<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<a href="/Acme-Citizen"><img src="images/logo.png"
		alt="Acme-Citizen Co., Inc." /></a>

	<security:authorize access="isAuthenticated()">

		<jstl:if test="${principal  != null}">
			<jstl:choose>
				<jstl:when test="${principal.bankAccount != null }">

					<h3>
						<spring:message var="formatGeneralPrice"
							code="master.page.format.general.price" />

						<b><spring:message code="bankAccount.money" />:&nbsp;</b> <font
							color="green"> <jstl:out
								value="${principal.bankAccount.money}" /> ${formatGeneralPrice}

						</font>
					</h3>
				</jstl:when>
				<jstl:otherwise>
					<br>
					<a href="bankAgent/list.do"><spring:message
							code="lottery.bankAccount" /></a>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:if>

	</security:authorize>

</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>
		<security:authorize access="hasRole('GOVERNMENTAGENT')">
			<li><a class="fNiv"><spring:message
						code="master.page.governmentAgent" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="governmentAgent/governmentAgent/dashboard.do"><spring:message
								code="master.page.governmentAgent.information" /></a></li>
					<li><a href="configuration/governmentAgent/edit.do"><spring:message
								code="master.page.configuration" /></a> <%-- 					<li><a href="newspaper/admin/list.do"><spring:message --%>
						<%-- 								code="master.page.newspaper.list" /></a></li> --%> <%-- 					<li><a href="article/admin/list.do"><spring:message --%>
						<%-- 								code="master.page.article.list" /></a></li> --%>
				</ul>
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="governmentAgent/governmentAgent/register.do"><spring:message
								code="master.page.register.govAgent" /></a></li>
					<li><a href="bankAgent/governmentAgent/register.do"><spring:message
								code="master.page.register.bankAgent" /></a></li>
					<li><a href="citizen/governmentAgent/register.do"><spring:message
								code="master.page.register.citizen" /></a></li>
				</ul>
		</security:authorize>



		<security:authorize access="hasRole('BANKAGENT')">
			<li><a class="fNiv"><spring:message
						code="master.page.bankAgent" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="bankaccount/bankAgent/create.do"><spring:message
								code="master.page.bankAgent.createAccount" /></a></li>
					<li><a href="citizen/list.do"><spring:message
								code="master.page.bankAgent.listActor" /></a></li>
				</ul></li>
		</security:authorize>

		<!--  BANK ACCOUNT -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.bankAccount" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="bankAccount/display.do"><spring:message
								code="master.page.seeBankAccount" /></a></li>
					<li><a href="transaction/create.do"><spring:message
								code="master.page.sendMoney" /></a></li>
					<li><a href="transaction/list.do"><spring:message
								code="master.page.transactions" /></a></li>
					<li><a href="transaction/listMoneyCreate.do"><spring:message
								code="master.page.moneyMade" /></a></li>
					<security:authorize
						access="hasAnyRole('BANKAGENT','GOVERNMENTAGENT')">
						<li><a href="transaction/createMoney.do"><spring:message
									code="master.page.createMoney" /></a></li>
					</security:authorize>

				</ul></li>
		</security:authorize>

		<security:authorize access="permitAll">

			<li><a class="fNiv"><spring:message
						code="master.page.lottos" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="lottery/list.do"><spring:message
								code="master.page.lottery.list" /></a></li>
					<security:authorize access="hasRole('CITIZEN')">
						<li><a href="lottery/myTickets.do"><spring:message
									code="master.page.citizen.myTickets" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('GOVERNMENTAGENT')">
						<li><a href="lottery/governmentAgent/create.do"><spring:message
									code="master.page.lottery.create" /></a></li>
						<li><a href="lottery/governmentAgent/MyLotterys.do"><spring:message
									code="master.page.lottery.MyLotterys" /></a></li>
					</security:authorize>
				</ul></li>
		</security:authorize>

		<li><a href="chirp/list.do"><spring:message
					code="master.page.listChirps" /></a> <security:authorize
				access="hasRole('GOVERNMENTAGENT')">
				<ul>
					<li class="arrow"></li>
					<li><a href="chirp/governmentAgent/list.do"><spring:message
								code="master.page.yourChirps" /> </a></li>
					<li><a href="chirp/governmentAgent/create.do"><spring:message
								code="master.page.createChirp" /></a></li>
				</ul>

			</security:authorize></li>

		<li><a class="fNiv"><spring:message
					code="master.page.listActors" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="governmentAgent/list.do"><spring:message
							code="master.page.listActors.govAgent" /></a></li>
				<li><a href="bankAgent/list.do"><spring:message
							code="master.page.listActors.bankAgent" /></a></li>
				<li><a href="citizen/list.do"><spring:message
							code="master.page.listActors.citizen" /></a></li>

			</ul>
		<li><a class="fNiv" href="petition/list.do"><spring:message
					code="master.page.listPetitions" /></a> <security:authorize
				access="hasRole('CITIZEN')">
				<ul>
					<li class="arrow"></li>
					<li><a href="petition/citizen/list.do"><spring:message
								code="master.page.yourPetitions" /> </a></li>
					<li><a href="petition/citizen/create.do"><spring:message
								code="master.page.createPetition" /></a></li>
				</ul>
			</security:authorize></li>
		<li><a class="fNiv" href="election/list.do"><spring:message
					code="master.page.listElections" /></a></li>
		<li><a class="fNiv" href="terms/list.do"><spring:message
					code="master.page.termsAndConditions" /></a></li>


		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.messages" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/create.do"><spring:message
								code="master.page.newmessage" /> </a></li>
					<security:authorize access="hasRole('GOVERNMENTAGENT')">
						<li><a href="message/governmentAgent/create-notification.do"><spring:message
									code="master.page.newnotification" /></a></li>
					</security:authorize>
					<li><a href="folder/list.do"><spring:message
								code="master.page.myfolders" /></a></li>

				</ul></li>

			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>

				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

