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
<style>
    .demi{
        float: left;
        width: 49%;
        margin-right: 1%;
        clear: none;
    }
    
    .pave{
        clear: left;
    }
    
    .cadre{
        border: 1px solid #0365AB;
        background-color: #E0EDF6;
        overflow: auto;
        height: 1%;
        padding: 13px;
    }
</style>
    <div class="cadre">
        <div class="demi pave">
            <fieldset>
                <legend>Attributs commune</legend>
                <table style="height: 105px;">
                    <tr>
                        <td width="38%"><label>Code INSEE : </label></td>
                        <td>${displayCommune.codeInsee}</td>  
                    </tr>
                    <c:if test="${displayCommune.article != '' && displayCommune.article != null}">
                        <tr>
                            <td><label>Article : </label></td>
                            <td>${displayCommune.article}</td>  
                        </tr>
                    </c:if>
                        
                    <c:if test="${displayCommune.articleEnrichi != '' && displayCommune.articleEnrichi != null}">  
                        <tr>
                            <td><label>Article enrichi : </label></td>
                            <td>${displayCommune.articleEnrichi}</td>  
                        </tr>
                    </c:if>
                        
                    <tr>
                        <td><label>Nom commune : </label></td>
                        <td>${displayCommune.nomMajuscule}</td>  
                    </tr>

                    <tr>
                        <td><label>Nom commune enrichi : </label></td>
                        <td>${displayCommune.nomEnrichi}</td>  
                    </tr>
                </table>
            </fieldset>
        </div>
        
        <div class="demi pave" style="clear: none;">
            <fieldset>
                <legend>Rattachement commune</legend>
                <table>
                    <tr>
                        <td  width="38%"><label>Département : </label></td>
                        <td>${displayCommune.nomDepartement} (${displayCommune.codeDepartement})</td>  
                    </tr>

                    <tr>
                        <td><label>Région : </label></td>
                        <td>${displayCommune.nomRegion}</td>  
                    </tr>
                    
                    <c:if test="${displayCommune.nomBassin != null}">
                        <tr>
                            <td><label>Bassin : </label></td>
                            <td>${displayCommune.nomBassin} <span style="font-size: 10px">(${displayCommune.codeBassin})</span></td>  
                        </tr>
                    </c:if>
                </table>
            </fieldset>
        </div>

        <div class="pave">
            <fieldset>
                <legend>Données historisation</legend>
                <table style="height: 105px;">
                    <tr>
                        <td  width="18%"><label>Début validité : </label></td>
                        <td>${displayCommune.debutValidite}</td>  
                        <c:if test="${displayCommune.finValidite != null}">
                            <td width="18%"><label>Fin validité : </label><td>
                            <td>${displayCommune.finValidite}</td>  
                        </c:if>
                    </tr>

                    <c:if test="${displayCommune.motifModification != '' && displayCommune.motifModification != null}">
                        <tr>
                            <td><label>Motif modification : </label></td>
                            <td>${displayCommune.motifModification}</td>  
                        </tr>
                    </c:if>
                    
                    <c:if test="${displayCommune.dateModification != '' && displayCommune.dateModification != null}">
                        <tr>
                            <td><label>Date modification : </label></td>
                            <td>${displayCommune.dateModification}</td>  
                        </tr>
                    </c:if>
                    
                    <c:if test="${displayCommune.dateCreation != '' && displayCommune.dateCreation != null}">
                        <tr>
                            <td><label>Date de création : </label></td>
                            <td>${displayCommune.dateCreation}</td>  
                        </tr>
                    </c:if>
                        
                    <c:if test="${displayCommune.commentaireModification != '' && displayCommune.commentaireModification != null}">
                        <tr>
                            <td><label>Commentaire : </label></td>
                            <td>${displayCommune.commentaireModification}</td>  
                        </tr>
                    </c:if>
                </table>
            </fieldset>
        </div>
                
                
        <div class="demi pave" style=font-size: 10px">
            <fieldset>
                <legend>Entités mères</legend>
                <table>
                    <tr>
                        <td>
                            <c:if test="${displayCommune.parents != null && displayCommune.parents.size() == 0}">
                                Aucune entité mère
                            </c:if>
                            <c:forEach items="${displayCommune.parents}" var="parent">
                                <div><a href="${displayCommune.findPage(parent.parentEnfant.parent)}">${displayCommune.findCodeInsee(parent.parentEnfant.parent)} - ${parent.parentEnfant.parent.nomEnrichi} (${parent.parentEnfant.parent.debutValidite}) ${parent.typeGenealogie.libelleLong}</a></div>
                            </c:forEach>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>   
                        
        <div class="demi pave" style="clear: none;font-size: 10px">
            <fieldset>
                <legend>Entités filles</legend>
                
                <table>
                    <tr>
                        <td> 
                            <c:if test="${displayCommune.enfants != null && displayCommune.enfants.size() == 0}">
                                Aucune entité fille
                            </c:if>
                            <c:forEach items="${displayCommune.enfants}" var="enfant">
                                <div><a href="${displayCommune.findPage(enfant.parentEnfant.enfant)}">${displayCommune.findCodeInsee(enfant.parentEnfant.enfant)} - ${enfant.parentEnfant.enfant.nomEnrichi} (${enfant.parentEnfant.enfant.debutValidite}) ${enfant.typeGenealogie.libelleLong}</a></div>
                            </c:forEach>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </div>
<jsp:include page="aesn_footer.jsp" />
