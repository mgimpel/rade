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
<jsp:include page="../aesn_header.jsp" />
<div class="row justify-content-center">
	<div class="col-12">
		<div class="card card-aesn">
			<div class="card-body card-body-aesn">
				<p>Les Batchs suivants sont présent:
				<ul>
					<li><a href="${pageContext.request.contextPath}/batch/info">Batch Info</a></li>
					<li><a href="${pageContext.request.contextPath}/batch/historiqueinseeimport">Batch Import Historique INSEE</a></li>
					<li><a href="${pageContext.request.contextPath}/batch/sandreimport">Batch Import Sandre</a></li>
					<li><a href="${pageContext.request.contextPath}/batch/delegationimport">Batch Import Délégation</a></li>
					<li><a href="${pageContext.request.contextPath}/batch/delegationexport">Batch Export Délégation</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../aesn_footer.jsp" />
