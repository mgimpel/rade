<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Language" content="fr">
	<title>RADE AESN - ${titre}</title>
	<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/aesn-styles.css" type="text/css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/aesn-coul_06.css" type="text/css">
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
<table id="contenant">
	<tbody><tr>
		<td width="171"><a href="#"><img src="<%=request.getContextPath()%>/img/logo.png" alt="Eau Seine Normandie" height="90" width="171"></a></td>
		<td id="header">
			<div class="filet_bas"><img src="<%=request.getContextPath()%>/img/bando_coul.png" height="10" width="736"></div> 
			<div id="nom_appli">   
				<div id="acces_rap">
					<form id="f_acces_rap" action="#" method="post">
						<select name="sel_acces_rapide" id="sel_acces_rapide">
							<option value="" selected>Accès rapide</option>
<spring:eval var="headermenu" expression="@headerMenu"/>
<c:forEach var="menuitem" items="${headermenu}">
							<option value="${menuitem.value}">${menuitem.key}</option>
</c:forEach>
						</select>
						<input class="btn_ok" onclick="javascript:accederRapidement();" value="OK" type="button">
					</form>
				</div>
				<h1>Rade</h1>
			</div>
			<div id="chemin_nav">Vous êtes ici : &nbsp;${titre}&nbsp;</div>
			<ul id="nav">
				<li><a href='<%=request.getContextPath()%>/'>Référentiel</a>
					<ul>
						<li><a href='<%=request.getContextPath()%>/referentiel/region'>Recherche Région</a></li>
						<li><a href='<%=request.getContextPath()%>/referentiel/departement'>Recherche Département</a></li>
						<li><a href='<%=request.getContextPath()%>/referentiel/commune'>Recherche Commune</a></li>
						<li><a href='<%=request.getContextPath()%>/referentiel/bassin'>Recherche Bassin</a></li>
						<li><a href='<%=request.getContextPath()%>/referentiel/delegation'>Recherche Délégation</a></li>
					</ul>
				</li>
				<li><a href='<%=request.getContextPath()%>/'>Historique</a>
					<ul>
						<li><a href='<%=request.getContextPath()%>/audit/search'>Recherche Audit</a></li>
					</ul>
				</li>
				<sec:authorize access="hasAuthority('RAD_ADMIN')">
				<li><a href='<%=request.getContextPath()%>/'>Administration</a>
					<ul>
						<li><a href='<%=request.getContextPath()%>/actuator/info'>Info Rade</a></li>
						<li><a href='<%=request.getContextPath()%>/actuator/health'>Etat Rade</a></li>
						<li><a href='<%=request.getContextPath()%>/actuator/logfile'>Log Rade</a></li>
						<li><a href='<%=request.getContextPath()%>/services/'>Services</a></li>
					</ul>
				</li>
				</sec:authorize>
			</ul>
		</td>
	</tr>
	<tr>
		<td id="nom_log">
			<sec:authorize access="isAuthenticated()"><sec:authentication property="principal.username" /><br><a href='<%=request.getContextPath()%>/logout'>Se déconnecter</a></sec:authorize>
			<sec:authorize access="!isAuthenticated()"><br><a href='<%=request.getContextPath()%>/login'>Se connecter</a></sec:authorize>
		</td>
		<td id="titre_page">
			<div id="picto">
				<a href="/aide.jsp" target="_blank" id="boutonAide"><img src="<%=request.getContextPath()%>/img/picto_aide.gif" alt="Aide" title="Afficher l'aide" style="margin-right: 0px" height="25" width="25"></a>
			</div>
			<h2>${titre}</h2>
		</td>
	</tr>
	<tr>
		<td colspan="2">
