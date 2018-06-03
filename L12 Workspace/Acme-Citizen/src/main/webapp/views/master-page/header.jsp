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

<div>
	<a href="/Acme-Citizen"><img src="images/logo.png"
		alt="Acme-Citizen Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('GOVERNMENTAGENT')">
			<li><a class="fNiv"><spring:message
						code="master.page.governmentAgent" /></a>
				<ul>
					<li><a href="governmentagent/governmentagent/dashboard.do"><spring:message
								code="master.page.governmentAgent.information" /></a></li>
					<li><a href="configuration/governmentagent/edit.do"><spring:message
								code="master.page.configuration" /></a> <%-- 					<li><a href="newspaper/admin/list.do"><spring:message --%>
						<%-- 								code="master.page.newspaper.list" /></a></li> --%> <%-- 					<li><a href="article/admin/list.do"><spring:message --%>
						<%-- 								code="master.page.article.list" /></a></li> --%>
					<li><a href="chirp/list.do"><spring:message
								code="master.page.chirp.list" /></a></li>

				</ul>
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li><a href="governmentagent/governmentagent/register.do"><spring:message
								code="master.page.register.govAgent" /></a></li>
					<li><a href="bankagent/governmentagent/register.do"><spring:message
								code="master.page.register.bankAgent" /></a></li>
					<li><a href="citizen/governmentagent/register.do"><spring:message
								code="master.page.register.citizen" /></a></li>
				</ul>
		</security:authorize>



		<security:authorize access="hasRole('BANKAGENT')">
			<li><a class="fNiv"><spring:message
						code="master.page.bankagent" /></a>
				<ul>
					<li><a href="bankaccount/bankagent/create.do"><spring:message
								code="master.page.bankagent.createAccount" /></a></li>
					<li><a href="citizen/list.do"><spring:message
								code="master.page.bankagent.listActor" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('CITIZEN')">
			<li><a class="fNiv"><spring:message
						code="master.page.citizen" /></a>
				<ul>
					<li><a href="lottery/list.do"><spring:message
								code="master.page.lottery.list" /></a></li>
					<li><a href="lottery/myTickets.do"><spring:message
								code="master.page.citizen.myTickets" /></a></li>

				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('GOVERNMENTAGENT')">
			<li><a class="fNiv">Lottos</a>
				<ul>
					<li><a href="lottery/governmentAgent/create.do"><spring:message
								code="master.page.lottery.create" /></a></li>
					<li><a href="lottery/governmentAgent/MyLotterys.do"><spring:message
								code="master.page.lottery.MyLotterys" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>

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

			<li><a class="fNiv"> <spring:message
						code="master.page.messages" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/create.do"><spring:message
								code="master.page.newmessage" /> </a></li>
					<security:authorize access="hasRole('GOVERNMENTAGENT')">
						<li><a href="message/governmentagent/create-notification.do"><spring:message
									code="master.page.newnotification" /></a></li>
					</security:authorize>
					<li><a href="folder/list.do"><spring:message
								code="master.page.myfolders" /></a></li>

				</ul></li>




		</security:authorize>



		<li><a class="fNiv"><spring:message
					code="master.page.listActors" /></a>
			<ul>
				<li><a href="governmentagent/list.do"><spring:message
							code="master.page.listActors.govAgent" /></a></li>
				<li><a href="bankagent/list.do"><spring:message
							code="master.page.listActors.bankAgent" /></a></li>
				<li><a href="citizen/list.do"><spring:message
							code="master.page.listActors.citizen" /></a></li>
			</ul>
		<li><a class="fNiv" href="petition/list.do"><spring:message
					code="master.page.listPetitions" /></a></li>
		<li><a class="fNiv" href="election/list.do"><spring:message
					code="master.page.listElections" /></a></li>
		<li><a class="fNiv" href="terms/list.do"><spring:message
					code="master.page.termsAndConditions" /></a></li>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

