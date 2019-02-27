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
<%@page import="fr.aesn.rade.common.util.DateConversionUtils" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="aesn" tagdir="/WEB-INF/tags"%>
<jsp:include page="../aesn_header.jsp" />
<div class="row justify-content-center">
	<div class="col-12">
		<div class="card card-aesn">
			<div class="card-body card-body-aesn">
				<div class="row justify-content-center">
					<c:if test="${communeSearch.codeInsee != null && !communeSearch.codeInsee.isEmpty()}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.code'/></strong> : ${communeSearch.codeInsee}</div>
					</c:if>
					<c:if test="${communeSearch.nomEnrichi != null && !communeSearch.nomEnrichi.isEmpty()}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.name'/></strong> : ${communeSearch.nomEnrichi}</div>
					</c:if>
					<c:if test="${communeSearch.codeRegion != null && !communeSearch.codeRegion.equals('-1')}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.region'/></strong> : ${communeSearch.codeRegion}</div>
					</c:if>
					<c:if test="${communeSearch.codeDepartement != null && !communeSearch.codeDepartement.equals('-1')}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.department'/></strong> : ${communeSearch.codeDepartement}</div>
					</c:if>
					<c:if test="${communeSearch.codeCirconscription != null && !communeSearch.codeCirconscription.equals('-1')}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.bassin'/></strong> : ${communeSearch.codeCirconscription}</div>
					</c:if>
					<c:if test="${communeSearch.dateEffet != null}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.date'/></strong> : ${DateConversionUtils.toUiString(communeSearch.dateEffet)}</div>
					</c:if>
				</div>
			</div>
		</div>
		<aesn:paging baseUrl="/referentiel/commune/resultats?page=" currentPage="${communeSearch.getPage()}" maxPage="${communeSearch.getPageMax()}"/>
		<table class="table table-sm table-bordered table-striped table-aesn w-100 mb-0">
			<tr>
				<th colspan="4" class="w-50"><spring:message code='communeresult.tablecol.commune'/></th>
				<th colspan="2"><spring:message code='communeresult.tablecol.parents'/></th>
			</tr>
			<tr>
				<th style="width: 6%"><spring:message code='communeresult.tablecol.commune.code'/></th>
				<th style="width: 20%"><spring:message code='communeresult.tablecol.commune.name'/></th>
				<th style="width: 12%"><spring:message code='communeresult.tablecol.commune.start'/></th>
				<th style="width: 12%"><spring:message code='communeresult.tablecol.commune.end'/></th>
				<th class="w-25"><spring:message code='communeresult.tablecol.parents.reason'/></th>
				<th><spring:message code='communeresult.tablecol.parents.code'/></th>
			</tr>
			<c:forEach items="${communeSearch.listeResultats}" var="commune">
				<tr>
					<td><a href="${commune.getUrlEntite(pageContext.request.contextPath)}">${commune.codeInsee}</a></td>
					<td><a href="${commune.getUrlEntite(pageContext.request.contextPath)}">${commune.nomEnrichi}</a></td>
					<td class="text-center">${DateConversionUtils.toUiString(commune.debutValidite)}</td>
					<td class="text-center">${DateConversionUtils.toUiString(commune.finValidite)}</td>
					<td class="text-center">${commune.motifModification}</td>
					<td>
						<c:forEach items="${commune.parents}" var="genealogieParent">
							<a href="${commune.getUrlEntite(pageContext.request.contextPath, genealogieParent.key, genealogieParent.value.entity.finValidite)}">${genealogieParent.key}</a>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</table>
		<aesn:paging baseUrl="/referentiel/commune/resultats?page=" currentPage="${communeSearch.getPage()}" maxPage="${communeSearch.getPageMax()}"/>
		<div class="row justify-content-between">
			<div class="col-auto">
				<a class="btn btn-sm btn-aesn" href="${pageContext.request.contextPath}/referentiel/commune">&lt;&lt; <spring:message code='communeresult.button.back'/></a>
			</div>
			<div class="col-auto">
				<form id="formExcel" action="${pageContext.request.contextPath}/referentiel/commune/export" method="POST">
					<button class="btn btn-sm btn-aesn" type="submit"><spring:message code='communeresult.button.export'/></button>
				</form>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../aesn_footer.jsp" />
