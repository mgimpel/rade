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
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="aesn" tagdir="/WEB-INF/tags"%>
<jsp:include page="aesn_header.jsp" />
<div class="row justify-content-center">
	<div class="col-12">
		<div class="card card-aesn">
			<div class="card-body card-body-aesn">
				<table>
					<tr>
						<c:if test="${!searchCommune.codeInsee.equals('') && searchCommune.codeInsee != null}">
							<td style="text-align: right; width: 100px"><strong>Code INSEE :</strong></td>
							<td>${searchCommune.codeInsee}</td>
						</c:if>
						<c:if test="${!searchCommune.nomEnrichi.equals('') && searchCommune.nomEnrichi != null}">
							<td style="text-align: right; width: 100px"><strong>Nom : </strong></td>
							<td>${searchCommune.nomEnrichi}</td>
						</c:if>
						<c:if test="${!searchCommune.codeRegion.equals('-1') && searchCommune.codeRegion != null}">
							<td style="text-align: right;width: 100px"><strong>Région : </strong></td>
							<td>${searchCommune.codeRegion}</td>
						</c:if>
						<c:if test="${!searchCommune.codeDepartement.equals('-1') && searchCommune.codeDepartement != null}">
							<td style="text-align: right;width: 100px"><strong>Département : </strong></td>
							<td>${searchCommune.codeDepartement}</td>
						</c:if>
						<c:if test="${!searchCommune.codeCirconscription.equals('-1') && searchCommune.codeCirconscription != null}">
							<td style="text-align: right;width: 100px"><strong>Circonscription : </strong></td>
							<td>${searchCommune.codeCirconscription}</td>
						</c:if>
						<c:if test="${!searchCommune.dateEffet.equals('') && searchCommune.dateEffet != null}">
							<td style="text-align: right;width: 100px"><strong>Date d'effet : </strong></td>
							<td>${DateConversionUtils.toUiString(searchCommune.dateEffet)}</td>
						</c:if>
					</tr>
				</table>
			</div>
		</div>
		<aesn:paging baseUrl="/referentiel/commune/resultats?page=" currentPage="${searchCommune.getPage()}" maxPage="${searchCommune.getPageMax()}"/>
		<table class="table table-sm table-bordered table-striped table-aesn w-100 mb-0">
			<tr>
				<th colspan="4" class="w-50">Commune</th>
				<th colspan="2">Entités mères</th>
			</tr>
			<tr>
				<th style="width: 6%">Code</th>
				<th style="width: 20%">Nom</th>
				<th style="width: 12%">Début validité</th>
				<th style="width: 12%">Fin validité</th>
				<th class="w-25">Motif de modification</th>
				<th>Code INSEE</th>
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
				<a class="btn btn-sm btn-aesn" href="${pageContext.request.contextPath}/referentiel/commune">&lt;&lt; Retour</a>
			</div>
			<div class="col-auto">
				<form id="formExcel" action="${pageContext.request.contextPath}/referentiel/commune/export" method="POST">
					<button class="btn btn-sm btn-aesn" type="submit">Exporter sous excel</button>
				</form>
			</div>
		</div>
	</div>
</div>
<jsp:include page="aesn_footer.jsp" />
