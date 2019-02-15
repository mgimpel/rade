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
<%@ page import="java.text.*,java.util.*" %>
<%@ page import="fr.aesn.rade.common.util.Version" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
		<!-- Footer -->
		<div class="row mt-4">
			<div class="col-lg-2 aesn-footer"><%
				SimpleDateFormat ft = new SimpleDateFormat("d MMMMM yyyy");
				out.print(ft.format(new Date()));
			%></div>
			<div class="col-lg-10 aesn-footer aesn-footer-border">
<spring:eval var="footermenu" expression="@footerMenu"/>
<c:forEach var="menuitem" items="${footermenu}">
				<a href="#" onclick="window.open('<c:url value="${menuitem.value}" />')">${menuitem.key}</a>&nbsp;&nbsp;|&nbsp;
</c:forEach>
				<%out.print(Version.PROJECT_VERSION);%>
			</div>
		</div>
	</div>
	<script src="${path}/webjars/popper.js/umd/popper.min.js" type="text/javascript"></script>
	<script src="${path}/webjars/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
