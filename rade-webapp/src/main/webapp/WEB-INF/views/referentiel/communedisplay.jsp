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
<jsp:include page="../aesn_header.jsp" />
<div class="row justify-content-center">
	<div class="col-12">
		<div class="card card-aesn">
			<div class="card-body card-body-aesn">
				<div class="row">
					<div class="col-6">
						<h5><spring:message code='communedisplay.details'/></h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<tr>
										<td class="w-33"><spring:message code='communedisplay.details.code'/> :</td>
										<td>${communeDisplay.codeInsee}</td>
									</tr>
									<c:if test="${communeDisplay.article != '' && communeDisplay.article != null}">
										<tr>
											<td><spring:message code='communedisplay.details.article'/> :</td>
											<td>${communeDisplay.article}</td>  
										</tr>
									</c:if>
									<c:if test="${communeDisplay.articleEnrichi != '' && communeDisplay.articleEnrichi != null}">
										<tr>
											<td><spring:message code='communedisplay.details.richarticle'/> :</td>
											<td>${communeDisplay.articleEnrichi}</td>
										</tr>
									</c:if>
									<tr>
										<td><spring:message code='communedisplay.details.name'/> :</td>
										<td>${communeDisplay.nomMajuscule}</td>
									</tr>
									<tr>
										<td><spring:message code='communedisplay.details.richname'/> :</td>
										<td>${communeDisplay.nomEnrichi}</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="col-6">
						<h5><spring:message code='communedisplay.geo'/></h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<tr>
										<td class="w-33"><spring:message code='communedisplay.geo.dept'/> :</td>
										<td>${communeDisplay.nomDepartement} (${communeDisplay.codeDepartement})</td>
									</tr>
									<tr>
										<td><spring:message code='communedisplay.geo.region'/> :</td>
										<td>${communeDisplay.nomRegion}</td>
									</tr>
									<c:if test="${communeDisplay.nomBassin != null}">
										<tr>
											<td><spring:message code='communedisplay.geo.bassin'/> :</td>
											<td>${communeDisplay.nomBassin} (${communeDisplay.codeBassin})</td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row mt-2">
					<div class="col-12">
						<h5><spring:message code='communedisplay.history'/></h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<tr>
										<td class="w-16"><spring:message code='communedisplay.history.start'/> :</td>
										<td>${DateConversionUtils.toUiString(communeDisplay.debutValidite)}</td>
									</tr>
									<c:if test="${communeDisplay.finValidite != null}">
										<tr>
											<td><spring:message code='communedisplay.history.end'/> :</td>
											<td>${DateConversionUtils.toUiString(communeDisplay.finValidite)}</td>
										</tr>
									</c:if>
									<c:if test="${communeDisplay.motifModification != null}">
										<tr>
											<td><spring:message code='communedisplay.history.reason'/> :</td>
											<td>${communeDisplay.motifModification}</td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row mt-2">
					<div class="col-6">
						<h5><spring:message code='communedisplay.parents'/></h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<c:if test="${communeDisplay.parents.isEmpty()}">
										<tr>
											<td><spring:message code='communedisplay.parents.none'/></td>
										</tr>
									</c:if>
									<c:forEach items="${communeDisplay.parents}" var="genealogieParent">
										<tr>
											<td class="w-16 text-center"><a href="${communeDisplay.getUrlEntite(pageContext.request.contextPath, genealogieParent.key, genealogieParent.value.finValidite)}">${genealogieParent.key}</a></td>
											<td>${genealogieParent.value.nom}</td>
											<td class="w-25">${DateConversionUtils.toUiString(genealogieParent.value.debutValidite)}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
					<div class="col-6">
						<h5><spring:message code='communedisplay.children'/></h5>
						<div class="card card-aesn">
							<div class="card-body card-body-aesn">
								<table class="w-100 h-100">
									<c:if test="${communeDisplay.enfants.isEmpty()}">
										<tr>
											<td><spring:message code='communedisplay.children.none'/></td>
										</tr>
									</c:if>
									<c:forEach items="${communeDisplay.enfants}" var="genealogieEnfant">
										<tr>
											<td class="w-16 text-center"><a href="${communeDisplay.getUrlEntite(pageContext.request.contextPath, genealogieEnfant.key, genealogieEnfant.value.finValidite)}">${genealogieEnfant.key}</a></td>
											<td>${genealogieEnfant.value.nom}</td>
											<td class="w-25">${DateConversionUtils.toUiString(genealogieEnfant.value.debutValidite)}</td>
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
				<a class="btn btn-sm btn-aesn" href="${pageContext.request.contextPath}/referentiel/commune/resultats">&lt;&lt; <spring:message code='communedisplay.button.back'/></a>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../aesn_footer.jsp" />
