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
<jsp:include page="aesn_header.jsp" />
<div class="row justify-content-center">
	<div class="col-12">
		<div class="card card-aesn">
			<div class="card-body card-body-aesn">
				<div class="row justify-content-center">
					<c:if test="${searchCommune.codeInsee != null && !searchCommune.codeInsee.isEmpty()}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.code'/></strong> : ${searchCommune.codeInsee}</div>
					</c:if>
					<c:if test="${searchCommune.nomEnrichi != null && !searchCommune.nomEnrichi.isEmpty()}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.name'/></strong> : ${searchCommune.nomEnrichi}</div>
					</c:if>
					<c:if test="${searchCommune.codeRegion != null && !searchCommune.codeRegion.equals('-1')}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.region'/></strong> : ${searchCommune.codeRegion}</div>
					</c:if>
					<c:if test="${searchCommune.codeDepartement != null && !searchCommune.codeDepartement.equals('-1')}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.department'/></strong> : ${searchCommune.codeDepartement}</div>
					</c:if>
					<c:if test="${searchCommune.codeCirconscription != null && !searchCommune.codeCirconscription.equals('-1')}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.bassin'/></strong> : ${searchCommune.codeCirconscription}</div>
					</c:if>
					<c:if test="${searchCommune.dateEffet != null}">
						<div class="col text-center"><strong><spring:message code='communeresult.label.date'/></strong> : ${DateConversionUtils.toUiString(searchCommune.dateEffet)}</div>
					</c:if>
				</div>
			</div>
		</div>
		<aesn:paging baseUrl="/referentiel/commune/resultats?page=" currentPage="${searchCommune.getPage()}" maxPage="${searchCommune.getPageMax()}"/>
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
			<c:forEach items="${searchCommune.listeResultats}" var="communeDisplay">
				<tr>
					<td><a href="${communeDisplay.getUrlEntite(pageContext.request.contextPath, communeDisplay.codeInsee, communeDisplay.finValidite)}">${communeDisplay.codeInsee}</a></td>
					<td><a href="${communeDisplay.getUrlEntite(pageContext.request.contextPath, communeDisplay.codeInsee, communeDisplay.finValidite)}">${communeDisplay.nomEnrichi}</a></td>
					<td class="text-center">${DateConversionUtils.toUiString(communeDisplay.debutValidite)}</td>
					<td class="text-center">${DateConversionUtils.toUiString(communeDisplay.finValidite)}</td>
					<td class="text-center">${communeDisplay.motifModification}</td>
					<td>
						<c:forEach items="${communeDisplay.parents}" var="genealogieParent">
							<a href="${communeDisplay.getUrlEntite(pageContext.request.contextPath, genealogieParent.key, genealogieParent.value.entity.finValidite)}">${genealogieParent.key}</a>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</table>
		<aesn:paging baseUrl="/referentiel/commune/resultats?page=" currentPage="${searchCommune.getPage()}" maxPage="${searchCommune.getPageMax()}"/>
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
<jsp:include page="aesn_footer.jsp" />
