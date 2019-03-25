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
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="../aesn_header.jsp" />
<div class="row justify-content-center">
	<div class="col-12">
		<div class="card card-aesn">
			<div class="card-body">
				<form:form method="POST" action="/referentiel/region" modelAttribute="region">
					<table class="w-100">
						<tr>
							<td><form:label path="codeInsee">Code INSEE:</form:label></td>
							<td><form:input path="codeInsee"/></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" name="submit" value="Rechercher"></td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../aesn_footer.jsp" />
