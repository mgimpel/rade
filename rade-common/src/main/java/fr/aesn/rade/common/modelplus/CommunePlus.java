/*  This file is part of the Rade project (https://github.com/mgimpel/rade).
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
 */
/* $Id$ */
package fr.aesn.rade.common.modelplus;

import java.util.Date;

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.common.util.SharedBusinessRules;
import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.CommuneSandre;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
import fr.aesn.rade.persist.model.TypeNomClair;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Enhanced Commune which regroups the details about the Commune from different
 * sources, in particular INSEE (code, libelle, filiation, ...), Sandre
 * (bassin, ...), ...
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@ToString @EqualsAndHashCode
public class CommunePlus {
  /** Code INSEE of the Commune. */
  private String code;
  /** The effective date. */
  private Date dateEffective;
  /** The INSEE Commune Entity. */
  private Commune insee;
  /** The Sandre Commune Entity. */
  private CommuneSandre sandre;

  /**
   * Empty Constructor.
   */
  public CommunePlus() {
    // Empty Constructor
  }

  /**
   * Basic constructor.
   * @param code Code INSEE of the Commune.
   * @param date
   */
  public CommunePlus(String code, Date date) {
    setCode(code);
    setDateEffective(date);
  }

  /**
   * Sets Code INSEE de la Commune.
   * @param code Code INSEE de la Commune.
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Sets the date at which all the details of the Commune are valid.
   * @param date the date at which all the details of the Commune are valid.
   */
  public void setDateEffective(Date date) {
    this.dateEffective = date;
  }

  /**
   * Sets the Commune (INSEE) Object.
   * @param insee the Commune (INSEE) Object.
   * @throws IllegalArgumentException if the given Commune is not Valid at the dateEffective.
   */
  public void setCommuneInsee(Commune insee)
    throws InvalidArgumentException {
    if (insee == null 
        || !SharedBusinessRules.isBetween(insee.getDebutValidite(),
                                          insee.getFinValidite(),
                                          dateEffective)) {
      throw new InvalidArgumentException("The given INSEE Commune is not valid for the effective date of this CommunePlus: " + insee);
    }
    this.insee = insee;
  }

  /**
   * Sets the CommuneSandre Object.
   * @param sandre the CommuneSandre Object.
   * @throws IllegalArgumentException
   */
  public void setCommuneSandre(CommuneSandre sandre)
    throws InvalidArgumentException {
    if (sandre == null 
        || !SharedBusinessRules.isBetween(sandre.getDateCreationCommune(),
                                          null,
                                          dateEffective)) {
      throw new InvalidArgumentException("The given Sandre Commune is not valid for the effective date of this CommunePlus: " + sandre);
    }
    this.sandre = sandre;
  }

  /**
   * Gets Code INSEE de la Commune.
   * @return Code INSEE de la Commune.
   */
  public String getCodeInsee() {
    return code;
  }

  /**
   * Gets Département auquel appartient la Commune.
   * @return Département auquel appartient la Commune.
   */
  public String getDepartement() {
    if (insee == null) {
      return null;
    }
    return insee.getDepartement();
  }

  /**
   * Gets Bassin auquel appartient la Commune.
   * @return Bassin auquel appartient la Commune.
   */
  public CirconscriptionBassin getCirconscriptionBassin() {
    if (sandre == null) {
      return null;
    }
    return sandre.getCirconscriptionBassin();
  }

  /**
   * Gets Article (enrichie) de l'entité.
   * @return Article (enrichie) de l'entité.
   */
  public String getArticleEnrichi() {
    if (insee == null) {
      return null;
    }
    return insee.getArticleEnrichi();
  }

  /**
   * Gets Nom (en majuscule) de l'entité.
   * @return Nom (en majuscule) de l'entité.
   */
  public String getNomMajuscule() {
    if (insee == null) {
      return null;
    }
    return insee.getNomMajuscule();
  }

  /**
   * Gets Nom (enrichie) de l'entité.
   * @return Nom (enrichie) de l'entité.
   */
  public String getNomEnrichi() {
    if (insee == null) {
      return null;
    }
    return insee.getNomEnrichi();
  }

  /**
   * Gets Type de Nom Clair (TNCC) de l'entité.
   * @return Type de Nom Clair (TNCC) de l'entité.
   */
  public TypeNomClair getTypeNomClair() {
    if (insee == null) {
      return null;
    }
    return insee.getTypeNomClair();
  }
  
  public Date getDebutValiditeCommuneInsee(){
    if(insee == null){
      return null;
    }
    return insee.getDebutValidite();
    
  }
  
  public Date getFinValiditeCommuneInsee(){
    if(insee == null){
      return null;
    }
    return insee.getFinValidite();
  }
  
  public Date getDateCreationCommuneSandre(){
    if(sandre == null){
      return null; 
    }
    return sandre.getDateCreationCommune();
  }
  
  public Date getDateMajCommuneSandre(){
    if (insee == null) {
      return null;
    }
    return sandre.getDateMajCommune();
  }
  
  public Set<GenealogieEntiteAdmin> getParentsCommuneInsee(){
    if (insee == null) {
      return null;
    }
    return insee.getParents();
  }
  
  public Set<GenealogieEntiteAdmin> getEnfantsCommuneInsee(){
    if (insee == null) {
      return null;
    }
    return insee.getEnfants();
  }

  public Set<GenealogieEntiteAdmin> getParentsInsee(){
    if (insee == null) {
      return null;
    }
    return insee.getParents();
  }

  public Set<GenealogieEntiteAdmin> getEnfantsInsee(){
    if (insee == null) {
      return null;
    }
    return insee.getEnfants();
  }
}
