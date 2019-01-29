<%/*
 *  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2018 Marc Gimpel
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */%>
<%/* $Id$ */%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:include page="aesn_header.jsp" />
<c:if test="${param.error != null}"><p align="center">Invalid username and password.</p></c:if>
<c:if test="${param.logout != null}"><p align="center">You have been logged out successfully.</p></c:if>
<form name="loginForm" action="<c:url value="/login"/>" method="post">
<table style="margin-left:auto;margin-right:auto;">
	<tr>
		<td><label for="username"><spring:message code="login.username"/>:</label></td>
		<td><input type="text" name="username" placeholder="<spring:message code="login.username.placeholder"/>" required autofocus></td>
	</tr>
	<tr>
		<td><label for="password"><spring:message code="login.password"/>:</label></td>
		<td><input type="password" name="password" placeholder="<spring:message code="login.password.placeholder"/>" required></td>
	</tr>
	<tr>
		<td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></td>
		<td><input type="submit" name="submit" value="<spring:message code="login.submit"/>"></td>
	</tr>
</table>
</form>
<jsp:include page="aesn_footer.jsp" />
