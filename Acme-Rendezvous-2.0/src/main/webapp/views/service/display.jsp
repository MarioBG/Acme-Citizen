<%-- edit.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h3><b><spring:message code="service.name"/>:&nbsp;</b><jstl:out value="${service.name}"/></h3>

<img src="<jstl:out value="${service.picture}"/>" width="450" height="174">
<br/>

<b><spring:message code="service.description"/>:&nbsp;</b><jstl:out value="${service.description}"/>
<br/>

<b><spring:message code="service.manager"/>:&nbsp;</b><jstl:out value="${service.manager.name} ${service.manager.surname}"/>
<br/>

<acme:cancel url="service/list.do" code="service.back"/>