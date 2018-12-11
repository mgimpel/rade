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
           
            <table class="prez" style="margin-left:auto;margin-right:auto;">
                <tr>
                    <c:if test="${searchCommune.codeInsee != '' && searchCommune.codeInsee != null}">
                        <td style="text-align: right; width: 100px"><strong>Code INSEE :</strong></td>
                        <td>${searchCommune.codeInsee}</td>
                    </c:if>
                    <c:if test="${searchCommune.nomEnrichi != '' && searchCommune.nomEnrichi != null}">
                        <td style="text-align: right; width: 100px"><strong>Nom : </strong></td>
                        <td>${searchCommune.nomEnrichi}</td>
                    </c:if>
                    <c:if test="${searchCommune.codeRegion != -1 && searchCommune.codeRegion != null}">
                        <td style="text-align: right;width: 100px"><strong>Région : </strong></td>
                        <td>${searchCommune.codeRegion}</td>
                    </c:if>
                    <c:if test="${searchCommune.codeDepartement != -1 && searchCommune.codeDepartement != null}">
                        <td style="text-align: right;width: 100px"><strong>Département : </strong></td>
                        <td>${searchCommune.codeDepartement}</td>
                    </c:if>
                    <c:if test="${searchCommune.codeCirconscription != -1 && searchCommune.codeCirconscription != null}">
                        <td style="text-align: right;width: 100px"><strong>Circonscription : </strong></td>
                        <td>${searchCommune.codeCirconscription}</td>
                    </c:if>
                    <c:if test="${searchCommune.dateEffet != '' && searchCommune.dateEffet != null}">
                        <td style="text-align: right;width: 100px"><strong>Date d'effet : </strong></td>
                        <td>${searchCommune.formatDate(searchCommune.dateEffet)}</td>
                    </c:if>
                </tr>
            </table>
        </td>
    </tr>
    
    <tr>
        <td colspan="2">
            <table class="tableauResultat" style=" border-collapse: collapse; width: 100%">
                <tr style="background-color: #0365ab; color: white;padding: 5px 0; margin: -1px; text-align: center; vertical-align: middle;">
                    <th>Code</th>
                    <th>Nom</th>
                    <th>Début validité</th>
                    <th>Fin validité</th>
                    <th>Entité mère</th>
                </tr>
                <c:forEach items="${searchCommune.listeResultats}" var="communeDisplay">
                   <tr>
                       <td><a href="/referentiel/commune/${communeDisplay.codeInsee}/${communeDisplay.debutValidite}">${communeDisplay.codeInsee}</a></td>
                       <td><a href="/referentiel/commune/${communeDisplay.codeInsee}/${communeDisplay.debutValidite}">${communeDisplay.nomEnrichi}</a></td>
                       <td>${communeDisplay.debutValidite}</td>
                       <td>${communeDisplay.finValidite}</td>
                       <td>
                           <table style="border: none">
                                <c:forEach items="${communeDisplay.parents}" var="parent">
                                    <tr>
                                        <td style="border: 0px;">
                                            <a href="${communeDisplay.findPage(parent.parentEnfant.parent)}">${communeDisplay.findCodeInsee(parent.parentEnfant.parent)}</a>
                                        </td>
                                        <td style="border: 0px;">
                                            ${parent.typeGenealogie.libelleLong}
                                        </td>
                                    </tr>
                                 </c:forEach>
                           </table>
                       </td>
                   </tr>
               </c:forEach>
            </table>
            <form:form method="POST" id="formExcel" action="/referentiel/commune/export">
                <div id="retour">
                    <a href="/referentiel/commune" onclick="">&lt;&lt; Retour</a>
                </div>
                <div class="action"><input type="submit" value="Exporter sous excel" onclick=""></input></div>
            </form:form>
        </td>
    </tr>
<jsp:include page="aesn_footer.jsp" />
