<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<div>
	<a href="/Acme-Rendezvous-2.0"><img src="images/logo.png" alt="Acme-Rendezvous 2.0 Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
			<ul>
				<li><a href="rendezvous/admin/list.do"><spring:message code="master.page.administrator.rendezvous" /></a></li>
				<li><a href="comment/admin/list.do"><spring:message code="master.page.administrator.comment" /></a></li>
				<li><a href="announcement/admin/list.do"><spring:message code="master.page.administrator.announcement" /></a></li>
				<li><a href="service/list.do"><spring:message code="master.page.service" /></a></li>
				<li><a href="administrator/display.do"><spring:message code="master.page.administrator.information" /></a></li>
			</ul>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
		<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
			<ul>
				<li><a href="rendezvous/user/list-organised.do"><spring:message code="master.page.user.organisedRendezvouses" /></a></li>
				<li><a href="rendezvous/user/list-rspv.do"><spring:message code="master.page.user.rspvRendezvouses" /></a></li>
				<li><a href="announcement/user/list.do"><spring:message code="master.page.user.streamingAnnouncements" /></a></li>
				<li><a href="service/list.do"><spring:message code="master.page.service" /></a></li>
			</ul>
			
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
		<li><a class="fNiv"><spring:message	code="master.page.manager" /></a>
			<ul>
				<li><a href="service/list.do"><spring:message code="master.page.service" /></a></li>
				<li><a href="service/manager/list.do"><spring:message code="master.page.manager.listServices" /></a></li>
			</ul>
			
		</security:authorize>
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
			<ul>
				<li><a href="manager/register.do"><spring:message code="master.page.registerManager" /></a></li>
				<li><a href="user/register.do"><spring:message code="master.page.registerUser" /></a></li>
			</ul>
			
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('USER')">
						<li><a href="user/user/edit.do"><spring:message code="master.page.editProfile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('MANAGER')">
						<li><a href="manager/manager/edit.do"><spring:message code="master.page.editProfile" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					
				</ul>
			</li>
	
		</security:authorize>
		
		<li><a class="fNiv" href="rendezvous/list.do"><spring:message code="master.page.avalibleRendezvouses" /></a></li>
		<li><a class="fNiv" href="category/list.do"><spring:message code="master.page.category" /></a></li>
		<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.listUsers" /></a></li>
		<li><a class="fNiv" href="terms/list.do"><spring:message code="master.page.termsAndConditions" /></a></li>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

