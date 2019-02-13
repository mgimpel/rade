<!doctype html>
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
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:eval var="appname" expression="@applicationName"/>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>${appname} - ${titre}</title>
	<link rel="icon" href="${path}/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" href="${path}/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" href="${path}/webjars/bootstrap/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="${path}/css/aesn.css" type="text/css">
	<script type="text/javascript">
	function accederRapidement() {
		var select = document.getElementById('sel_acces_rapide');
		var options = select.childNodes;
		for (var i=0; i<options.length; i++) {
			if(options[i].selected) { 
				var url = options[i].value;
				if (url != '')
					window.open(url);
			}
		}
	}
	</script>
</head>
<body>
	<div class="container-fluid aesn-page">
		<!-- Header -->
		<div class="row justify-content-center">
			<!-- Logo Col -->
			<div class="col-lg-2 aesn-logo">
				<div class="row">
					<div class="col-auto">
						<img src="${path}/img/LogoAESN.png" alt="AESN">
					</div>
				</div>
				<div class="row mt-1">
					<div class="col-auto">
						<sec:authorize access="isAuthenticated()"><b><sec:authentication property="principal.username" /></b><br><a href='${path}/logout'>Se déconnecter</a></sec:authorize>
						<sec:authorize access="!isAuthenticated()"><br><a href='${path}/login'>Se connecter</a></sec:authorize>
					</div>
				</div>
			</div>
			<div class="col-lg-10">
				<div class="row">
					<img src="${path}/img/ColorBand.png">
				</div>
				<!-- App Name Row -->
				<div class="row aesn-gradband">
					<div class="col-auto">
						<h1>${appname}</h1>
					</div>
					<div class="col-auto ml-auto">
						<form class="form-inline" action="#" method="post">
							<select class="form-control" name="sel_acces_rapide" id="sel_acces_rapide">
								<option value="" selected>Accès rapide</option>
<spring:eval var="headermenu" expression="@headerMenu"/>
<c:forEach var="menuitem" items="${headermenu}">
								<option value="${menuitem.value}">${menuitem.key}</option>
</c:forEach>
							</select>
							<button class="form-control" type="button" onclick="javascript:accederRapidement();">OK</button>
						</form>
					</div>
				</div>
				<!-- Breadcrumb Row -->
				<div class="row aesn-breadcrumb">
					<div class="col-auto">
						<p>Vous êtes ici:</p>
					</div>
					<div class="col-auto">
						<nav aria-label="breadcrumb aesn-breadcrumb-nav">
							<ol class="breadcrumb">
								<!--li class="breadcrumb-item"><a href="#">Home</a></li-->
								<li class="breadcrumb-item active" aria-current="page">${titre}</li>
							</ol>
						</nav>
					</div>
					<div class="col-auto ml-auto">
						<form:form class="form-inline" action="${path}/referentiel/entiteSearch" method="post" modelAttribute="entite">
							<form:input class="form-control" path="code" placeholder="code" maxlength="5"/>
							<form:select class="form-control" path="type" disabled="true">
								<form:option value="region">Région</form:option>
								<form:option value="dept">Département</form:option>
								<form:option value="commune" selected="selected">Commune</form:option>
								<form:option value="bassin">Bassin</form:option>
								<form:option value="delegation">Délégation</form:option>
							</form:select>
							<button class="form-control form-control-sm mr-3" type="submit">Rechercher</button>
						</form:form>
					</div>
				</div>
				<!-- Navigation Bar Row -->
				<div class="row">
					<div class="col-12 pl-0 pr-0">
						<nav class="navbar navbar-expand-lg navbar-light navbar-aesn">
							<ul class="navbar-nav">
								<li class="nav-item dropdown active">
									<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										Référentiel
									</a>
									<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
										<a class="dropdown-item" href="${path}/referentiel/region">Recherche Région</a>
										<a class="dropdown-item" href="${path}/referentiel/departement">Recherche Département</a>
										<a class="dropdown-item" href="${path}/referentiel/commune">Recherche Commune</a>
										<a class="dropdown-item" href="${path}/referentiel/bassin">Recherche Bassin</a>
										<a class="dropdown-item" href="${path}/referentiel/delegation">Recherche Délégation</a>
									</div>
								</li>
								<li class="nav-item dropdown active">
									<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										Historique
									</a>
									<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
										<a class="dropdown-item" href="${path}/audit/search">Recherche Audit</a>
									</div>
								</li>
								<sec:authorize access="hasAuthority('RAD_ADMIN')">
								<li class="nav-item dropdown active">
									<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										Administration
									</a>
									<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
										<a class="dropdown-item" href="${path}/actuator/info">Info Rade</a>
										<a class="dropdown-item" href="${path}/actuator/health">Etat Rade</a>
										<a class="dropdown-item" href="${path}/actuator/logfile">Log Rade</a>
										<a class="dropdown-item" href="${path}/services">Services</a>
									</div>
								</li>
								</sec:authorize>
							</ul>
						</nav>
					</div>
				</div>
				<!-- Page Title Row -->
				<div class="row mt-2">
					<div class="col-auto">
						<h2>${titre}</h2>
					</div>
					<div class="col-auto ml-auto">
						<a href="${path}/aide.jsp" target="_blank" id="boutonAide"><img src="${path}/img/picto_aide.gif" alt="Aide" title="Afficher l'aide" style="margin-right: 0px" width="25" height="25"></a>
					</div>
				</div>
			</div>
		</div>
		<!-- Body -->
