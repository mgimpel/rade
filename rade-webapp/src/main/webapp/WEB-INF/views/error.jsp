<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<c:set var="titre" value="${(empty titre) ? 'Error Page' : titre}" scope="request"/>
<jsp:include page="aesn_header.jsp" />
<table cellpadding="5">
	<tr>
		<td>Date</td>
		<td>${timestamp}</td>
	</tr>
	<tr>
		<td>Error</td>
		<td>${error}</td>
	</tr>
	<tr>
		<td>Status</td>
		<td>${status}</td>
	</tr>
	<tr>
		<td>Message</td>
		<td>${message}</td>
	</tr>
	<tr>
		<td>Exception</td>
		<td>${exception}</td>
	</tr>
	<tr>
		<td valign="top">Trace</td>
		<td>
			<pre>${trace}</pre>
		</td>
	</tr>
</table>
<jsp:include page="aesn_footer.jsp" />
