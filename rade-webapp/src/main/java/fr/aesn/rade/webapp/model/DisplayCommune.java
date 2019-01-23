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

import fr.aesn.rade.common.modelplus.CommunePlus;
import fr.aesn.rade.common.util.DateConversionUtils;
import fr.aesn.rade.common.util.StringConversionUtils;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
import fr.aesn.rade.service.CommuneService;
import fr.aesn.rade.service.DepartementService;
import fr.aesn.rade.service.RegionService;
import java.util.Date;
import java.util.HashMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
  @DateTimeFormat(pattern="dd/MM/yyyy")
  Date dateCreation, dateModification, debutValidite, finValidite;
  HashMap<GenealogieEntiteAdmin, String> genealogieParentCodeInsee;
  HashMap<GenealogieEntiteAdmin, String> genealogieEnfantCodeInsee;

  public DisplayCommune(CommunePlus communePlus, CommuneService communeService, DepartementService departementService, RegionService regionService){
      
    this.codeInsee = communePlus.getCodeInsee();     
    this.nomMajuscule = communePlus.getNomMajuscule();
    this.nomEnrichi = communePlus.getNomEnrichi();
    this.debutValidite = communePlus.getDebutValiditeCommuneInsee();
    this.finValidite = communePlus.getFinValiditeCommuneInsee();
    this.article = communePlus.getTypeNomClair().getArticle();
    this.articleEnrichi = communePlus.getArticleEnrichi();
    this.genealogieParentCodeInsee = new HashMap<>();
    this.genealogieEnfantCodeInsee = new HashMap<>();

    for(GenealogieEntiteAdmin genealogieParent : communePlus.getParentsCommuneInsee()){
      this.setMotifModification(genealogieParent.getTypeGenealogie().getLibelleLong());
      EntiteAdministrative entiteAdmin = genealogieParent.getParentEnfant().getParent();
      assert genealogieParent.getParentEnfant().getParent().getTypeEntiteAdmin().getCode().equals("COM");
      
      this.getGenealogieParentCodeInsee().put(genealogieParent, communeService.getCommuneById(entiteAdmin.getId()).getCodeInsee());
    }
    
    for(GenealogieEntiteAdmin genealogieEnfant : communePlus.getEnfantsCommuneInsee()){
      EntiteAdministrative entiteAdmin = genealogieEnfant.getParentEnfant().getParent();
      assert genealogieEnfant.getParentEnfant().getEnfant().getTypeEntiteAdmin().getCode().equals("COM");
      this.getGenealogieEnfantCodeInsee().put(genealogieEnfant, communeService.getCommuneById(entiteAdmin.getId()).getCodeInsee());
    }

    if(communePlus.getParentsCommuneInsee().size() > 0){
      GenealogieEntiteAdmin parent = communePlus.getParentsCommuneInsee().iterator().next();
      this.setMotifModification(parent.getTypeGenealogie().getLibelleLong());
      this.setCommentaireModification(parent.getCommentaire());
    }

    if(communePlus.getCirconscriptionBassin() != null){
      this.setNomBassin(communePlus.getCirconscriptionBassin().getLibelleLong());
      this.setDateCreation(communePlus.getDateCreationCommuneSandre());
      this.setDateModification(communePlus.getDateMajCommuneSandre());
      this.setCodeBassin(communePlus.getCirconscriptionBassin().getCode());
    }

    Departement departement = departementService.getDepartementByCode(communePlus.getDepartement(), communePlus.getDebutValiditeCommuneInsee());
    this.setNomDepartement(departement.getNomEnrichi());
    this.setCodeDepartement(departement.getCodeInsee());
    this.setNomRegion(regionService.getRegionByCode(departement.getRegion(), communePlus.getDebutValiditeCommuneInsee()).getNomEnrichi());
  }
  
  /**
   * @param finValidite 
   * @return date
   */
  public String getDateEffet(Date finValidite){
    Date date = new Date();

    if(finValidite != null){
      date.setTime(finValidite.getTime() - 86400000);
    }
    return DateConversionUtils.formatDateToStringUrl(date);
  }

  /**
   * Renvoie la date formatée au format dd/MM/yyyy
   * @param date 
   * @return date formatée
   */
  public String formatDate(Date date){
    if(date != null){
      return DateConversionUtils.formatDateToStringIHM(date);
    }
    return null;
  }

  /**
   * Renvoie l'url correspondant à l'entité
   * @param entite 
   * @return L'url permettant d'afficher l'entité
   */
  public String entiteUrl(EntiteAdministrative entite, String codeInsee){
    if(entite != null){
      return "/referentiel/commune/" + codeInsee + "?date=" + getDateEffet(entite.getFinValidite());
    }
    return null;
  }
}
