/*  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2018 Sophie Belin
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
 */
/* $Id$ */
package fr.aesn.rade.webapp.model;

import fr.aesn.rade.common.modelplus.CommunePlus;
import fr.aesn.rade.common.modelplus.CommunePlusWithGenealogie;
import fr.aesn.rade.common.util.DateConversionUtils;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.Region;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Affichage d'une commune.
 * @author sophie.belin
 */
@Getter @NoArgsConstructor
public class DisplayCommune {
  private String codeInsee;
  private String motifModification;
  private String nomEnrichi;
  private String commentaireModification;
  private String article;
  private String articleEnrichi;
  private String codeBassin;
  private String nomBassin;
  private String codeDepartement;
  private String nomDepartement;
  private String nomMajuscule;
  private String nomRegion;
  private Date debutValidite, finValidite;
  private Map<String, CommunePlusWithGenealogie.GenealogieTypeAndEntity> parents;
  private Map<String, CommunePlusWithGenealogie.GenealogieTypeAndEntity> enfants;

  /**
   * Constructor.
   * @param communePlusWithGenealogie Commune details.
   */
  public DisplayCommune(CommunePlusWithGenealogie communePlusWithGenealogie) {
    CommunePlus commune = communePlusWithGenealogie.getCommunePlus();
    this.codeInsee = commune.getCodeInsee();
    this.nomMajuscule = commune.getNomMajuscule();
    this.nomEnrichi = commune.getNomEnrichi();
    this.debutValidite = commune.getDebutValiditeCommuneInsee();
    this.finValidite = commune.getFinValiditeCommuneInsee();
    this.article = commune.getTypeNomClair().getArticle();
    this.articleEnrichi = commune.getArticleEnrichi();
    this.parents = communePlusWithGenealogie.getParents();
    this.enfants = communePlusWithGenealogie.getEnfants();
    Map<String, CommunePlusWithGenealogie.GenealogieTypeAndEntity> genealogie = null;
    if(!parents.isEmpty()) {
      genealogie = communePlusWithGenealogie.getParents();
    } else if(!enfants.isEmpty()) {
      genealogie = communePlusWithGenealogie.getEnfants();
    }
    if (genealogie != null) {
      Iterator<Map.Entry<String, CommunePlusWithGenealogie.GenealogieTypeAndEntity>> it = genealogie.entrySet().iterator();
      if(it.hasNext()){
        this.motifModification = ((CommunePlusWithGenealogie.GenealogieTypeAndEntity)it.next().getValue()).getType().getLibelleLong();
      }
    }
    if(communePlusWithGenealogie.getCommunePlus().getCirconscriptionBassin() != null){
      this.nomBassin = communePlusWithGenealogie.getCommunePlus().getCirconscriptionBassin().getLibelleLong();
      this.codeBassin = communePlusWithGenealogie.getCommunePlus().getCirconscriptionBassin().getCode();
    }
  }

  /**
   * Constructor.
   * @param communePlusWithGenealogie Commune details.
   * @param departement Departement details.
   * @param region Region details.
   */
  public DisplayCommune(CommunePlusWithGenealogie communePlusWithGenealogie, Departement departement, Region region){
    this(communePlusWithGenealogie);
    this.nomDepartement = departement.getNomEnrichi();
    this.codeDepartement = departement.getCodeInsee();
    this.nomRegion = region.getNomEnrichi();
  }

  /**
   * Renvoie la date comme String au format IHM: dd/MM/yyyy
   * @param date la date à formatter.
   * @return date formatée.
   */
  public String getDateIHM(Date date){
    return DateConversionUtils.formatDateToStringUi(date);
  }

  /**
   * Renvoie la date comme String au format URL: yyyy-MM-dd
   * @param date la date à formatter.
   * @return date formatée.
   */
  public String getDateUrl(Date date){
    return DateConversionUtils.formatDateToStringUrl(date);
  }

  /**
   * Renvoie l'url correspondant à l'entité.
   * @param codeInsee 
   * @param dateFinValidite 
   * @return L'url permettant d'afficher l'entité
   */
  public String getUrlEntite(String codeInsee, Date dateFinValidite){
    if(codeInsee == null) {
      return null;
    }
    Date date;
    if(dateFinValidite == null){
      date = new Date();
    }else{
      date = new Date(dateFinValidite.getTime() - 1);
    }
    return "/referentiel/commune/" + codeInsee + "?date=" + DateConversionUtils.formatDateToStringUrl(date);
  }
}
