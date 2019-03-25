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
<%@tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="baseUrl" required="true" type="java.lang.String"%>
<%@attribute name="currentPage" required="true" type="java.lang.String"%>
<%@attribute name="maxPage" required="true" type="java.lang.Integer"%>
<c:if test="${maxPage > 1}">
	<nav aria-label="Page navigation">
		<ul class="pagination pagination-sm justify-content-end pagination-aesn">
			<li class="page-item<c:if test="${currentPage == 1}"> disabled</c:if>"><a class="page-link" href="${baseUrl}1" aria-label="First"><span aria-hidden="true">&lt;&lt;</span></a></li>
			<li class="page-item<c:if test="${currentPage == 1}"> disabled</c:if>"><a class="page-link" href="${baseUrl}${currentPage - 1}" aria-label="Previous"><span aria-hidden="true">&lt;</span></a></li>
			<c:forEach begin="1" end="${maxPage}" step="1" var="numPage">
				<c:choose>
					<c:when test="${numPage == currentPage}">
						<li class="page-item active" aria-current="page"><a class="page-link" href="#">${numPage}<span class="sr-only">(current)</span></a></li>
					</c:when>
					<c:when test="${numPage <= 2
								|| numPage >= maxPage - 1
								|| numPage == currentPage - 1
								|| numPage == currentPage + 1}">
						<li class="page-item"><a class="page-link" href="${baseUrl}${numPage}">${numPage}</a></li>
					</c:when>
					<c:when test="${(numPage > 2 && numPage == currentPage - 2)
								|| (numPage < maxPage - 2 && numPage == currentPage + 2)}">
						<li class="page-item disabled"><a class="page-link" href="#">...</a></li>
					</c:when>
				</c:choose>
			</c:forEach>
			<li class="page-item<c:if test="${currentPage == maxPage}"> disabled</c:if>"><a class="page-link" href="${baseUrl}${currentPage + 1}" aria-label="Next"><span aria-hidden="true">&gt;</span></a></li>
			<li class="page-item<c:if test="${currentPage == maxPage}"> disabled</c:if>"><a class="page-link" href="${baseUrl}${maxPage}" aria-label="Last"><span aria-hidden="true">&gt;&gt;</span></a></li>
		</ul>
	</nav>
</c:if>
