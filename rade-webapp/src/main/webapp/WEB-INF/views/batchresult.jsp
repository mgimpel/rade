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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="aesn_header.jsp" />
<div class="row justify-content-center">
	<div class="col-12">
		<div class="card card-aesn">
			<div class="card-body card-body-aesn">
				<div class="row">
					<div class="col-12">
						<h5><spring:message code='batchresult.upload'/></h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100">
									<tr>
										<td class="w-25">OriginalFileName: </td>
										<td>${file.originalFilename}</td>
									</tr>
									<tr>
										<td>Type:</td>
										<td>${file.contentType}</td>
									</tr>
									<tr>
										<td>Name:</td>
										<td>${file.name}</td>
									</tr>
									<tr>
										<td>Size:</td>
										<td>${file.size}</td>
									</tr>
									<tr>
										<td>URI:</td>
										<td>${uri}</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
<c:if test="${params != null}">
				<div class="row mt-2">
					<div class="col-12">
						<h5><spring:message code='batchresult.job'/></h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100">
<c:forEach items="${params}" var="entry">
									<tr>
										<td class="w-25">${entry.key}:</td>
										<td>${entry.value}</td>
									</tr>
</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
</c:if>
<c:if test="${status != null}">
				<div class="row mt-2">
					<div class="col-12">
						<h5><spring:message code='batchresult.exec'/></h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100">
									<tr>
										<td class="w-25">Exit Code:</td>
										<td>${status.exitCode}</td>
									</tr>
									<tr>
										<td>Exit Description:</td>
										<td>${status.exitDescription}</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
</c:if>
<c:if test="${message != null}">
				<div class="row mt-2">
					<div class="col-12">
						<h5><spring:message code='batchresult.message'/></h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								${message}
							</div>
						</div>
					</div>
				</div>
</c:if>
			</div>
		</div>
		<div class="row mt-2">
			<div class="col-auto">
				<a class="btn btn-sm btn-aesn" href="${pageContext.request.contextPath}/batch">&lt;&lt; <spring:message code='batchresult.button.back'/></a>
			</div>
		</div>
	</div>
</div>
<jsp:include page="aesn_footer.jsp" />