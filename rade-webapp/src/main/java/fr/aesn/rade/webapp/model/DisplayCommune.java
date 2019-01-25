/*
 * Copyright (C) 2018 sophie.belin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.aesn.rade.webapp.model;

import fr.aesn.rade.common.modelplus.CommunePlusWithGenealogie;
import fr.aesn.rade.common.util.DateConversionUtils;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.Region;
import java.util.Date;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Affichage d'une commune
 * @author sophie.belin
 */
@Getter @Setter @NoArgsConstructor
public class DisplayCommune {
  String codeInsee;
  String motifModification;
  String nomEnrichi;
  String commentaireModification;
  String article;
  String articleEnrichi;
  String codeBassin;
  String nomBassin;
  String codeDepartement;
  String nomDepartement;
  String nomMajuscule;
  String nomRegion;
  Date debutValidite, finValidite;
  Map<String, CommunePlusWithGenealogie.GenealogieTypeAndEntity> parents;
  Map<String, CommunePlusWithGenealogie.GenealogieTypeAndEntity> enfants;

  public DisplayCommune(CommunePlusWithGenealogie communePlusWithGenealogie, Departement departement, Region region){
    
    this.codeInsee = communePlusWithGenealogie.getCommunePlus().getCodeInsee();     
    this.nomMajuscule = communePlusWithGenealogie.getCommunePlus().getNomMajuscule();
    this.nomEnrichi = communePlusWithGenealogie.getCommunePlus().getNomEnrichi();
    this.debutValidite = communePlusWithGenealogie.getCommunePlus().getDebutValiditeCommuneInsee();
    this.finValidite = communePlusWithGenealogie.getCommunePlus().getFinValiditeCommuneInsee();
    this.article = communePlusWithGenealogie.getCommunePlus().getTypeNomClair().getArticle();
    this.articleEnrichi = communePlusWithGenealogie.getCommunePlus().getArticleEnrichi();
    this.parents = communePlusWithGenealogie.getParents();
    this.enfants = communePlusWithGenealogie.getEnfants();

    this.setNomDepartement(departement.getNomEnrichi());
    this.setCodeDepartement(departement.getCodeInsee());
    this.setNomRegion(region.getNomEnrichi());
    
    if (!parents.isEmpty()) {
      this.motifModification = parents.values().iterator().next().getType().getLibelleLong();
    }
    
    if(communePlusWithGenealogie.getCommunePlus().getCirconscriptionBassin() != null){
      this.setNomBassin(communePlusWithGenealogie.getCommunePlus().getCirconscriptionBassin().getLibelleLong());
      this.setCodeBassin(communePlusWithGenealogie.getCommunePlus().getCirconscriptionBassin().getCode());
    }
  }

  /**
   * Renvoie la date formatée au format dd/MM/yyyy
   * @param date 
   * @return date formatée
   */
  public String getDateIHM(Date date){
    return DateConversionUtils.formatDateToStringIHM(date);
  }
  
  public String getDateUrl(Date date){
    return DateConversionUtils.formatDateToStringUrl(date);
  }

  /**
   * Renvoie l'url correspondant à l'entité
   * @param codeInsee 
   * @param dateFinValidite 
   * @return L'url permettant d'afficher l'entité
   */
  public String getUrlEntite(String codeInsee, Date dateFinValidite){
    if(codeInsee != null){
      String urlParams = dateFinValidite != null ? getDateUrl(new Date(dateFinValidite.getTime() - 1)) : getDateUrl(new Date());
      return "/referentiel/commune/" + codeInsee + "?date=" + urlParams;
    }
    return null;
  }
}
