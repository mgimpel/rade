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
<jsp:include page="aesn_header.jsp" />
<style>
.tableauResultat td{
	border: 1px solid #5893BC;
	padding: 5px;
}
.tableauResultat th{
	padding:5px;
	border: 1px solid white;
}
</style>
	<tr>
		<td colspan="2">
			<table class="prez" style="margin-left:auto;margin-right:auto;padding-bottom: 4px;">
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
						<td>${searchCommune.getDateIHM(searchCommune.dateEffet)}</td>
					</c:if>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<div style="text-align:right">
				<c:if test="${searchCommune.getPageMax() > 1}">
					<c:if test="${searchCommune.getPage() > 1}">
						<a href="/referentiel/commune/resultats?page=1">&lt;&lt;</a>
						<a href="/referentiel/commune/resultats?page=${searchCommune.getPage()-1}">&lt;</a>
					</c:if>
					<c:if test="${searchCommune.getPage() == 1}">
						<b>&lt;&lt;</b>
						<b>&lt;</b>
					</c:if>
					<c:forEach begin="1" end="${searchCommune.getPageMax()}" step="1" var="numPage">
						<c:if test="${(numPage > 2 && numPage == searchCommune.getPage() - 2)
									|| (numPage == searchCommune.getPage() + 2
									&& numPage <= searchCommune.getPageMax() - 2)}">
							..
						</c:if>  
						<c:if test="${numPage <= 2 
									|| numPage >= searchCommune.getPageMax() -1 
									|| (numPage >= searchCommune.getPage() - 1 
									&& numPage <= searchCommune.getPage())
									|| (numPage >= searchCommune.getPage() 
									&& numPage <= searchCommune.getPage() + 1)}">
							<c:if test="${numPage == searchCommune.getPage()}">
								<b>${numPage}</b>
							</c:if>
							<c:if test="${numPage != searchCommune.getPage()}">
								<a href="/referentiel/commune/resultats?page=${numPage}">${numPage}</a>
							</c:if>
						</c:if>
					</c:forEach>
					<c:if test="${searchCommune.getPage() < searchCommune.getPageMax()}">
						<a href="/referentiel/commune/resultats?page=${searchCommune.getPage()+1}">&gt;</a>
						<a href="/referentiel/commune/resultats?page=${searchCommune.getPageMax()}">&gt;&gt;</a>
					</c:if>
					<c:if test="${searchCommune.getPage() == searchCommune.getPageMax()}">
						<b>&gt;</b>
						<b>&gt;&gt;</b>
					</c:if>
				</c:if>
			</div>
			<table class="tableauResultat" style=" border-collapse: collapse; width: 100%">
				<tr style="background-color: #0365ab; color: white;padding: 5px 0; margin: -1px; text-align: center; vertical-align: middle;">
					<th colspan="4" style="width: 50%">Commune</th>
					<th colspan="2">Entités mères</th>
				</tr>
				<tr style="background-color: #0365ab; color: white;padding: 5px 0; margin: -1px; text-align: center; vertical-align: middle;">
					<th style="width: 6%">Code</th>
					<th style="width: 120px">Nom</th>
					<th style="width: 75px">Début validité</th>
					<th style="width: 75px">Fin validité</th>
					<th>Motif de modification</th>
					<th style="width: 25%">Code INSEE</th>
				</tr>
				<c:forEach items="${searchCommune.listeResultats}" var="communeDisplay">
					<tr>
						<td>
							<a href="${communeDisplay.getUrlEntite(communeDisplay.codeInsee, communeDisplay.finValidite)}">
								${communeDisplay.codeInsee}
							</a>
						</td>
						<td><a href="${communeDisplay.getUrlEntite(communeDisplay.codeInsee,communeDisplay.finValidite)}">${communeDisplay.nomEnrichi}</a></td>
						<td style="text-align: center">${communeDisplay.getDateIHM(communeDisplay.debutValidite)}</td>
						<td style="text-align: center">${communeDisplay.getDateIHM(communeDisplay.finValidite)}</td>
						<td style="text-align: center">${communeDisplay.motifModification}</td>
						<td>
							<c:forEach items="${communeDisplay.parents}" var="genealogieParent">
								<a href="${communeDisplay.getUrlEntite(genealogieParent.key, genealogieParent.value.entity.finValidite)}">${genealogieParent.key}</a>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</table>
			<div style="text-align:right">
				<c:if test="${searchCommune.getPageMax() > 1}">
					<c:if test="${searchCommune.getPage() > 1}">
						<a href="/referentiel/commune/resultats?page=1">&lt;&lt;</a>
						<a href="/referentiel/commune/resultats?page=${searchCommune.getPage()-1}">&lt;</a>
					</c:if>
					<c:if test="${searchCommune.getPage() == 1}">
						<b>&lt;&lt;</b>
						<b>&lt;</b>
					</c:if>
					<c:forEach begin="1" end="${searchCommune.getPageMax()}" step="1" var="numPage">
						<c:if test="${(numPage > 2 && numPage == searchCommune.getPage() - 2) || (numPage == searchCommune.getPage() + 2 && numPage <= searchCommune.getPageMax() - 2)}">
							..
						</c:if>
						<c:if test="${numPage <= 2 
									|| numPage >= searchCommune.getPageMax() -1 
									|| (numPage >= searchCommune.getPage() - 1 
									&& numPage <= searchCommune.getPage())
									|| (numPage >= searchCommune.getPage() 
									&& numPage <= searchCommune.getPage() + 1)}">
							<c:if test="${numPage == searchCommune.getPage()}"><b>${numPage}</b></c:if>
							<c:if test="${numPage != searchCommune.getPage()}"><a href="/referentiel/commune/resultats?page=${numPage}">${numPage}</a></c:if>
						</c:if>
					</c:forEach>
					<c:if test="${searchCommune.getPage() < searchCommune.getPageMax()}">
						<a href="/referentiel/commune/resultats?page=${searchCommune.getPage()+1}">&gt;</a>
						<a href="/referentiel/commune/resultats?page=${searchCommune.getPageMax()}">&gt;&gt;</a>
					</c:if>
					<c:if test="${searchCommune.getPage() == searchCommune.getPageMax()}">
						<b>&gt;</b>
						<b>&gt;&gt;</b>
					</c:if>
				</c:if>
			</div>
			<form:form method="POST" id="formExcel" action="/referentiel/commune/export">
				<div id="retour">
					<a href="/referentiel/commune" onclick="">&lt;&lt; Retour</a>
				</div>
				<div class="action"><input type="submit" value="Exporter sous excel" onclick=""></input></div>
			</form:form>
		</td>
	</tr>
<jsp:include page="aesn_footer.jsp" />
