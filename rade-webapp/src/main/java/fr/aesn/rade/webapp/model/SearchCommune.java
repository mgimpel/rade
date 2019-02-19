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

import fr.aesn.rade.common.modelplus.CommunePlusWithGenealogie;
import fr.aesn.rade.common.util.DateConversionUtils;
import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.Region;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Formulaire de recherche d'une commune
 * @author sophie.belin
 */
@Slf4j
@Getter @Setter
public class SearchCommune {
  public static final int PAGE_SIZE = 10;
  private String codeRegion;
  private String codeDepartement;
  private String codeCirconscription;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date dateEffet;
  private String codeInsee;
  private String nomEnrichi;
  private int page;
  private List<Departement> departements;
  private Map<String,String> departementsByCodeInsee;
  private Map<String,String> regionsByCodeInsee;
  private Map<String, String> circonscriptionByCode;
  private List<DisplayCommune> listeResultats;
  private List<CommunePlusWithGenealogie> communes;

  /**
   * Constructor.
   */
  public SearchCommune() {
    reset();
  }

  /**
   * Resets the search criteria.
   */
  public void reset() {
    codeInsee = null;
    codeDepartement = "-1";
    codeRegion = "-1";
    codeCirconscription = "-1";
    nomEnrichi = null;
    dateEffet = new Date();
    page = 1;
  }

  /**
   * Renvoie la date au format dd/MM/yyyy 
   * ou un département
   * @param date 
   * @return Date
   */
  public String getDateIHM(Date date) {
    return DateConversionUtils.formatDateToStringUi(date);
  }

  /**
   * Set the page number.
   * @param stringPage page number.
   */
  public void setPage(String stringPage) {
    try {
      int numPage = Integer.parseInt(stringPage);
      if(numPage <= getPageMax()) {
        page = numPage;
      }
    } catch(NumberFormatException e) {
      log.info("Cannot set page: Invalid parameter {}", stringPage);
    }
  }

  /**
   * Renvoie l'index de la première commune
   * @return Index 
   */
  public int getFirstCommuneIndex() {
    if(communes == null || communes.isEmpty()) {
      return 1;
    }
    int index = (page - 1) * PAGE_SIZE;
    assert index <= communes.size();
    return index;
  }

  /**
   * Renvoie l'index de la dernière commune
   * @return Index
   */
  public int getLastCommuneIndex() {
    if(communes == null || communes.isEmpty()) {
      return 1;
    }
    int index = page * PAGE_SIZE;
    if(index > communes.size()) {
      index = communes.size();
    }
    return index;
  }

  /**
   * Renvoie le nombre de pages au total pour afficher l'ensemble des comunes
   * ou un département
   * @return Nombre de page
   */
  public int getPageMax() {
    if(communes != null && !communes.isEmpty()) {
      return pagesNeeded(PAGE_SIZE, communes.size());
    } else {
      return 1;
    }
  }

  /**
   * Return the number of pages needed to display the given number of items and
   * the size of the page.
   * @param pageSize maximum number of items per page.
   * @param itemCount number of items.
   * @return the number of pages needed.
   */
  private int pagesNeeded(int pageSize, int itemCount) {
    if (itemCount <= 0 || pageSize <= 0) {
      return 0;
    }
    return ((itemCount - 1) / pageSize) + 1;
  }

  /**
   * Sets the Regions map from the given Regions list.
   * @param regions Regions list.
   */
  public void setRegionsByCodeInsee(List<Region> regions) {
    regionsByCodeInsee = new HashMap<>();
    if(regions != null) {
      for(Region r : regions) {
        if(!regionsByCodeInsee.containsKey(r.getCodeInsee())){
          regionsByCodeInsee.put(r.getCodeInsee(), r.getNomEnrichi() );
        }
      }
      regionsByCodeInsee = sortByValue(regionsByCodeInsee);
    }
  }

  /**
   * Sets the Departements map from the given Departements list.
   * @param departements Departements list.
   */
  public void setDepartementsByCodeInsee(List<Departement> departements) {
    departementsByCodeInsee = new HashMap<>();
    if(departements != null) {
      for(Departement r : departements) {
        if(!departementsByCodeInsee.containsKey(r.getCodeInsee())) {
          departementsByCodeInsee.put(r.getCodeInsee(), r.getNomEnrichi() );
        }
      }
      this.departementsByCodeInsee = sortByValue(departementsByCodeInsee);
      this.departements = departements;
    }
  }

  /**
   * Sets the Bassins map from the given Bassins list.
   * @param bassins Bassins list.
   */
  public void setCirconscriptionByCode(List<CirconscriptionBassin> bassins) {
    circonscriptionByCode = new HashMap<>();
    if(bassins != null){
      for(CirconscriptionBassin c : bassins) {
        if(!circonscriptionByCode.containsKey(c.getCode())) {
          circonscriptionByCode.put(c.getCode(), c.getLibelleLong());
        }
      }
      this.circonscriptionByCode = sortByValue(circonscriptionByCode);
    }
  }

  /**
   * Static method for sorting the given map.
   * @param map
   * @return Sorted map.
   */
  private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
    list.sort(Map.Entry.comparingByValue());
    Map<K, V> result = new LinkedHashMap<>();
    for (Map.Entry<K, V> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }
    return result;
  }
}
