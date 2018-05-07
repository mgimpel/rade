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
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
	<meta http-equiv="Content-Language" content="fr">
	<title>RADE AESN - ${titre}</title>
	<link rel="icon" href="<%=request.getContextPath()%>/img/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/img/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/aesn-tiers-styles.css" type="text/css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/aesn-tiers-coul_06.css" type="text/css">
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
	function goMenuGauche(act, menu)
	{
		document.myForm.BAF_ACTION_DEM.value = act;
		if(isNaN(act) && act.indexOf('HAB') != -1)
			document.myForm.action = "/refTiers/HAB_routeur";
		document.myForm.submit();
		SUBMITTED = true;
	}
	function goMenuHaut(act, menu)
	{
		document.myForm.HAB_BAF_ACTION_DEM.value = act;
		document.myForm.BAF_ACTION_DEM.value = act;
		document.myForm.submit();
		SUBMITTED = true;
	}
	</script>
</head>
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
							<option value="http://nanselx2:8080/Redevances/RE04E40_Emission_recherche_certificat_titreAction.do?methode=init">Recherche certificats / titres</option>
							<option value="http://nanselx4:8280/ComptaProgramme/rechCompteProgrammeAction02.do?actions=affRechercherComptesProgrammes">C2P Consultation</option>
							<option value="http://nanselx4:8880/Dequado/Dq01e23rechercheAnalysesAction.do?actions=init">Recherche d'analyses</option>
							<option value="http://nanselx4:8280/ComptaProgramme/rechCompteAction07.do?actions=affEcranRecherche">C2P Plan Comptable</option>
							<option value="http://gs58slli113:80/HabilitationsExtranet/menu.do?actions=Recherche_agent">Recherche d'un extranaute</option>
							<option value="http://nanselx2:8080/Redevances/RE02E01_Interro_RNPU_rechercheAction.do?methode=init&amp;codeMenuAppel=CU_RECHERCHE_RNPU">Recherche de RN_PU</option>
							<option value="http://nanselx4:8080/Aides/menu.do?actions=init&amp;code=recherche_dossiers_aides">Aides recherche dossiers</option>
							<option value="http://nanselx4:8880/Dequado/Dq02e01ReseauInfosAction.do?actions=init">Visualiser les informations d'un réseau</option>
							<option value="http://nanselx4:8580/SitouRef/Ov02e01rechercheSitouAction.do?actions=init">Recherche d'un Sitou</option>
							<option value="http://nanselx4:8880/Dequado/Dq01e18RechercheDepotAction.do?actions=init">Recherche de dépôts</option>
							<option value="http://nanselx2:8080/Redevances/RE01E01_GestionSRPS_rechercheAction.do?methode=init">Gestion SRP</option>
						</select>
						<input class="btn_ok" onclick="javascript:accederRapidement();" value="OK" type="button">
					</form>
				</div>
				<h1>Rade</h1>
			</div>
			<div id="chemin_nav">Vous êtes ici : &nbsp;${titre}&nbsp;</div>
			<ul id="nav">
				<li><a href='javascript:goMenuGauche("3",%20"rechTiers")'>Tiers</a>
					<ul>
						<li><a href='javascript:goMenuGauche("3",%20"rechTiers")'>Recherche</a></li>
						<li><a href='javascript:goMenuGauche("26",%20"create")'>Création</a></li>
						<li><a href='javascript:goMenuGauche("10500",%20"createAssiste")'>Création assistée</a></li>
						<li><a href='javascript:goMenuGauche("200",%20"validTiers")'>Validation d'un tiers juridique</a></li>
					</ul>
				</li>
			</ul>
		</td>
	</tr>
	<tr>
		<td id="nom_log"><sec:authorize access="isAuthenticated()"><sec:authentication property="principal.username" /><br><a href='<%=request.getContextPath()%>/logout'>Se déconnecter</a></sec:authorize>
						 <sec:authorize access="!isAuthenticated()"><br><a href='<%=request.getContextPath()%>/login'>Se connecter</a></sec:authorize></td>
		<td id="titre_page">
			<div id="picto">
				<a href="#" style="display:none;" id="boutonImp"><img src="<%=request.getContextPath()%>/img/picto_print.gif" alt="Imprimer" title="Imprimer" height="25" width="25"></a>
				<a href="/aide.jsp" target="_blank" id="boutonAide"><img src="<%=request.getContextPath()%>/img/picto_aide.gif" alt="Aide" title="Afficher l'aide" style="margin-right: 0px" height="25" width="25"></a>
			</div>
			<h2>${titre}</h2>
		</td>
	</tr>
	<tr>
		<td colspan="2">
