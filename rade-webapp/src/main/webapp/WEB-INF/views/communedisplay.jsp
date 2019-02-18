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
<jsp:include page="aesn_header.jsp" />
<div class="row justify-content-center">
	<div class="col-12">
		<div class="card card-aesn">
			<div class="card-body card-body-aesn">
				<div class="row">
					<div class="col-6">
						<h5>Attributs commune</h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<tr>
										<td class="w-33">Code INSEE :</td>
										<td>${displayCommune.codeInsee}</td>
									</tr>
									<c:if test="${displayCommune.article != '' && displayCommune.article != null}">
										<tr>
											<td>Article :</td>
											<td>${displayCommune.article}</td>  
										</tr>
									</c:if>
									<c:if test="${displayCommune.articleEnrichi != '' && displayCommune.articleEnrichi != null}">
										<tr>
											<td>Article enrichi :</td>
											<td>${displayCommune.articleEnrichi}</td>
										</tr>
									</c:if>
									<tr>
										<td>Nom commune :</td>
										<td>${displayCommune.nomMajuscule}</td>
									</tr>
									<tr>
										<td>Nom commune enrichi :</td>
										<td>${displayCommune.nomEnrichi}</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="col-6">
						<h5>Rattachement commune</h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<tr>
										<td class="w-33">Département :</td>
										<td>${displayCommune.nomDepartement} (${displayCommune.codeDepartement})</td>
									</tr>
									<tr>
										<td>Région :</td>
										<td>${displayCommune.nomRegion}</td>
									</tr>
									<c:if test="${displayCommune.nomBassin != null}">
										<tr>
											<td>Bassin :</td>
											<td>${displayCommune.nomBassin} (${displayCommune.codeBassin})</td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row mt-2">
					<div class="col-12">
						<h5>Données historisation INSEE</h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<tr>
										<td class="w-16">Début validité :</td>
										<td>${displayCommune.getDateIHM(displayCommune.debutValidite)}</td>
									</tr>
									<c:if test="${displayCommune.finValidite != null}">
										<tr>
											<td>Fin validité :</td>
											<td>${displayCommune.getDateIHM(displayCommune.finValidite)}</td>
										</tr>
									</c:if>
									<c:if test="${displayCommune.motifModification != null}">
										<tr>
											<td>Motif de modification :</td>
											<td>${displayCommune.motifModification}</td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row mt-2">
					<div class="col-6">
						<h5>Entités mères</h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<c:if test="${displayCommune.parents.isEmpty()}">
										<tr>
											<td>Aucune entités mères</td>
										</tr>
									</c:if>
									<c:forEach items="${displayCommune.parents}" var="genealogieParent">
										<tr>
											<td class="w-16 text-center"><a href="${displayCommune.getUrlEntite(genealogieParent.key, genealogieParent.value.entity.finValidite)}">${genealogieParent.key}</a></td>
											<td>${genealogieParent.value.entity.nomEnrichi}</td>
											<td class="w-25">${displayCommune.getDateIHM(genealogieParent.value.entity.debutValidite)}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
					<div class="col-6">
						<h5>Entités filles</h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<c:if test="${displayCommune.enfants.isEmpty()}">
										<tr>
											<td>Aucune entités filles</td>
										</tr>
									</c:if>
									<c:forEach items="${displayCommune.enfants}" var="genealogieEnfant">
										<tr>
											<td class="w-16 text-center"><a href="${displayCommune.getUrlEntite(genealogieEnfant.key, genealogieEnfant.value.entity.finValidite)}">${genealogieEnfant.key}</a></td>
											<td>${genealogieEnfant.value.entity.nomEnrichi}</td>
											<td class="w-25">${displayCommune.getDateIHM(genealogieEnfant.value.entity.debutValidite)}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row mt-2">
			<div class="col-auto">
				<a class="btn btn-sm btn-aesn" href="${pageContext.request.contextPath}/referentiel/commune/resultats">&lt;&lt; Retour</a>
			</div>
		</div>
	</div>
</div>
<jsp:include page="aesn_footer.jsp" />
