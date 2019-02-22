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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Model for the Commune Search form and subsequent results.
 * @author sophie.belin
 */
@Getter @Setter
public class SearchCommune {
  public static final int PAGE_SIZE = 10;
  // Champs requête
  private String codeInsee;
  private String nomEnrichi;
  private String codeRegion;
  private String codeDepartement;
  private String codeCirconscription;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date dateEffet;
  // Résultats
  private List<CommunePlusWithGenealogie> communes;
  private int page;

  /**
   * Constructor.
   */
  public SearchCommune() {
    reset();
  }

  /**
   * Resets the search criteria and subsequent results.
   */
  public void reset() {
    reset(new Date());
  }

  /**
   * Resets the search criteria and subsequent results.
   */
  public void reset(final Date date) {
    codeInsee = null;
    codeDepartement = "-1";
    codeRegion = "-1";
    codeCirconscription = "-1";
    nomEnrichi = null;
    dateEffet = date;
    page = 1;
    communes = null;
  }

  /**
   * Set the page number.
   * @param numPage page number.
   */
  public void setPage(final int numPage) {
    if(numPage <= getPageMax()) {
      page = numPage;
    }
  }

  /**
   * Returns the index of the first item on the current page.
   * @return the index of the first item on the current page.
   */
  private int getFirstCommuneIndex() {
    if(communes == null || communes.isEmpty()) {
      return 1;
    }
    int index = (page - 1) * PAGE_SIZE;
    assert index <= communes.size();
    return index;
  }

  /**
   * Returns the index of the last item on the current page.
   * @return the index of the last item on the current page.
   */
  private int getLastCommuneIndex() {
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
   * Returns the number of pages needed to display all the results.
   * @return the number of pages needed to display all the results.
   */
  public int getPageMax() {
    if(communes == null || communes.isEmpty()) {
      return 1;
    }
    return ((communes.size() - 1) / PAGE_SIZE) + 1;
  }

  /**
   * Build and return the result list for the current page number.
   * @return the result list for the current page number.
   */
  public List<DisplayCommune> getListeResultats() {
    List<DisplayCommune> list = new ArrayList<>();
    int firstCommuneIndex = getFirstCommuneIndex();
    int lastCommuneIndex = getLastCommuneIndex();
    for(int i = firstCommuneIndex; i < lastCommuneIndex; i++) {
      CommunePlusWithGenealogie commune = communes.get(i);
      list.add(new DisplayCommune(commune));
    }
    return list;
  }
}
