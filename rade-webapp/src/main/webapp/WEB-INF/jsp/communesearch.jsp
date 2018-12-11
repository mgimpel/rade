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
    <script>        
        function validerForm(){
            // Code INSEE
            var nomEnrichi = document.getElementById("nomEnrichi").value;
            var codeRegion = document.getElementById("codeRegion").value;
            var codeDepartement = document.getElementById("codeDepartement").value;
            var circonscription = document.getElementById("codeCirconscription").value;
            var codeInsee = document.getElementById("codeInsee").value;
            if(codeInsee != "" && codeInsee != null){
                if(!/^[0-9]{5}$/.test(codeInsee)){
                    alert("Le code INSEE doit être composé de cinq chiffres");
                    return false;
                }
            }
            
            // Date
            var dateEffet = document.getElementById("dateEffet").value;
            if(dateEffet != "" && dateEffet != null){
                if(!/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/.test(dateEffet)){
                    alert("La date n'est pas valide");
                    return false;
                }
            }
            
            if(codeInsee == "" && nomEnrichi == "" && codeRegion == -1 && codeDepartement == -1 && circonscription == ""){
                alert("Au moins un des champs doit être renseigné");
                return false;
            }

            return true;
        }
    </script>
    <tr>
        <td colspan="2">
            <c:if test="${errorRecherche != null && !errorRecherche.equals('')}">
                <script>
                    window.onload = function(){
                        alert("${errorRecherche}");
                    }
                </script>
            </c:if>
            
            <form:form id="formCommune" method="POST" action="/referentiel/commune" modelAttribute="searchCommune">
                <table  class="prez" style="margin-left:auto;margin-right:auto;">
                    <tr>
                        <td style="text-align: right"><form:label path="codeInsee">Code INSEE :</form:label></td>
                        <td><form:input style="width:95px"  path="codeInsee"/></td>
                    </tr>

                    <tr>
                        <td style="text-align: right"><form:label path="nomEnrichi">Nom :</form:label></td>
                        <td><form:input  style="width:300px"  path="nomEnrichi"/></td>
                    </tr>

                    <tr>
                        <td style="text-align: right"><form:label path="codeRegion">Region :</form:label></td>
                        <td>
                            <form:select  style="width:300px"  path="codeRegion">
                                <form:option value="-1" label="Sélectionner une région..." />
                                <form:options  items="${listeRegions}"  />
                            </form:select>
                        </td>
                    </tr>

                     <tr>
                        <td style="text-align: right"><form:label path="codeDepartement">Département :</form:label></td>
                        <td>
                            <form:select style="width:300px" path="codeDepartement">
                                <form:option value="-1" label="Sélectionner un département..." />
                                <form:options  items="${listeDepartements}"  />
                            </form:select>
                        </td>
                    </tr>

                    <tr>
                        <td style="text-align: right"><form:label path="codeCirconscription">Circonscription bassin :</form:label></td>
                        <td>
                            <form:select  style="width:300px"  path="codeCirconscription">
                                <form:option value="-1" label="Sélectionner une conscription..." />
                                <form:options  items="${listeCirconscriptions}" itemLabel="libelleLong" itemValue="code"  />
                            </form:select>
                        </td>
                    </tr>

                    <tr>
                        <td style="text-align: right"><label for="dateEffet">Date d'effet : </label></td>
                        <td><form:input type="date" style="width:95px"  path="dateEffet"/></td>
                    </tr>
                </table>
                <div class="action"><input type="submit" name="submit" value="Rechercher" onclick="return validerForm();"></div>
            </form:form>
        </td>
    </tr> 

<jsp:include page="aesn_footer.jsp" />
