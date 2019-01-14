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

import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
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

  /**
   * @param finValidite 
   * @return date
   */
  public String getDateEffet(Date finValidite){
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    if(finValidite != null){
      date.setTime(finValidite.getTime() - 86400000);
    }
    return sdf.format(date);
  }

  /**
   * Renvoie la date formatée au format dd/MM/yyyy
   * @param date 
   * @return date formatée
   */
  public String formatDate(Date date){
    if(date != null){
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      return sdf.format(date);
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
      String typeCommune = null;
      switch(entite.getTypeEntiteAdmin().getCode()){
      case "COM":
        typeCommune = "commune";
        break;
      case "REG":
        typeCommune = "departement";
        break;
      case "DEP":
        typeCommune = "region";
      }

      StringBuilder sb = new StringBuilder();
      sb.append("/referentiel/");
      sb.append(typeCommune);
      sb.append("/");
      sb.append(codeInsee);
      sb.append("/");
      sb.append(getDateEffet(entite.getFinValidite()));
      return sb.toString();
    }
    return null;
  }
}
